 package kr.jay.reactorpattern;

import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * HttpEventHandler
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/07/04
 */

@Slf4j
public class HttpEventHandler implements EventHandler {

	private final ExecutorService executor = Executors.newFixedThreadPool(50);
	private final SocketChannel clientSocket;
	private final Selector selector;
	private final MsgCodec msgCodec;


	@SneakyThrows
	public HttpEventHandler(final SocketChannel clientSocket, final Selector selector) {
		this.clientSocket = clientSocket;
		this.selector = selector;
		this.clientSocket.configureBlocking(false);
		this.clientSocket.register(this.selector, SelectionKey.OP_READ)
			.attach(this);
		this.msgCodec = new MsgCodec();
	}

	@Override
	public void handle() {
		final String requestBody = handleRequest(this.clientSocket);
		log.info("requestBody : {}", requestBody);
		sendResponse(this.clientSocket, requestBody);
	}

	@SneakyThrows
	private String handleRequest(final SocketChannel client) {

		final ByteBuffer requestByteBuffer = ByteBuffer.allocateDirect(1024);
		client.read(requestByteBuffer);
		return msgCodec.decode(requestByteBuffer);

	}

	@SneakyThrows
	private void sendResponse(final SocketChannel client, final String requestBody) {
		CompletableFuture.runAsync(() -> {
			try {
				Thread.sleep(10);
				final ByteBuffer content = msgCodec.encode(requestBody);
				client.write(content);
				client.close();
			} catch (Exception e) {
			}
		}, executor);
	}

}
