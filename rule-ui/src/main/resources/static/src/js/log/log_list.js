///////////////////////////////////////////////////////////////////////
var preUrl = "/dispatch/service/droolsLog/";
var p_logId = 0;
layui.config({
    base: '/src/js/modules/' //假设这是你存放拓展模块的根目录
}).extend({ //设定模块别名
    myutil: 'common' //如果 mymod.js 是在根目录，也可以不用设定别名

});

layui.use(['table', 'form','laydate','util','myutil','code'], function () {
    var layer = layui.layer;
    var table = layui.table;
    var laydate =  layui.laydate;
   var userTable = layui.table,logTable=layui.table,userActive;
    var app = layui.app, $ = layui.jquery, form = layui.form;
    var comon = layui.myutil;
    var config = comon.config;
    var preUrl = config.ruleDroolsUrl;
    //日期范围
    laydate.render({
        elem: '.date'
        , range: true
    });
//初始化
    //查询构造
    comon.business.init("",$("#businessDiv"),"businessId_ser");
    //comon.business.init4chirden('',$("#businessDiv"));
    logTable.render({
        elem: '#loglist'
        ,height: 550
        ,cellMinWidth: 50
        ,url: preUrl+'log/page' //数据接口
        ,page: true //开启分页
        ,id:'loglist'
        ,cols: [[ // 表头
            {
                field: 'id',
                event: 'setItem',
                align: 'center',
                title: 'ID',
            },
             {
                field: 'senceName',
                event: 'setItem',
                align: 'center',
                title: '决策标识'
            }, {
                field: 'versionNum',
                event: 'setItem',
                align: 'center',
                title: '版本号',
            },
            {
                field: 'senceType',
                event: 'setItem',
                align: 'center',
                title: '类型',
                templet: '#type'
            },
            {
                field: 'executeTime',
                event: 'setItem',
                align: 'center',
                title: '花费时间'
            }, {
                field: 'createTime',
                title: '创建时间',
                align: 'center',
              //  templet: '#createTime'
            }
        ]],
        done: function(res, curr, count){
        //如果是直接赋值的方式，res即为：{data: [], count: 99} data为当前页数据、count为数据总长度
        if(res.data.length > 0){
            //设置默认
            var logId = res.data[0].id;
            var code = res.data[0].inParamter;
            userActive.reload(logId,code);
        }
    }
    });
    //监听工具条
    logTable.on('tool(loglist)', function(obj){
        var data = obj.data;
        if(obj.event === 'setItem'){
            //选择实体对象的id
            userActive.reload(data.id,data.inParamter);
        }
    });
    // 第一个实例
    userTable.render({
        elem: '#userlist'
        ,url: preUrl + 'log/getDetails' //数据接口
        ,page: true //开启分页
        ,id:'userlist'
        ,cols: [[ // 表头
            {
                field: 'ruleNum',
                align: 'center',
                title: '规则号'
            },{
                field: 'executeRulename',
                title: '规则描述',
                align: 'center',
            }
            , {
                field: 'selectStatus',
                title: '命中状态',
                align: 'center',
                templet: '#select'
            }
            ,{
                field: 'result',
                title: '结果',
                align: 'center',
            }
            ]]
    });

    // 重载
    // 这里以搜索为例
    active = {
        reload: function () {
            // var demoReload = $('#demoReload');
            var time  = $('#time').val();
            var startDate = time.split(" - ")[0];
            var endDate = time.split(" - ")[1];
            // 执行重载
            table.reload('loglist', {
                page: {
                    curr: 1
                    // 重新从第 1 页开始
                },
                where: {
                    date: startDate,
                    endDate: endDate,
                    sceneCode:$('#contact').val(),
                    businessId:$("#businessId_ser").val()
                }
            });
        }
    };

    // 重载
    // 这里以搜索为例
        userActive = {
            reload: function(logId,code){
                //var demoReload = $('#demoReload');
                //执行重载
                table.reload('userlist', {
                      page: {
                     curr: 1 //重新从第 1 页开始
                      },
                    where: {
                        logId: logId
                    }
                });
                $(".layui-code").html(code);

                layui.code({
                    title: '传入参数信息',
                    height: '250px',
                    about: false
                });
            }
        };


});


