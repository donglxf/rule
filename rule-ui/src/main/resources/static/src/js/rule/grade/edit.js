(function ($) {
    $.fn.extend({
        //表格合并单元格，colIdx要合并的列序号，从0开始
        "rowspan": function (colIdx) {
            return this.each(function () {
                var that;
                $('tr', this).each(function (row) {
                    $('td:eq(' + colIdx + ')', this).filter(':visible').each(function (col) {
                        if (that != null && $(this).html() == $(that).html()) {
                            rowspan = $(that).attr("rowSpan");
                            if (rowspan == undefined) {
                                $(that).attr("rowSpan", 1);
                                rowspan = $(that).attr("rowSpan");
                            }
                            rowspan = Number(rowspan) + 1;
                            $(that).attr("rowSpan", rowspan);
                            $(this).hide();
                        } else {
                            that = this;
                        }
                    });
                });
            });
        }
    });

})(jQuery);

/**
 * 当前行下添加一行
 * @param t
 */
function  addRow(t) {
   // console.log($('#trTpl').find(".ctr").html());
    var tr = $(t).parent().parent().parent();
    tr.after( tr.clone() );
    init();
    //获取当前行行数
    //$(t).append( $(t).parent().parent().parent().clone(true))
}

/**
 * 删除当前行
 * @param t
 */
function deleteRow(t){
     $(t).parent().parent().parent().remove();
}

/**
 * 添加条件列
 */
function addCol() {

    $th = $("#tpl").find("th:eq(0)").clone();
    $td = $("#tpl").find("td:eq(0)").clone();
    $col = $("#tpl").find("col:eq(0)").clone();
    var index = $(".contion").length;
    index = index - 2;
    $("#table>colgroup>col:eq("+index+")").after($col);
    $("#table>thead>tr>th:eq("+index+")").after($th);

    //需要添加多少列
    $("#table>tbody>tr").each(function(i,e){
        var td = $("#tpl").find("td:eq(0)").clone();
        $(e).find("td:eq("+index+")").after(td);
       // $($(e).find("td:eq("+index+")")[0]).after(td);
    });
    init();
}

/**
 * 添加动作列
 */
function addActionCol() {

    $th = $("#tpl").find("th:eq(1)").clone();
    $td = $("#tpl").find("td:eq(1)").clone();
    $col = $("#tpl").find("col:eq(1)").clone();
    var index = $("#table thead tr th").length;
    index = index - 2;
  //  index = index - 2;
    $("#table>colgroup>col:eq("+index+")").after($col);
    $("#table>thead>tr>th:eq("+index+")").after($th);

    //需要添加多少列
    $("#table>tbody>tr").each(function(i,e){
        var td = $("#tpl").find("td:eq(1)").clone();
        $(e).find("td:eq("+index+")").after(td);
        // $($(e).find("td:eq("+index+")")[0]).after(td);
    });
    init();
}

/**
 * 添加条件列
 */
function deleteCol(t) {
    var td = $(t).parent();
    var index = td.index();
    $("#table>colgroup>col:eq("+index+")").remove();

    $("#table>thead>tr>th:eq("+index+")").remove();
    $("#table>tbody>tr").each(function(i,e){
        var td = $("#tpl").find("td").clone();
        $(e).find("td:eq("+index+")").remove();
    });


}

