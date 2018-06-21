var ModelVerification = function (opt) {
    layui.use(['jquery'], function () {
        var $ = layui.jquery;
        this.options = $.extend({}, this.defaults, opt)
    });
}
ModelVerification.prototype = {

    // initRuleBatchResult:function(data){
    //     var html="";
    //     for (var i=0;i<data.length;i++){
    //         html += "<label style=\"font-size: 15px;\">匹配规则数量 "+data[i].count+"</label>"+
    //         "   <a href=\"javascript:void(0)\" onclick=\"showRuleTestResult('"+data[i].logId+"','"+data[i].versionId+"')\">查看详情</a>";
    //     }
    //     return html;
    // },
    initParam:function(data){
        var html="<table class=\"layui-table\">\n" +
            "    <colgroup>\n" +
            "    <col width=\"50%\">\n" +
            "    <col>\n" +
            "    </colgroup>\n" +
            "    <thead>\n" +
            "    <tr>\n" +
            "    <th>变量名称</th>\n" +
            "    <th>传入值</th>\n" +
            "    </tr>\n" +
            "    </thead>\n" ;

        for(var i=0;i<data.length;i++){
            html+="<tbody><tr></tbody><td>"+data[i].variableName+"</td>";
                html+="<td>"+data[i].variableValue+"</td>";
        }
        html+="</tr></tbody></table>";
        return html;
    },
    initResult:function(data){
        var html="<table class=\"layui-table\">\n" +
            "    <colgroup>\n" +
            "    <col width=\"50%\">\n" +
            "    <col>\n" +
            "    </colgroup>\n" +
            "    <thead>\n" +
            "    <tr>\n" +
            "    <th>规则</th>\n" +
            "    <th>输出</th>\n" +
            "    <th>结果</th>\n" +
            "    </tr>\n" +
            "    </thead>\n" ;
        for(var i=0;i<data.length;i++){
            html+="<tbody><tr></tbody><td>"+data[i].ruleDesc.replace(/\$/g,'')+"</td>";
            if(data[i].validationResult=='0'){
                html+="<td style='color: #ff598f;'> <input type=\"checkbox\"  readonly title=\"匹配\" checked></td>";
                html+="<td style='color: #ff598f;'>"+data[i].result+"</td>";
            }else{
                html+="<td style='color: #ff598f;'></td>";
                html+="<td></td>";
            }
        }
        html+="</tr></tbody></table>";
        return html;
    },
    initSelect: function (name, optionData) {
        var html = '<div class="layui-input-inline">';
        html += '<select name="' + name + '0" lay-filter="aihao">';
        html+='<option value="all">所有</option>';
        for (var i = 0; i < optionData.length; i++) {
            var data = optionData[i];
            html += '<option value="' + data.value + '">' + data.name + '</option>';
        }
        html += '</select>';
        html += '</div>';
        return html;
    },
    /**
     * 初始化Lable
     * @param name
     * @returns {string}
     */
    initLabel: function (name) {
        return '<label class="layui-form-label">' + name + '</label>';
    },
    /**
     * 初始化普通输入框
     * @param name
     * @returns {string}
     */
    initInput: function (name, tmpValue) {
        var html = '<div class="layui-input-inline">'
        if (tmpValue != null && tmpValue != "") {
            html += '<input value= "' + tmpValue + '"' + 'type="text" name="' + name + '" lay-verify="required|number" placeholder="整形" autocomplete="off" class="layui-input">'
        } else {
            html += '<input type="text" name="' + name + '" lay-verify="required|number" placeholder="整形" autocomplete="off" class="layui-input">'
        }
        html += '</div>';
        return html;
    },

    /**
     * 初始化普通输入框
     * @param name
     * @returns {string}
     */
    initStrInput: function (name, tmpValue) {
        var html = '<div class="layui-input-inline">'
        if (tmpValue != null && tmpValue != "") {
            html += '<input value= "' + tmpValue + '"' + 'type="text" name="' + name + '" lay-verify="required" placeholder="字符" autocomplete="off" class="layui-input">'
        } else {
            html += '<input type="text" name="' + name + '" lay-verify="required" placeholder="字符" autocomplete="off" class="layui-input">'
        }
        html += '</div>';
        return html;
    },

    initSelect: function (name, optionData, tmpValue) {
        var html = '<div class="layui-input-inline">';
        html += '<select name="' + name + '" lay-filter="">';
        for (var i = 0; i < optionData.length; i++) {
            var data = optionData[i];
            //需要根据值对比,选中默认值
            if (data.value == tmpValue) {
                html += '<option  selected="selected" value="' + data.value + '">' + data.name + '</option>';
            } else {
                html += '<option value="' + data.value + '">' + data.name + '</option>';
            }
        }
        html += '</select>';
        html += '</div>';
        return html;
    },

    /**
     *
     渲染单个变量
     */
    initOneValible: function (valible) {
        var tmpValue = valible.tmpValue;
        var html = "";
        var name=valible.senceVersionId + '#sd#' + valible.variableCode;
        switch (valible.dataType) {
            case "select":
                html = this.initSelect(valible.variableName, valible.optionData);
                break;
            case "date":
                html = this.initDate(valible.variableName);
                break;
            case "time":
                html = this.initTime(valible.variableName);
                break;
            case "Double":
                html = this.initInput(name, tmpValue);
                break;
            case "Integer":
                html = this.initInput(name, tmpValue);
                break;
            case "String":
                html = this.initStrInput(name, tmpValue);
                break;
            case "CONSTANT":
                html = this.initSelect(name, valible.optionData, tmpValue);
                break;
            default:
                break;
        }
        return html;
    },


    /**
     * 初始化日期时间输入框
     * @param name
     * @returns {string}
     */
    initDate: function (name) {
        var html = '<div class="layui-input-inline">';
        html += '<input type="text" name="' + name + '" id="date" lay-verify="date" placeholder="yyyy-MM " autocomplete="off" class="layui-input">';
        html += '</div>';
        return html;
    },
    /**
     * 初始化日期输入框
     * @param name
     * @returns {string}
     */
    initTime: function (name) {
        console.log(index)
        var html = '<div class="layui-input-inline">';
        html += '<input type="text" name="'
            + name +
            '" id="time' + index
            + '" lay-verify="date" placeholder="yyyy-MM-dd HH:mm:ss" autocomplete="off" class="layui-input time">';
        html += '</div>';
        index++;
        return html;
    },

    /**
     * 渲染每个变量的div
     * @param data
     * @returns {string}
     */
    initDiv: function (data) {
        var size = 3;
        var length = data.length;
        var count = Math.ceil(length / size);
        var html = '';
        for (var i = 0; i < count; i++) {
            html += '<div class="layui-form-item">';
            for (var j = 0; j < size && i * size +j < length; j++) {
                var index = i * size +j;
                var name=data[index].variableName;
                html += this.initLabel(name.substring(name.indexOf('_')+1));
                html += this.initOneValible(data[index]);
            }
            html += '</div>';
        }
        return html;
    },
    /**
     * 渲染单个策列表
     * @param senceData
     * @returns {string}
     */
    initSence: function (senceData) {
        var senceName = senceData.sceneName;
        var valiableData = senceData.data;
        var html = '<fieldset class="layui-elem-field layui-field-title" style="margin-top: 10px;font-size: 16px">\n';
        html += '<legend style="font-size: 16px">' + senceName + '</legend>\n<div style="padding-top: 10px">';
        html += this.initDiv(valiableData);
        html += '</div></fieldset>';

        return html;
    },
    /**
     * 模型渲染入口
     * @param data
     * @returns {string}
     */
    initModel: function (data) {
        var senceData = data.variableMap;
        var html = '';
        html += ' <div class="layui-form-item" >\n' +
            '<button type="reset" class="layui-btn layui-btn-primary">重 置</button>' +
            '<button class="layui-btn"  lay-submit="" lay-filter="save" id="save">保 存</button>\n' +
            '</div>';


        for (var i = 0; i < senceData.length; i++) {
            html += this.initSence(senceData[i]);
        }
        return html;
    }
}


