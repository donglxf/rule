//config的设置是全局的,引入工具包
layui.config({
    base: '/dispatch/ui/src/js/modules/' //假设这是你存放拓展模块的根目录
}).extend({ //设定模块别名
    sceneUtil: 'decision', //如果 mymod.js 是在根目录，也可以不用设定别名
    myutil:'common',
});
var sceneId = parent.sceneId;
var type = parent.sceneType;
layui.use(['table','form','laytpl','sceneUtil','myutil'], function() {
    var laytpl = layui.laytpl;
    sceneUtil = layui.sceneUtil;
    var form = layui.form;
    var app = layui.app,
        $ = layui.jquery
        , form = layui.form;
    //设置提交类型
    sceneUtil.subType = 2;
    //执行初始化
    $(function(){
        //评分卡
        if(type == 2){
            sceneUtil.openGradeRuleInit(sceneId);
        }
        //决策
        else{
            sceneUtil.openSceneRuleInit(sceneId);
        }

    });

    //导入变量库
    $(".import-entity").on('click', function () {
        sceneUtil.openImport(1);
    });
    //导入变量库
    $(".import-action").on('click', function () {
        sceneUtil.openImport(2);
    });
    //监听权值操作
    form.on('checkbox(lockDemo)', function(obj){
        var sta = obj.elem.checked ? 1 : 0;
        var id = this.value;
        if(obj.elem.checked){
            $(".qzdiv").show();
        }else{
            $(".qzdiv").hide();
            $(".qz").val(1);
        }
    });


});