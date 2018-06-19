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
   var userTable = layui.table,logTable=layui.table,userActive;
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
    logTable.render({
        elem: '#loglist'
        ,height: 550
        ,cellMinWidth: 80
        ,url: preItemUrl+'ruleDpUserLog/page' //数据接口
        ,page: true //开启分页
        ,id:'loglist'
        ,cols: [[ // 表头
            {
                field: 'contractId',
                event: 'setItem',
                align: 'center',
                title: '合同号'
            }, {
                field: 'roleCode',
                event: 'setItem',
                align: 'center',
                title: '角色编号'
            }, {
                field: 'senceVersionid',
                event: 'setItem',
                align: 'center',
                title: '版本号',
            },
            {
                field: 'stage',
                event: 'setItem',
                align: 'center',
                title: '节点'
            },
            {
                field: 'remark',
                event: 'setItem',
                align: 'center',
                title: '结果'
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
                templet: '#createTime'
            }
        ]],
        done: function(res, curr, count){
        //如果是异步请求数据方式，res即为你接口返回的信息。
        //如果是直接赋值的方式，res即为：{data: [], count: 99} data为当前页数据、count为数据总长度
        if(res.data.length > 0){
            //设置默认
            var entityId = res.data[0].dpUserLogId;
            userActive.reload(entityId);

        }
    }
    });
    //监听工具条
    logTable.on('tool(loglist)', function(obj){
        var data = obj.data;
        if(obj.event === 'setItem'){
            //选择实体对象的id
            userActive.reload(data.dpUserLogId);
        }
    });
    // 第一个实例
    userTable.render({
        elem: '#userlist'
        ,height: 550
        ,url: preUrl + 'ruleDpUserSelectLog/page' //数据接口
        ,page: true //开启分页
        ,id:'userlist'
        ,cols: [[ // 表头
            {
                field: 'selectSysUserId',
                event: 'setItem',
                align: 'center',
                title: '用户id'
            },{
                field: 'selectUserName',
                event: 'setItem',
                title: '用户名',
                align: 'center',
            }
            ,{
                field: 'selectUserGrade',
                event: 'setItem',
                title: '得分',
                align: 'center',
            }
            , {
                field: 'type',
                event: 'setItem',
                title: '类型',
                align: 'center',
                templet: '#type'
            }
            , {
                field: 'selectStatus',
                event: 'setItem',
                title: '选中状态',
                align: 'center',
                templet: '#select'
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
                    logId:$('#contact').val()
                }
            });
        }
    };

    // 重载
    // 这里以搜索为例
        userActive = {
            reload: function(logId){
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
            }
        };


});


