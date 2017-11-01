package pku.netlab.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.EpollDatagramChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import pku.netlab.entity.codec.MessageEncoder;

import java.net.InetSocketAddress;

@Component
public class Client {
    private EventLoopGroup workers;
    private InetSocketAddress serverAddress;

    @Autowired
    public Client(@Value("${SERVER.HOST}") String serverHost, @Value("${SERVER.PORT}") int port) {
        serverAddress = new InetSocketAddress(serverHost, port);
    }
    public void run() {
        Bootstrap b = new Bootstrap();
        b.group(this.workers)
                .channel(EpollDatagramChannel.class)
                .handler(new ChannelInitializer<EpollDatagramChannel>() {
                    @Override
                    protected void initChannel(EpollDatagramChannel ch) throws Exception {
                        ChannelPipeline p = ch.pipeline();
                        p.addLast(new MessageEncoder(serverAddress));
                        p.addLast(new ClientInboundHandler(serverAddress));
                    }
                });
        try {
            b.bind(0).sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Autowired
    @Qualifier("clientWorkers")
    public void setWorkers(EventLoopGroup workers) {this.workers = workers;}
}
