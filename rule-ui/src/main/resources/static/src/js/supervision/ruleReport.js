///////////////////////////////////////////////////////////////////////
var preUrl = "/dispatch/service/ruleReport/";
layui.use(['table', 'form','laydate','util'], function () {
    var laydate = layui.laydate;
    var layer = layui.layer;
   var table = layui.table;
    var entityTable = layui.table;
    var app = layui.app, $ = layui.jquery, form = layui.form;


    $('.demoTable .layui-btn').on('click', function () {
        var type = $(this).data('type');
        active[type] ? active[type].call(this) : '';
    });

});