<!--定义一个手动测试类-->
var ModelAutoVerification = function (opt) {
    layui.use(['jquery'], function () {
        var $ = layui.jquery;
        this.options = $.extend({}, this.defaults, opt)
    });
}

var modelVerification = new ModelVerification();
var index = 0;

ModelAutoVerification.prototype = {

    initSelect: function (name, optionData) {
        var html = '<div class="layui-input-inline">';
        html += '<select name="' + name + '0" lay-filter="aihao">';
        for (var i = 0; i < optionData.length; i++) {
            var data = optionData[i];
            html += '<option value="' + data.value + '">' + data.name + '</option>';
        }
        html += '</select>';
        html += '</div>';
        return html;
    },
    /**
     * 初始化Lable
     * @param name
     * @returns {string}
     */
    initLabel: function (name) {
        // return '<div class="layui-input-inline"><label class="layui-form-label">' + name + '</label></div>';
        return '<div class="layui-input-inline" style="margin: 0px" ><input type="text" value=' + name + ' class="layui-input" disabled="disabled" style="width: auto"/></div>';
    },
    /**
     * 初始化普通输入框
     * @param name
     * @returns {string}
     */
    initInput: function (name, bindTable, bindColumn) {
        var html = ''
        html += '<div class="layui-input-inline" style="margin: 0px"><input value= "' + bindTable + '"' + 'type="text" name="' + name + "_table" + '"  style="width: auto" placeholder="未绑定" autocomplete="off" class="layui-input"></div>'
        html += '<div class="layui-input-inline" style="margin: 0px"><input value= "' + bindColumn + '"' + 'type="text" name="' + name + "_column" + '"  style="width: auto" placeholder="未绑定" autocomplete="off" class="layui-input"</div>'
        return html;
    },


    /**
     *
     渲染单个变量
     */
    initOneValible: function (valible) {
        var senceVersionId = valible.senceVersionId;
        var variableCode = valible.variableCode;
        var bindTable = valible.bindTable;
        var bindColumn = valible.bindColumn;
        return this.initInput(senceVersionId + '_' + variableCode, bindTable, bindColumn);
    },

    /**
     * 初始化日期时间输入框
     * @param name
     * @returns {string}
     */
    initDate: function (name) {

        var html = '<div class="layui-input-inline">';
        html += '<input type="text" name="' + name + '" id="date' + index + '" lay-verify="date" placeholder="yyyy-MM-dd " autocomplete="off" class="layui-input">';
        html += '</div>';
        index++;
        return html;
    },
    /**
     * 初始化日期输入框
     * @param name
     * @returns {string}
     */
    initTime: function (name) {
        var html = '<div class="layui-input-inline">';
        html += '<input type="text" name="' + name + '" id="dateTime" lay-verify="date" placeholder="yyyy-MM-dd HH:mm:ss" autocomplete="off" class="layui-input">';
        html += '</div>';
        return html;
    },

    /**
     * 渲染单个策列表
     * @param senceData
     * @returns {string}
     */
    initSence: function (senceData) {
        var senceName = senceData.sceneName;
        var valiableData = senceData.data;
        var html = '<fieldset class="layui-elem-field layui-field-title" style="margin-top: 10px;font-size: 16px">\n';
        html += '<legend style="font-size: 16px">' + senceName + '</legend>\n<div style="padding-top: 10px">';
        html += '<div class="layui-form-item">'
        html += '<div class="layui-input-inline" style="margin: 0px"><input  disabled="disabled"  value="变量名称" type="text" name="' + name + '" lay-verify="required" style="width: 400px" autocomplete="off" class="layui-input"></div>'
        html += '<div class="layui-input-inline" style="margin: 0px" ><input  disabled="disabled"  value="数据表" type="text" name="' + name + '" lay-verify="required" style="width: 400px" autocomplete="off" class="layui-input"></div>'
        html += '<div class="layui-input-inline" style="margin: 0px"><input  disabled="disabled"  value="字段" type="text" name="' + name + '" lay-verify="required" style="width: 400px" autocomplete="off" class="layui-input"></div>'
        html += '</div>'
        html += this.initDiv(valiableData);
        html += '</div></fieldset>';

        return html;
    },
    /**
     * 模型渲染入口
     * @param data
     * @returns {string}
     */
    initModel: function (data) {
        var senceData = data.variableMap;
        var html = '';
        html += ' <div class="layui-form-item" >\n' +
            ' <div class="layui-inline">\n' +
            '      <label class="layui-form-label" >测试数量</label>\n' +
            '      <div class="layui-input-inline" style="max-width: 50px">\n' +
            '        <input style="max-width: 100px" type="text" name="amount" autocomplete="off" class="layui-input" onkeyup="this.value=this.value.replace(/\\D/g,\'\')"  onafterpaste="this.value=this.value.replace(/\\D/g,\'\')" maxlength="5" placeholder="请输入整数"/>\n' +
            '      </div>\n' +
            '    </div>' +
            '<button type="reset" class="layui-btn layui-btn-primary">重 置</button>' +
            '<button class="layui-btn"  lay-submit="" lay-filter="save_auto" id="save_auto">保 存</button>\n' +
            '</div>';


        for (var i = 0; i < senceData.length; i++) {
            html += this.initSence(senceData[i]);
        }
        return html;
    }


}