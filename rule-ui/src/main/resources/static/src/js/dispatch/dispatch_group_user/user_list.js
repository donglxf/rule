/**
 * 设置表单值
 * @param el
 * @param data
 */
//获取url参数
(function ($) {
    var qy = $.GetQueryString = function (name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
        var r = window.location.search.substr(1).match(reg);
        if (r != null) return unescape(r[2]);
        return "";
    };
    var qy2 = $.GetUrlQueryString = function (url, name) {
        var result = null;
        if (url) {
            var myIndex = url.indexOf("?");
            if (myIndex != -1) {
                var search = url.substr(myIndex, url.length - myIndex);
                var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
                var r = search.substr(1).match(reg);
                if (r != null) {
                    result = unescape(r[2]);
                }
            }
        }
        return result;
    };
})(jQuery);
var groupId = $.GetQueryString("groupId");
var businessSystemId = $.GetQueryString("businessSystemId");

function setFromValues(el, data) {
    for (var p in data) {
        el.find(":input[name='" + p + "']").val(data[p]);
    }
}

var business = {
    baseUrl: "/dispatch/service/group/",
    uiUrl: "edit.html",
    tableId: "user_list_table",
    toolbarId: "#toolbar",
    unique: "id",
    order: "asc",
    currentItem: {},
    count: 0,
    limit: 10,
    curr: 1
};

//表头
business.cols = function () {
    return [ //表头
        //{field: 'businessId',  event: 'setItem',title: 'ID',sort: true, fixed: 'left'}
        {
            field: 'userName',
            align: 'center',
            title: '成员姓名', sort: true
            ,fixed:'left'
        }
        ,
        {
            field: 'remoteUserId',
            align: 'center',
            title: '成员id'
        },
        {
            field: 'roleCode',
            align: 'center',
            title: '角色编号'
        },
        {
            field: 'businessId',
            title: '操作',
            fixed: 'right',
            align: 'center',
            toolbar: business.toolbarId
        }
    ];
};
var layerTopIndex;
layui.config({
    base: '/dispatch/ui/src/js/modules/' //假设这是你存放拓展模块的根目录
}).extend({ //设定模块别名
    myutil: 'common' //如果 mymod.js 是在根目录，也可以不用设定别名
});


business.loadPage = function (table) {

    var whereCondition = {
        "belongSystemId": $("#belongSystemId").val(),
        "businessTypeId": $("#businessTypeId").val(),
        "contractId": $("#contractId").val(),
        "status": $("#orderStatus").val(),
        "beginTime": $('#timerBegin').val(),
        "endTime": $('#timerStop').val()
    };
    table.reload(business.tableId, {
        page: {
            curr: 1
        },
        where: whereCondition
    })
};

layui.use(['table', 'form', 'laytpl', 'myutil', 'ht_auth', 'laydate'], function () {
    laydate = layui.laydate;
    var laytpl = layui.laytpl;
    var laypage = layui.laypage;
    var table = layui.table;
    businessTable = layui.table;
    var app = layui.app,
        ht_auth = layui.ht_auth,
        form = layui.form;
    $ = layui.jquery;


    //查询按钮
    $('#search-btn').on('click', function () {
        business.loadPage(table);
    });
//重置按钮
    $('#reset').on('click', function () {
        //批量重置搜索条件
        $("#mainForm input").each(function (ind, item) {
            $(item).val("");
        });
        $("#mainForm select").each(function (ind, item) {
            $(item).val("");
        });
        form.render('select');
    });


    //表格加载,开启分页功能
    function loadTable(page, limit) {
        $.ajax({
            type: "POST",
            url: business.baseUrl + 'selectGroupAndUser',
            data: JSON.stringify({groupId: groupId}),
            contentType: "application/json; charset=utf-8",
            success: function (result) {
                //小组名称回显
                $('#groupName').val(result.data.groupName)
                var userList = result.data.userList;
                businessTable.render({
                    elem: '#' + business.tableId
                    , height: 'full'
                    , cellMinWidth: 10
                    //, url: business.baseUrl + 'selectGroupList' //数据接口
                    , page: false //开启分页
                    , id: business.tableId
                    , cols: [business.cols()]
                    , method: 'post'
                    , data: userList
                });
                laypage.render({
                    elem: 'pageDemo' //分页容器的id
                    , count: result.count //总数
                    , limit: business.limit
                    , skin: '#1E9FFF' //自定义选中色值
                    , curr: business.curr
                    //,skip: true //开启跳页
                    , jump: function (obj, first) {
                        if (!first) {
                            loadTable(obj.curr, business.limit);
                            business.curr = obj.curr;
                        }
                    }
                });
            }
        });
    }

    loadTable(1, business.limit);

    //监控工具栏点击事件
    table.on('tool(user_list_table)', function (obj) { //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
        var remoteUserId = obj.data.remoteUserId
        var remoteUserIdList = [remoteUserId];
        var requestDto = {
            groupId: groupId,
            remoteUserIdList: remoteUserIdList,
            businessSystemId:businessSystemId
        }

        var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
        if (layEvent === 'del') { //删除
            layer.confirm('确认删除这条记录?', function (index) {
                $.ajax({
                    type: "POST",
                    url: business.baseUrl + 'groupDeleteUser',
                    data: JSON.stringify(requestDto),
                    contentType: "application/json; charset=utf-8",
                    success: function (result) {
                        //todo : 后台异常
                        //删除成功后提示,并删除表结构
                        var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                        parent.layer.close(index); //再执行关闭
                        alert(result.msg)
                        location.reload();
                    }, error: function (result) {
                        alert(result.msg)
                        location.reload();
                    }

                });
            });
        }
    });

    //弹窗,添加组员到小组
    function addUser2Group() {
        var url = "../dispatch_group_user/add_user.html?groupId=" + groupId + "&businessSystemId=" + businessSystemId;
        var openIndex = layer.open({
            type: 2,
            area: ['60%', '60%'],
            full: true,
            fixed: false,
            maxmin: true,
            title: '派单记录',
            content: url,
            //btn: ['保存', '取消'],
            yes: function (index, layero) {
                var openwin = $(layero).find("iframe")[0].contentWindow;
                if (openwin) {
                    var openPage = openwin.Cobject;
                    if (openPage && openPage.save) {
                        openPage.save(function () {
                            layer.msg('保存数据成功', {icon: 1, time: 1000});
                            layer.close(openIndex);
                            processTypeList.loadPage();
                        });
                    }
                }
            }
        });
    }

    //触发新增组员
    $("#addGroupUser").on('click', function () {
        addUser2Group();
    });
});