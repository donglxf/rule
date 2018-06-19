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
    baseUrl: "/dispatch/service/user/",
    paramUrl: "/dispatch/service/researchParam/",
    tableId: "business",
    toolbarId: "#toolbar",
    count: 0,
    limit: 1,
    curr: 1
};

//表头
business.cols = function () {
    return [
        {
            field: 'userName',
            align: 'center',
            width: 150,
            title: '姓名', sort: true
            , fixed: 'left'
        }
        ,
        {
            field: 'remoteUserId',
            align: 'center',
            width: 150,
            title: '用户业务ID'
        },
        {
            field: 'businessSystemName',
            align: 'center',
            width: 150,
            title: '归属系统'
        }
        ,
        {
            field: 'businessTypeName',
            align: 'center',
            width: 150,
            title: '业务线'
        }
        ,
        {
            align: 'center',
            title: '用户状态',
            width: 100,
            templet: "#userStatusTpl"
        },
        {
            field: 'avgHandleOrderCount',
            align: 'center',
            width: 150,
            title: '日处理订单数', sort: true
        },
        {
            field: 'totalStatisticsDay',
            align: 'center',
            width: 150,
            title: '统计天数', sort: true
        }
        , {
            field: 'totalOrder',
            align: 'center',
            width: 150,
            title: '总订单数', sort: true
        },
        {
            field: 'avgHandleTime',
            align: 'center',
            width: 150,
            title: '订单平均处理时长', sort: true
        },
        {
            field: 'roleCode',
            align: 'center',
            width: 150,
            title: '角色编号', sort: true
        }
        ,
        {
            field: 'referenceAvgHandleOrderCount',
            align: 'center',
            title: '参考日接单数',
            sort: true,
            fixed: 'right'
        }
        // ,
        // {
        //     field: 'businessId',
        //     title: '操作',
        //     fixed: 'right',
        //     align: 'center',
        //     width: 300,
        //     toolbar: business.toolbarId
        // }
    ];
};
var layerTopIndex;
layui.config({
    base: '/src/js/modules/' //假设这是你存放拓展模块的根目录
}).extend({ //设定模块别名
    myutil: 'common' //如果 mymod.js 是在根目录，也可以不用设定别名
});

layui.use(['table', 'form', 'laytpl', 'myutil', 'ht_auth', 'laydate'], function () {
    laydate = layui.laydate;
    var laytpl = layui.laytpl;
    var laypage = layui.laypage;
    var table = layui.table;
    var common = layui.myutil;
    businessTable = layui.table;
    var app = layui.app,
        ht_auth = layui.ht_auth,
        form = layui.form;
    $ = layui.jquery;
    //查询按钮

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
    //第一个实例
    businessTable.render({
        elem: '#' + business.tableId
        , method: 'post'
        , height: 'full'
        // , cellMinWidth: 10
        , url: business.baseUrl + 'selectUserList/back'
        // data:[{"businessId":1,"businessName":"测试规则","businessDesc":"测试规则引擎","businessIdentify":"testrule","pkgName":"com.sky.testrule","creUserId":1,"creTime":1500522092000,"isEffect":1,"remark":null}]
        , page: true //开启分页
        // , id: business.tableId
        , width: window.innerWidth - 30
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
            where: whereCondition
        })
    });


    // //渲染下拉框
    // $.get(business.paramUrl + "getParamList", function (response) {
    //     var result = response.data;
    //     if (result) {
    //         if (result.businessSystemList) {
    //             SetControlData($("#businessSystemList"), result.businessSystemList, false, "请选择");
    //         }
    //         if (result.businessTypeList) {
    //             SetControlData($("#businessTypeList"), result.businessTypeList, false, "请选择");
    //         }
    //     }
    //     form.render('select');
    // }, 'json');

    //common.business.init4chirden('',$("#businessSystemList"));

    common.business.init4chirden('',$("#businessSystemList"),'selectSysId');
    form.render();
});
