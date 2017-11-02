package pku.netlab.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.socket.DatagramPacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pku.netlab.entity.Message;

import java.io.RandomAccessFile;


/**
 * @author lei
 *
 */
@ChannelHandler.Sharable
public class ServerInboundHandler extends ChannelInboundHandlerAdapter {
    private static Logger logger = LoggerFactory.getLogger(ServerInboundHandler.class);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.debug("SERVER: channel active!");
        super.channelActive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        logger.debug("SERVER: Channel read!");
        Message message = (Message) msg;
        logger.debug("{}:{}", message.getSender(), message.toString());
        RandomAccessFile raf = new RandomAccessFile("data/data.txt", "rw");
        raf.seek(0);
        int length = message.getEnd() - message.getStart();
        byte[] barr = new byte[length];
        raf.read(barr, 0, length);
        ByteBuf b = ctx.channel().alloc().buffer(length).writeBytes(barr);
        ctx.writeAndFlush(new DatagramPacket(b, message.getSender()));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
