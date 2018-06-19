//手动指派
//获取url参数
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
layui.use([ 'form'], function () {
    var form = layui.form;
    var business = {
        baseUrl: "/dispatch/service/orderMainInfo/",
        uiUrl: "edit.html",
    };
    // 手动指派需要指定
    var businessSystemId = $.GetQueryString("businessSystemId");
    var businessTypeId = $.GetQueryString("businessTypeId");
    var askFromDispatch = $.GetQueryString("askFromDispatch");
    var orderMainInfoId = $.GetQueryString("orderMainInfoId");
    var requestDto = {
        orderMainInfoId: orderMainInfoId,
        businessSystemId: businessSystemId,
        businessTypeId: businessTypeId,
        askFromDispatch: askFromDispatch
    };
    form.on('submit(formDemo)', function (data) {
        var remoteUserId = $('#remoteUserId').val();
        requestDto['remoteUserId'] = remoteUserId;
        $.ajax({
            type: "POST",
            url: business.baseUrl + 'manualDispatch',
            data: JSON.stringify(requestDto),
            contentType: "application/json; charset=utf-8",
            success: function (result) {
                var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                parent.layer.close(index); //再执行关闭
                alert(result.msg)
            }
        });
        return false;
    });
});