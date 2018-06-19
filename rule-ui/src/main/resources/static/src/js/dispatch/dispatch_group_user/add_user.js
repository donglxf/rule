// 添加小组成员js
/**
 * 设置表单值
 * @param el
 * @param data
 */
//获取url参数
var business = {
    baseUrl: "/dispatch/service/group/",
};
(function ($) {
    var qy = $.GetQueryString = function (name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
        var r = window.location.search.substr(1).match(reg);
        if (r != null) return unescape(r[2]);
        return "";
    };
    var qy2 = $.GetUrlQueryString = function (url, name) {
        var result = null;
        if (url) {
            var myIndex = url.indexOf("?");
            if (myIndex != -1) {
                var search = url.substr(myIndex, url.length - myIndex);
                var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
                var r = search.substr(1).match(reg);
                if (r != null) {
                    result = unescape(r[2]);
                }
            }
        }
        return result;
    };
})(jQuery);
var groupId = $.GetQueryString("groupId");
var businessSystemId = $.GetQueryString("businessSystemId");
layui.use(['form', 'layer'], function () {

    var form = layui.form;
    var layer = layui.layer;
    var $ = layui.jquery;
    //监听提交
    //todo : 接口报错空指针
    form.on('submit(demo1)', function (data) {
        // console.log(JSON.stringify(data.field), {
        //     title: '最终的提交信息'
        // })
        var remoteUserId = data.field.remoteUserId;
        var remoteUserIdList = [remoteUserId];
        var requestDto = {
            groupId: groupId,
            remoteUserIdList: remoteUserIdList,
            businessSystemId: businessSystemId
        }
        $.ajax({
            type: "POST",
            url: business.baseUrl + 'groupAddUser',
            data: JSON.stringify(requestDto),
            contentType: "application/json; charset=utf-8",
            success: function (result) {
                    alert(result.msg);
                //刷新父页面并关闭当前iframe
                window.parent.location.reload();
                var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                parent.layer.close(index); //再执行关闭
            }
        });


        return false;
    });
});