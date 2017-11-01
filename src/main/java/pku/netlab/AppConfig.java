package pku.netlab;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.*;
import pku.netlab.client.Client;
import pku.netlab.server.Server;

@Configuration
@ComponentScan
@PropertySource("classpath:config.properties")
public class AppConfig {
    private static Logger logger = LoggerFactory.getLogger(AppConfig.class);

    @Bean(name = "serverWorkers")
    public EventLoopGroup serverWorkers() {return new EpollEventLoopGroup();}

    @Bean(name = "clientWorkers")
    public EventLoopGroup clientWorkers() {return new EpollEventLoopGroup();}

    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
        ctx.getBean(Server.class).run().addListener(f -> {
            if (!f.isSuccess())
                logger.debug("Staring client!");
            new Thread(ctx.getBean(Client.class)::run).start();
        });
        Runtime.getRuntime().addShutdownHook(new Thread(ctx::close));

    }
}
