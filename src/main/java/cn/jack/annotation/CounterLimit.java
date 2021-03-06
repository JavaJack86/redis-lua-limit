package cn.jack.annotation;

import java.lang.annotation.*;

/**
 * @author MysteryJack
 * 公众号: Java菜鸟程序员
 * @date 2021/3/5
 * @Description:计数器限流
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CounterLimit {

    /**
     * 限流唯一标示
     *
     * @return
     */
    String key() default "";

    /**
     * 限流时间
     *
     * @return
     */
    int time();

    /**
     * 限流次数
     *
     * @return
     */
    int count();





}
