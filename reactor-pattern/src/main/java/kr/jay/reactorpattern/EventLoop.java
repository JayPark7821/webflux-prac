package kr.jay.reactorpattern;

import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * Reactor
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/07/03
 */
@Slf4j
public class EventLoop implements Runnable {
	private static ExecutorService executor = Executors.newSingleThreadExecutor();
	private final ServerSocketChannel serverSocket;
	private final Selector selector;
	private final EventHandler acceptor;

	@SneakyThrows
	public EventLoop(final int port) {
		selector = Selector.open();
		serverSocket = ServerSocketChannel.open();
		serverSocket.bind(new InetSocketAddress("localhost", port));
		serverSocket.configureBlocking(false);

		acceptor = new Acceptor(serverSocket, selector);
		serverSocket.register(selector, SelectionKey.OP_ACCEPT)
			.attach(acceptor);
	}

	@Override
	public void run() {
		executor.submit(() -> {
			while (true) {
				selector.select();
				final Iterator<SelectionKey> selectedKeys = selector.selectedKeys().iterator();

				while (selectedKeys.hasNext()) {
					final SelectionKey key = selectedKeys.next();
					selectedKeys.remove();

					dispatch(key);
				}
			}
		});
	}

	private void dispatch(final SelectionKey key) {
		final EventHandler handler = (EventHandler) key.attachment();

		if (key.isReadable() || key.isAcceptable()) {
			handler.handle();
		}
	}


}
