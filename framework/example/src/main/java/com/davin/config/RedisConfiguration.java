package com.davin.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.util.Strings;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.ClusterServersConfig;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URI;

/**
 * @author davin.bao
 * @date 2024/6/4
 */
@Configuration
@EnableCaching
public class RedisConfiguration {
    @Bean
    public RedissonClient getRedissonClient(Config redissConfig) {
        return Redisson.create(redissConfig);
    }


    @Bean
    public Config redissConfig(@Value("${spring.redis.host}") String host,
                               @Value("${spring.redis.port}") int port,
                               @Value("${spring.redis.database:0}") int database,
                               @Value("${spring.redis.password}") String password,
                               ObjectMapper objectMapper) {
        String address = String.format("redis://%s:%s", host, port);
        Config config = new Config();
        SingleServerConfig singleServerConfig = config
                .useSingleServer()
                .setAddress(host + ":" + port)
                .setDatabase(database)
                .setConnectionPoolSize(64)
                .setConnectionMinimumIdleSize(24)
                .setSubscriptionConnectionPoolSize(50)
                .setAddress(address);

        if (Strings.isNotEmpty(password)) {
            singleServerConfig.setPassword(password);
        }
        config.setCodec(new JsonJacksonCodec(objectMapper));
        return config;
    }
}
