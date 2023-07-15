package kr.jay.selector;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import reactor.netty.http.server.HttpServer;
import reactor.netty.http.server.HttpServerRoutes;

/**
 * JavaNIONonBlockingServer
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/07/03
 */
@Slf4j
public class SelectorMultiServer {
	private static ExecutorService executor = Executors.newFixedThreadPool(50);
	@SneakyThrows
	public static void main(String[] args) {
		log.info("start");
		try (final ServerSocketChannel serverSocket = ServerSocketChannel.open();
			 final Selector selector = Selector.open();
		) {
			serverSocket.bind(new InetSocketAddress("localhost", 8080));
			serverSocket.configureBlocking(false);
			serverSocket.register(selector, SelectionKey.OP_ACCEPT);

			while (true) {
				selector.select();
				final Iterator<SelectionKey> selectedKeys = selector.selectedKeys().iterator();

				while (selectedKeys.hasNext()) {
					final SelectionKey key = selectedKeys.next();
					selectedKeys.remove();

					if (key.isAcceptable()) {
						final SocketChannel clientSocket = ((ServerSocketChannel)key.channel()).accept();
						clientSocket.configureBlocking(false);
						clientSocket.register(selector, SelectionKey.OP_READ);
					} else if (key.isReadable()) {
						final SocketChannel clientSocket = (SocketChannel)key.channel();
						final String requestBody = handleRequest(clientSocket);
						sendResponse(clientSocket, requestBody);

					}
				}

			}
		}
	}

	@SneakyThrows
	private static String handleRequest(final SocketChannel client) {
		final ByteBuffer requestByteBuffer = ByteBuffer.allocateDirect(1024);
		client.read(requestByteBuffer);

		requestByteBuffer.flip();
		final String requestBody = StandardCharsets.UTF_8.decode(requestByteBuffer).toString();
		log.info("request: {}",requestBody);

		return requestBody.trim();

	}

	@SneakyThrows
	private static void sendResponse(final SocketChannel client, final String requestBody) {
		CompletableFuture.runAsync(() -> {
			try {
				Thread.sleep(10);

				final String content = "received : " + requestBody;
				final ByteBuffer responseByteBuffer = ByteBuffer.wrap(content.getBytes());
				client.write(responseByteBuffer);
				client.close();
			} catch (Exception e) {
			}
		},executor);

	}
}
