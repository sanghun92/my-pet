package com.shshon.mypet.config;

import com.shshon.mypet.util.CacheNames;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@EnableCaching
@Configuration
public class CacheConfig {

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private int port;

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(host);
        redisStandaloneConfiguration.setPort(port);
        return new LettuceConnectionFactory(redisStandaloneConfiguration);
    }

    @Bean
    public RedisTemplate<String, ?> redisTemplate() {
        RedisTemplate<String, ?> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        var jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);

        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
        return redisTemplate;
    }

    @Bean
    public RedisCacheManager redisCacheManager(RedisConnectionFactory redisConnectionFactory) {
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .disableCachingNullValues() // NULL 캐시 안함
                .serializeKeysWith(RedisSerializationContext
                        .SerializationPair
                        .fromSerializer(RedisSerializer.string()))
                .serializeValuesWith(RedisSerializationContext
                        .SerializationPair
                        .fromSerializer(RedisSerializer.json()));

        Map<String, RedisCacheConfiguration> cacheConfiguration = InitialCacheConfigurationBuilder.builder()
                .add(CacheNames.MEMBER, CacheNames.MEMBER_EXP_SEC)
                .build();

        return RedisCacheManager
                .RedisCacheManagerBuilder
                .fromConnectionFactory(redisConnectionFactory)
                .cacheDefaults(redisCacheConfiguration)
                .withInitialCacheConfigurations(cacheConfiguration)
                .build();
    }

    private static class InitialCacheConfigurationBuilder {

        private final Map<String, RedisCacheConfiguration> cacheConfiguration = new HashMap<>();

        private static InitialCacheConfigurationBuilder builder() {
            return new InitialCacheConfigurationBuilder();
        }

        private InitialCacheConfigurationBuilder add(String cacheName, long expireSeconds) {
            this.cacheConfiguration.put(
                    cacheName,
                    RedisCacheConfiguration.defaultCacheConfig()
                            .entryTtl(Duration.ofSeconds(expireSeconds))
            );
            return this;
        }

        private Map<String, RedisCacheConfiguration> build() {
            return this.cacheConfiguration;
        }
    }
}
