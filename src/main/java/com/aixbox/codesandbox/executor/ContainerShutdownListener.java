package com.aixbox.codesandbox.executor;

import cn.hutool.core.io.FileUtil;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import javax.annotation.Resource;

@Component
public class ContainerShutdownListener {

    @Resource
    private DockerDao dockerDao;

    @Resource
    private ContainerPoolExecutor containerPoolExecutor;

    @PreDestroy
    public void onShutdown() {

        // 清理所有的容器以及残余文件
        containerPoolExecutor
                .getContainerPool()
                .forEach(containerInfo -> {
                    FileUtil.del(containerInfo.getCodePathName());
                    dockerDao.cleanContainer(containerInfo.getContainerId());
                });
        // 处理容器停止时的逻辑
        System.out.println("Docker container is stopping. Cleaning up resources...");
        // 在这里添加你的清理逻辑
    }
}
