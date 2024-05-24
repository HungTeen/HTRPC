package love.pangteen.socket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * @program: HTRPC
 * @author: PangTeen
 * @create: 2024/5/24 15:26
 **/
public class HelloClient {

    public Object send(Message message, String host, int port) {
        try (Socket socket = new Socket(host, port)) {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(message);
            objectOutputStream.flush();
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            return objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        HelloClient helloClient = new HelloClient();
        Message message = new Message();
        message.setContent("Hello Server");
        Message returnMessage = (Message) helloClient.send(message, "127.0.0.1", 8080);
        System.out.println("服务器返回消息：" + returnMessage.getContent());
    }
}
