/**
 * add by tanrq 2018/1/21
 */
layui.define(['config'],function (exports) {
    var  getRootPath_web = function() {
        //获取当前网址，如： http://localhost:8083/uimcardprj/share/meun.jsp
        var curWwwPath = window.document.location.href;
        //获取主机地址之后的目录，如： uimcardprj/share/meun.jsp
        var pathName = window.document.location.pathname;
        var pos = curWwwPath.indexOf(pathName);
        //获取主机地址，如： http://localhost:8083
        var localhostPaht = curWwwPath.substring(0, pos);
        //获取带"/"的项目名，如：/uimcardprj
        // var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
        return (localhostPaht);
    }
    // var basePath = "http://localhost:9000/",

    var basePath = "http://172.16.200.110:30111/",
        rule = "uc";
    try {
        basePath = gatewayUrl ? gatewayUrl : basePath;
    } catch (e) {
        console.log(e);
    }
    var ruleServicePathUrl = basePath + ruleServiceUrl;

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
        app: "RULE"
        , basePath: basePath + rule + "/"
        , loadMenuUrl: basePath + rule + "/auth/loadMenu"
        , loadBtnAndTabUrl: basePath + rule + "/auth/loadBtnAndTab"
        , loginUrl: basePath + "uaa/auth/login"
        , refreshTokenUrl: basePath + "uaa/auth/token"
        , ruleServicePath: ruleServicePath // 规则服务
         ,ruleDroolsUrl:basePath + ruleDroolsUrl//派单服务

        , changePwdUrl : changePwdUrl
        , userInfoUpdate: userInfoUpdate
        ,loginIndex: getRootPath_web()+"/",
    });
});