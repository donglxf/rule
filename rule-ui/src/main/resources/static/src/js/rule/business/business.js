
///////////////////////////////////////////////////////////////////////

var layer,entityTable,itemTable,table,active,itemActive;
var businessSystemlId ;
var topIndexId ;
layui.config({
    base: '/src/js/modules/' //假设这是你存放拓展模块的根目录
}).extend({ //设定模块别名
    myutil: 'common' //如果 mymod.js 是在根目录，也可以不用设定别名
});
layui.use(['table','form','laytpl','myutil'], function(){

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
    layer = layui.layer;
    table = layui.table;
    entityTable = layui.table;
    itemTable = layui.table;
    var laytpl = layui.laytpl;
    var app = layui.app,
        $ = layui.jquery
        ,form = layui.form;
    var common = layui.myutil;
    //查询构造
    //  common.business.init("",$("#business_ser"),"businessId_ser");
    var config = common.config;
    var preItemUrl = config.ruleServicePath.business.base;
    var preUrl =config.ruleServicePath.businessSystem.base;
    //第一个实例
    entityTable.render({
        elem: '#demo'
        ,height: 550
        ,cellMinWidth: 80
        ,url: preUrl + 'page' //数据接口
        // data:[{"businessSystemlId":1,"entityName":"测试规则","entityDesc":"测试规则引擎","entityIdentify":"testrule","pkgName":"com.sky.testrule","creUserId":1,"creTime":1500522092000,"isEffect":1,"remark":null}]
        ,page: true //开启分页
        ,id:'demos'
        ,cols: [[ //表头
            //{field: 'entityId',  event: 'setItem',title: 'ID',sort: true, fixed: 'left'}
            {field: 'businessSystemName',  event: 'setItem',title: '名称'}
            ,{field: 'businessSystemCode', event: 'setItem', title: '编码'}
            ,{field: 'createTime', event: 'setItem', title: '创建时间'}
            // ,{field: 'isEffect',title: '状态', sort: true,templet: '#checkboxTpl', unresize: true,fixed: 'right'}
            ,{field: 'businessSystemlId', title: '操作', fixed: 'right',align:'center', toolbar: '#bar'}
        ]],
        done: function(res, curr, count){
            //如果是异步请求数据方式，res即为你接口返回的信息。
            //如果是直接赋值的方式，res即为：{data: [], count: 99} data为当前页数据、count为数据总长度
            if(res.data.length > 0){
                //设置默认
                businessSystemlId = res.data[0].businessSystemlId;
                itemActive.reload(businessSystemlId);

            }
        }
    });
    //重载
    //这里以搜索为例
    active = {
        reload: function(){
            //var demoReload = $('#demoReload');

            //执行重载
            table.reload('demos', {
                page: {
                    curr: 1 //重新从第 1 页开始
                }
                ,where: {
                    key: $('#entityName_ser').val(),
                    businessId:$("#businessId_ser").val(),
                }
            });
        }
    };

    $('.demoTable .layui-btn').on('click', function(){
        var type = $(this).data('type');
        active[type] ? active[type].call(this) : '';
    });
    //监听单元格编辑
    /*entityTable.on('edit(entityTable)', function(obj){
        var value = obj.value //得到修改后的值
            ,data = obj.data //得到所在行所有键值
            ,field = obj.field; //得到字段
        layer.msg('[ID: '+ data.id +'] ' + field + ' 字段更改为：'+ value);
    });*/
    //监听工具条
    entityTable.on('tool(entityTable)', function(obj){
        var data = obj.data;
        console.log(obj);
        if(obj.event === 'detail'){
            layer.msg('ID：'+ data.id + ' 的查看操作');
        } else if(obj.event === 'del'){
            layer.confirm('是否删除？', function(index){
                $.get(preUrl+'delete?id='+data.businessSystemlId,function (data) {
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
        } else if(obj.event === 'edit'){
            //  layer.alert('编辑行：<br>'+ JSON.stringify(data))
            edit(data.businessSystemlId);
        }else if(obj.event === 'setItem'){
            //选择实体对象的id
            businessSystemlId = data.businessSystemlId;
            itemActive.reload(data.businessSystemlId);
        }
    });


    //第一个实例
    itemTable.render({
        elem: '#itemTable'
        ,height: 550
        ,cellMinWidth: 80
        ,url: preItemUrl+'getAllBySys' //数据接口
        // data:[{"businessSystemlId":1,"entityName":"测试规则","entityDesc":"测试规则引擎","entityIdentify":"testrule","pkgName":"com.sky.testrule","creUserId":1,"creTime":1500522092000,"isEffect":1,"remark":null}]
        ,page: false
        ,id:'itemT'
        ,cols: [[ //表头
            {field: 'businessTypeId', title: 'ID',  sort: true, fixed: 'left'}
            ,{field: 'businessTypeName', title: '名称'}
            ,{field: 'businessTypeCode', title: '标识'}
            ,{field: 'belongSystemLocalId', title: '操作', fixed: 'right',align:'center', toolbar: '#item_bar'}
        ]]

    });

    //监听工具条
    itemTable.on('tool(itemTable)', function(obj){
        var data = obj.data;
        if(obj.event === 'detail'){
            layer.msg('ID：'+ data.id + ' 的查看操作');
        } else if(obj.event === 'del2'){
            layer.confirm('是否删除？', function(index){
                $.get(preItemUrl+'delete?id='+data.itemId,function (data) {
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
        } else if(obj.event === 'edit2'){
            //  layer.alert('编辑行：<br>'+ JSON.stringify(data))
            editItem(data.businessTypeId);
        }
    });
    //监听锁定操作
    form.on('checkbox(lockDemo)', function(obj){
        var sta = obj.elem.checked ? 1 : 0;
        var id = this.value;
        $.post(preUrl+'/forbidden',{id:id,status:sta},function (data) {
            if(data.data == '0'){
                if(obj.elem.checked ){
                    $(obj.elem).next().find("span").text("启用");
                }else{
                    $(obj.elem).next().find("span").text("停用");
                }
                layer.msg('操作成功', {icon: 1});
                // active.reload();
            }else{
                layer.msg('操作失败', {icon: 2});
            }
        },'json');
    });
    //重载
    //这里以搜索为例
    itemActive = {
        reload: function(sysId){
            //var demoReload = $('#demoReload');
            //执行重载
            table.reload('itemT', {
                //   page: {
                //  curr: 1 //重新从第 1 页开始
                //  }
                where: {
                    sysId: sysId
                }
            });
        }
    };
    //新增
    $("#sys_btn_add").on('click',function () {
        $.get('/src/html/business_sys/business_sys_edit.html', null, function (form) {
            topIndexId =  layer.open({
                type :1,
                title : '新增',
                maxmin : true,
                shadeClose : false, // 点击遮罩关闭层
                area : [ '550px', '460px' ],
                content :  form,
                btnAlign: 'c',
                btn: ['保存', '取消'],
                success: function (layero, index) {
                    // setFromValues(layero, result);
                    //初始化
                    common.business.init('',$("#businessDiv"));
                }
                ,yes: function (index) {
                    //layedit.sync(editIndex);
                    //触发表单的提交事件
                    $('form.layui-form').find('button[lay-filter=formDemo]').click();
                },
            });
        });
    });
    function  edit(id) {
        $.get(preUrl+"getInfoById?id="+id,function (data) {
            var result = data.data;
            $.get('/src/html/business_sys/business_sys_edit.html', null, function (form) {
                topIndexId =  layer.open({
                    type :1,
                    title : '修改',
                    maxmin : true,
                    shadeClose : false, // 点击遮罩关闭层
                    area : [ '550px', '460px' ],
                    content :  form,
                    btn: ['保存', '取消'],
                    btnAlign: 'c',
                    success: function (layero, index) {
                        console.log(layero);
                        setFromValues(layero, result);
                        //初始化
                        // common.business.init(result.businessId,$("#businessDiv"));
                    }
                    ,yes: function (index) {
                        //layedit.sync(editIndex);
                        //触发表单的提交事件
                        $('form.layui-form').find('button[lay-filter=formDemo]').click();
                    },
                });
            });
        },'json')
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //新增
    $("#entity_item_btn_add").on('click',function () {
        if(businessSystemlId == undefined || businessSystemlId == ''){
            layer.msg("必须选择一个数据对象哦");
        }

        $.get('/src/html/business/edit.html', null, function (form) {
            topIndexId =   layer.open({
                type :1,
                title : '新增',
                maxmin : true,
                shadeClose : false, // 点击遮罩关闭层
                area : [ '550px', '560px' ],
                content :  form,
                btnAlign: 'c',
                btn: ['保存', '取消'],
                success: function (layero, index) {
                    var result = {"belongSystemLocalId":businessSystemlId};
                    setFromValues(layero, result);
                }
                ,yes: function (index) {
                    //layedit.sync(editIndex);
                    //触发表单的提交事件
                    $('form.layui-form').find('button[lay-filter=formDemo]').click();
                },
            });
        });
    });
    function  editItem(id) {
        $.get(preItemUrl+"getInfoById?id="+id,function (data) {
            var result = data.data;
            $.get('/src/html/business/edit.html', function (form) {
                topIndexId =  layer.open({
                    type :1,
                    title : '修改',
                    maxmin : true,
                    shadeClose : false, // 点击遮罩关闭层
                    area : [ '550px', '560px' ],
                    content :  form,
                    btn: ['保存', '取消'],
                    btnAlign: 'c',
                    success: function (layero, index) {
                        setFromValues(layero, result);
                        var form = layui.form;
                        form.render('select');
                    }
                    ,yes: function (index) {
                        //layedit.sync(editIndex);
                        //触发表单的提交事件
                        $('form.layui-form').find('button[lay-filter=formDemo]').click();

                    },
                });
            });
        },'json')
    }
});
