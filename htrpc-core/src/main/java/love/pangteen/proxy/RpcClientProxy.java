package love.pangteen.proxy;

import cn.hutool.core.lang.UUID;
import lombok.extern.slf4j.Slf4j;
import love.pangteen.config.ConfigManager;
import love.pangteen.config.RpcServiceConfig;
import love.pangteen.enums.RpcErrorMessage;
import love.pangteen.enums.RpcResult;
import love.pangteen.exception.RpcException;
import love.pangteen.remoting.dto.RpcRequest;
import love.pangteen.remoting.dto.RpcResponse;
import love.pangteen.remoting.transport.RpcRequestTransport;
import love.pangteen.remoting.transport.netty.client.NettyRpcClient;
import love.pangteen.remoting.transport.socket.SocketRpcClient;
import love.pangteen.utils.factory.ProxyFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * @program: HTRPC
 * @author: PangTeen
 * @create: 2024/5/31 9:31
 **/
@Slf4j
public class RpcClientProxy implements InvocationHandler {

    private static final String INTERFACE_NAME = "interfaceName";

    private volatile RpcRequestTransport transport;
    private final RpcServiceConfig serviceConfig;

    public RpcClientProxy() {
        this(new RpcServiceConfig());
    }

    public RpcClientProxy(RpcServiceConfig serviceConfig) {
        this.serviceConfig = serviceConfig;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        log.info("invoke method: [{}]", method.getName());
        boolean invokeSuccess = false;
        RpcResponse<Object> response = null;
        for(int i = 0; i < ConfigManager.getRetries(); ++ i){
            boolean trySuccess = true;
            RpcRequest request = RpcRequest.builder()
                    .requestId(UUID.randomUUID().toString())
                    .interfaceName(method.getDeclaringClass().getName())
                    .methodName(method.getName())
                    .parameters(args)
                    .paramTypes(method.getParameterTypes())
                    .version(serviceConfig.getVersion())
                    .group(serviceConfig.getGroup())
                    .build();
            try {
                switch (getTransport()){
                    case SocketRpcClient socketRpcClient -> {
                        response = (RpcResponse<Object>) socketRpcClient.sendRpcRequest(request);
                    }
                    case NettyRpcClient nettyRpcClient -> {
                        CompletableFuture<RpcResponse<Object>> completableFuture = (CompletableFuture<RpcResponse<Object>>) nettyRpcClient.sendRpcRequest(request);
                        response = completableFuture.get(ConfigManager.getTimeout(), TimeUnit.MILLISECONDS);
                    }
                    default -> throw new IllegalStateException("Unexpected value: " + getTransport());
                }
                check(request, response);
            } catch (Exception e){
                log.warn(e.getMessage());
                trySuccess = false;
            }
            if(trySuccess){
                invokeSuccess = true;
                break;
            } else {
                log.info("the {} try failed for {}", i + 1, method.getName());
            }
        }
        if(response == null || ! invokeSuccess){
            log.error("invoke remote method {} failed finally !", method.getName());
            throw new RpcException(RpcErrorMessage.RETRY_FAILED_FINALLY);
        }

        return response.getData();
    }

    public <T> T getProxy(Class<T> clazz) {
        return ProxyFactory.getProxy(clazz, this);
    }

    private static void check(RpcRequest rpcRequest, RpcResponse<Object> rpcResponse) {
        // Response为空。
        if (rpcResponse == null) {
            throw new RpcException(RpcErrorMessage.SERVICE_INVOCATION_FAILURE, INTERFACE_NAME + ":" + rpcRequest.getInterfaceName());
        }

        // RequestId不匹配。
        if (!rpcRequest.getRequestId().equals(rpcResponse.getRequestId())) {
            throw new RpcException(RpcErrorMessage.REQUEST_NOT_MATCH_RESPONSE, INTERFACE_NAME + ":" + rpcRequest.getInterfaceName());
        }

        // 返回Code不正确。
        if (rpcResponse.getCode() == null || !rpcResponse.getCode().equals(RpcResult.SUCCESS.getCode())) {
            throw new RpcException(RpcErrorMessage.SERVICE_INVOCATION_FAILURE, INTERFACE_NAME + ":" + rpcRequest.getInterfaceName());
        }
    }

    private RpcRequestTransport getTransport(){
        if(transport == null){
            synchronized (RpcClientProxy.class){
                if(transport == null){
                    transport = ConfigManager.getRpcRequestTransport();
                }
            }
        }
        return transport;
    }

}
