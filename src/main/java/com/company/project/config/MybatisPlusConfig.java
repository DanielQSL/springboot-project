package com.company.project.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.apache.ibatis.reflection.MetaObject;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

/**
 * MyBatis-Plus配置
 *
 * @author DanielQSL
 */
@MapperScan("com.company.project.springbootproject.mapper")
@Configuration
public class MybatisPlusConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(MybatisPlusConfig.class);

    private static final String CREATE_USER = "createUser";
    private static final String CREATE_TIME = "createTime";
    private static final String UPDATE_USER = "updateUser";
    private static final String UPDATE_TIME = "updateTime";

    /**
     * 插件配置
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // 分页插件
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        // 乐观锁插件
        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        // 防止全表更新与删除
        interceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());

        return interceptor;
    }

    /**
     * 通用字段自动填充配置
     * <p>
     * 注意！这里需要标记为填充字段
     * TableField(.. fill = FieldFill.INSERT)
     */
    @Bean
    public MetaObjectHandler metaObjectHandler() {
        return new MetaObjectHandler() {
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
//                this.strictUpdateFill(metaObject, UPDATE_USER, String.class, name);
//                this.strictUpdateFill(metaObject, UPDATE_TIME, LocalDateTime.class, LocalDateTime.now());
                // 如果属性有值则不覆盖, 如果填充值为null则不填充，故使用setFieldValByName
                this.setFieldValByName(UPDATE_USER, name, metaObject);
                this.setFieldValByName(UPDATE_TIME, LocalDateTime.now(), metaObject);
            }
        };
    }

}