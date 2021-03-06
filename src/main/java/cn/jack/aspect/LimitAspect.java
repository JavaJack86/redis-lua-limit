package cn.jack.aspect;

import cn.jack.annotation.BucketLimit;
import cn.jack.annotation.CounterLimit;
import cn.jack.expection.LimitException;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;

import java.io.Serializable;
import java.lang.reflect.Method;

/**
 * @author MysteryJack
 * 公众号: Java菜鸟程序员
 * @date 2021/3/5
 * @Description:环绕通知AOP
 */
@Slf4j
@Aspect
@Configuration
public class LimitAspect {


    @Autowired
    private RedisTemplate<String, Serializable> redisTemplate;

    @Autowired
    private DefaultRedisScript<Long> redisBucketLuaScript;

    @Autowired
    private DefaultRedisScript<Long> redisCounterLuaScript;


    @Around("execution(* cn.jack.controller ..*(..) )")
    public Object interceptor(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        CounterLimit counterLimit = method.getAnnotation(CounterLimit.class);
        BucketLimit bucketLimit = method.getAnnotation(BucketLimit.class);
        if (counterLimit != null) {
            //key 可以但不限于以下的情况
            //ip + 接口
            //user_id + 接口
            String key = counterLimit.key();
            Long counter = redisTemplate.execute(redisCounterLuaScript, Lists.newArrayList(key), counterLimit.count(), counterLimit.time());
            if (counter != null && counter.intValue() != -1 && counter <= counterLimit.count()) {
                log.info("限流时间段内访问第：{} 次", counter);
                return joinPoint.proceed();
            }
        } else if (bucketLimit != null) {
            String key = bucketLimit.key();
            Long tokenCount = redisTemplate.execute(redisBucketLuaScript, Lists.newArrayList(key),
                    bucketLimit.intervalTime(), System.currentTimeMillis(), bucketLimit.initTokens(), bucketLimit.maxCapacity(), bucketLimit.resetBucketInterval());
            if (tokenCount != null && tokenCount.intValue() > 0 && tokenCount <= bucketLimit.maxCapacity()) {
                log.info("限流时间段内令牌桶还剩下：{} 个令牌", tokenCount);
                return joinPoint.proceed();
            }
        } else {
            return joinPoint.proceed();
        }
        throw new LimitException("已经达到设置限流次数!");
    }

}
