package com.lqkj.web.cmccr2;

import com.lqkj.web.cmccr2.message.MessageBean;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.async.CallableProcessingInterceptor;

import java.util.concurrent.Callable;

/**
 * 异步全局异常处理
 */
public class GlobalAsyncExceptionHandler implements CallableProcessingInterceptor {

    @Override
    public <T> Object handleTimeout(NativeWebRequest request, Callable<T> task) throws Exception {
        return MessageBean.error("请求超时");
    }

    @Override
    public <T> Object handleError(NativeWebRequest request, Callable<T> task, Throwable t) throws Exception {
        t.printStackTrace();
        return MessageBean.error(t.toString());
    }
}
