package cn.jack.annotation;

import java.lang.annotation.*;

/**
 * @author MysteryJack
 * 公众号: Java菜鸟程序员
 * @date 2021/3/5
 * @Description:令牌桶限流
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BucketLimit {

    /**
     * 限流唯一标示
     *
     * @return
     */
    String key() default "";

    /**
     * 桶最大容量
     *
     * @return
     */
    int maxCapacity();

    /**
     * 令牌生成间隔(ms)
     *
     * @return
     */
    int intervalTime();

    /**
     * 重置令牌桶间隔(ms)
     *
     * @return
     */
    int resetBucketInterval();

    /**
     * 令牌桶初始化的令牌数
     *
     * @return
     */
    int initTokens();

}
