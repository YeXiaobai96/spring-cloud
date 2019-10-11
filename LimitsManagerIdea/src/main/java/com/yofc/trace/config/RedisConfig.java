package com.yofc.trace.config;

import java.net.UnknownHostException;
import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * 
 * 标准redis配置
 *
 */
@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport {

	@Value("${spring.redis.host}")
	private String host;

	@Value("${spring.redis.port}")
	private int port;

	@Value("${spring.redis.timeout}")
	private int timeout;

	@Value("${spring.redis.database}")
	private int database;

	@Value("${spring.redis.jedis.pool.max-idle}")
	private int maxIdle;

	@Value("${spring.redis.jedis.pool.min-idle}")
	private int minIdle;

	@Value("${spring.redis.jedis.pool.max-wait}")
	private int maxWait;

	/**
	 * redis模板，存储关键字是字符串，值是Jdk序列化
	 * 
	 * @Description:
	 * @param factory
	 * @return
	 */
	@Bean
	public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory factory) throws UnknownHostException {
		RedisTemplate<Object, Object> template = new RedisTemplate<Object, Object>();
		template.setConnectionFactory(factory);
		// key值的序列化方式
		RedisSerializer<String> redisSerializer = new StringRedisSerializer();
		template.setKeySerializer(redisSerializer);
		template.setHashKeySerializer(redisSerializer);

		// Value序列化方式
		JdkSerializationRedisSerializer jdkRedisSerializer = new JdkSerializationRedisSerializer();
		template.setValueSerializer(jdkRedisSerializer);
		template.setHashValueSerializer(jdkRedisSerializer);
		template.afterPropertiesSet();

		return template;
	}

	/**
	 * 缓存管理
	 * 
	 * @param connectionFactory
	 * @return
	 */
	@Bean
	public CacheManager cacheManager(RedisConnectionFactory connectionFactory) {
		// 初始化一个RedisCacheWriter
		RedisCacheWriter cacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(connectionFactory);
		
		// RedisCacheConfiguration默认是使用StringRedisSerializer序列化key,JdkSerializationRedisSerializer序列化value
		RedisCacheConfiguration cacheConfig = RedisCacheConfiguration.defaultCacheConfig();
		
		// 设置默认超过期时间
		RedisCacheConfiguration duration = cacheConfig.entryTtl(Duration.ofHours(3));
		
		// 初始化RedisCacheManager
		RedisCacheManager cacheManager = new RedisCacheManager(cacheWriter, duration);
		
		return cacheManager;
	}
}
