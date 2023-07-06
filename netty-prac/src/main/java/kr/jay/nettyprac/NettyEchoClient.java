package kr.jay.nettyprac;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;

/**
 * NettyEchoClient
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/07/06
 */
public class NettyEchoClient {

	public static void main(String[] args) {
		final EventLoopGroup workerGroup = new NioEventLoopGroup(1);

	}
}
