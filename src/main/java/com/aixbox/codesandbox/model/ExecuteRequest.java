package com.aixbox.codesandbox.model;

import lombok.Data;

import java.util.List;

@Data
public class ExecuteRequest {
    /**
     * 语言
     */
    private String language;

    /**
     * 代码
     */
    private String code;

    /**
     * 输入参数列表
     */
    private List<String> inputList;
}

