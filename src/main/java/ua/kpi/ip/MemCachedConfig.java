package ua.kpi.ip;

import net.spy.memcached.AddrUtil;
import net.spy.memcached.ConnectionFactoryBuilder;
import net.spy.memcached.MemcachedClient;
import net.spy.memcached.auth.AuthDescriptor;
import net.spy.memcached.auth.PlainCallbackHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.io.IOException;

@Configuration
@PropertySource("classpath:application.yml")
public class MemCachedConfig {
    @Value("${spring.data.memcached.servers}")
    String host;

    @Value("${spring.data.memcached.username}")
    String username;

    @Value("${spring.data.memcached.password}")
    String password;

    @Bean
    MemcachedClient mc() {
        AuthDescriptor ad = new AuthDescriptor(new String[]{"PLAIN"},
                new PlainCallbackHandler(username, password));

        try {
            MemcachedClient mc = new MemcachedClient(
                    new ConnectionFactoryBuilder()
                            .setProtocol(ConnectionFactoryBuilder.Protocol.BINARY)
                            .setAuthDescriptor(ad).build(),
                    AddrUtil.getAddresses(host));
            return mc;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }
}
