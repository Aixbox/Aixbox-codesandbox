package com.aixbox.codesandbox.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExecuteMessage {

    /**
     * 执行是否成功
     */
    private boolean success;

    /**
     * 输入
     */
    private String input;

    /**
     * 标准输出
     */
    private String message;

    /**
     * 错误输出
     */
    private String errorMessage;

    /**
     * 执行耗时
     */
    private Long time;

    /**
     * 内存占用
     */
    private Long memory;
}

