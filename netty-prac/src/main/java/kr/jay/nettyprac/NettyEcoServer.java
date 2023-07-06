package kr.jay.nettyprac;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.FutureListener;
import lombok.extern.slf4j.Slf4j;

/**
 * NettyEcoServer
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/07/06
 */
@Slf4j
public class NettyEcoServer {
	public static void main(String[] args) {
		final EventLoopGroup parentGroup = new NioEventLoopGroup(); // serverSocketChannel accept event
		final EventLoopGroup childGroup = new NioEventLoopGroup(4); // socketChannel read/write event
		final DefaultEventExecutorGroup eventExecutorGroup = new DefaultEventExecutorGroup(4);

		try{
			final ServerBootstrap serverBootstrap = new ServerBootstrap();
			final ServerBootstrap server = serverBootstrap
				.group(parentGroup, childGroup)
				.channel(NioServerSocketChannel.class)
				.childHandler(new ChannelInitializer<>() {
					@Override
					protected void initChannel(final Channel ch) throws Exception {
						ch.pipeline().addLast(
							eventExecutorGroup, new LoggingHandler(LogLevel.INFO)
						);
						ch.pipeline().addLast(
							new StringEncoder(),
							new StringDecoder(),
							new NettyEchoServerHandler()
						);
					}
				});
			server.bind(8080).sync()
				.addListener(new FutureListener<>(){

					@Override
					public void operationComplete(final Future<Void> future) throws Exception {
						if(future.isSuccess()){
							log.info("success to bind 8080");
						}else{
							log.error("fail to bind 8080");
						}
					}
				}).channel().closeFuture().sync();
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		} finally {
			parentGroup.shutdownGracefully();
			childGroup.shutdownGracefully();
		}

	}
}
