layui.use(['table', 'jquery', 'laydate', 'form'], function () {
    var table = layui.table;
    var $ = layui.jquery;
    var laydate = layui.laydate;
    var form = layui.form;

    console.log(form);
    //第一个实例
    table.render({
        elem: '#batch_list'
        , height: 'auto'
        , url: pathConfig.activitiConfigPath+'verficationBatchPage' //数据接口
        , page: true //开启分页
        , where: {}
        , cols: [[ //表头\
               {field: 'batchId', title: '批次号', align: "center", width: "20%"}
            , {field: 'modelName', title: '模型名称', align: "center", width: "20%"}
            , {field: 'modelVersion', title: '模型版本', align: "center", width: "20%"}
            , {field: 'status', title: '状态', align: "center", width: "20%"}
            , {fixed: 'right', title: "操作", width: 150, align: 'center', toolbar: '#batchDemo', width: "20%"}
        ]]
    });
    var active = {
        reload: function () {
            //执行重载
            table.reload('batch_list', {
                page: {
                    curr: 1, //重新从第 1 页开始
                }
                , where: {
                }
            });
        }
    };
    table.on('tool(batch)', function (obj) { //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
        var data = obj.data; //获得当前行数据
        var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
        var tr = obj.tr; //获得当前行 tr 的DOM对象
        var batchId = data.batchId;
        if (layEvent === 'detail_info') { //模型验证结果查询
            console.log(data);
            table.render({
                elem: '#task_list'
                , height: 'auto'
                , url: pathConfig.activitiConfigPath+'queryProcInstId?batchId='+batchId //数据接口
                , page: true //开启分页
                , where: {}
                , cols: [[ //表头\
                      {field: 'id', title: '批次号', align: "center", width: "35%"}
                    , {field: 'procInstId', title: '流程实例ID', align: "center", width: "25%"}
                    , {field: 'status', title: '状态', align: "center", width: "20%"}
                    , {fixed: 'right', title: "操作", width: 150, align: 'center', toolbar: '#taskBar', width: "20%"}
                ]]
            });
        }
    });
    table.on('tool(task)', function (obj) { //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
        var data = obj.data; //获得当前行数据
        var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
        var tr = obj.tr; //获得当前行 tr 的DOM对象
        var taskId = data.id;
        $("#task_hidden_input").val(taskId);
        if (layEvent === 'detail_info') { //模型验证结果查询
            var layIndex =layer.open({
                type: 2,
                shade: false,
                area: ['1200px', '800px'],
                title: "模型验证结果",
                //请求的弹出层路径
                content: pathConfig.ruleUiPath+"model/verfication/result/detail",
                zIndex: layer.zIndex, //重点1
                success: function (layero, index) {
                    layer.setTop(layero); //重点2
                }
            });
        }
    });
});
