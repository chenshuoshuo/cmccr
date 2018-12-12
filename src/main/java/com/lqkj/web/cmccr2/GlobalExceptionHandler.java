package com.lqkj.web.cmccr2;

import com.lqkj.web.cmccr2.message.MessageBean;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常处理
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler
    @ResponseBody
    public MessageBean<String> resolveException(Exception e) {
        e.printStackTrace();

        return MessageBean.error(e.toString());
    }
}
