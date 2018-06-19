layui.config({
    base: '/src/js/modules/' //假设这是你存放拓展模块的根目录
}).extend({ //设定模块别名
    myutil: 'common' //如果 mymod.js 是在根目录，也可以不用设定别名
});
var reload ;
layui.use(['table','form','laytpl','myutil','element', 'laydate'], function(){
    var element = layui.element;
    var laydate = layui.laydate;
    layer = layui.layer;
    table = layui.table;
    entityTable = layui.table;
    itemTable = layui.table;
    var laytpl = layui.laytpl;
    var app = layui.app,
        $ = layui.jquery
        ,form = layui.form;
    var common = layui.myutil;


    //日期范围
    laydate.render({
        elem: '#date_sys'
        , range: true
        ,value: date_in
    });
    laydate.render({
        elem: '#date_all'
        , range: true
        ,value: date_in
    });
    laydate.render({
        elem: '#data_business'
        , range: true
        ,value: date_in
    });


    reload = function () {
        //同步加载 单量统计数据
        var time  = $('#date_all').val()||date_in;
        var startDate = time.split(" - ")[0];
        var endDate = time.split(" - ")[1];
        var param = {
            "beginTime":startDate,
            "endTime":endDate,
            "dispatchType":$("#status").val(),
            businessSystemId:$("#businessId").val(),
            businessTypeId:$("#businessId_son").val(),


        };
        $.ajax({
            url:urlMap.orderUrl, //处理页面的路径
            data:JSON.stringify(param), //要提交的数据是一个JSON
            type:"POST", //提交方式
            async:false,

            contentType: 'application/json; charset=utf-8',
            dataType:"JSON", //返回数据的类型
            //TEXT字符串 JSON返回JSON XML返回XML
            success:function(data){ //回调函数 ,成功时返回的数据存在形参data里
                var result = data.data;
                myChart3.setOption({
                    xAxis: {
                        data: result.statisticsDateList
                    },
                    series: [{
                        data: result.totalOrderCountList
                    },
                        {
                            data: result.finishOrderCountList
                        },
                        {
                            data: result.closeOrderCountList
                        }
                    ]
                });

                //表格渲染
                //表格
                var dax = result.statisticsDateList;
                var data1 = result.totalOrderCountList;
                var data2 = result.finishOrderCountList;
                var data3 =  result.closeOrderCountList;
                function changeData(dax,data1,data2,data3){
                    var data = [];
                    var sum ={
                        time:'合计',new:0,ok:0,close:0
                    }
                    var news,oks,closes = 0;
                    for(var i=0;i<dax.length;i++){
                        var dd = {
                            time:dax[i],
                            new:data1[i],
                            ok:data2[i],
                            close:data3[i],
                        };
                        sum.new += parseInt(dd.new);
                        sum.ok += parseInt(dd.ok);
                        sum.close += parseInt(dd.close);
                        data.push(dd);
                    }
                    data.splice(0, 0, sum);
                    //  data.push(sum);
                    return data;

                }
                var data = changeData(dax,data1,data2,data3);

                table.render({
                    skin: 'line' //行边框风格
                    ,even: true //开启隔行背景
                    ,elem: '#test'
                    // ,width: dom3.style.width
                    ,data: data
                    // ,cellMinWidth: '25%' //全局定义常规单元格的最小宽度，layui 2.2.1 新增
                    ,cols: [[
                        {field:'time',  align: 'center',title: '日期', sort: true}
                        ,{field:'new', style:'color:#01AAED',  align: 'center',title: '新建', sort: true}
                        ,{field:'ok',   style:'color:#01AAED', align: 'center',title: '完成', sort: true}
                        ,{field:'close',style:'color:red',   align: 'center',  title: '关闭', sort: true}
                    ]]
                    , page: false
                });

            }
        });
    }


    //数据转化
    reload();
//初始化
    $(".layui-this").click();

    common.business.init4chirden('',$("#businessDiv"));
    common.business.init4sys('',$("#businessDiv2"),'selectSysId');


});