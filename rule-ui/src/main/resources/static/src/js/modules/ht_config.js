/**
 * add by tanrq 2018/1/21
 */
layui.define(['config'],function (exports) {
    // var basePath = "http://localhost:9000/",
    var basePath = "http://172.16.200.110:30111/",
        rule = "uc";
    try {
        basePath = gatewayUrl ? gatewayUrl : basePath;
    } catch (e) {
        console.log(e);
    }
    var ruleServicePathUrl = ruleServiceUrl;

    var ruleServicePath = {
        basePath: ruleServicePathUrl,
        //动作管理
        actionInfo:{
            base:ruleServicePathUrl+"actionInfo/",
            page:this.base+"page",
            edit:this.base+"edit",
            delete:this.base+"getInfoById",
        },
        //动作参数管理
        actionParamInfo:{
            base:ruleServicePathUrl+"actionParamInfo/",
            page:this.base+"page",
            edit:"edit",
            delete:"getInfoById",
        },
        entityInfo:{
            base:ruleServicePathUrl+"entityInfo/",
            page:this.base+"page",
            edit:"edit",
            delete:"getInfoById",
        },
        entityItemInfo:{
            base:ruleServicePathUrl+"entityItemInfo/",
            page:this.base+"page",
            edit:"edit",
            delete:"getInfoById",
        },

        sceneInfo:{
            base:ruleServicePathUrl+"sceneInfo/",
            page:this.base+"page",
            edit:"edit",
            delete:"getInfoById",
        },
        sceneVersion:{
            base:ruleServicePathUrl+"sceneVersion/",
            page:this.base+"page",
            edit:"edit",
            delete:"getInfoById",
        },
        businessSystem:{
            base:ruleServicePathUrl+"businessSystem/",
            page:this.base+"page",
            edit:this.base+"edit",
            delete:this.base+"getInfoById",
        },
        business:{
            base:ruleServicePathUrl+"business/",
            page:this.base+"page",
            edit:this.base+"edit",
            delete:this.base+"getInfoById",
        },
        strategy:{
            base:ruleServicePathUrl+"strategy/",
            page:this.base+"page",
            edit:this.base+"edit",
            delete:this.base+"getInfoById",
        },


    };
    exports('ht_config', {
        app: "DP"
        , basePath: basePath + rule + "/"
        , loadMenuUrl: basePath + rule + "/auth/loadMenu"
        , loadBtnAndTabUrl: basePath + rule + "/auth/loadBtnAndTab"
        , loginUrl: basePath + "uaa/auth/login"
        , refreshTokenUrl: basePath + "uaa/auth/token"
        , centerServicePath:"/service/center/" //
        , ruleServicePath: ruleServicePath // 规则服务
        ,dispatchServicePath:dispatchServicePath//派单服务
        ,dev:true
    });
});