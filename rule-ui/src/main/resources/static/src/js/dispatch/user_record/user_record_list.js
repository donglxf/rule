/**
 * 设置表单值
 * @param el
 * @param data
 */
var data = {
    "orderMainInfo": {},
    "orderDetails": []
}

function setFromValues(el, data) {
    for (var p in data) {
        el.find(":input[name='" + p + "']").val(data[p]);
    }
}

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

var businessSystemId = $.GetQueryString("businessSystemId");
var business = {
    baseUrl: "/dispatch/service/userRecord/list",
    uiUrl: "edit.html",
    tableId: "business",
    toolbarId: "#toolbar",
    unique: "id",
    order: "asc",
    currentItem: {}
};

//重载表格
business.loadPage = function (table) {

    //执行重载
    var time = $("#timer").val();
    var beginTime = time.substring(0, 10);
    var endTime = time.substring(13, 24);

    var whereCondition = {
        "businessSystemId": businessSystemId,
        "groupId": $("#groupCode").val(),
        "userId": $("#userId").val(),
        "beginTime": beginTime,
        "endTime": endTime
    };
    table.reload(business.tableId, {
        url: business.baseUrl,
        page: {
            curr: 1
        },
        where: whereCondition,
        method: 'post'
    })
};

//表头
business.cols = function () {
    return [ //表头
        //{field: 'businessId',  event: 'setItem',title: 'ID',sort: true, fixed: 'left'}
        {
            field: 'userName',
            align: 'center',
            width: '20%',
            title: '跟进人'
        }
        ,
        {
            field: 'groupName',
            align: 'center',
            width: '20%',
            title: '所属小组'
        },
        {
            field: 'acceptedOrderNumber',
            align: 'center',
            width: '20%',
            title: '派件数量', sort: true
        },
        {
            field: 'handleOrderNumber',
            align: 'center',
            width: '20%',
            title: '完成数量', sort: true
        },
        {
            field: 'notHandleOrderNumber',
            align: 'center',
            width: '20%',
            title: '未完成数量'
        }
    ];
};
var layerTopIndex;
layui.config({
    base: '/dispatch/ui/src/js/modules/' //假设这是你存放拓展模块的根目录
}).extend({ //设定模块别名
    myutil: 'common' //如果 mymod.js 是在根目录，也可以不用设定别名
});
layui.use(['table', 'form', 'laytpl', 'myutil', 'ht_auth', 'laydate'], function () {
    var laytpl = layui.laytpl;
    var laydate = layui.laydate;
    table = layui.table;
    var app = layui.app,
        ht_auth = layui.ht_auth,
        form = layui.form;
    $ = layui.jquery;
    $.post(business.baseUrl, {
        "businessSystemId": businessSystemId,
        "groupId": $("#groupCode").val(),
        "userId": $("#userId").val(),
        "beginTime": $('#timerBegin').val(),
        "endTime": $('#timerStop').val()
    }, function (result) {
        //表格数据渲染
        table.render({
            elem: '#' + business.tableId
            , height: 'full',
            // , cellMinWidth: 10
            //, url: business.baseUrl + 'page?orderMainInfoId=' + orderMainInfoId //数据接口
            data: result.data
            , page: true //开启分页
            , id: business.tableId
            , cols: [business.cols()]
            , method: 'post'
        });
        form.render();
    });

    //查询按钮
    $('#search-btn').on('click', function () {
        business.loadPage(table);
    });
//重置按钮
    $('#reset').on('click', function () {
        //批量重置搜索条件
        $("#mainForm input").each(function (ind, item) {
            $(item).val("");
        });
        $("#mainForm select").each(function (ind, item) {
            $(item).val("");
        });
        form.render('select');
    });

    //日期范围
    laydate.render({
        elem: '.date'
        , range: true
    });
});