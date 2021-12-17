package com.company.project.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * MyBatis-Plus 元数据填充处理
 * <p>
 * 注意！这里需要标记为填充字段
 * TableField(.. fill = FieldFill.INSERT)
 *
 * @author DanielQSL
 */
@Component
public class MyBatisPlusMetaObjectHandler implements MetaObjectHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyBatisPlusMetaObjectHandler.class);

    private static final String CREATE_USER = "createUser";
    private static final String CREATE_TIME = "createTime";
    private static final String UPDATE_USER = "updateUser";
    private static final String UPDATE_TIME = "updateTime";

    @Override
    public void insertFill(MetaObject metaObject) {
        LOGGER.debug("start insert fill ....");
        // 此处从上下文中获取用户信息
        String name = "";
        this.strictInsertFill(metaObject, CREATE_USER, String.class, name);
        this.strictInsertFill(metaObject, CREATE_TIME, LocalDateTime.class, LocalDateTime.now());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        LOGGER.debug("start update fill ....");
        // 此处从上下文中获取用户信息
        String name = "";
        this.strictUpdateFill(metaObject, UPDATE_USER, String.class, name);
        this.strictUpdateFill(metaObject, UPDATE_TIME, LocalDateTime.class, LocalDateTime.now());
    }

}
