/**
 * 设置表单值
 * @param el
 * @param data
 */
function setFromValues(el, data) {
    for (var p in data) {
        el.find(":input[name='" + p + "']").val(data[p]);
    }
}

var business = {
    baseUrl: "/dispatch/service/orderMainInfo/",
    orderDetailUrl: "/dispatch/service/orderDetail/",
    paramUrl: "/dispatch/service/researchParam/",
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
            field: 'contractId',
            align: 'center',
            width: '17%',
            title: '合同ID'
        },
        {
            field: 'businessSystemName',
            align: 'center',
            width: '13%',
            title: '归属系统'
        }
        ,
        {
            field: 'businessTypeName',
            align: 'center',
            width: '10%',
            title: '归属业务线'
        }
        ,
        {
            field: 'status',
            align: 'center',
            width: '8%',
            title: '状态',
            templet: '#statusTpl'
        }, {
            field: 'dispatchType',
            align: 'center',
            width: '15%',
            title: '订单指派方式',
            templet: '#dispatchTypeTpl'
        },
        {
            field: 'roleCode',
            align: 'center',
            width: '7%',
            title: '角色编号'
        },
        // {
        //     field: 'dispatchToUser',
        //     align: 'center',
        //     width: '15%',
        //     title: '固定指派人'
        // },
        {
            field: 'createTime',
            align: 'center',
            width: '10%',
            title: '创建时间'
        }
        ,
        {
            field: 'businessId',
            title: '操作',
            align: 'center',
            width: '20%',
            toolbar: business.toolbarId
        }
    ];
};
var layerTopIndex;
layui.config({
    base: '/src/js/modules/' //假设这是你存放拓展模块的根目录
}).extend({ //设定模块别名
    myutil: 'common' //如果 mymod.js 是在根目录，也可以不用设定别名
});


//时间控件初始化
business.InitControl = function () {
    laydate.render({
        elem: '#timerBegin', //指定元素
        done: function (value) {
            if ($('#timerStop').val() != "") {
                if (value > $('#timerStop').val()) {
                    $('#timerBegin').val("");
                    layer.alert("开始时间不能大于结束时间!");
                }
            }
        }
    });
    laydate.render({
        elem: '#timerStop', //指定元素
        done: function (value) {
            if ($('#timerBegin').val() != "") {
                if (value < $('#timerBegin').val()) {
                    $('#timerStop').val("");
                    layer.alert("结束时间不能小于开始时间!");
                }
            }
        }
    });
};

business.loadPage = function (table) {

    var time = $("#test1").val();
    var beginTime = time.substring(0, 10);
    var endTime = time.substring(13, 24);

    var whereCondition = {
        "businessSystemId": $("#selectSysId").val(),
        "businessTypeId": $("#selectSysId_son").val(),
        "contractId": $("#contractId").val(),
        "status": $("#orderStatus").val(),
        "beginTime": beginTime,
        "endTime": endTime,
        "status": $('#status').val()
    };
    table.reload(business.tableId, {
        page: {
            curr: 1
        },
        where: whereCondition
    })
};


