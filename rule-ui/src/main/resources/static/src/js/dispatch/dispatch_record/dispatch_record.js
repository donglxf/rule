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
    baseUrl: "/dispatch/service/systemDaily/",
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
            field: 'businessSystemName',
            align: 'center',
            title: '类型', sort: true
        }
        ,
        {
            field: 'userNumber',
            align: 'center',
            title: '接单人数'
        }
        ,
        {
            field: 'autoDispatchOrderNumber',
            align: 'center',
            title: '自动派单次数'
        },
        {
            field: 'manualDispatchOrderNumber',
            align: 'center',
            width: 180,
            title: '手动派单次数', sort: true
        },
        {
            field: 'totalDispatchOrderNumber',
            align: 'center',
            width: 180,
            title: '累计派单次数', sort: true
        }
        , {
            field: 'dispatchType',
            align: 'center',
            width: 180,
            title: '累计派单天数', sort: true
        },
        {
            field: 'businessId',
            title: '操作',
            fixed: 'right',
            align: 'center',
            width: 300,
            toolbar: business.toolbarId
        }
    ];
};
var layerTopIndex;
layui.config({
    base: '/dispatch/ui/src/js/modules/' //假设这是你存放拓展模块的根目录
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
    //2018-05-01 - 2018-06-12

    var whereCondition = {
        "beginTime": beginTime,
        "endTime": endTime
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
    //第一个实例
    businessTable.render({
        elem: '#' + business.tableId
        , height: 'full'
        // , cellMinWidth: 10
        , url: business.baseUrl + 'list' //数据接口
        // data:[{"businessId":1,"businessName":"测试规则","businessDesc":"测试规则引擎","businessIdentify":"testrule","pkgName":"com.sky.testrule","creUserId":1,"creTime":1500522092000,"isEffect":1,"remark":null}]
        , page: true //开启分页
        , id: business.tableId
        , cols: [business.cols()]
        , method: 'post'
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
        if (obj.event === 'detail') {
            layer.msg('ID：' + data.id + ' 的查看操作');
        } else if (obj.event === 'close_order') {
            layer.confirm('确认关闭订单？', function (index) {
                $.get(business.baseUrl + 'delete?id=' + data.businessId, function (data) {
                    if (data.code < 0) {
                        layer.msg('删除失败，该数据正在被其他数据引用', {icon: 5});
                        layer.close(index);
                    } else {
                        layer.msg("删除成功！");
                        obj.del();
                        layer.close(index);
                    }

                }, 'json');
            });
        } else if (obj.event === 'edit') {
            edit(data.businessSystemId);
        } else if (obj.event === 'manual_dispatch') {
            manualDispatch(data.orderMainInfoId);
        }
    });
    //新增
    $("#scene_btn_add").on('click', function () {
        save(business.uiUrl, null);
    });

    //查看订单派送详情
    function edit(businessSystemId) {
        var url = "../user_record/user_record_list.html?businessSystemId=" + businessSystemId;
        var openIndex = layer.open({
            type: 2,
            area: ['95%', '95%'],
            full: true,
            fixed: false,
            maxmin: true,
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

    //按钮权限控制隐藏，页面大按钮
    ht_auth.render();
});