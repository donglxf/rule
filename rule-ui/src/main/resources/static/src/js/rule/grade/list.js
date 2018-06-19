var tableNoDataPt = document.getElementById('table').innerHTML;
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

var  scene = {
    baseUrl: "/dispatch/service/sceneInfo/",
    uiUrl :"/dispatch/decision/scene/gradeCardEdit",
    entity: "sceneInfo",
    tableId: "sceneInfoTable",
    toolbarId: "#toolbar",
    unique: "id",
    order: "asc",
    currentItem: {}
};
//表头
scene.cols = function () {
    return [ //表头
        //{field: 'sceneId',  event: 'setItem',title: 'ID',sort: true, fixed: 'left'}
        {field: 'sceneName',
            event: 'setItem',
            align:'center',
            title: '名称'},
        {field: 'sceneIdentify',
            event: 'setItem',
            align:'center',
            title: '标识'}
        ,{field: 'sceneId',
            title: '操作',
            fixed: 'right',
            align:'center',
            width:150,
            toolbar: scene.toolbarId
    }
    ];
};
/*
scene.queryParams = function (params) {
    if (!params)
        return {
            name: $("#name").val()
        };
    var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
        limit: params.limit, //页面大小
        offset: params.offset, //页码
        name: $("#name").val()
    };
    return temp;
};*/
///////////////////////////////////////////////////////////////////////
var layer,sceneTable,table,active;
var sceneId ;
//config的设置是全局的,引入工具包
layui.config({
    base: '/src/js/modules/' //假设这是你存放拓展模块的根目录
}).extend({ //设定模块别名
    sceneUtil: 'decision', //如果 mymod.js 是在根目录，也可以不用设定别名
    myutil:'common',
});
layui.use(['table','form','laytpl','sceneUtil','myutil'], function() {
    var laytpl = layui.laytpl;
    sceneUtil = layui.sceneUtil;
    var form = layui.form;
    form.render();
    /**
     * 公共方法：保存
     * @param url
     * @param result
     */
    function save(url, result) {
        $.get(url, function (form) {
            layer.open({
                type: 1,
                title: '保存信息',
                maxmin: true,
                shadeClose: false, // 点击遮罩关闭层
                area: ['550px', '460px'],
                content: form,
                btnAlign: 'c',
                btn: ['保存', '取消'],
                success: function (layero, index) {
                    setFromValues(layero, result);
                }
                , yes: function (index) {
                    //触发表单的提交事件
                    $('form.layui-form').find('button[lay-filter=formDemo]').click();
                   // layer.close(index);
                },
            });
        });
    }

    sceneTable = layui.table;
    var app = layui.app,
        $ = layui.jquery
        , form = layui.form;
    //第一个实例
    sceneTable.render({
        elem: '#'+scene.tableId
        , height: 'full'
        , cellMinWidth:80
        , url: scene.baseUrl + 'page?sceneType=2' //数据接口
        // data:[{"sceneId":1,"sceneName":"测试规则","sceneDesc":"测试规则引擎","sceneIdentify":"testrule","pkgName":"com.sky.testrule","creUserId":1,"creTime":1500522092000,"isEffect":1,"remark":null}]
        , page: true //开启分页
        , id: scene.tableId
        , cols: [scene.cols()]
        ,done: function(res, curr, count){
            //如果是异步请求数据方式，res即为你接口返回的信息。
            //如果是直接赋值的方式，res即为：{data: [], count: 99} data为当前页数据、count为数据总长度
            if(res.data.length > 0){
                sceneId = res.data[0].sceneId;
                getRuleData(sceneId);
            }
            //得到当前页码
            console.log(curr);
            //得到数据总量
            console.log(count);
        }
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
                    key: $('#scene_key_ser').val()
                    , sceneType : 2
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
        } else if (obj.event === 'edit') {
            edit(data.sceneId);
        }
        //发布
        else if (obj.event === 'push') {
            push(data.sceneId);
        }
        else if (obj.event === 'setItem') {
            //选择实体对象的id
            sceneId = data.sceneId;
            var tr = obj.tr;
            $("table>tbody>tr").removeClass("select");
            tr.addClass("select");
            getRuleData(sceneId);
            //itemActive.reload(data.sceneId);
        }
    });

    function getRuleData(sceneId) {
        ///var index = layer.load(2);
        var index2 =   layer.msg("数据加载中", {
            icon: 16,
            // offset: [e.elem.offset().top + e.elem.height() / 2 - 35 - T.scrollTop() + "px", e.elem.offset().left + e.elem.width() / 2 - 90 - T.scrollLeft() + "px"],
            time: -1,
            anim: -1,
            fixed: !1
        });
        $.get('/dispatch/service/dispatch/getGradeCardAll',{'sceneId':sceneId},function(data){
           // console.log(data);
            if(data.code == '0'){
                var result = data.data;
                var hasWeight = result.hasWeight;

               // console.log(result);
                var getTpl = tableTp.innerHTML
                    ,view = document.getElementById('table');
                laytpl(getTpl).render(result, function(html){
                    view.innerHTML = html;
                });
                //初始化 实体类的值
                sceneUtil.sceneId = sceneId;
                sceneUtil.dataInit.entityBank();
                sceneUtil.dataInit.actionBank();
                sceneUtil.gradeInit();
                //是否有权值
                if(hasWeight > 0){
                    $("#openQz").attr("checked",true);
                    $("#table tbody tr td .qzdiv").show();
                    form.render('checkbox');
                }
            }else{
                $("#table").html(tableNoDataPt);
                //初始化 实体类的值
                sceneUtil.sceneId = sceneId;
                //sceneUtil.dataInit.entityBank();
                //sceneUtil.dataInit.actionBank();
                sceneUtil.gradeInit();
                //layer.msg("数据异常");
            }

            layer.close(index2);
        },'json');
        
    }
    //新增
    $("#scene_btn_add").on('click', function () {
        save(scene.uiUrl, null);
    });
    //导入变量库
    $(".import-entity").on('click', function () {
        sceneUtil.openImport(1);
    });
    //导入动作库
    //导入变量库
    $(".import-action").on('click', function () {
        sceneUtil.openImport(2);
    });
    //监听锁定操作
    form.on('checkbox(lockDemo)', function(obj){
        var sta = obj.elem.checked ? 1 : 0;
        var id = this.value;
        if(obj.elem.checked){
            $(".qzdiv").show();
        }else{
            $(".qzdiv").hide();
        }
    });
    //修改
    function edit(id) {
        $.get(scene.baseUrl + "getInfoById/" + id, function (data) {
            var result = data.data;
            save(scene.uiUrl, result);
        }, 'json')
    }

    //发布
    function push(id) {
        getRuleData(id);
        //询问框
       var index =  layer.confirm('您确定要发布新颁布吗？', {
            btn: ['确定','取消'] //按钮
        }, function(){

            var rule_drl ,rule_div;
            rule_div = $("#table").html();
            var result = {sceneId:id,ruleDiv:rule_div};
            layer.close(index);
            save("/dispatch/decision/version/edit", result);
           // layer.msg('的确很重要', {icon: 1});

        }, function(){
        });
    }

});