layui.use(['table', 'form', 'laytpl', 'myutil', 'ht_auth', 'laydate'], function () {
    laydate = layui.laydate;
    var laytpl = layui.laytpl;
    var table = layui.table;
    businessTable = layui.table;
    var common = layui.myutil;
    var app = layui.app,
        ht_auth = layui.ht_auth,
        form = layui.form;
    $ = layui.jquery;

    laydate.render({
        elem: '.date'
        , range: true
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


    //时间控件初始化
    business.InitControl();

    /**
     * 公共方法：保存
     * @param url
     * @param result
     */
    function save(url, result) {
        $.get(url, function (form) {
            layerTopIndex = layer.open({
                type: 1,
                title: '保存信息',
                maxmin: true,
                shadeClose: false, // 点击遮罩关闭层
                area: ['550px', '460px'],
                content: form,
                btnAlign: 'c',
                btn: ['保存', '取消'],
                success: function (layero, index) {
                    setFromValues(layero, result);
                }
                , yes: function (index) {
                    //触发表单的提交事件
                    $('form.layui-form').find('button[lay-filter=formDemo]').click();
                    // layer.close(index);
                },
            });
        });
    }


    //第一个实例
    businessTable.render({
        elem: '#' + business.tableId
        , method: 'post'
        , height: 'full'
        // , cellMinWidth: 10
        , url: business.baseUrl + 'page/back' //数据接口
        // data:[{"businessId":1,"businessName":"测试规则","businessDesc":"测试规则引擎","businessIdentify":"testrule","pkgName":"com.sky.testrule","creUserId":1,"creTime":1500522092000,"isEffect":1,"remark":null}]
        , page: true //开启分页
        , id: business.tableId
        , width: window.innerWidth - 30
        , cols: [business.cols()]
    });
    //重载
    //这里以搜索为例
    active = {
        reload: function () {
            //执行重载
            businessTable.reload(business.tableId, {
                page: {
                    curr: 1 //重新从第 1 页开始
                }
                , where: {
                    key: $("#key").val()
                }
            });
        }
    };
    //按钮权限控制隐藏
    businessTable.on('renderComplete(' + business.tableId + ')', function (obj) {
        ht_auth.render();
    });
    //监听工具条
    businessTable.on('tool(' + business.tableId + ')', function (obj) {
        var data = obj.data;
        var orderMainInfoId = data.orderMainInfoId;
        var businessSystemId = data.businessSystemId;
        var businessTypeId = data.businessTypeId;
        var contractId = data.contractId;
        var askFromDispatch = "dispatch";
        var requestDto = {
            orderMainInfoId: orderMainInfoId,
            businessSystemId: businessSystemId,
            businessTypeId: businessTypeId,
            askFromDispatch: askFromDispatch,
            contractId: contractId
        };
        console.log(obj);
        if (obj.event === 'detail') {
            layer.msg('ID：' + data.id + ' 的查看操作');
        } else if (obj.event === 'close_order') {
            layer.confirm('确认关闭订单？', function (index) {
                $.ajax({
                    type: "POST",
                    url: business.baseUrl + 'closeDispatch',
                    data: JSON.stringify(requestDto),
                    contentType: "application/json; charset=utf-8",
                    success: function (result) {
                        layer.close(index);
                        layer.alert(result.msg)
                        //更新缓存
                        // obj.update({
                        //     status: '4'
                        // });
                        active.reload();
                    }
                });
            });
        } else if (obj.event === 'edit') {
            edit(data.orderMainInfoId);
        } else if (obj.event === 'manual_dispatch') {
            manualDispatch(requestDto);
        } else if (obj.event === 'contractDetail') {
            contractDetail(requestDto);
        }
    });
    //新增
    $("#scene_btn_add").on('click', function () {
        save(business.uiUrl, null);
    });

    //查看合同详情
    function contractDetail(requestDto) {
        var businessSystemId = requestDto.businessSystemId;
        var businessTypeId = requestDto.businessTypeId;
        var contractId = requestDto.contractId;
        var url = "../contract_time_line/list.html?businessSystemId=" + businessSystemId + "&businessTypeId=" + businessTypeId + "&contractId=" + contractId;
        var openIndex = layer.open({
            type: 2,
            area: ['100%', '100%'],
            full: true,
            fixed: true,
            scrollbar: false,
            maxmin: true,
            title: '合同详情',
            content: url,
            //btn: ['保存', '取消'],
            yes: function (index, layero) {
                var openwin = $(layero).find("iframe")[0].contentWindow;
                if (openwin) {
                    var openPage = openwin.Cobject;
                    if (openPage && openPage.save) {
                        openPage.save(function () {
                            layer.msg('保存数据成功', {icon: 1, time: 1000});
                            layer.close(openIndex);
                            processTypeList.loadPage();
                        });
                    }
                }
            }
        });
    }

    //查看订单派送详情
    function edit(orderMainInfoId) {
        var url = "../order_detail/order_detail_list.html?orderMainInfoId=" + orderMainInfoId;
        var openIndex = layer.open({
            type: 2,
            area: ['100%', '100%'],
            full: true,
            fixed: false,
            maxmin: true,
            scrollbar: false,
            title: '派单记录',
            content: url,
            //btn: ['保存', '取消'],
            yes: function (index, layero) {
                var openwin = $(layero).find("iframe")[0].contentWindow;
                if (openwin) {
                    var openPage = openwin.Cobject;
                    if (openPage && openPage.save) {
                        openPage.save(function () {
                            layer.msg('保存数据成功', {icon: 1, time: 1000});
                            layer.close(openIndex);
                            processTypeList.loadPage();
                        });
                    }
                }
            }
        });
    }

    //手动指派
    function manualDispatch(requestDto) {
        var orderMainInfoId = requestDto.orderMainInfoId;
        var businessSystemId = requestDto.businessSystemId;
        var businessTypeId = requestDto.businessTypeId;
        var askFromDispatch = requestDto.askFromDispatch;
        var url = "../order_main_info/manual_dispatch.html?businessSystemId=" + businessSystemId + '&orderMainInfoId=' + orderMainInfoId + '&businessTypeId=' + businessTypeId + '&askFromDispatch=' + askFromDispatch;
        var openIndex = layer.open({
            type: 2,
            area: ['30%', '35%'],
            full: true,
            fixed: false,
            maxmin: true,
            scrollbar: false,
            // title: '手动指派',
            content: url,
            //btn: ['保存', '取消'],
            yes: function (index, layero) {
                var openwin = $(layero).find("iframe")[0].contentWindow;
                if (openwin) {
                    var openPage = openwin.Cobject;
                    if (openPage && openPage.save) {
                        openPage.save(function () {
                            layer.msg('保存数据成功', {icon: 1, time: 1000});
                            layer.close(openIndex);
                            processTypeList.loadPage();
                        });
                    }
                }
            }
        });
    }

    //按钮权限控制隐藏，页面大按钮
    ht_auth.render();

    //渲染下拉框
    common.business.init4chirden('', $("#businessSystemList"), 'selectSysId');

});
