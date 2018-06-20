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
var baseUrl = "";
var  sceneLeft = {
    baseUrl: baseUrl+"sceneInfo/",
    uiUrl :"grade_card_edit.html",
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
            title: '决策名'},
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
        {field: 'sceneId2',
            title: '规则定义',
            event: 'setItem',
            align:'center',
            width:120,
            toolbar: "#bar_rule_defined"
        },
        {field: 'sceneId',
            title: '操作',
            fixed: 'right',
            event: 'setItem',
            align:'center',sort: true,
            width:150,
            toolbar: sceneLeft.toolbarId}
    ];
};


var  scene = {
    baseUrl: baseUrl+"sceneVersion/",
    uiUrl :"grade_card_edit.html",
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
        {field: 'version',
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
        /* {field: 'type',
             align:'center',
             width:100,
             title: '版本类型',sort: true,
             templet: '#versionTypeTpl'
         } ,

         {field: 'creUserId',
             align:'center',
             title: '创建用户'}
         ,*/
        // {field: 'orderStatus',
        //     align:'center',
        //     title: '状态',
        //     sort:true,
        //     templet: '#statusTpl',
        // }
        // ,
        {field: 'sceneId',
            title: '操作',
            fixed: 'right',
            align:'center',
            width:220,
            toolbar: scene.toolbarId
        }
    ];
};
var layer,sceneTable,table,active,leftActive,leftTable;
var sceneId,sceneType,$ ;
var layerTopIndex;
var baseUrl;
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
    scene.baseUrl=  common.config.ruleServicePath.sceneVersion.base;
    sceneLeft.baseUrl = common.config.ruleServicePath.sceneInfo.base;
    baseUrl = common.config.ruleServicePath.basePath;

    //查询构造
    common.business.init("",$("#business_ser"),"businessId_ser");
    //第一个实例
    sceneTable.render({
        elem: '#'+scene.tableId
        , height: 'full'
        // , cellMinWidth: 10
        , url: scene.baseUrl + 'page' //数据接口
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
                    //   , sceneType : 2
                    ,
                    key:$("#versionN").val()
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
                $.get(scene.baseUrl + 'delete?id=' + data.sceneId, function (data) {
                    if(data.code < 0){
                        layer.msg('删除失败，该数据正在被其他数据引用', {icon: 5});
                        layer.close(index);
                    }else{
                        layer.msg("删除成功！");
                        obj.del();
                        layer.close(index);
                    }
                },'json');
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
                maxmin: true,
                shadeClose: true, // 点击遮罩关闭层
                area: ['850px', '600px'],
                content: $("#rule_div").html(),
            });

        } else if (obj.event === 'check') {
            manuTest(data.sceneId, data.versionId,data.sceneIdentify,data.version);
        }else if (obj.event === 'push4zs') {
            push4zs(data.versionId);
        }
    });
    //监听锁定操作
    form.on('checkbox(lockDemo)', function(obj){
        var sta = obj.elem.checked ? 1 : 0;
        var id = this.value;
        $.post(scene.baseUrl+"forbidden",{versionId:id,status:sta},function (data) {
            if(data.data == '0'){
                if(obj.elem.checked ){
                    $(obj.elem).next().find("span").text("正常");
                }else{
                    $(obj.elem).next().find("span").text("冻结");
                }
                layer.msg('操作成功', {icon: 1});
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
                $.get(sceneLeft.baseUrl + 'delete?id=' + data.sceneId, function (data) {
                    if(data.code < 0){
                        layer.msg('删除失败，该数据正在被其他数据引用', {icon: 5});
                        layer.close(index);
                    }else{
                        layer.msg("删除成功！");
                        obj.del();
                        layer.close(index);
                    }
                },'json');
            });
        } else if (obj.event === 'push') {
            push(data.sceneId,data.sceneType);
        }else if(obj.event === 'rule_defined'){
            ruleDefind(data.sceneId,data.sceneType);

        }else if(obj.event === 'edit'){
            sceneId = data.sceneId;
            sceneEdit(data.sceneId,data.sceneType);
        }else if (obj.event === 'setItem') {
            //选择实体对象的id
            sceneId = data.sceneId;
            active.reload();
            //itemActive.reload(data.sceneId);
        }
    });

    /**
     * 编辑规则列表
     * @param sceneId
     */
    function ruleDefind(sceneId,type){
        var url = "/src/html/decision/gradeCard_edit.html";
        if(type == 1){
            url = "/src/html/decision/scene_edit.html";
        }
        sceneType = type;
        layer.open({
            type: 2,
            title: '定义规则',
            maxmin: false,
            shadeClose: false, // 点击遮罩关闭层
            area: ['880px', '88%'],
            content: url,
            //skin: 'layui-layer-rim', //加上边框
            success: function(layero, index){
                var  body = layer.getChildFrame('body', index);
                //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
                // var iframeWin = window[layero.find('iframe')[0]['name']];
                //执行初始化方法
                sceneId = sceneId;
            }
        });
    }
    /**
     * 得到规则查看列表
     * @param sceneId
     * @param type
     */
    function getRuleData(sceneId,type) {
        var url = type == 2 ? 'getGradeCardAll':'getAll';
        $.get( baseUrl+'rule/'+url,{'sceneId':sceneId},function(data){
            if(data.code == '0'){
                var result = data.data;
                var getTpl = tableTp1.innerHTML
                //评分卡
                if(type == '2'){
                    getTpl = tableTp2.innerHTML
                }
                var view = document.getElementById('tableView');
                laytpl(getTpl).render(result, function(html){
                    view.innerHTML = html;
                });
                //设置值了
                $("input[name='sceneId']").val(sceneId);
                $("input[name='ruleDiv']").val($("#tableView").html());
                if(type == 2){
                    sceneUtil.rowspan4grade();
                }else{

                }
            }else{
                $("#table").html('');
            }
        },'json');

    }
    /**
     * 公共方法：保存
     * @param url
     * @param result
     */
    function save(url, result,id,type) {
        $.get(url, function (form) {
            layerTopIndex =  layer.open({
                type: 1,
                title: '版本发布',
                maxmin: true,
                shadeClose: false, // 点击遮罩关闭层
                area: ['750px', '560px'],
                content: form,
                btnAlign: 'c',
                btn: ['保存', '取消'],
                success: function (layero, index) {
                    //设置table内容页
                    getRuleData(id,type);
                    var rule_div = $("#rule_div").html();
                }
                , yes: function (index) {
                    layerTopIndex = index;
                    //触发表单的提交事件
                    $("#subs").click();
                   // $('form.layui-form').find('button[lay-filter=formDemo-push]').click();
                },
            });
        });
    }
    function push4zs(id){
        var index =  layer.confirm('您确定要发布为正式颁布吗？', {
            btn: ['确定','取消'] //按钮
        }, function(){
            $.get(baseUrl+'/push4zs?versionId='+id,function (res) {
                layer.msg('发布成功');
                active.reload();
            },'json')
        }, function(){
        });
    }
    //发布
    function push(id,type) {
        sceneId = id;
        //询问框
        var index =  layer.confirm('您确定要发布新的测试版吗？', {
            btn: ['确定','取消'] //按钮
        }, function(){
            var rule_drl ,rule_div;
            rule_div = $("#table").html();
            var result = {sceneId:id,ruleDiv:rule_div};
            layer.close(index);
            save("viewEdit.html", result,id,type);
            // layer.msg('的确很重要', {icon: 1});

        }, function(){
        });
    }

    /**
     * 公共方法：保存
     * @param url
     * @param result
     */
    function sceneSave(url, result) {
        $.get(url, function (form) {
            layerTopIndex = layer.open({
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
                    if(result != undefined  && result != null && result.businessId != null ){
                        common.business.init(result.businessId,$("#businessDiv"));
                    }else{
                        common.business.init('',$("#businessDiv"));
                    }

                }
                , yes: function (index) {
                    //触发表单的提交事件
                    $('form.layui-form').find('button[lay-filter=formDemo]').click();
                    // layer.close(index);
                },
            });
        });
    }
    //新增评分卡
    $("#grade_btn_add").click(function(){
        sceneSave("grade_card_edit.html", null);

    });
    //新增评分卡
    $("#scene_btn_add").click(function(){
        sceneSave("scene_edit.html", null);
    });
    //修改
    function sceneEdit(id,type) {
        $.get(baseUrl+"sceneInfo/" + "getInfoById?id=" + id, function (data) {
            var result = data.data;
            if(type == 1){
                sceneSave("scene_edit.html", result);
            }else{
                sceneSave("grade_card_edit.html", result);
            }

        }, 'json')
    }
    // 验证
    function manuTest(sceneId, versionId,sceneIdentify,version){
        versionIds=versionId;
        // var url=baseUrl+"business/"+ "getAll?versionId=" + versionId ;
        var layIndex = layer.open({
            type: 2,
            shade: false,
            title: "",
            area: ['85%', '100%'],
            content: "/src/html/strategy/manualTest.html",
            //   zIndex: layer.zIndex, //重点1
            success: function (layero, index) {
                var body = layer.getChildFrame('body',index);//建立父子联系
                // var iframeWin = window[layero.find('iframe')[0]['name']];
                var inputList = body.find("input[type='hidden']");
                for(var j = 0; j< inputList.length; j++){
                    var inputName=inputList[j].name;
                    if(inputName=='senceVersionId'){
                        $(inputList[j]).val(versionId);
                    }else if(inputName =='senceId'){
                        $(inputList[j]).val(sceneId);
                    }else if(inputName =='sceneIdentify'){
                        $(inputList[j]).val(sceneIdentify);
                    }else if(inputName =='version'){
                        $(inputList[j]).val(version);
                    }
                }
                layer.setTop(layero); //重点2
            }
        })
        //layer.full(layIndex);
    }
});