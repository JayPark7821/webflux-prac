package kr.jay.javanioserver;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * JavaIOServer
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/07/03
 */
@Slf4j
public class JavaIOServer {

	@SneakyThrows
	public static void main(String[] args) {
		log.info("start");
		try (final ServerSocket server = new ServerSocket()) {
			server.bind(new InetSocketAddress("localhost", 8080));

			while (true) {
				final Socket client = server.accept();
				log.info("connected");

				final byte[] requestByte = new byte[1024];
				final InputStream in = client.getInputStream();
				in.read(requestByte);
				log.info("request: {}", new String(requestByte).trim());

				final OutputStream out = client.getOutputStream();
				final String response = "This is server";
				out.write(response.getBytes());
				out.flush();
			}
		}
	}
}
