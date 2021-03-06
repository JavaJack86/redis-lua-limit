package cn.jack.expection;

/**
 * @author MysteryJack
 * 公众号: Java菜鸟程序员
 * @date 2021/3/5
 * @Description:自定义限流异常
 */
public class LimitException extends Exception{

    public LimitException(String message) {
        super(message);
    }
}
