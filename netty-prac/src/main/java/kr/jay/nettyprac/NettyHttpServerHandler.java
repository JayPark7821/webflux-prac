package kr.jay.nettyprac;

import java.net.http.HttpHeaders;
import java.nio.charset.StandardCharsets;

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;

/**
 * NettyHttpServerHandler
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/07/07
 */
public class NettyHttpServerHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelRead(final ChannelHandlerContext ctx, final Object msg) throws Exception {
		if (msg instanceof FullHttpRequest) {
			final FullHttpRequest request = (FullHttpRequest)msg;
			final DefaultFullHttpResponse response = new DefaultFullHttpResponse(
				request.protocolVersion(), HttpResponseStatus.OK);

			  response.headers().set("Content-Type", "text/plain; charset=UTF-8");
			  response.content().writeCharSequence("Hello World", StandardCharsets.UTF_8);

			ctx.writeAndFlush(response)
				.addListener(ChannelFutureListener.CLOSE);
		}
	}
}
