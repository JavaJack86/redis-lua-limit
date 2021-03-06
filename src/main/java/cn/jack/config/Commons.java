package cn.jack.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.scripting.support.ResourceScriptSource;

import java.io.Serializable;

/**
 * @author MysteryJack
 * 公众号: Java菜鸟程序员
 * @date 2021/3/5
 * @Description:配置lua信息以及RedisTemplate
 */
@Configuration
public class Commons {

    /**
     * 读取令牌桶限流脚本
     *
     * @return
     */
    @Bean
    public DefaultRedisScript<Long> redisBucketLuaScript() {
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>();
        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("redis-bucket-limit.lua")));
        redisScript.setResultType(Long.class);
        return redisScript;
    }

    /**
     * 读取计数器限流脚本
     *
     * @return
     */
    @Bean
    public DefaultRedisScript<Long> redisCounterLuaScript() {
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>();
        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("redis-counter-limit.lua")));
        redisScript.setResultType(Long.class);
        return redisScript;
    }


    /**
     * RedisTemplate
     *
     * @return
     */
    @Bean
    public RedisTemplate<String, Serializable> redisTemplate(LettuceConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Serializable> template = new RedisTemplate<>();
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }

}
