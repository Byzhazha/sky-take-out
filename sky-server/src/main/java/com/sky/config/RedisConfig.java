package com.sky.config;

// 导入缺失的类
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        StringRedisSerializer stringSerializer = new StringRedisSerializer();



        // 1. 创建一个自定义的 ObjectMapper
        ObjectMapper objectMapper = new ObjectMapper();

        // 2. 注册 JavaTimeModule 模块以支持 LocalDateTime
        objectMapper.registerModule(new JavaTimeModule());

        // 3. 启用默认类型信息，以便在反序列化时能够正确转换对象类型
        // 这会解决从Redis取数据时可能发生的 ClassCastException
        objectMapper.activateDefaultTyping(
                objectMapper.getPolymorphicTypeValidator(),
                ObjectMapper.DefaultTyping.NON_FINAL
        );

        // 4. 使用配置好的 ObjectMapper 创建序列化器
        GenericJackson2JsonRedisSerializer jsonSerializer = new GenericJackson2JsonRedisSerializer(objectMapper);

        // ------------------- ↑↑↑ 主要修改区域 ↑↑↑ -------------------

        // Key 和 HashKey 都使用 String 序列化
        redisTemplate.setKeySerializer(stringSerializer);
        redisTemplate.setHashKeySerializer(stringSerializer);

        // Value 和 HashValue 都使用刚刚配置好的 JSON 序列化器
        redisTemplate.setValueSerializer(jsonSerializer);
        redisTemplate.setHashValueSerializer(jsonSerializer);

        redisTemplate.afterPropertiesSet();

        return redisTemplate;
    }
}