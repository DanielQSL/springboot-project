-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`
(
    `id`          bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增主键id',
    `username`    varchar(64)       DEFAULT NULL COMMENT '用户名字',
    `age`         tinyint(4) NOT NULL DEFAULT 0 COMMENT '性别:0-未知,1-男,2-女',
    `address`     varchar(256)      DEFAULT NULL COMMENT '地址',
    `version`     int(11) NOT NULL DEFAULT '0' COMMENT '版本号',
    `deleted`     tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除标识:0-未删除,1-已删除',
    `create_user` varchar(128)      DEFAULT NULL COMMENT '创建人',
    `update_user` varchar(128)      DEFAULT NULL COMMENT '修改人',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_username` (`username`) USING BTREE,
    KEY           `idx_address` (`address`) USING BTREE,
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';