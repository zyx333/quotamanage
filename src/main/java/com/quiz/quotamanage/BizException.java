package com.quiz.quotamanage;

/**
 * 自定义业务异常
 */
public class BizException extends Exception{
    public BizException(String message) {
        super(message);
    }
}
