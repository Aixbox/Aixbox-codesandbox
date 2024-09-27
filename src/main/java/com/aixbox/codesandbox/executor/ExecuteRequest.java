package com.aixbox.codesandbox.executor;

import lombok.Data;

/**
 * 执行请求类
 */

@Data
public class ExecuteRequest {
    private String language;
    private String code;
}

