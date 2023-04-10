import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class HelperMethods {
	public static void sendMessage(SocketChannel socketChannel, String message) {
		try {
			ByteBuffer buffer = ByteBuffer.allocate(message.length() + 1);
			buffer.put(message.getBytes(StandardCharsets.UTF_8));
			buffer.put((byte) 0x00);
			buffer.flip();
			while (buffer.hasRemaining()) {
				socketChannel.write(buffer);
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	public static String receiveMessage(SocketChannel socketChannel) {
		try {
			ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
			String message = "";
			if (socketChannel.read(byteBuffer) > 0) {
				byteBuffer.flip();
				Charset charset = Charset.defaultCharset();
				message = charset.decode(byteBuffer).toString();
			}
			return message;
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return "";
	}
}
