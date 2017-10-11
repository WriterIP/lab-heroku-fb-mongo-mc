package ua.kpi.ip;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.net.URI;
import java.net.URISyntaxException;

@Configuration
@PropertySource("classpath:application.yml")
public class RedisConfig {
    @Value("${spring.data.redis.host}")
    String host;

    @Value("${spring.data.redis.port}")
    int port;

    @Value("${spring.data.redis.uri}")
    String uri;


      JedisPool jedisPool() {
          URI redisURI = null;
          try {
              redisURI = new URI(uri);
          } catch (URISyntaxException e) {
              throw new RuntimeException(e);
          }

          JedisPool pool = new JedisPool( redisURI);
        return pool;
    }

    @Bean
    JedisConnectionFactory jedisConnectionFactory() {

        JedisPoolConfig poolConfig = new JedisPoolConfig();

//        poolConfig.maxActive = 10;
//        poolConfig.maxIdle = 5;
//        poolConfig.minIdle = 1;
//        poolConfig.testOnBorrow = true;
//        poolConfig.testOnReturn = true;
//        poolConfig.testWhileIdle = true;
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(poolConfig);
        return jedisConnectionFactory;

    }

    @Bean
    public RedisTemplate<String, String> redisTemplate() {
        final RedisTemplate<String, String> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());
        //  template.setValueSerializer(new GenericToStringSerializer<Object>(Object.class));
        return template;
    }
}
