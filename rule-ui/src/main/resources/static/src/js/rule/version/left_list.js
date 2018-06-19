
var  scene = {
    baseUrl: "/dispatch/service/sceneInfo/",
    uiUrl :"/dispatch/ui/dispatch/decision/scene/gradeCardEdit",
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
            width:130,
            toolbar: scene.toolbarId
    }
    ];
};
var layer,sceneTable,table,active;
var sceneId ;

layui.use(['table','form','laytpl'], function() {
    var laytpl = layui.laytpl;
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
                    layer.close(index);
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
        , cellMinWidth: 40
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
            //getRuleData(sceneId);
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
            console.log(data);
            if(data.code == '0'){
                var result = data.data;
                console.log(result);
                var getTpl = tableTp.innerHTML
                    ,view = document.getElementById('table');
                laytpl(getTpl).render(result, function(html){
                    view.innerHTML = html;
                });
                //初始化 实体类的值
                dataEntityInit();
                dataActionInit();
                // dataInit(sceneId);
                init();
            }else{
                $("#table").html(tableNoDataPt);
                init();
                //layer.msg("数据异常");
            }
            layer.close(index2);
        },'json');
        
    }
    //新增
    $("#scene_btn_add").on('click', function () {
        save(scene.uiUrl, null);
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
            save("/dispatch/ui/dispatch/decision/version/edit", result);
           // layer.msg('的确很重要', {icon: 1});

        }, function(){
        });

    }
});