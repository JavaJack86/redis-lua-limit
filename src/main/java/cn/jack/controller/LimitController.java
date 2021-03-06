package cn.jack.controller;

import cn.jack.annotation.BucketLimit;
import cn.jack.annotation.CounterLimit;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author MysteryJack
 * 公众号: Java菜鸟程序员
 * @date 2021/3/5
 * @Description:LimitController
 */
@RestController
public class LimitController {

    /**
     * @return
     * @{CounterLimit} 计数器限流 10秒中，可以访问5次
     */
    @CounterLimit(key = "jack-counter", time = 10, count = 5)
    @GetMapping("/counterLimit")
    public String counterLimit() {
        return "请求到达计数器限流策略";
    }


    /**
     * @return
     * @{CounterLimit} 令牌桶限流 最大容量100,初始容量100,每10秒添加1个,每60秒重置令牌
     */
    @BucketLimit(key = "jack-bucket", maxCapacity = 100, initTokens = 100, intervalTime = 10000, resetBucketInterval = 60000)
    @GetMapping("/bucketLimit")
    public String bucketLimit() {
        return "请求到达令牌桶限流策略";
    }

}
