layui.config({
    base: '/dispatch/ui/src/js/modules/' //假设这是你存放拓展模块的根目录
}).extend({ //设定模块别名
    myutil: 'common' //如果 mymod.js 是在根目录，也可以不用设定别名
});

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
    baseUrl: "/dispatch/service/systemDaily/userAnalysisReport",
    paramUrl: "/dispatch/service/researchParam/",
    uiUrl: "edit.html",
    tableId: "business",
    toolbarId: "#toolbar",
    unique: "id",
    order: "asc",
    currentItem: {},
    count: 0
};

//表头
business.cols = function () {
    return [ //表头
        //人员姓名，工号，系统，业务线，接单次数，平均处理时长(需要添加字段)，累计处理时长
        {
            field: 'userName',
            align: 'center',
            width: '15%',
            title: '人员姓名'
        }
        ,
        {
            field: 'jobNumber',
            align: 'center',
            width: '15%',
            title: '工号'
        }
        ,
        {
            field: 'businessSystemName',
            align: 'center',
            width: '20%',
            title: '归属系统'
        },
        {
            field: 'businessTypeName',
            align: 'center',
            width: '20%',
            title: '业务线'
        },
        {
            field: 'orderTotalTime',
            align: 'center',
            width: '10%',
            title: '累计处理时长'
        },
        {
            field: 'orderCount',
            align: 'center',
            title: '接单次数',
            width: '10%',
            templet: "#statusTpl"
        },
        {
            field: 'avgHandleTime',
            align: 'center',
            width: '10%',
            title: '平均处理时长',
            templet: "#statusTpl",
        }
    ];
};
var layerTopIndex;
layui.config({
    base: '/dispatch/ui/src/js/modules/' //假设这是你存放拓展模块的根目录
}).extend({ //设定模块别名
    myutil: 'common' //如果 mymod.js 是在根目录，也可以不用设定别名
});

function getBeforeDate(n){
    var n = n;
    var d = new Date();
    var year = d.getFullYear();
    var mon=d.getMonth()+1;
    var day=d.getDate();
    if(day <= n){
        if(mon>1) {
            mon=mon-1;
        }
        else {
            year = year-1;
            mon = 12;
        }
    }
    d.setDate(d.getDate()-n);
    year = d.getFullYear();
    mon=d.getMonth()+1;
    day=d.getDate();
    s = year+"-"+(mon<10?('0'+mon):mon)+"-"+(day<10?('0'+day):day);
    return s;
}
var date_in = getBeforeDate(7)+' - '+ getBeforeDate(0);
layui.use(['table','form','laytpl','myutil','element', 'laydate'], function(){
    var element = layui.element;
    var laydate = layui.laydate;
    layer = layui.layer;
    table = layui.table;
    entityTable = layui.table;
    businessTable = layui.table;
    var laytpl = layui.laytpl;
    var app = layui.app,
        $ = layui.jquery
        ,form = layui.form;
    var common = layui.myutil;
    //日期范围
    laydate.render({
        elem: '.date'
        , range: true
        ,value: date_in
    });


//业务线 系统级联
    $(".layui-this").click();

    common.business.init4chirden('',$("#businessDiv"),'selectSysId');

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

    //按钮权限控制隐藏
    businessTable.on('renderComplete(' + business.tableId + ')', function (obj) {

    });

    //第一个实例
    businessTable.render({
        elem: '#' + business.tableId
        , method: 'post'
        , height: 'full'+200
        , url: business.baseUrl+"/back"
        , page: false //开启分页
        , cols: [business.cols()]
        ,width: window.innerWidth - 30,
    });

    //重载
    active = {
        reload: function () {
            //执行重载
            var time = $("#timer").val();
            var beginTime = time.substring(0, 10);
            var endTime = time.substring(13, 24);

            businessTable.reload(business.tableId, {
                page: false
                , where: {
                    "businessSystemId": $("#businessId").val(),
                    "businessTypeId": $("#businessId_son").val(),
                    "orderStatus": $("#status").val(),
                    "beginTime": beginTime,
                    "endTime": endTime
                }
            });
        }
    };

});