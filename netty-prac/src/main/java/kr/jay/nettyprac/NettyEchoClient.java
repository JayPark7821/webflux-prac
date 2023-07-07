package kr.jay.nettyprac;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * NettyEchoClient
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/07/06
 */
@Slf4j
public class NettyEchoClient {

	@SneakyThrows
	public static void main(String[] args) {
		final EventLoopGroup workerGroup = new NioEventLoopGroup(1);
		try {
			final Bootstrap bootstrap = new Bootstrap();
			final Bootstrap client = bootstrap
				.group(workerGroup)
				.channel(NioSocketChannel.class)
				.handler(new ChannelInitializer<Channel>() { // socketChannel socketChannel이 connect되었을때 핸들링 하겠다!!
					@Override
					protected void initChannel(final Channel ch) throws Exception {
						ch.pipeline()
							.addLast(
								new LoggingHandler(LogLevel.INFO),
								new StringEncoder(),
								new StringDecoder(),
								new NettyEchoClientHandler()
							);
					}
				});
			client.connect("localhost", 8080).sync()
				.channel().closeFuture().sync();
		} finally {
			workerGroup.shutdownGracefully();
		}

	}
}