function hb(){
    $("#table").rowspan(0); //以第一列合并可用，但是会影响后面的新增，或删除操作
}
var en ={value:'',text:'',sons:[]}
var entitys = [
  //  en
];
var items = [

];
var actions = [];
var condata = [
    { value: "==", text: "等于" },
    { value: "!=", text: "不等于" },
    {value: "<",text:"小于"},
    {value:"<=",text:"小于或等于"},
    {value:">",text:"大于"},
    {value:">=",text:"大于或等于"},
    {value:"in",text:"包含"},
    {value:"not in",text:"不包含"},
    {value:"like%",text:"开始以"},
    {value:"%like",text:"结束以"},
    {value:"===",text:"忽略"},
];
var actionTypes = [
    { value: "3", text: "得分" },
];
function init(){

   // headItem();
    $("#table thead tr th").hover(function () {
        $(this).find(".del").show();
    },function () {
        $(this).find(".del").hide();
    })
    $('.val').editable({
        type: "text",                //编辑框的类型。支持text|textarea|select|date|checklist等
        title: "值",              //编辑框的标题
        disabled: false,             //是否禁用编辑
        emptytext: "空文本",          //空值的默认文本
        mode: "popup",              //编辑框的模式：支持popup和inline两种模式，默认是popup
        onblur:"submit",         //取消的时候 提交
        validate: function (value) { //字段验证
            if (!$.trim(value)) {
                return '不能为空';
            }
            $(this).attr("data-value",value);
        }
    });
    $('.actionVal').editable({
        type: "text",                //编辑框的类型。支持text|textarea|select|date|checklist等
        title: "值",              //编辑框的标题
        disabled: false,             //是否禁用编辑
        emptytext: "空文本",          //空值的默认文本
        mode: "popup",              //编辑框的模式：支持popup和inline两种模式，默认是popup
        onblur:"submit",         //取消的时候 提交
        validate: function (value) { //字段验证
            if (!$.trim(value)) {
                return '不能为空';
            }
            $(this).attr("data-value",value);
        }
    });

    //对象
    $('.entityC').editable({
        type: "select",              //编辑框的类型。支持text|textarea|select|date|checklist等
        // value:'2',
        source: entitys,
        title: "选择对象",           //编辑框的标题
        disabled: false,           //是否禁用编辑
        emptytext: "选择对象",       //空值的默认文本
        mode: "popup",            //编辑框的模式：支持popup和inline两种模式，默认是popup
        onblur:"submit",         //取消的时候 提交
        validate: function (value) { //字段验证
            if (!$.trim(value)) {
                return '不能为空';
            }
            $(this).attr("data-value",value);

            $(this).parent().find(".itemC").text("请选择");
            $(this).parent().find(".itemC").attr("data-value","");

            setItemSelect(value,this);
            //触发变量的选择
        }
    });
    //初始化 变量
    $(".entityC").each(function () {
       var entityId = $(this).attr("data-value");
       if(entityId != '' && entityId != undefined){
           setItemSelect(entityId,this);
       }
    });
    //条件类型
    $('.con').editable({
        type: "select",              //编辑框的类型。支持text|textarea|select|date|checklist等
        source: condata,
        title: "选择条件",           //编辑框的标题
        disabled: false,           //是否禁用编辑
        emptytext: "选择条件",       //空值的默认文本
        mode: "popup",            //编辑框的模式：支持popup和inline两种模式，默认是popup
        onblur:"submit",         //取消的时候 提交
        validate: function (value) { //字段验证
            if (!$.trim(value)) {
                return '不能为空';
            }
            if(value == '==='){
                $(this).parent().find(".val").text("");
                $(this).parent().find(".val").attr("data-value","true");
            }else {
                $(this).parent().find(".val").text("值");
                $(this).parent().find(".val").attr("data-value","");
            }
            $(this).attr("data-value",value);

        }
    });
    $('.actionType').editable({
        type: "select",              //编辑框的类型。支持text|textarea|select|date|checklist等
        source: actions,
        title: "动作类型",           //编辑框的标题
        disabled: false,           //是否禁用编辑
        emptytext: "动作类型",       //空值的默认文本
        mode: "popup",            //编辑框的模式：支持popup和inline两种模式，默认是popup
        onblur:"submit",         //取消的时候 提交
        validate: function (value) { //字段验证
            if (!$.trim(value)) {
                return '不能为空';
            }
            var actionId = value;
            //动作参数值
            for(var i = 0; i < actions.length;i++){
                if(actions[i].value == actionId){
                    $(this).attr("data-value",actions[i].paramInfoList[0].value);
                    $(this).parent().find(".actionVal").text(actions[i].paramInfoList[0].text);
                    $(this).parent().find(".actionVal").attr("data-value","");
                    break;
                }
            }

        }
    });

}

/**
 * 数据初始哈哈
 * @param sceneId
 */
function  dataEntityInit() {
    if(entitys  == [] || entitys.length < 1){
        $.ajax({
            cache : true,
            type : "get",
            url : '/dispatch/service/entityInfo/getEntitys',
            // data : data.field,// 你的formid
            async : false,
            error : function(request) {
                alert("Connection error");
            },
            success : function(da) {
                if (da.code == 0) {
                    entitys = da.data;
                } else {
                    layer.msg(data.msg);
                }
            }
        });
    }

}
/**
 * 数据初始哈哈
 * @param sceneId
 */
function  dataActionInit() {
    if(actions  == [] || actions.length < 1){
        $.ajax({
            cache : true,
            type : "get",
            url : '/dispatch/service/actionInfo/getByIds',
            data : {ids:'1,2,3,4,5,6,7'},// 你的formid
            async : false,
            error : function(request) {
                alert("Connection error");
            },
            success : function(da) {
                if (da.code == 0) {
                    actions = da.data;
                } else {
                    layer.msg(data.msg);
                }
            }
        });
    }

}

/**
 * 重置变量集合
 * @param entityId
 * @param t
 */
