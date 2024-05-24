package love.pangteen.socket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @program: HTRPC
 * @author: PangTeen
 * @create: 2024/5/24 15:26
 **/
public class HelloServer {

    public void start(int port){
        try(ServerSocket serverSocket = new ServerSocket(port)){
            System.out.println("服务器启动成功");
            System.out.println("服务器信息："+serverSocket.getLocalSocketAddress());
            Socket socket;
            while((socket = serverSocket.accept()) != null){
                System.out.println("客户端连接："+socket.getRemoteSocketAddress());
                try(ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream())
                ){
                    Message message = (Message) objectInputStream.readObject();
                    System.out.println("收到客户端消息："+message.getContent());
                    Message returnMessage = new Message();
                    returnMessage.setContent("Hello Client");
                    objectOutputStream.writeObject(returnMessage);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        HelloServer helloServer = new HelloServer();
        helloServer.start(8080);
    }
}
