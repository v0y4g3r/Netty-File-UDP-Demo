package pku.netlab.entity.codec;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.MessageToMessageEncoder;
import pku.netlab.entity.Message;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * @author lei
 * Encode {@link Message} to {@link DatagramPacket}.
 * The byte stream of Message is simple, 4 bytes for start and 4 bytes for end
 */
@ChannelHandler.Sharable
public class MessageEncoder extends MessageToMessageEncoder<Message> {
    private InetSocketAddress remote;

    public MessageEncoder(InetSocketAddress remote) {this.remote = remote;}

    @Override
    protected void encode(ChannelHandlerContext ctx, Message msg, List<Object> out) throws Exception {
        DatagramPacket d = new DatagramPacket(ctx.alloc().buffer().writeInt(msg.getStart()).writeInt(msg.getEnd()), remote);
        out.add(d);
    }
}
