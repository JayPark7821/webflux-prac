package kr.jay.nettyprac;

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * NettyEchoServerHandler
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/07/06
 */

@Slf4j
public class NettyEchoServerHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelRead(final ChannelHandlerContext ctx, final Object msg) throws Exception {
		if(msg instanceof String){
			log.info("Received: {} ", msg);
			ctx.writeAndFlush(msg)
				.addListener(ChannelFutureListener.CLOSE);
		}
	}
}
