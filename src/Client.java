import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        SocketAddress address = new InetSocketAddress("127.0.0.1", 3005);
        SocketChannel socketChannel = SocketChannel.open(address);
        System.out.println("게임 서버에 연결되었습니다. \n name : ");
        String name = sc.next();
        HelperMethods.sendMessage(socketChannel, name);
        Thread sendMessage = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    String msg = sc.next();
                    if(msg.equals("quit")) {
                        System.exit(0);
                    }
                    HelperMethods.sendMessage(socketChannel, msg);

                }
            }
        });
        Thread readMessage = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    String msg = HelperMethods.receiveMessage(socketChannel);
                    if(msg.equals("exit")) {
                        System.exit(0);
                    }
                    System.out.println(msg);
                }
            }
        });
        readMessage.start();
        sendMessage.start();
    }
}
