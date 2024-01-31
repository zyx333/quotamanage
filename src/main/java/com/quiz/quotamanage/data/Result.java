/*
 *
 * Result.java
 * Copyright 2023 Qunhe Tech, all rights reserved.
 * Qunhe PROPRIETARY/CONFIDENTIAL, any form of usage is subject to approval.
 */

package com.quiz.quotamanage.data;

import lombok.Data;

/**
 * controller 层结果封装
 * @description:
 * @author: zyx
 * @date: 2024-01-31 16:18
 **/
@Data
public class Result<T> {

    /**
     * 状态码，0 表示成功，非 0 表示失败
     */
    private int code;
    /**
     * 异常信息
     */
    private String msg;

    /**
     * 业务数据
     */
    private T data;

    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.setCode(0);
        result.setData(data);
        return result;

    }

    public static <T> Result<T> error(String msg) {
        Result<T> result = new Result<>();
        result.setCode(-1);
        result.setMsg(msg);
        return result;
    }
}
