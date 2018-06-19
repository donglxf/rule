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
var  sceneLeft = {
    baseUrl: "/dispatch/service/sceneInfo/",
    uiUrl :"/dispatch/decision/scene/gradeCardEdit",
    entity: "sceneList",
    tableId: "sceneList",
    toolbarId: "#toolbar_left",
    unique: "id",
    order: "asc",
    currentItem: {}
};
//表头
sceneLeft.cols = function () {
    return [ //表头
        //{field: 'sceneId',  event: 'setItem',title: 'ID',sort: true, fixed: 'left'}
        {field: 'sceneName',
            event: 'setItem',
            align:'center',
            title: '策略名'},
        {field: 'sceneIdentify',
            event: 'setItem',
            align:'center',
            title: '标识'},
        {field: 'sceneType',
            align:'center',
            event: 'setItem',
            title: '类型',
            width:120,
            templet: '#typeTpl'},
    ];
};


var  scene = {
    baseUrl: "/dispatch/service/sceneVersion/",
    uiUrl :"/dispatch/decision/version/gradeCardEdit",
    entity: "version",
    tableId: "version",
    toolbarId: "#toolbar",
    unique: "id",
    order: "asc",
    currentItem: {}
};
//表头
scene.cols = function () {
    return [ //表头
        //{field: 'sceneId',  event: 'setItem',title: 'ID',sort: true, fixed: 'left'}
        {field: 'officialVersion',
            align:'center',
            title: '版本号', sort: true
        }
        ,
        {field: 'title',
            align:'center',
            title: '版本标题'}
        ,
       /* {field: 'comment',
            align:'center',
            title: '版本描述'}
        ,*/
        {field: 'creTime',
            align:'center',
            title: '创建时间',sort: true
           }
        ,
        {field: 'type',
            align:'center',
            width:100,
            title: '版本类型',sort: true,
            templet: '#versionTypeTpl'
        } ,

        {field: 'creUserId',
            align:'center',
            width:100,
            title: '创建用户'}
        ,
        {field: 'status',
            align:'center',
            title: '状态',
            sort:true,
            templet: '#statusTpl',
        }
        ,
        {field: 'sceneId',
            title: '操作',
            fixed: 'right',
            align:'center',
            width:200,
            toolbar: scene.toolbarId
        }
    ];
};
var layer,sceneTable,table,active,leftActive,leftTable;
var sceneId,sceneType,$ ;
var layerTopIndex;
layui.config({
    base: '/src/js/modules/' //假设这是你存放拓展模块的根目录
}).extend({ //设定模块别名
    sceneUtil: 'decision', //如果 mymod.js 是在根目录，也可以不用设定别名
    myutil:'common',
});
layui.use(['table','form','laytpl','sceneUtil','myutil'], function() {
    var laytpl = layui.laytpl;
    var sceneUtil = layui.sceneUtil;
    sceneTable = layui.table;
    leftTable = layui.table;
    var app = layui.app,

         form = layui.form;
    $ = layui.jquery;
    var common = layui.myutil;
    //查询构造
    common.business.init("",$("#business_ser"),"businessId_ser");
    //第一个实例
    sceneTable.render({
        elem: '#'+scene.tableId
        , height: 'full'
       // , cellMinWidth: 10
        , url: scene.baseUrl + 'page4zs' //数据接口
        // data:[{"sceneId":1,"sceneName":"测试规则","sceneDesc":"测试规则引擎","sceneIdentify":"testrule","pkgName":"com.sky.testrule","creUserId":1,"creTime":1500522092000,"isEffect":1,"remark":null}]
        , page: true //开启分页
        , id: scene.tableId
        , cols: [scene.cols()]
    });
    //重载
    //这里以搜索为例
    active = {
        reload: function () {
            //执行重载
            sceneTable.reload(scene.tableId, {
                page: {
                    curr: 1 //重新从第 1 页开始
                }
                , where: {
                    sceneId: sceneId
                   // , sceneType : 2
                   , key:$("#versionN").val()
                }
            });
        }
    };
    //监听工具条
    sceneTable.on('tool('+scene.tableId+')', function (obj) {
        var data = obj.data;
        console.log(obj);
        if (obj.event === 'detail') {
            layer.msg('ID：' + data.id + ' 的查看操作');
        } else if (obj.event === 'del') {
            layer.confirm('是否删除？', function (index) {
                $.get(scene.baseUrl + 'delete/' + data.sceneId, function (data) {
                    layer.msg("删除成功！");
                    obj.del();
                    layer.close(index);
                });
            });
        } else if (obj.event === 'ruleLook') {

            var divHtml = data.ruleDiv;
            //查看规则
            $("#table").html("");
            $("#table").html(divHtml);
            layer.open({
                type: 1,
                title:false,
                //title: '规则查看',
                skin: 'layui-layer-rim', //加上边框
                maxmin: false,
                shadeClose: true, // 点击遮罩关闭层
                area: ['850px', '600px'],
                content: $("#rule_div").html(),
            });

        } else if (obj.event === 'setItem') {
        }else if(obj.event === 'rulePush'){

            if(data.type == 1){
                layer.msg('该版本已经发布过了，不可重复发布');
                return;
            }
            push(data.versionId);
        }
    });
    //监听锁定操作
    form.on('checkbox(lockDemo)', function(obj){
        var sta = obj.elem.checked ? 1 : 0;
        var id = this.value;
        $.post(scene.baseUrl+"forbidden",{versionId:id,status:sta},function (data) {
            if(data.data == '0'){
                if(obj.elem.checked ){
                    $(obj.elem).next().find("span").text("启用");
                }else{
                    $(obj.elem).next().find("span").text("停用");
                }
                layer.msg('操作成功', {icon: 1});
                active.reload();
            }else{
                layer.msg('操作失败', {icon: 2});
            }
        },'json');
    });

    /*
    * 左边列表
    * */
    //第一个实例
    leftTable.render({
        elem: '#'+sceneLeft.tableId
        , height: 'full'
        // , cellMinWidth: 10
        , url: sceneLeft.baseUrl + 'page' //数据接口
        // data:[{"sceneId":1,"sceneName":"测试规则","sceneDesc":"测试规则引擎","sceneIdentify":"testrule","pkgName":"com.sky.testrule","creUserId":1,"creTime":1500522092000,"isEffect":1,"remark":null}]
        , page: true //开启分页
        , id: sceneLeft.tableId
        , cols: [sceneLeft.cols()]
        ,done: function(res, curr, count){
            //如果是异步请求数据方式，res即为你接口返回的信息。
            //如果是直接赋值的方式，res即为：{data: [], count: 99} data为当前页数据、count为数据总长度
            if(res.data.length > 0){
                sceneId = res.data[0].sceneId;
                active.reload();
            }
            //getRuleData(sceneId);
            //得到当前页码
            console.log(curr);
            //得到数据总量
            console.log(count);
        }
    });
    //重载
    //这里以搜索为例
    leftActive = {
        reload: function () {
            //执行重载
            leftTable.reload(sceneLeft.tableId, {
                page: {
                    curr: 1 //重新从第 1 页开始
                }
                , where: {
                    sceneType : $("#sceneType").val(),
                    key : $("#key").val(),
                    businessId:$("#businessId_ser").val()
                }
            });
        }
    };
    //监听工具条
    leftTable.on('tool('+sceneLeft.tableId+')', function (obj) {
        var data = obj.data;
        if (obj.event === 'detail') {
            layer.msg('ID：' + data.id + ' 的查看操作');
        } else if (obj.event === 'del') {
            layer.confirm('是否删除？', function (index) {
                $.get(sceneLeft.baseUrl + 'delete/' + data.sceneId, function (data) {
                    layer.msg("删除成功！");
                    obj.del();
                    layer.close(index);
                });
            });
        } else if (obj.event === 'push') {
            push(data.sceneId,data.sceneType);
        }else if(obj.event === 'rule_defined'){
            ruleDefind(data.sceneId,data.sceneType);

        }else if(obj.event === 'edit'){
            sceneId = id;
            sceneEdit(data.sceneId,data.sceneType);
        }else if (obj.event === 'setItem') {
            //选择实体对象的id
            sceneId = data.sceneId;
            active.reload();
            //itemActive.reload(data.sceneId);
        }
    });

    //发布
    function push(id) {
        //询问框
        var index =  layer.confirm('您确定要发布到正式环境吗？', {
            btn: ['确定','取消'] //按钮
        }, function(){
            $.post(scene.baseUrl + 'push4zs?versionId=' + id, function (data) {
                    if(data.code == 0){
                        layer.msg("发布成功！");
                        active.reload();
                        layer.close(index);
                    } else{
                        layer.msg(data.msg);
                        layer.close(index);
                    }
                },'json');

        }, function(){
        });
    }


});