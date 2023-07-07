package kr.jay.nettyprac;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * NettyEchoClientHandler
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/07/07
 */
@Slf4j
public class NettyEchoClientHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelActive(final ChannelHandlerContext ctx) throws Exception {
		ctx.writeAndFlush("This is client.");
	}

	@Override
	public void channelRead(final ChannelHandlerContext ctx, final Object msg) throws Exception {
		if (msg instanceof String) {
			log.info("Received: {} ", msg);
		}
	}
}
