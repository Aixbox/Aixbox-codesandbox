package com.aixbox.codesandbox.executor;

import cn.hutool.core.io.resource.ResourceUtil;
import com.aixbox.codesandbox.enums.LanguageCmdEnum;
import com.aixbox.codesandbox.model.ExecuteMessage;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import java.nio.charset.StandardCharsets;

@SpringBootTest
class DockerSandboxTest {

    @Resource
    private DockerSandbox dockerSandbox;

    @Test
    void testJava() {
        String code = ResourceUtil.readStr("languageCode/Main.java", StandardCharsets.UTF_8);
        ExecuteMessage execute = dockerSandbox.execute(LanguageCmdEnum.JAVA, code, "sdfsdafsaf数字大大的");
        System.out.println(execute);
    }
}