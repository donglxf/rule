///////////////////////////////////////////////////////////////////////
var layer, entityTable, itemTable, table, active, itemActive;
var versionId;
layui.config({
    base: '/dispatch/ui/src/js/modules/' //假设这是你存放拓展模块的根目录
}).extend({ //设定模块别名
    myutil: 'common' //如果 mymod.js 是在根目录，也可以不用设定别名
});
layui.use([ 'table', 'form' ], function() {
    /**
     * 设置表单值
     *
     * @param el
     * @param data
     */
    function setFromValues(el, data) {
        for ( var p in data) {
            el.find(":input[name='" + p + "']").val(data[p]);
        }
    }

    layer = layui.layer;
    table = layui.table;
    entityTable = layui.table;
    itemTable = layui.table;
    var app = layui.app, $ = layui.jquery, form = layui.form;
    var common = layui.myutil;
    var preItemUrl = common.config.ruleServicePath.basePath+"variableBind";
    var preUrl = common.config.ruleServicePath.basePath+"variableBind";
    // 第一个实例
    entityTable.render({
        elem : '#demo',
        height : 550,
        cellMinWidth : 80,
        url : preUrl + 'page/' // 数据接口
        // data:[{"conId":1,"entityName":"测试规则","entityDesc":"测试规则引擎","entityIdentify":"testrule","pkgName":"com.sky.testrule","creUserId":1,"creTime":1500522092000,"isEffect":1,"remark":null}]
        ,
        page : true // 开启分页
        ,
        id : 'demos',
        cols : [ [ // 表头
            // {field: 'conId', event: 'setItem',title: 'ID',sort: true, fixed:
            // 'left'}
            {
                field : 'versionId',
                event : 'setItem',
                title : '标识'
            }, {
                field : 'version',
                event : 'setItem',
                title : '版本'
            }, {
                field : 'title',
                event : 'setItem',
                title : '标题'
            }, {
                field : 'comment',
                event : 'setItem',
                title : '描述'
            }, {
                field : 'status',
                event : 'setItem',
                title : '状态',
                sort : true,
                templet : '#statusConvert',
                unresize : true,
                fixed : 'right'
            }, {
                field : 'sceneId',
                title : '操作',
                fixed : 'right',
                align : 'center',
                toolbar : '#bar'
            } ] ]
    });
    // 重载
    // 这里以搜索为例
    active = {
        reload : function() {
            // var demoReload = $('#demoReload');

            // 执行重载
            table.reload('demos', {
                page : {
                    curr : 1
                    // 重新从第 1 页开始
                },
                where : {
                    key : $('#entityName_ser').val()
                }
            });
        }
    };

    $('.demoTable .layui-btn').on('click', function() {
        var type = $(this).data('type');
        active[type] ? active[type].call(this) : '';
    });
    // 监听锁定操作
    form.on('checkbox(lockDemo)', function(obj) {
        layer.tips(this.value + ' ' + this.name + '：' + obj.elem.checked,
            obj.othis);
    });
    // 监听工具条
    entityTable.on('tool(entityTable)', function(obj) {
        var data = obj.data;
        console.log(obj);
        if (obj.event === 'detail') {
            layer.msg('ID：' + data.versionId + ' 的查看操作');
        } else if (obj.event === 'del') {
            // layer.confirm('是否删除？', function(index) {
            // 	$.get(preUrl + 'delete/' + data.versionId, function(data) {
            // 		layer.msg("删除成功！");
            // 		obj.del();
            // 		layer.close(index);
            // 	});
            //
            // });
        } else if (obj.event === 'edit') {
            // layer.alert('编辑行：<br>'+ JSON.stringify(data))
            edit(data.versionId);
        } else if (obj.event === 'bind') {
            // 选择实体对象的id
            versionId = data.versionId;
            edit(data.sceneId,data.versionId);
            // getm(data.sceneId, data.versionId);
        }
    });

    var getm = function(sceneId, versionId) {
        // alert(sceneId+"====="+versionId);
        $
            .ajax({
                cache : true,
                type : "GET",
                url : preUrl+'getAll/',
                data : {
                    "sceneId" : sceneId
                },
                async : false,
                error : function(request) {
                    alert("Connection error");
                },
                success : function(da) {
                    console.log(da);
                    var size = da.data.length;
                    var si=$("#variableCode").find("option").size();
                    $("#variableCode").empty();
                    $("#variableCode").append("<option value=\"\">请选择</option>");
                    for (var i = 0; i < size; i++) {
                        $("#variableCode")
                            .append(
                                "<option value=\""
                                + da.data[i].itemIdentify
                                + "\">"
                                + da.data[i].itemName
                                + "</option>");
                    }
                    $("#senceVersionid").val(versionId);

                    var form = layui.form;
                    form.render('select');
                    $("#bind").show();
                }
            });
    }

    // 这里以搜索为例
    itemActive = {
        reload : function(sceneId, versionId) {
            // var demoReload = $('#demoReload');
            // 执行重载
            table.reload('itemT', {
                page : {
                    curr : 1
                    // 重新从第 1 页开始
                },
                where : {
                    sceneId : sceneId,
                    versionId : versionId,
                }
            });
        }
    };
    function edit(sceneId,versionId) {
        $.get(preUrl + "getVariableBind?sceneId=" + versionId, function(data) {
            var result = data.data;
            $.get('/dispatch/ui/ruleBind/index/edit', null, function(form) {
                layer.open({
                    type : 1,
                    title : '修改',
                    maxmin : true,
                    shadeClose : false, // 点击遮罩关闭层
                    area : [ '730px', '460px' ],
                    content : form,
                    btn : [ '保存', '取消' ],
                    btnAlign : 'c',
                    zIndex: layer.zIndex, //重点1
                    success : function(da, index) {
                        console.log(">>"+da+">>index:=="+index);


                        setVariableBindFiled(da, result);
                        $("#senceVersionid").val(versionId);
                        $("#sceneId").val(sceneId);


                    },
                    yes : function(index) {
                        // layedit.sync(editIndex);
                        // 触发表单的提交事件
                        $('form.layui-form')
                            .find('button[lay-filter=formDemo]').click();
                        layer.close(index);
                    },
                });
            });
        }, 'json')
    }



});


