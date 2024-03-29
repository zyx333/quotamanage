DROP TABLE IF EXISTS `quota_account`;
CREATE TABLE `quota_account` (
    `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增主键',
    `user_id` bigint(20) NOT NULL COMMENT ' 用户 id',
    `account_type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '账号类型',
    `quota` double NOT NULL COMMENT '账号额度',
    `deleted` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除状态，0 否，1 是',
    `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `lastmodified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
    PRIMARY KEY (`id`), UNIQUE KEY `udx_user_account_type` (`user_id`, `account_type`) USING BTREE ) ENGINE=InnoDB
    AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='额度管理表';

DROP TABLE IF EXISTS `user_account`;
CREATE TABLE `user_account` (
     `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增主键',
     `user_id` bigint(20) NOT NULL COMMENT ' 用户 id',
     `quota` double NOT NULL COMMENT '账号总额度',
     `deleted` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除状态，0 否，1 是',
     `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
     `lastmodified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
     PRIMARY KEY (`id`), UNIQUE KEY `udx_user` (`user_id`) USING BTREE ) ENGINE=InnoDB
    AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='用户账号表';

DROP TABLE IF EXISTS `quota_update_log`;
CREATE TABLE `quota_update_log` (
    `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增主键',
    `quota_account_id` bigint(20) NOT NULL COMMENT ' 额度账号 id',
    `update_type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '变更类型',
    `quota` double NOT NULL COMMENT '变更额度',
    `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `lastmodified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
    PRIMARY KEY (`id`), KEY `idx_quota_account_id` (`quota_account_id`) USING BTREE ) ENGINE=InnoDB
    AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='额度更新日志表';


