
///////////////////////////////////////////////////////////////////////

var layer,entityTable,table,active,itemActive;
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
    var laytpl = layui.laytpl;
    var app = layui.app,
        $ = layui.jquery
        ,form = layui.form;
    var common = layui.myutil;
    //查询构造
    //  common.business.init("",$("#business_ser"),"businessId_ser");
    var config = common.config;
    var preUrl = config.dispatchServicePath.warningNotice.base;

    //查询构造
    common.business.init("",$("#business_ser"),"businessId_ser");
    //第一个实例
    entityTable.render({
        elem: '#warning'
       // ,height: 550
        ,cellMinWidth: 80
        ,url: preUrl + 'page' //数据接口
        // data:[{"businessSystemlId":1,"entityName":"测试规则","entityDesc":"测试规则引擎","entityIdentify":"testrule","pkgName":"com.sky.testrule","creUserId":1,"creTime":1500522092000,"isEffect":1,"remark":null}]
        ,page: true //开启分页
        ,id:'warning'
        ,cols: [[ //表头
            //{field: 'entityId',  event: 'setItem',title: 'ID',sort: true, fixed: 'left'}
            {field: 'businessName',  event: 'setItem',title: '业务线名'}
            ,{field: 'appCode', event: 'setItem', title: '系统编号'}
            ,{field: 'roleCode', event: 'setItem', title: '节点编号'}
            ,{field: 'title', event: 'setItem', title: '标题'}
            ,{field: 'acceptEmail', event: 'setItem', title: '接受人'}
            ,{field: 'carbonCopyEmail', event: 'setItem', title: '抄送人'}
            ,{field: 'outTime', event: 'setItem', title: '超时时间'}
            ,{field: 'createTime', event: 'setItem', title: '创建时间'}
            ,{field: 'status',title: '状态', sort: true,templet: '#checkboxTpl', unresize: true,fixed: 'right'}
            ,{field: 'warningNoticeId', title: '操作', fixed: 'right',align:'center', toolbar: '#bar'}
        ]],
        done: function(res, curr, count){
            //如果是异步请求数据方式，res即为你接口返回的信息。
            //如果是直接赋值的方式，res即为：{data: [], count: 99} data为当前页数据、count为数据总长度
        }
    });
    //重载
    //这里以搜索为例
    active = {
        reload: function(){
            //var demoReload = $('#demoReload');

            //执行重载
            table.reload('warning', {
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
    //监听工具条
    entityTable.on('tool(warning)', function(obj){
        var data = obj.data;
        console.log(obj);
        if(obj.event === 'detail'){
            layer.msg('ID：'+ data.id + ' 的查看操作');
        } else if(obj.event === 'del'){
            layer.confirm('是否删除？', function(index){
                $.get(preUrl+'delete?id='+data.warningNoticeId,function (data) {
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
            edit(data.warningNoticeId);
        }
    });
    //监听锁定操作
    form.on('checkbox(lockDemo)', function(obj){
        var sta = obj.elem.checked ? 1 : 0;
        var id = this.value;
        $.post(preUrl+'forbidden',{id:id,status:sta},function (data) {
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
    //新增
    $("#sys_btn_add").on('click',function () {
        $.get('/src/html/warning_notice/edit.html', null, function (form) {
            topIndexId =  layer.open({
                type :1,
                title : '新增',
                maxmin : true,
                shadeClose : false, // 点击遮罩关闭层
                area : [ '650px', '560px' ],
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
            $.get('/src/html/warning_notice/edit.html', null, function (form) {
                topIndexId =  layer.open({
                    type :1,
                    title : '修改',
                    maxmin : true,
                    shadeClose : false, // 点击遮罩关闭层
                    area : [ '650px', '560px' ],
                    content :  form,
                    btn: ['保存', '取消'],
                    btnAlign: 'c',
                    success: function (layero, index) {
                        console.log(layero);
                        setFromValues(layero, result);
                        //初始化
                         common.business.init(result.businessId,$("#businessDiv"));
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
});
