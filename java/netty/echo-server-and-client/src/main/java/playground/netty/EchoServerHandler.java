package playground.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

@ChannelHandler.Sharable
public class EchoServerHandler extends ChannelInboundHandlerAdapter {

   @Override
   public void channelRead(ChannelHandlerContext ctx, Object msg) {
      ByteBuf in = (ByteBuf)msg;
      System.out.println("server received: " + in.toString(CharsetUtil.UTF_8));
      ctx.write(in);
   }

   @Override
   public void channelReadComplete(ChannelHandlerContext ctx) {
      ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
   }

   @Override public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
      cause.printStackTrace();
      ctx.close();
   }
}
