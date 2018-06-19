///////////////////////////////////////////////////////////////////////
var preUrl = "/dispatch/service/droolsLog/";
var p_logId = 0;
layui.config({
    base: '/src/js/modules/' //假设这是你存放拓展模块的根目录
}).extend({ //设定模块别名
    myutil: 'common' //如果 mymod.js 是在根目录，也可以不用设定别名

});
layui.use(['table', 'form','laydate','util','myutil'], function () {
    var layer = layui.layer;
   var table = layui.table;
   var laydate =  layui.laydate;
    var entityTable = layui.table,modelTable=layui.table;
    var app = layui.app, $ = layui.jquery, form = layui.form;
    var comon = layui.myutil;
    var config = comon.config;
    var preItemUrl = config.ruleServicePath.basePath;
    var preUrl = config.ruleServicePath.basePath;
    //日期范围
    laydate.render({
        elem: '.date'
        , range: true
    });
//初始化

    comon.business.init4chirden('',$("#businessDiv"));
    // 第一个实例
    entityTable.render({
        elem: '#demo'
        ,height: 550
        ,url: preUrl + 'droolsLog/page' //数据接口
        ,page: true //开启分页
        ,id:'demos'
        ,cols: [[ // 表头
            {
                field: 'id',
                event: 'setItem',
                title: 'id'
            },{
                field: 'createTime',
                event: 'setItem',
                title: '触发时间',
                templet: '#createTime'
            }, {
                field: 'senceVersionid',
                event: 'setItem',
                title: '决策版本号'
            }, {
                field: 'inParamter',
                event: 'setItem',
                title: '入参'
            }, {
                field: 'outParamter',
                event: 'setItem',
                title: '计算结果'
            }, {
                field: 'type',
                title: '调用方',
                align: 'center',
                templet: '#checkboxTpl'
            }]]
    });

    modelTable.render({
        elem: '#modelTab'
        ,height: 550
        ,cellMinWidth: 80
        ,url: preItemUrl+'ruleDpUserLog/page' //数据接口
        ,page: true //开启分页
        ,id:'modelTab'
        ,cols: [[ // 表头
            {
                field: 'dpUserLogId',
                event: 'setItem',
                title: '流水号'
            }, {
                field: 'orderNum',
                event: 'setItem',
                title: '派单号'
            }, {
                field: 'senceVersionid',
                event: 'setItem',
                title: '版本id',
                fixed: 'right'
            },
            {
                field: 'selectUserGrade',
                event: 'setItem',
                title: '结果'
            },
            {
                field: 'selectUser',
                event: 'setItem',
                title: '派送人'
            },
            {
                field: 'inParamter',
                event: 'setItem',
                title: '入参'
            }, {
                field: 'outParamter',
                event: 'setItem',
                title: '出参'
            },
            {
                field: 'executeTime',
                event: 'setItem',
                title: '花费时间'
            }, {
                field: 'createTime',
                title: '创建时间',
                align: 'center',
                templet: '#createTime'
            }
            ]]
    });

    // 重载
    // 这里以搜索为例
    active = {
        reload: function () {
            // var demoReload = $('#demoReload');
            var startDate=$('#startDate').val();
            var endDate=$('#endDate').val();
            var d1 = new Date(startDate.replace(/\-/g, "\/"));
            var d2 = new Date(endDate.replace(/\-/g, "\/"));
            if(startDate!=""&&endDate!=""&& d1 >= d2)
            {
                alert("开始时间不能大于等于结束时间！");
                return false;
            }
            // 执行重载
            table.reload('demos', {
                page: {
                    curr: 1
                    // 重新从第 1 页开始
                },
                where: {
                    date: startDate,
                    endDate: endDate,
                    logId:$('#logId').val()
                }
            });
        }
    };

    // 重载
    // 这里以搜索为例
    modelActive = {
        reload: function () {
            // var demoReload = $('#demoReload');
            var type=$('#type').val();
            var startDate=$('#startTime').val();
            var endDate=$('#timerStop').val();
            var d1 = new Date(startDate.replace(/\-/g, "\/"));
            var d2 = new Date(endDate.replace(/\-/g, "\/"));
            if(startDate!=""&&endDate!=""&& d1 > d2)
            {
                alert("开始时间不能大于结束时间！");
                return false;
            }
            // 执行重载
            table.reload('modelTab', {
                page: {
                    curr: 1
                    // 重新从第 1 页开始
                },
                where: {
                    date: startDate,
                    endDate:endDate ,
                    type:type,
                    modId:$('#modId').val()
                }
            });
        }
    };

    $('.demoTable .layui-btn').on('click', function () {
        var type = $(this).data('type');
        active[type] ? active[type].call(this) : '';
    });

    entityTable.on('tool(entityTable)',function(obj){
        var data = obj.data;
        if (obj.event === 'view'){
            edit(data.id);
        }
    });

    modelTable.on('tool(modelTable)',function(obj){
        var data = obj.data;
        if (obj.event === 'view'){
            modelEdit(data.procInstId,data.type,data.id);
        }
    });



    function modelEdit(procInstId,type,taskId) {
        console.log(procInstId);
        $("#procInstId_hidden_input").val(procInstId);
        $("#taskId_hidden_input").val(taskId);
        $("#type_hidden_input").val(type);
        console.log(type);
        var layIndex =layer.open({
            type: 2,
            shade: false,
            area: ['1000px', '600px'],
            title: "模型验证结果",
            //请求的弹出层路径
            content: "model/verfication/log/modelLogDetail",
            zIndex: layer.zIndex, //重点1
            success: function (layero, index) {
                layer.setTop(layero); //重点2
            }
        });
    }

    function edit(logId) {
        p_logId=logId;
            layer.open({
                type : 2,
                title : '查看详情',
                maxmin : true,
                shadeClose : false, // 点击遮罩关闭层
                area : [ '730px', '460px' ],
                content : '/supervision/log/view',
                btn : [ '关闭'],
                btnAlign : 'c',
                zIndex: layer.zIndex, //重点1
                success : function(da, index) {

                    layer.setTop(da); //重点2
                }

        });
    }

});


