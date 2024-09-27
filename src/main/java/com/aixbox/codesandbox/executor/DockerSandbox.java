package com.aixbox.codesandbox.executor;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ArrayUtil;
import com.aixbox.codesandbox.enums.LanguageCmdEnum;
import com.aixbox.codesandbox.model.ExecuteMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.nio.charset.StandardCharsets;

/**
 * docker代码沙箱
 */
@Service
@Slf4j
public class DockerSandbox {


    @Resource
    private DockerDao dockerDao;

    @Resource
    private ContainerPoolExecutor containerPoolExecutor;

    /**
     * 执行代码方法
     * @param languageCmdEnum 语言命令枚举
     * @param code 代码
     * @param input 输入用例
     * @return
     */
    public ExecuteMessage execute(LanguageCmdEnum languageCmdEnum, String code, String input) {
        return containerPoolExecutor.run(containerInfo -> {
            try {
                String containerId = containerInfo.getContainerId();

                String codePathName = containerInfo.getCodePathName();

                String codeFileName = codePathName + File.separator + languageCmdEnum.getSaveFileName();

                FileUtil.writeString(code, codeFileName, StandardCharsets.UTF_8);

                dockerDao.copyFileToContainer(containerId, codeFileName);

                // 编译代码
                String[] compileCmd = languageCmdEnum.getCompileCmd();
                ExecuteMessage executeMessage;

                // 不为空则代表需要编译
                if (compileCmd != null) {
                    executeMessage = dockerDao.execCmd(containerId, compileCmd);
                    log.info("compile complete...");
                    // 编译错误
                    if (!executeMessage.isSuccess()) {
                        return executeMessage;
                    }
                }
                String[] runCmd = languageCmdEnum.getRunCmd();
                String[] runCmdArray = ArrayUtil.append(runCmd, input);
                executeMessage = dockerDao.execCmd(containerId, runCmdArray);
                log.info("run complete...");
                executeMessage.setInput(input);
                return executeMessage;
            } catch (Exception e) {
                return ExecuteMessage.builder()
                        .success(false)
                        .errorMessage(e.getMessage())
                        .build();
            }
        });

    }


}
