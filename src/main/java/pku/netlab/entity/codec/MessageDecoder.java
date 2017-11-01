package pku.netlab.entity.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.MessageToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pku.netlab.entity.Message;

import java.util.List;

/**
 * @author lei
 * {@link Message} decoder
 */
@ChannelHandler.Sharable
public class MessageDecoder extends MessageToMessageDecoder<DatagramPacket> {
    private static Logger logger = LoggerFactory.getLogger(MessageDecoder.class);

    @Override
    protected void decode(ChannelHandlerContext ctx, DatagramPacket msg, List<Object> out) throws Exception {
        logger.debug("SERVER: decoding...");
        ByteBuf in = msg.content();
        if (in.readableBytes() < 8) {
            logger.warn("Bad message format!");
            return;
        }
        int start = in.readInt();
        int end = in.readInt();
        Message m = new Message(start, end, msg.sender());
        out.add(m);
    }
}
