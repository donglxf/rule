layui.use(['table', 'jquery', 'element', 'laytpl'], function () {
    var $ = layui.jquery;
    var element = layui.element;
    var laytpl = layui.laytpl;
    var sence = $("#sence")
    var senceHtml = sence.html();
    var content = $("#main_content");
    var taskId = $("#task_hidden_input", parent.document).val();
    layer.load();
    $.ajax({
        cache : true,
        type : "GET",
        url : pathConfig.ruleServicePath+'verification/queryTaskVerficationResult',
        data:{
            "taskId":taskId
        },
        timeout : 6000, //超时时间设置，单位毫秒
        async : false,
        error : function(request) {
            layer.msg("查询失败！");
        },
        success : function(data) {
            if(data.code == 0){
                var hitRules = data.data.hitRules;
                $("#procReleaseId").val(data.data.procReleaseId);
                $("#taskId").val(data.data.taskId);
                $("#validateFlag").val(data.data.validateFlag);
                var ruleHtml = "模型执行结果："+data.data.message+"\n";
                if(hitRules != null && hitRules.length >0){
                    ruleHtml += "命中如下规则："+"\n";
                    for(var i=0;i<hitRules.length;i++){
                        ruleHtml += "["+(i+1)+"]: "+hitRules[i].ruleDesc+"\n";
                    }
                }
                $("#modelResult").html(ruleHtml);
                if(data.data.sences ==null){
                    layer.closeAll('loading');
                    return;
                }
                laytpl(senceHtml).render(data.data.sences, function (html) {
                    var contentHtml = content.html()+html;
                    content.html(contentHtml);
                    element.render('test');
                    layer.closeAll('loading');
                });
            }
            if(data.code == 1){
                layer.msg("查询失败！");
            }
        }
    });

    $("#pass").click( function() {
        layer.load();
        var validateFlag = $("#validateFlag").val();
        if(validateFlag == '1'){
            layer.msg("模型验证未通过，不能更新模型验证状态为通过！");
            return ;
        }
        var procReleaseId = $("#procReleaseId").val();
        verficationProcRelease(procReleaseId,1);
    });
    $("#unpass").click( function() {
        layer.load();
        var procReleaseId = $("#procReleaseId").val();
        verficationProcRelease(procReleaseId,2);
    });
    // 发起审批
    $("#approve").click( function() {
        layer.load();
        var procReleaseId = $("#procReleaseId").val();
        approveProcRelease(procReleaseId,3);
    });

    function approveProcRelease(procReleaseId,isApprove){
        console.log(procReleaseId);
        $.ajax({
            cache: true,
            type: "POST",
            url: pathConfig.activitiConfigPath+'actProcRelease/update',
            async: false,
            data: {
                id:procReleaseId,
                isApprove:isApprove
            },
            timeout: 60000,
            error : function(request) {
                layer.closeAll('loading');
                layer.msg("网络异常!");
            },
            success: function (data) {
                layer.closeAll('loading');
                layer.msg(data.msg);
            }
        });
    }


    function verficationProcRelease(procReleaseId,checkType){
        console.log(procReleaseId);
        $.ajax({
            cache: true,
            type: "POST",
            url: pathConfig.activitiConfigPath+'actProcRelease/update',
            async: false,
            data: {
                id:procReleaseId,
                isValidate:checkType
            },
            timeout: 60000,
            error : function(request) {
                layer.closeAll('loading');
                layer.msg("网络异常!");
            },
            success: function (data) {
                layer.closeAll('loading');
                layer.msg(data.msg);
            }
        });
    }


});
