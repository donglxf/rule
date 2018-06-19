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
    baseUrl: "/dispatch/service/group/",
    paramUrl: "/dispatch/service/researchParam/",
    uiUrl: "edit.html",
    tableId: "business",
    toolbarId: "#toolbar",
    unique: "id",
    order: "asc",
    currentItem: {},
    count: 0,
    limit: 3,
    curr: 1
};

//表头
business.cols = function () {
    return [ //表头
        //{field: 'businessId',  event: 'setItem',title: 'ID',sort: true, fixed: 'left'}
        {
            field: 'groupName',
            align: 'center',
            width: '8%',
            title: '组名'
        },
        {
            field: 'groupCode',
            align: 'center',
            width: '8%',
            title: '小组编码',
            fixed: 'left'
        }
        ,

        {
            field: 'isValid',
            align: 'center',
            title: '是否有效',
            width: '6%',
            templet: '#isValid'
        }
        ,
        {
            field: 'businessSystemName',
            align: 'center',
            width: '6%',
            title: '归属系统'
        },
        {
            field: 'businessTypeName',
            align: 'center',
            width: '8%',
            title: '业务线'
        },
        {
            field: 'status',
            align: 'center',
            title: '状态',
            width: '6%',
            templet: "#statusTpl"
        }
        ,
        {
            field: 'numerOfUser',
            align: 'center',
            width: '6%',
            title: '总人数',
        },
        {
            field: 'roleCode',
            align: 'center',
            width: '6%',
            title: '角色编号'
        }
        ,
        {
            field: 'totalOrder',
            align: 'center',
            width: '8%',
            title: '总处理订单数'
        }
        , {
            field: 'avgOrderPerDay',
            align: 'center',
            width: '8%',
            title: '日完成订单数'
        }
        , {
            field: 'avgOrderPerDayPerUser',
            align: 'center',
            width: '8%',
            title: '日人均处理订单'
        }
        , {
            field: 'avgTimePerOrder',
            align: 'center',
            width: '10%',
            title: '订单平均处理时长'
        }
        ,
        {
            field: 'businessId',
            title: '操作',
            fixed: 'right',
            align: 'center',
            width: '12%',
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

    var whereCondition = {
        "belongSystemId": $("#belongSystemId").val(),
        "businessTypeId": $("#businessTypeId").val(),
        "contractId": $("#contractId").val(),
        "status": $("#orderStatus").val(),
        "beginTime": $('#timerBegin').val(),
        "endTime": $('#timerStop').val()
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
    var laypage = layui.laypage;
    var table = layui.table;
    var businessTable = layui.table;
    var common = layui.myutil;
    var app = layui.app,
        ht_auth = layui.ht_auth,
        form = layui.form;
    $ = layui.jquery;

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


    //按钮权限控制隐藏
    businessTable.on('renderComplete(' + business.tableId + ')', function (obj) {
        ht_auth.render();
    });
    //监听工具条
    businessTable.on('tool(' + business.tableId + ')', function (obj) {
        var data = obj.data;
        var groupId = data.groupId;
        var businessSystemId = data.businessSystemId;
        if (obj.event === 'edit') {
            edit(data.groupId);
        } else if (obj.event === 'del') {
            layer.confirm('确认删除小组？', function (index) {
                $.ajax({
                    type: "POST",
                    url: business.baseUrl + 'deleteGroup',
                    data: JSON.stringify({groupId: data.groupId, isValid: '0'}),
                    contentType: "application/json; charset=utf-8",
                    success: function (result) {
                        if (result.code == 0) {
                            layer.msg("删除成功")
                        } else {
                            layer.msg("删除失败")
                        }
                    }
                });
            });
        } else if (obj.event === 'edit') {
            edit(data.orderMainInfoId);
        } else if (obj.event === 'showMember') {
            showMember(groupId, businessSystemId)
        }
    });

    //成员信息
    function showMember(groupId, businessSystemId) {
        var url = "../dispatch_group_user/user_list.html?groupId=" + groupId + "&businessSystemId=" + businessSystemId;
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


    //新增小组
    $("#add-btn").click(function () {
        edit();
    });

    //新增小组
    function edit(groupId) {
        var url = "dispatch_group_edit.html";
        if (groupId != undefined && groupId != '') {
            url = "dispatch_group_edit.html?groupId=" + groupId;
        }
        var openIndex = layer.open({
            type: 2,
            area: ['800px', '95%'],
            full: true,
            fixed: false,
            maxmin: true,
            title: '小组信息',
            content: url,
            success: function (result) {

            }
        });
    }


    //按钮权限控制隐藏，页面大按钮
    ht_auth.render();

    //第一个实例
    businessTable.render({
        elem: '#' + business.tableId
        , method: 'post'
        , height: 'full'
        , url: business.baseUrl + 'selectGroupList/back'
        , page: true //开启分页
        , cols: [business.cols()]
        , width: window.innerWidth - 30,
    });

    //重载
    active = {
        reload: function () {
            //执行重载
            var businessSystemId = $("#selectSysId").val();
            var businessTypeId = $("#selectSysId_son").val();
            businessTable.reload(business.tableId, {
                page: {
                    curr: 1 //重新从第 1 页开始
                }
                , where: {
                    businessSystemId: businessSystemId,
                    businessTypeId: businessTypeId
                }
            });
        }
    };

    $('#search-btn').on('click', function () {
        var whereCondition = {
            "businessSystemId": $("#selectSysId").val(),
            "businessTypeId": $("#selectSysId_son").val(),
        };
        table.reload(business.tableId, {
            page: {
                curr: 1
            },
            where: whereCondition
        })
    });

    //渲染下拉框
    common.business.init4chirden('', $("#businessSystemList"), 'selectSysId');
});
