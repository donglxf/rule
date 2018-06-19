/**
 * Name:utils.js
 * Author:Van
 * E-mail:zheng_jinfan@126.com
 * Website:http://kit.zhengjinfan.cn/
 * LICENSE:MIT
 */
layui.define(['layer','laytpl','form','ht_ajax','ht_config'], function(exports) {
    var $ = layui.jquery,
        layer = layui.layer,
        form = layui.form,
        laytpl = layui.laytpl,
        _modName = 'myutil';
    var basePath = layui.ht_config.ruleServicePath.basePath;
    //统一验证添加
    form.verify({
        /**
         * 验证key值的唯一性,需要标识id isId = true
         */
        //value：表单的值、item：表单的DOM对象
        identify: function(value, item){
            var id = $("input[isId=true]").val();
            var flag = false;
            var msg = '';
            if(!new RegExp("^[a-zA-Z0-9_.\u4e00-\u9fa5\\s·]+$").test(value)){
                return '不能有特殊字符';
            }
            //  if(id == undefined || id == ''){
            $.ajax({
                cache : true,
                type : "GET",
                url : basePath+'check/key',
                data : {key:value,
                    type:$(item).attr("identifyType"),
                    other:$(".other").val(),
                    other2:$(".other2").val()
                    , id:id},// 你的formid

                async : false,
                dataType:'json',
                error : function(request) {
                    //alert("Connection error");
                },
                success : function(da) {
                    if (da.code != 0) {
                        flag = true;
                        $(item).focus();
                        msg = da.msg;
                    }
                }
            });
            if(flag)
                return msg;
            // }
        },
        name: function(value, item){ //value：表单的值、item：表单的DOM对象
            if(!new RegExp("^[a-zA-Z0-9_.\u4e00-\u9fa5\\s·]+$").test(value)){
                return '不能有特殊字符';
            }
            if(/^\d+$/.test(value)){
                return '不能全为数字';
            }
        },
        max: function(value, item){ //value：表单的值、item：表单的DOM对象
            if(!new RegExp("^[a-zA-Z0-9_.\u4e00-\u9fa5\\s·]+$").test(value)){
                return '不能有特殊字符';
            }
            if(/(^\_)|(\__)|(\_+$)/.test(value)){
                return '首尾不能出现下划线\'_\'';
            }
            if(/^\d+$/.test(value)){
                return '不能全为数字';
            }
        }
    });

    var myUtil = {
        config : layui.ht_config,
        v: '1.0.3',
        baseSerive:'/dispatch/service',
        //业务相关
        business:{
            select:{
                name:'businessId',
                id:'businessId'
            },
            init_url:  layui.ht_config.ruleServicePath.businessSystem.base+'getAll',
            init_html: function () {
                return     '      <div class="layui-input-inline">\n' +
                    '                    <select name="'+myUtil.business.select.name+'"  lay-filter="business_select" lay-search="" id="'+myUtil.business.select.id+'" lay-verify="required">\n' +
                    '                        <option value="">选择业务线</option>\n' +
                    '                    </select>\n' +
                    '                </div>'
            },
            init_html2: function (id) {
                return ' <select name="'+myUtil.business.select.name+'" lay-filter="business_select" lay-search="" id="'+id+'" lay-verify="required">\n' +
                    '    <option value="">选择业务线</option>\n' +
                    '    </select>';
            },

            init_html4chirden: function (id) {
                return ' <select name="'+myUtil.business.select.name+'" lay-filter="business_select_son" lay-search="" id="'+id+'" lay-verify="required">\n' +
                    '    <option value="">选择业务线</option>\n' +
                    '    </select>';
            },
            init_html3: function (id) {
                return ' <select name="'+myUtil.business.select.name+'" lay-filter="business_select" lay-search="" id="'+id+'" lay-verify="required">\n' +
                    '    <option value="">请选择系统</option>\n' +
                    '    </select>';
            },
            /**
             * 显示下拉框
             * @param businessId 业务id
             * @param obj 放入的地方
             */
            init:function (businessId,obj,selectId) {
                $.get(myUtil.business.init_url,function(data){
                    if(data.code == '0'){
                        if(selectId == '' || selectId == undefined){
                            selectId = myUtil.business.select.id;
                        }
                        var h = myUtil.business.init_html2(selectId);
                        //初始化
                        $(obj).html(h);
                        var result = data.data;
                        for(var i = 0; i<result.length;i++){
                            var option = '<optgroup label="'+result[i].businessSystemName+'">';
                            var businesses = result[i].businessList;
                            if(businesses != null && businesses.length > 0){
                                for(var j = 0; j< businesses.length ;j++){
                                    var ischeck = '';
                                    //选中的设置
                                    if( businesses[j].businessTypeId == businessId){
                                        ischeck = 'selected="true"';
                                    }
                                    option += '<option value="'+businesses[j].businessTypeId+'" '+ischeck+' >'+businesses[j].businessTypeName+'</option>';
                                }
                            }
                            option += '</optgroup>';
                            $(obj).find("#"+selectId).append(option);

                        }
                        if(businessId != undefined && businessId != null ){
                            $(obj).find("#"+selectId).val(businessId);
                        }
                        form.render('select');
                        form.on('select(business_select)', function(data){
                            myUtil.business.selectBack(data);
                        });
                    }
                },'json');
            },
            init4dis:function (businessId,obj,selectId,dis) {
                $.get(myUtil.business.init_url,function(data){
                    if(data.code == '0'){
                        if(selectId == '' || selectId == undefined){
                            selectId = myUtil.business.select.id;
                        }
                        var h = myUtil.business.init_html2(selectId);
                        //初始化
                        $(obj).html(h);
                        var result = data.data;
                        for(var i = 0; i<result.length;i++){
                            var option = '<optgroup label="'+result[i].businessSystemName+'">';
                            var businesses = result[i].businessList;
                            if(businesses != null && businesses.length > 0){
                                for(var j = 0; j< businesses.length ;j++){
                                    var ischeck = '';
                                    //选中的设置
                                    if( businesses[j].businessTypeId == businessId){
                                        ischeck = 'selected="true"';
                                    }
                                    option += '<option value="'+businesses[j].businessTypeId+'" '+ischeck+' >'+businesses[j].businessTypeName+'</option>';
                                }
                            }
                            option += '</optgroup>';
                            $(obj).find("#"+selectId).append(option);
                            $(obj).find("#"+selectId).attr("disabled",true);
                        }
                        if(businessId != undefined && businessId != null ){
                            $(obj).find("#"+selectId).val(businessId);
                        }
                        form.render('select');
                        form.on('select(business_select)', function(data){
                            myUtil.business.selectBack(data);
                        });
                    }
                },'json');
            },
            //渲染出二级级联，包括系统， 业务线
            init4chirden:function (businessId,obj,selectId) {
                $.get(myUtil.business.init_url,function(data){
                    // alert(JSON.stringify(data))
                    if(data.code == '0'){
                        if(selectId == '' || selectId == undefined){
                            selectId = myUtil.business.select.id;
                        }
                        //渲染系统菜单
                        var h = myUtil.business.init_html3(selectId);
                        //初始化
                        $(obj).html(h);
                        var result = data.data;
                        var option = '';
                        for(var i = 0; i<result.length;i++){
                             option += '<option value="'+result[i].businessSystemlId+'">'+result[i].businessSystemName+'</option>';
                        }
                        $(obj).find("#"+selectId).append(option);

                        //添加子集
                        $(obj).after(
                            '<div class="layui-input-inline">'+
                            myUtil.business.init_html4chirden(selectId+"_son")+
                        '</div>');

                        form.render('select');
                        form.on('select(business_select)', function(data){
                            console.info(data);
                            //级联
                            $(obj).find("#"+selectId+"_son").html('');
                            var index = -1;
                            for(var i = 0; i<result.length;i++){
                                if(data.value == result[i].businessSystemlId){
                                    index = i;
                                    break;
                                }

                            }
                            var  option = '<option value=""  >请选择业务线</option>';
                            if(index < 0){


                            }else{
                                var businesses = result[index].businessList;
                                if(businesses != null && businesses.length > 0){
                                    for(var j = 0; j< businesses.length ;j++){
                                        option += '<option value="'+businesses[j].businessTypeId+'"  >'+businesses[j].businessTypeName+'</option>';
                                    }
                                }
                            }
                            $("#"+selectId+"_son").html(option);
                            form.render('select');

                        });
                    }
                },'json');
            },
            //系统下拉
            init4sys:function (businessId,obj,selectId) {
                $.get(myUtil.business.init_url,function(data){
                    if(data.code == '0'){
                        if(selectId == '' || selectId == undefined){
                            selectId = myUtil.business.select.id;
                        }
                        var h = myUtil.business.init_html3(selectId);
                        //初始化
                        $(obj).html(h);
                        var result = data.data;
                        var option = '';
                        for(var i = 0; i<result.length;i++){
                            option += '<option value="'+result[i].businessSystemlId+'">'+result[i].businessSystemName+'</option>';
                        }
                        $(obj).find("#"+selectId).append(option);
                        form.render('select');
                    }
                },'json');
            },
            selectBack:function (data) {
            }
        },

    };
    exports('myutil', myUtil);
});