package playground.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

@ChannelHandler.Sharable
public class EchoClientHandler extends SimpleChannelInboundHandler<ByteBuf> {

   @Override public void channelActive(ChannelHandlerContext ctx) {
      ctx.writeAndFlush(Unpooled.copiedBuffer("something", CharsetUtil.UTF_8));
   }

   @Override protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) {
      System.out.println("client received: " + msg.toString(CharsetUtil.UTF_8));
   }

   @Override public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
      cause.printStackTrace();
      ctx.close();
   }
}
