package pku.netlab.client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pku.netlab.entity.Message;

import java.net.InetSocketAddress;

/**
 * @author lei
 * When connected to server, send a {@link Message} to server and wait for reply(ACK).
 */
public class ClientInboundHandler extends ChannelInboundHandlerAdapter {
    private static Logger logger = LoggerFactory.getLogger(ClientInboundHandler.class);
    private InetSocketAddress recipient; //Every UDP channel should have a fixed recipient, thus it's not sharable

    public ClientInboundHandler(InetSocketAddress recipient) {this.recipient = recipient;}

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.debug("CLIENT:Channel active!");
        ctx.writeAndFlush(new Message(0, 20, recipient));
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object packet) throws Exception {
        ByteBuf msg = ((DatagramPacket) packet).content();
        logger.debug("CLIENT:read {}", msg.toString(CharsetUtil.UTF_8));
    }
}
