package com.company.project.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 健康检测 Controller层
 *
 * @author danielqsl
 */
@Controller
public class HealthCheckController {

    /**
     * 预热数据完成标志
     */
    public volatile boolean finishedFlag = false;

    /**
     * 健康检查接口
     * （数据预热完成后再对外提供接口）
     * <p>
     * ResponseEntity可以理解为 @ResponseBody + @ResponseStatus 的组合
     *
     * @return 响应
     */
    @RequestMapping(value = "/healthcheck", method = RequestMethod.GET)
    public ResponseEntity<String> healthCheck() {
        if (finishedFlag) {
            return ResponseEntity.ok("OK");
        } else {
            return new ResponseEntity<>("Waiting", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
