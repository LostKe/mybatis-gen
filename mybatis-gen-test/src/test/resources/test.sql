
DROP TABLE IF EXISTS `t_system_log`;
CREATE TABLE IF NOT EXISTS `t_system_log` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) unsigned NOT NULL COMMENT '操作人id',
  `operator` varchar(32) NOT NULL COMMENT '操作人名',
  `ip` varchar(64) NOT NULL,
  `content` VARCHAR(128) NOT NULL COMMENT '内容',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime COMMENT '更新时间',
  `is_delete` TINYINT(1) DEFAULT 0 COMMENT '1.已删除, 0.未删除. 默认是 0',
  PRIMARY KEY (`id`),
  KEY `k_user_id` (`user_id`),
  KEY `k_operator` (`operator`),
  KEY `k_ip` (`ip`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=COMPACT COMMENT='日志表';
