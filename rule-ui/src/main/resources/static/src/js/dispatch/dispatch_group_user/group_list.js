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
    limit: 10,
    curr: 1
};

//表头
business.cols = function () {
    return [ //表头
        //{field: 'businessId',  event: 'setItem',title: 'ID',sort: true, fixed: 'left'}
        {
            field: 'groupCode',
            align: 'center',
            title: '小组编码',
            sort: true,
            fixed: 'left'
        }
        ,
        {
            field: 'groupName',
            align: 'center',
            title: '组名'
        },
        {
            field: 'isValid',
            align: 'center',
            title: '是否有效',
            templet: "#isValidTpl"
        }
        ,
        {
            field: 'businessSystemName',
            align: 'center',
            title: '归属系统'
        },
        {
            field: 'businessTypeName',
            align: 'center',
            width: 180,
            title: '业务线', sort: true
        },
        {
            field: 'status',
            align: 'center',
            title: '状态',
            templet: "#statusTpl"

        }
        , {
            field: 'avgOrderPerDayPerUser',
            align: 'center',
            width: 200,
            title: '人均日处理订单', sort: true
        },
        {
            field: 'numerOfUser',
            align: 'center',
            title: '总人数', sort: true
        },
        {
            field: 'roleCode',
            align: 'center',
            title: '角色编号', sort: true
        }
        ,
        {
            field: 'totalOrder',
            align: 'center',
            width: 180,
            title: '总处理订单数', sort: true
        }
        , {
            field: 'avgOrderPerDay',
            align: 'center',
            width: 180,
            title: '日完成订单数', sort: true
        }
        ,
        {
            field: 'businessId',
            fixed: 'right',
            align: 'center',
            width: 100,
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

layui.use(['table', 'form', 'laytpl', 'myutil', 'ht_auth', 'laydate'], function () {
    laydate = layui.laydate;
    var table = layui.table;
    businessTable = layui.table;
    var common = layui.myutil;
    var app = layui.app,
        ht_auth = layui.ht_auth,
        form = layui.form;
    $ = layui.jquery;

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
        // alert(JSON.stringify(data));
        if (obj.event === 'edit') {
            edit(groupId, businessSystemId);
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
        }
    });
    //新增
    $("#scene_btn_add").on('click', function () {
        save(business.uiUrl, null);
    });

    //管理小组成员
    function edit(groupId, businessSystemId) {
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
    });
    //重载
    //这里以搜索为例
    active = {
        reload: function () {
            //执行重载
            var businessSystemId = $('#businessSystemList').val();
            var businessTypeId = $('#businessTypeList').val();
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
            where:whereCondition
        })
    });

    //渲染下拉框
    common.business.init4chirden('',$("#businessSystemList"),'selectSysId');

});
