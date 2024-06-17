package love.pangteen;

import love.pangteen.config.ConfigManager;
import love.pangteen.config.HTRpcConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;

/**
 * @program: HTRPC
 * @author: PangTeen
 * @create: 2024/6/17 15:32
 **/
@AutoConfigureAfter(HTRpcBeanRegister.class)
public class HTRpcBeanInject {

    @Autowired
    public void injectConfig(HTRpcConfig htRpcConfig){
        if(htRpcConfig != null) {
            ConfigManager.setConfig(htRpcConfig);
        }
    }

}
