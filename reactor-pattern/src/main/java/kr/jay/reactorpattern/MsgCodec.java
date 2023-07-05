package kr.jay.reactorpattern;

import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * MsgCodec
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/07/04
 */
public class MsgCodec {

	ByteBuffer encode(final String msg) {
		final String body = "<html><body>hello, " + msg + "</body></html>";
		final int contentLength = body.getBytes().length;

		final String httpResponse = "HTTP/1.1 200 OK\n" +
			"Content-Type: text/html; charset=UTF-8\n" +
			"Content-Length: " + contentLength + "\n\n" +
			"<html><body><h1>hello, " + msg + "</h1></body></html>";

		return StandardCharsets.UTF_8.encode(httpResponse);
	}

	/**
	 * GET / HTTP/1.1
	 * Host: localhost:8080
	 * Connection: Keep-Alive
	 * User-Agent: Apache-HttpClient/4.5.14 (Java/17.0.6)
	 * Accept-Encoding: br,deflate,gzip,x-gzip
	 */
	public String decode(final ByteBuffer buffer) {
		buffer.flip();
		var httpRequest = StandardCharsets.UTF_8.decode(buffer).toString().trim();
		var firstLine = httpRequest.split("\n")[0];
		var path = firstLine.split(" ")[1];
		URI uri = URI.create(path);

		var query = uri.getQuery() == null ? "" : uri.getQuery();

		// get name from uri by query string
		var queryMap = Arrays.stream(query.split("&"))
			.map(s -> s.split("="))
			.filter(s -> s.length == 2)
			.collect(Collectors.toMap(s -> s[0], s -> s[1]));

		return queryMap.getOrDefault("name", "World");
	}
}
