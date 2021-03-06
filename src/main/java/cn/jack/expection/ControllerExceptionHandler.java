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
        log.error(e.getMessage());
        return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
    }

}
