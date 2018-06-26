# rule 规则引擎
技术栈
    spring boot + spring cloud
    mybatis-plus 通用mapper插件
    drools 规则引擎
    layui + kit 前端框架
    mysql 5.6
#功能描述
    对规则的配置，执行获取不通规则的不同结果
#项目架构
rule 
    rule-common 公共模块 包括entity,mapper
    rule-config 规则配置信息服务
    rule-drools 规则引擎调用服务，对外输出服务
    rule-ui     前端html代码         

