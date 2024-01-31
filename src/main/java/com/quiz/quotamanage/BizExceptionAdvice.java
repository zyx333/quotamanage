package com.quiz.quotamanage;

import com.quiz.quotamanage.data.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Component
public class BizExceptionAdvice {
    private static final Logger logger = LoggerFactory.getLogger(BizExceptionAdvice.class);
    @ExceptionHandler({BizException.class})
    public Result handleIllegalArgException(BizException e) {
        logger.error("业务异常", e);

        return Result.error("请稍后重试");
    }
}
