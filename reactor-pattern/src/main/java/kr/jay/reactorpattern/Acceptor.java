package kr.jay.reactorpattern;

import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * Acceptor
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/07/04
 */

@Slf4j
@RequiredArgsConstructor
public class Acceptor implements EventHandler {

	private final ServerSocketChannel serverSocket;
	private final Selector selector;

	@SneakyThrows
	@Override
	public void handle() {
		final SocketChannel clientSocket = serverSocket.accept();
		log.info("client : {} ", clientSocket);
		new HttpEventHandler(clientSocket, selector);
	}

}