function setItemSelect(entityId,t){
    console.log(entitys);   
    var items = [];
    for(var i=0;i<entitys.length;i++){
        var enid = entitys[i].value;
      //  console.log(enid);
        if(enid == entityId){
            items = entitys[i].sons;
            //console.log(entitys[i].sons);
        }
    }

    $(t).parent().find(".itemC").editable('destroy');
    //变量
    $(t).parent().find(".itemC").editable({
        type: "select",              //编辑框的类型。支持text|textarea|select|date|checklist等
        source: items,
        title: "选择变量名",           //编辑框的标题
        disabled: false,           //是否禁用编辑
        emptytext: "选择变量",       //空值的默认文本
        mode: "popup",            //编辑框的模式：支持popup和inline两种模式，默认是popup
        onblur:"submit",
        validate: function (value) { //字段验证
            if (!$.trim(value)) {
                return '不能为空';
            }
            $(this).attr("data-value",value);
        }
    });

}
//条件体
var conditionInfo = {
    //条件拼接
    conditionExpression:'',
    //条件+值 中文描述
    conditionDesc:'',
    val:''
};
//动作体
var actionValInfo = {
    //动作id参数Id
    actionParamId:'3',
    //值
    paramValue:''

};
var subForm = {
    //变量 集
    itemVals : {},
    //条件集合
    conditionInfos:[],
    //动作集合
    actionInfos:[],
}
var itemVals = [];
var itemTexts = [];
var entityIds = [];
/**
 * 获取变量列表
 */
function headItem() {
     itemVals = [];
     itemTexts = [];
 /*   $("#table>thead>tr>th a.itemC").each(function () {
        itemVals.push($(this).data('value'));
        itemTexts.push($(this).text());
    });
    $("#table>thead>tr>th a.entityC").each(function () {
        entityIds.push($(this).attr('data-value'));
    });*/


    return itemVals;
}
function getRuleList(){
    var len = $("#table>thead>tr>th.contion").length;
    var subForms = [];
   var  headLen =  $("#table>thead>tr>th").length;
    $("#table>tbody>tr").each(function () {
         subForm = {
            //条件集合
            conditionInfos:[],
            //动作集合
            actionInfos:[]
        }
        var conditionInfos= [];
        var actionInfos = [];
        $(this).find("td").each(function (i,e) {
            //获取条件体
            if(i < len){
                //拼条件 的变量 ，运算符 ，值
                var itemv = $(e).find("a.itemC").attr("data-value");
                var entitId = $(e).find("a.entityC").attr("data-value");
                var itemText = $(e).find("a.itemC").text();

                var ysf = $(e).find("a.con").attr("data-value");
                var ysfText = $(e).find("a.con").text();
                var val = $(e).find("a.val").attr("data-value");
                if(val == '' || ysf == '' || itemv == ''){
                    layer.msg('必选项不能为空');
                    return null;
                }
                var conditionInfo = {
                   conditionExpression:'$'+itemv+'$'+''+ysf+''+val,
                   conditionDesc:'$'+itemText+'$'+''+ysfText+''+val,
                    val:val
               }
               //变量值设置
                itemVals.push(itemv);
                itemTexts.push(itemText);
                //实体类
                entityIds.push(entitId);
                conditionInfos.push(conditionInfo);
            }
            //结果
            else if(i < headLen-1){
                var actionV = $(e).find("a.actionVal").attr("data-value");
                var actionType = $(e).find("a.actionType").attr("data-value");
                if(actionV == ''  ){
                    layer.msg('必选项不能为空');
                    return null;
                }
               var actionValInfo = {
                    //动作id参数Id
                    actionParamId:actionType,
                    //值
                    paramValue:actionV
                };
                actionInfos.push(actionValInfo);
            }

        });
        subForm.conditionInfos = conditionInfos;
        subForm.actionInfos = actionInfos;
        subForms.push(subForm);
    });

    return subForms;
}
/**
 * 提交数据
 */
function sub() {

  //  var itemVal = headItem();
    //获取
    var subForms = getRuleList();
    console.info(subForms);
    if(sceneId == '' || sceneId == undefined){
        layer.msg("必须选中一个场景哦");
        return;
    }
    var form = {
        //场景id
        sceneId:sceneId,
        //变量集合
        itemVals:itemVals,
        //实体类集合
        entityIds :entityIds,
        //条件 和结果集
        vos:subForms
    }
    //转json
    var str = JSON.stringify(form);
    console.log(str);
    $.ajax({
        type: "POST",
        url: "/dispatch/service/dispatch/save",
        data:str,
        dataType: "json",
        contentType: "application/json; charset=utf-8",
        success: function (message) {
            //console.log(message);
            if(message.code == ''){
                layer.msg("恭喜保存成功");
            }
        },
        error: function (message) {
            $("#request-process-patent").html("提交数据失败！");
        }
    });

}

$(function () {

    //初始化 实体类的值
    dataEntityInit();
    //动作
    dataActionInit();
    //console.log(actions);
    // dataInit(sceneId);
    init();

});