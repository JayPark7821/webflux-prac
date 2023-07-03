package kr.jay.javanioserver;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * JavaNIOBlockingServer
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/07/03
 */
@Slf4j
public class JavaNIOBlockingServer {

	@SneakyThrows
	public static void main(String[] args) {
		log.info("start");
		try (final ServerSocketChannel server = ServerSocketChannel.open()) {
			server.bind(new InetSocketAddress("localhost", 8080));


			while (true) {
				final SocketChannel client = server.accept();
				log.info("connected");

				final ByteBuffer requestByteBuffer = ByteBuffer.allocateDirect(1024);
				client.read(requestByteBuffer);
				requestByteBuffer.flip();
				final String requestBody = StandardCharsets.UTF_8.decode(requestByteBuffer).toString();
				log.info("request: {}", new String(requestBody).trim());

				final String response = "This is server";
				final ByteBuffer responseByteBuffer = ByteBuffer.wrap(response.getBytes());
				client.write(responseByteBuffer);
				client.close();
			}
		}
	}
}
