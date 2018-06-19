/**
 * 设置表单值
 * @param el
 * @param data
 */
var data = {
    "orderMainInfo": {},
    "orderDetails": [],
    "orderItemValueDtoList": [],
    "operateLogList": []
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
var orderMainInfoId = $.GetQueryString("orderMainInfoId");
var business = {
    baseUrl: "/dispatch/service/orderDetail/",
    uiUrl: "edit.html",
    tableId: "business",
    toolbarId: "#toolbar",
    unique: "id",
    order: "asc",
    currentItem: {}
};

//表头
business.cols = function () {
    return [ //表头
        //{field: 'businessId',  event: 'setItem',title: 'ID',sort: true, fixed: 'left'}
        {
            field: 'dispatchToUserId',
            align: 'center',
            width: '15%',
            title: '订单被指派人'
        }
        ,
        {
            field: 'orderStatus',
            align: 'center',
            title: '订单状态',
            width: '10%',
            templet: "#StatusTpl"
        },
        {
            field: 'createTime',
            align: 'center',
            width: '15%',
            title: '派件时间'
        },
        {
            field: 'timerBegin',
            align: 'center',
            width: '15%',
            title: '开始时间'
        },
        {
            field: 'timerStop',
            align: 'center',
            width: '15%',
            title: '结束时间'
        },
        {
            field: 'businessDesc',
            align: 'center',
            title: '处理时长(分钟)',
            width: '10%',
            templet: function (d) {
                var createTime = d.createTime;
                var updateTime = d.updateTime;
                var date1 = new Date(createTime);
                var date2 = new Date(updateTime);
                var handleTime = parseInt(date2 - date1) / 1000 / 60;
                return handleTime;
            }
        },
        {
            field: 'operateRemark',
            align: 'center',
            width: '10%',
            title: '处理反馈'
        },
        {
            field: 'operateResult',
            align: 'center',
            width: '10%',
            title: '处理结果'
        }
    ];
};
var layerTopIndex;
layui.config({
    base: '/src/js/modules/' //假设这是你存放拓展模块的根目录
}).extend({ //设定模块别名
    myutil: 'common' //如果 mymod.js 是在根目录，也可以不用设定别名
});
layui.use(['table', 'form', 'laytpl', 'myutil', 'ht_auth'], function () {
    var laytpl = layui.laytpl;
    table = layui.table;
    var app = layui.app,
        ht_auth = layui.ht_auth,
        form = layui.form;
    $ = layui.jquery;
    $.get(business.baseUrl + 'page/back?orderMainInfoId=' + orderMainInfoId, function (result) {
        data.orderDetails = result.data.orderDetails;
        data.orderMainInfo = result.data.orderMainInfo;
        data.orderItemValueDtoList = result.data.orderItemValueDtoList;
        data.operateLogList = result.data.orderOperateLogDtoList;
        var orderMainInfo = data.orderMainInfo
        //订单详情数据回显
        $('#contractId').val(orderMainInfo.contractId);
        if (orderMainInfo.dispatchType == 0) {
            $('#dispatchType').val("手动指派")
        }
        if (orderMainInfo.dispatchType == 1) {
            $('#dispatchType').val("系统自动匹配");
        }
        $('#createTime').val(orderMainInfo.createTime);
        if (orderMainInfo.status == '3' || orderMainInfo.status == '4') {
            $('#updateTime').val(orderMainInfo.updateTime);
            var createTime = orderMainInfo.createTime.replace(/\-/g, "/");
            var updateTime = orderMainInfo.updateTime.replace(/\-/g, "/");
            var date1 = new Date(createTime);
            var date2 = new Date(updateTime);
            var handleTime = parseInt(date2 - date1) / 60;
            if (updateTime != null && updateTime != "") {
                $('#handleTime').val(handleTime);
            }
        } else {
            var createTime = orderMainInfo.createTime.replace(/\-/g, "/");
            var date1 = new Date(createTime);
            var date2 = new Date();
            var handleTime = Math.abs(date2 - date1) / 1000 / 60;
            handleTime = parseInt(handleTime);
            if (date1 != null && date2 != null) {
                $('#handleTime').val(handleTime);
            }
        }
        $('#stage').val(orderMainInfo.stage);
        //订单状态(0未匹配规则,1处理中，2未匹配到相关人，3已完成，4已关闭
        if (orderMainInfo.status == 0) {
            $('#status').val("未匹配规则")
        } else if (orderMainInfo.status == 1) {
            $('#status').val("处理中")
        } else if (orderMainInfo.status == 2) {
            $('#status').val("未匹配到相关人")
        } else if (orderMainInfo.status == 3) {
            $('#status').val("已完成")
        } else if (orderMainInfo.status == 4) {
            $('#status').val("已关闭")
        }

        //表格数据渲染
        table.render({
            elem: '#' + business.tableId
            , height: 'full',
            // , cellMinWidth: 10
            //, url: business.baseUrl + 'page?orderMainInfoId=' + orderMainInfoId //数据接口
            data: data.orderDetails
            , page: false //开启分页
            , id: business.tableId
            , cols: [business.cols()]
        });
        //渲染特殊属性
        var html1 = ininInput(data.orderItemValueDtoList);
        var form3 = $("#form3");
        form3.html(html1);
        //$('.layui-col-md6:last').hidden = hidden;
        //渲染日志
        var html2 = initOperateLog(data.operateLogList);
        var operateLog = $("#operateLog");
        operateLog.html(html2);
        form.render();
    });
    //按钮权限控制隐藏，页面大按钮
    ht_auth.render();
});

function ininInput(orderItemValueDtoList) {
    var html = '';
    var json = orderItemValueDtoList;
    for (var i = 0, l = json.length; i < l; i += 2) {
        //一个特殊属性,包含名称跟值

        //判断属性剩余个数,如果剩余
        if (i + 1 > json.length) {
            var value1 = '';
            var name1 = '';
            for (var key in json[i]) {
                if (key == 'orderItemValueName') {
                    name1 = json[i][key];
                }
                if (key == 'orderItemValue') {
                    value1 = json[i][key];
                }
                console.log(key + ':' + json[i][key]);
            }
            //每两个特殊属性生成一个栅格行
            html +=
                "                <div class=\"layui-row layui-col-space10\">\n" +
                "                    <div class=\"layui-col-md6\">\n" +
                                        '<div class="layui-form-item">\n' +
                '                    <label class="layui-form-label">' + name1 + '</label>\n' +
                '                           <div class="layui-input-block">\n' +
                '                               <input type="text" value="' + value1 + '" name="" id="" lay-verify="title" autocomplete="off"  class="layui-input">\n' +
                '                            </div>\n' +
                '                        </div>' +
                "                    </div>\n" +
                "                </div>"
        }else {
            var value1 = '';
            var name1 = '';
            for (var key in json[i]) {
                if (key == 'orderItemValueName') {
                    name1 = json[i][key];
                }
                if (key == 'orderItemValue') {
                    value1 = json[i][key];
                }
                console.log(key + ':' + json[i][key]);
            }

            var value2 = '';
            var name2 = '';
            for (var key in json[i + 1]) {
                if (key == 'orderItemValueName') {
                    name2 = json[i + 1][key];
                }
                if (key == 'orderItemValue') {
                    value2 = json[i + 1][key];
                }
                console.log(key + ':' + json[i + 1][key]);
            }
            //每两个特殊属性生成一个栅格行
            html +=
                "                <div class=\"layui-row layui-col-space10\">\n" +
                "                    <div class=\"layui-col-md6\">\n" + '<div class="layui-form-item">\n' +
                '                    <label class="layui-form-label">' + name1 + '</label>\n' +
                '                    <div class="layui-input-block">\n' +
                '                        <input type="text" value="' + value1 + '" name="" id="" lay-verify="title" autocomplete="off"  class="layui-input">\n' +
                '                    </div>\n' +
                '                </div>' +
                "                    </div>\n" +
                "                    <div class=\"layui-col-md6\">\n" + '<div class="layui-form-item" hidden="">\n' +
                '                    <label class="layui-form-label">' + name2 + '</label>\n' +
                '                    <div class="layui-input-block">\n' +
                '                        <input type="text" value="' + value2 + '" name="" id="" lay-verify="title" autocomplete="off"  class="layui-input">\n' +
                '                    </div>\n' +
                '                </div>' +
                "                    </div>\n" +
                "                </div>"
        }
    }
    return html;
}

function initOperateLog(operateLogList) {
    var html = '';
    var json = operateLogList;
    for (var i = 0, l = json.length; i < l; i++) {
        //一个特殊属性,包含名称跟值
        var createTime = '';
        var operateRecord = '';
        for (var key in json[i]) {
            if (key == 'createTime') {
                createTime = json[i][key];
            }
            if (key == 'operateRecord') {
                operateRecord = json[i][key];
            }
            console.log(key + ':' + json[i][key]);
        }
        html += '<li class="layui-timeline-item">\n' +
            '            <i class="layui-icon layui-timeline-axis">&#xe63f;</i>\n' +
            '            <div class="layui-timeline-content layui-text">\n' +
            '                <h3 class="layui-timeline-title">' + createTime + '</h3>\n' +
            '                <p>' + operateRecord + '</p>\n' +
            '            </div>\n' +
            '        </li>';
    }
    return html;
}