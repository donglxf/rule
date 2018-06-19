#  规则动作表
CREATE TABLE `rule_action_info` (
  `action_id` bigint(20) NOT NULL COMMENT '主键',
  `action_type` int(11) NOT NULL COMMENT '动作类型(1实现2自身)',
  `action_name` varchar(200) NOT NULL COMMENT '动作名称',
  `action_desc` varchar(3000) DEFAULT NULL COMMENT '动作描述',
  `action_class` varchar(200) NOT NULL COMMENT '动作实现类(包路径)',
  `is_effect` int(11) NOT NULL DEFAULT '1' COMMENT '是否有效',
  `cre_user_id` varchar(32) NOT NULL COMMENT '创建人',
  `cre_time` datetime NOT NULL COMMENT '创建时间',
  `remark` varchar(3000) DEFAULT NULL COMMENT '备注',
  `business_id` varchar(32) DEFAULT '0' COMMENT '业务线id',
  `action_method` varchar(255) DEFAULT NULL COMMENT '动作执行方法',
  PRIMARY KEY (`action_id`),
  KEY `action_type` (`action_type`),
  KEY `action_name` (`action_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='规则动作定义信息';
# 规则动作参数表
CREATE TABLE `rule_action_param_info` (
  `action_param_id` bigint(20) NOT NULL COMMENT '主键',
  `action_id` bigint(20) NOT NULL COMMENT '动作id',
  `action_param_name` varchar(200) NOT NULL COMMENT '参数名称',
  `action_param_desc` varchar(3000) DEFAULT NULL COMMENT '参数描述',
  `param_identify` varchar(200) NOT NULL COMMENT '标识',
  `is_effect` int(11) NOT NULL DEFAULT '1' COMMENT '是否有效',
  `cre_user_id` varchar(32) NOT NULL COMMENT '创建人',
  `cre_time` datetime NOT NULL COMMENT '创建时间',
  `remark` varchar(3000) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`action_param_id`),
  KEY `action_id` (`action_id`),
  KEY `action_param_name` (`action_param_name`),
  KEY `param_identify` (`param_identify`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='动作参数信息表';
# 规则动作参数值表
CREATE TABLE `rule_action_param_value_info` (
  `action_param_value_id` bigint(20) NOT NULL COMMENT '主键',
  `rule_action_rel_id` bigint(20) NOT NULL COMMENT '动作规则关系主键',
  `action_param_id` bigint(20) NOT NULL COMMENT '动作参数',
  `param_value` varchar(200) NOT NULL COMMENT '参数值',
  `param_text` varchar(255) DEFAULT NULL COMMENT '参数文字描述',
  `is_effect` int(11) NOT NULL DEFAULT '1' COMMENT '是否有效',
  `cre_user_id` varchar(32) NOT NULL COMMENT '创建人',
  `cre_time` datetime NOT NULL COMMENT '创建时间',
  `remark` varchar(3000) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`action_param_value_id`),
  KEY `rule_action_rel_id` (`rule_action_rel_id`),
  KEY `action_param_id` (`action_param_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='动作参数对应的属性值信息表';
# 规则条件数据表
CREATE TABLE `rule_condition_info` (
  `condition_id` bigint(20) NOT NULL COMMENT '主键',
  `rule_id` bigint(20) NOT NULL COMMENT '规则',
  `condition_name` varchar(3000) NOT NULL COMMENT '条件名称',
  `condition_expression` varchar(3000) NOT NULL COMMENT '条件表达式',
  `condition_desc` varchar(3000) NOT NULL COMMENT '条件描述',
  `is_effect` int(11) NOT NULL DEFAULT '1' COMMENT '是否有效',
  `cre_user_id` varchar(32) NOT NULL COMMENT '创建人',
  `cre_time` datetime NOT NULL COMMENT '创建时间',
  `remark` varchar(3000) DEFAULT NULL COMMENT '备注',
  `val` varchar(255) DEFAULT NULL COMMENT '值',
  `hasvariable` tinyint(3) DEFAULT '0' COMMENT '1常量0输入值',
  PRIMARY KEY (`condition_id`),
  KEY `rule_id` (`rule_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='规则条件信息表';
#规则常量表
CREATE TABLE `rule_constant_info` (
  `con_id` bigint(20) NOT NULL COMMENT '主键',
  `con_key` varchar(200) NOT NULL COMMENT '常量类别',
  `con_name` varchar(200) NOT NULL COMMENT '常量名',
  `con_type` varchar(200) NOT NULL DEFAULT '0' COMMENT '常量类型',
  `con_code` varchar(200) NOT NULL COMMENT '变量code',
  `is_effect` int(11) NOT NULL DEFAULT '1' COMMENT '是否有效',
  `cre_user_id` varchar(32) NOT NULL COMMENT '创建人',
  `cre_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `remark` varchar(3000) NOT NULL DEFAULT '' COMMENT '备注',
  `business_id` varchar(32) DEFAULT '0' COMMENT '业务线id',
  PRIMARY KEY (`con_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='常量表';

# 业务线表
CREATE TABLE `rule_business` (
  `business_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `app_id` varchar(64) DEFAULT '0' COMMENT '系统id',
  `business_name` varchar(32) DEFAULT NULL COMMENT '业务线名',
  `business_desc` varchar(255) DEFAULT NULL COMMENT '描述',
 `business_identify` varchar(50) NOT NULL COMMENT '标识',
 `cre_user_id` varchar(32) DEFAULT NULL COMMENT '创建人',
 `cre_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`business_id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8 COMMENT='业务线表';
#业务线属性id
CREATE TABLE `rule_business_item_info` (
  `item_id` bigint(20) NOT NULL COMMENT '主键',
  `business_id` bigint(20) NOT NULL COMMENT '实体id',
  `item_name` varchar(50) NOT NULL COMMENT '字段名称',
  `item_identify` varchar(50) NOT NULL COMMENT '字段标识',
  `item_desc` varchar(50) DEFAULT NULL COMMENT '属性描述',
  `cre_user_id` varchar(32) NOT NULL COMMENT '创建人',
  `cre_time` datetime DEFAULT NULL COMMENT '创建时间',
  `is_effect` int(11) NOT NULL DEFAULT '1' COMMENT '是否有效',
  `remark` varchar(50) DEFAULT NULL COMMENT '备注',
  `data_type` varchar(16) DEFAULT NULL COMMENT '数据类型 ',
  `constant_id` bigint(20) DEFAULT NULL COMMENT '常量id',
  PRIMARY KEY (`item_id`),
  KEY `business_id` (`business_id`),
  KEY `item_identify` (`item_identify`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='业务线属性信息';
# 规则表信息
CREATE TABLE `rule_info` (
  `rule_id` bigint(20) NOT NULL COMMENT '主键',
  `scene_id` bigint(20) NOT NULL COMMENT '场景',
  `rule_name` varchar(50) NOT NULL COMMENT '名称',
  `rule_desc` varchar(3000) DEFAULT NULL COMMENT '描述',
  `rule_enabled` int(11) NOT NULL DEFAULT '1' COMMENT '是否启用',
  `is_effect` int(11) NOT NULL DEFAULT '1' COMMENT '是否有效',
  `cre_user_id` varchar(32) NOT NULL COMMENT '创建人',
  `cre_time` datetime NOT NULL COMMENT '创建时间',
  `remark` varchar(3000) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`rule_id`),
  KEY `scene_id` (`scene_id`),
  KEY `rule_name` (`rule_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='规则信息';
#业务线场景表
CREATE TABLE `rule_scene_info` (
  `scene_id` bigint(20) NOT NULL COMMENT '主键',
  `scene_identify` varchar(50) NOT NULL COMMENT '标识',
  `scene_type` int(11) DEFAULT NULL COMMENT '类型(暂不使用)',
  `scene_name` varchar(50) NOT NULL COMMENT '名称',
  `scene_desc` varchar(3000) DEFAULT NULL COMMENT '描述',
  `is_effect` int(11) NOT NULL DEFAULT '1' COMMENT '是否有效',
  `cre_user_id` varchar(32) NOT NULL COMMENT '创建人',
  `cre_time` datetime NOT NULL COMMENT '创建时间',
  `remark` varchar(3000) DEFAULT NULL COMMENT '备注',
  `version` varchar(32) DEFAULT NULL COMMENT '版本号',
  `business_id` varchar(32) DEFAULT '0' COMMENT '业务线id',
  PRIMARY KEY (`scene_id`),
  KEY `scene_identify` (`scene_identify`),
  KEY `scene_type` (`scene_type`),
  KEY `scene_name` (`scene_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='规则引擎使用场景';
#场景字段关联表
CREATE TABLE `rule_scene_item_rel` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `scene_id` bigint(20) DEFAULT NULL COMMENT '场景id',
  `business_item_id` bigint(20) DEFAULT NULL COMMENT '变量id',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `cre_time` timestamp NULL DEFAULT NULL,
  `business_id` bigint(20) DEFAULT NULL COMMENT '实体类id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=229 DEFAULT CHARSET=utf8 COMMENT='策略表';






