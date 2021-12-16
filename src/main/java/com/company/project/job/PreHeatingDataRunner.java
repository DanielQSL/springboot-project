package com.company.project.job;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * 系统启动时，预加载数据至本地
 *
 * @author DanielQSL
 */
@Slf4j
@Component
public class PreHeatingDataRunner implements ApplicationRunner {

    /**
     * 预热数据完成标志
     */
    public volatile boolean finishedFlag = false;

    @Override
    public void run(ApplicationArguments args) {
        try {
            log.info("====== [xxxx] start load initial data ======");

            finishedFlag = true;
            log.info("====== [xxxx] finished to load initial data ======");
        } catch (Exception e) {
            log.error("[xxxx] loaded initial data error.", e);
        }
    }

}
