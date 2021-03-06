package cn.jack.expection;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author MysteryJack
 * 公众号: Java菜鸟程序员
 * @date 2021/3/5
 * @Description:异常处理
 */
@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler({LimitException.class})
    @ResponseBody
    public ResponseEntity redisLimitException(LimitException e) {
        log.error("已经到设置限流次数!!!");
        return new ResponseEntity<>("请求太频繁,已到限流次数!!!", HttpStatus.OK);
    }

}
