package kr.jay.selector;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * JavaNIONonBlockingServer
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/07/03
 */
@Slf4j
public class JavaNIONonBlockingServer {

	@SneakyThrows
	public static void main(String[] args) {
		log.info("start");
		try (final ServerSocketChannel server = ServerSocketChannel.open()) {
			server.bind(new InetSocketAddress("localhost", 8080));
			server.configureBlocking(false);

			while (true) {
				final SocketChannel client = server.accept();
				if(client == null) {
					Thread.sleep(100);
					continue;
				}
				log.info("connected");

				handleRequest(client);
			}
		}
	}

	@SneakyThrows
	private static void handleRequest(final SocketChannel client) {
		final ByteBuffer requestByteBuffer = ByteBuffer.allocateDirect(1024);
		while (client.read(requestByteBuffer) == 0){
			log.info("Reading...");
		}
		requestByteBuffer.flip();
		final String requestBody = StandardCharsets.UTF_8.decode(requestByteBuffer).toString();
		log.info("request: {}", new String(requestBody).trim());

		Thread.sleep(10 );

		final String response = "This is server";
		final ByteBuffer responseByteBuffer = ByteBuffer.wrap(response.getBytes());
		client.write(responseByteBuffer);
		client.close();
	}
}
