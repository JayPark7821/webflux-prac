package kr.jay.javanioserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * JavaIOClient
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/07/03
 */
@Slf4j
public class JavaIOClient {

	@SneakyThrows
	public static void main(String[] args) {
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

		}
	}
}
