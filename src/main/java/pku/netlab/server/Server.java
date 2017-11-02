package pku.netlab.server;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.epoll.EpollDatagramChannel;
import io.netty.util.concurrent.Future;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import pku.netlab.entity.codec.MessageDecoder;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
@PropertySource("classpath:config.properties")
public class Server {

    private static Logger logger = LoggerFactory.getLogger(Server.class);
    @Value("${SERVER.PORT}")
    private int port;
    private EventLoopGroup workers;
    private Bootstrap b;

    public Server(@Autowired @Qualifier("serverWorkers") EventLoopGroup workers) {
        this.workers = workers;
    }

    @PostConstruct
    public void init() {
        b = new Bootstrap();
        b.group(this.workers)
                .channel(EpollDatagramChannel.class)
                .option(ChannelOption.SO_REUSEADDR, true)
                .handler(new ChannelInitializer<Channel>() {
                    private MessageDecoder decoder = new MessageDecoder();
                    private ServerInboundHandler serverInboundHandler = new ServerInboundHandler();

                    @Override
                    protected void initChannel(Channel ch) throws Exception {
                        ChannelPipeline p = ch.pipeline();
                        p.addLast(decoder);
                        p.addLast(serverInboundHandler);
                    }
                });
    }

    public Future run() {
        try {
            return b.bind(this.port).sync();
        } catch (InterruptedException e) {
            logger.error("Server bind failed:", e);
            return this.workers.next().newFailedFuture(e);
        }
    }

    @PreDestroy
    public void shutdown() {
        try {
            this.workers.shutdownGracefully().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
