package com.ezee.identity.config;

import java.time.Duration;

import javax.cache.Caching;
import javax.cache.spi.CachingProvider;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ehcache.config.CacheConfiguration;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.jsr107.Eh107Configuration;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.jcache.JCacheCacheManager;
import org.springframework.cache.support.CompositeCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableCaching
public class CacheConfig {
	private static final Logger LOGGER = LogManager.getLogger("com.identity");

	@Bean
	public RedisCacheConfiguration redisCacheConfiguration() {
		RedisCacheConfiguration redisCacheConfiguration = null;

		try {
			redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(30))
					.serializeKeysWith(
							RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
					.serializeValuesWith(RedisSerializationContext.SerializationPair
							.fromSerializer(new GenericJackson2JsonRedisSerializer()))
					.disableCachingNullValues();

		} catch (Exception e) {
			LOGGER.info("Redis cache configuration failed ", e.getMessage());
		}

		return redisCacheConfiguration;
	}

	@Bean
	public CacheManager redisCacheManager(RedisConnectionFactory redisConnectionFactory) {
		RedisCacheManager redisCacheManager = null;

		try {
			redisCacheManager = RedisCacheManager.builder(redisConnectionFactory)
					.cacheDefaults(redisCacheConfiguration()).build();
		} catch (Exception e) {
			LOGGER.info("Redis CacheManager creation failed ", e.getMessage());
		}

		return redisCacheManager;
	}

	@Bean
	public CacheManager ehCacheManager() {
		javax.cache.CacheManager cacheManager = null;

		try {
			CacheConfiguration<Object, Object> cacheConfig = CacheConfigurationBuilder
					.newCacheConfigurationBuilder(Object.class, Object.class, ResourcePoolsBuilder.heap(100))
					.withExpiry(ExpiryPolicyBuilder.expiry().access(Duration.ofMinutes(20))
							.create(Duration.ofMinutes(30)).build())
					.build();

			CachingProvider cachingProvider = Caching.getCachingProvider();
			cacheManager = cachingProvider.getCacheManager();

			cacheManager.createCache("tokenEhCache", Eh107Configuration.fromEhcacheCacheConfiguration(cacheConfig));
			cacheManager.createCache("identityCache", Eh107Configuration.fromEhcacheCacheConfiguration(cacheConfig));
		} catch (Exception e) {
			LOGGER.info("Message", e.getMessage());
		}

		return new JCacheCacheManager(cacheManager);
	}

	@Primary
	@Bean
	public CacheManager compositeCacheManager(CacheManager ehCacheManager, CacheManager redisCacheManager) {
		CompositeCacheManager cacheManager = null;

		try {
			cacheManager = new CompositeCacheManager(ehCacheManager, redisCacheManager);
			cacheManager.setFallbackToNoOpCache(false);
		} catch (Exception e) {
			LOGGER.info("Composite cache Manager failed", e.getMessage());
		}

		return cacheManager;
	}
}
