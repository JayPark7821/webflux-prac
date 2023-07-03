package kr.jay.javanioserver;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * JavaIOMultiClient
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/07/03
 */
@Slf4j
public class JavaIOMultiClient {

	private static final ExecutorService executorService = Executors.newFixedThreadPool(50);
	@SneakyThrows
	public static void main(String[] args) {
		List<CompletableFuture<Void>> futures = new ArrayList<>();
		final long start = System.currentTimeMillis();
		for (int i = 0; i < 1000; i++) {
			final CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
				try (final Socket socket = new Socket()) {
					socket.connect(new InetSocketAddress("localhost", 8080));
					log.info("connected");

					final OutputStream out = socket.getOutputStream();
					final String requestBody = "This is client";
					out.write(requestBody.getBytes());
					out.flush();

					final InputStream in = socket.getInputStream();
					final byte[] responseBytes = new byte[1024];
					in.read(responseBytes);
					log.info("response: {}", new String(responseBytes).trim());
				}catch (Exception e){}
			},executorService);
			futures.add(future);
		}
		CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
		executorService.shutdown();

		log.info("end main");
		final long end = System.currentTimeMillis();
		log.info("time: {}", (end - start) / 1000.0);
	}
}
