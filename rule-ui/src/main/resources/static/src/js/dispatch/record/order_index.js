//时间
function getBeforeDate(n){
    var n = n;
    var d = new Date();
    var year = d.getFullYear();
    var mon=d.getMonth()+1;
    var day=d.getDate();
    if(day <= n){
        if(mon>1) {
            mon=mon-1;
        }
        else {
            year = year-1;
            mon = 12;
        }
    }
    d.setDate(d.getDate()-n);
    year = d.getFullYear();
    mon=d.getMonth()+1;
    day=d.getDate();
    s = year+"-"+(mon<10?('0'+mon):mon)+"-"+(day<10?('0'+day):day);
    return s;
}
var date_in = getBeforeDate(7)+' - '+ getBeforeDate(0);
var dom = document.getElementById("container_sys1");
var dom2 = document.getElementById("container_sys2");
//单量折线图
var dom3 = document.getElementById("container_sys3");
//业务线图
var dom4 = document.getElementById("container_sys4");
var dom5 = document.getElementById("container_sys5");
//用于使chart自适应高度和宽度,通过窗体高宽计算容器高宽
var resizeWorldMapContainer = function () {
    dom2.style.width = window.innerWidth/2-50+'px';
    dom2.style.height = window.innerHeight-250+'px';
    dom.style.width = window.innerWidth/2-50+'px';
    dom.style.height = window.innerHeight-250+'px';

    dom4.style.width = window.innerWidth/2-50+'px';
    dom4.style.height = window.innerHeight-250+'px';
    dom5.style.width = window.innerWidth/2-50+'px';
    dom5.style.height = window.innerHeight-250+'px';

    dom3.style.width = window.innerWidth-200+'px';
    dom3.style.height = window.innerHeight-350+'px';
};
//设置容器高宽
resizeWorldMapContainer();
var myChart = echarts.init(dom);
var myChart2 = echarts.init(dom2);
var myChart3 = echarts.init(dom3);

var myChart4 = echarts.init(dom4);
var myChart5 = echarts.init(dom5);
var app = {};
var app2 = {};
var app3 = {};
option = null;

app.title = '坐标轴刻度与标签对齐';

option = {
    color: ['#3398DB'],
    title: {
        text: '单量柱形图'
    },
    legend: {
        orient: 'vertical',
        x: 'center',
        data:['派单数']
    },
    tooltip : {
        trigger: 'axis',
        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
        }
    },
    grid: {
        left: '3%',
        right: '4%',
        bottom: '3%',
        containLabel: true
    },
    xAxis : [
        {
            type : 'category',
            data : ['业务线1', '业务线2', '业务线3', '业务线4'],
            axisTick: {
                alignWithLabel: true
            }
        }
    ],
    yAxis : [
        {
            type : 'value'
        }
    ],
    series : [
        {
            name:'派单数',
            type:'bar',
            barWidth: '40%',
            data:[10, 52, 200, 150]
        }
    ]

};

app2.title = '环形图';

option2 = {
    title : {
        text: '单量饼图',
        x:'center'
    },
    tooltip : {
        trigger: 'item',
        formatter: "{a} <br/>{b} : {c} ({d}%)"
    },
    legend: {
        bottom: 10,
        left: 'center',
        data:['业务线1', '业务线2', '业务线3', '业务线4']
    },
    series : [
        {
            name: '访问来源',
            type: 'pie',
            radius : '55%',
            center: ['50%', '60%'],
            data:[
                {value:335, name:'业务线1'},
                {value:310, name:'业务线2'},
                {value:234, name:'业务线3'},
                {value:135, name:'业务线4'}
            ],
            itemStyle: {
                emphasis: {
                    shadowBlur: 10,
                    shadowOffsetX: 0,
                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                }
            }
        }
    ]
};
option4 = {
    color: ['#3398DB'],
    title: {
        text: '单量柱形图'
    },
    legend: {
        orient: 'vertical',
        x: 'center',
        data:['派单数']
    },
    tooltip : {
        trigger: 'axis',
        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
        }
    },
    grid: {
        left: '3%',
        right: '4%',
        bottom: '3%',
        containLabel: true
    },
    xAxis : [
        {
            type : 'category',
            data : ['业务线1', '业务线2', '业务线3', '业务线4'],
            axisTick: {
                alignWithLabel: true
            }
        }
    ],
    yAxis : [
        {
            type : 'value'
        }
    ],
    series : [
        {
            name:'派单数',
            type:'bar',
            barWidth: '40%',
            data:[10, 52, 200, 150]
        }
    ]

};


option5 = {
    title : {
        text: '单量饼图',
        x:'center'
    },
    tooltip : {
        trigger: 'item',
        formatter: "{a} <br/>{b} : {c} ({d}%)"
    },
    legend: {
        bottom: 10,
        left: 'center',
        data:['业务线1', '业务线2', '业务线3', '业务线4']
    },
    series : [
        {
            name: '访问来源',
            type: 'pie',
            radius : '55%',
            center: ['50%', '60%'],
            data:[
                {value:335, name:'业务线1'},
                {value:310, name:'业务线2'},
                {value:234, name:'业务线3'},
                {value:135, name:'业务线4'}
            ],
            itemStyle: {
                emphasis: {
                    shadowBlur: 10,
                    shadowOffsetX: 0,
                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                }
            }
        }
    ]
};
/**
 * 总体图
 */

option3 = {
    title: {
        text: '单量统计'
    },
    tooltip: {
        trigger: 'axis'
    },
    legend: {
        x:'center',
        data:['新建','完成','关闭']
    },
    grid: {
        left: '3%',
        right: '4%',
        bottom: '3%',
        containLabel: true
    },
    toolbox: {
        feature: {
            saveAsImage: {}
        }
    },
    xAxis: {
        type: 'category',
        boundaryGap: false,
        data: ['周一','周二','周三','周四','周五','周六','周日']
    },
    yAxis: {
        type: 'value'
    },
    dataZoom: [
        {   // 这个dataZoom组件，默认控制x轴。
            type: 'slider', // 这个 dataZoom 组件是 slider 型 dataZoom 组件
            start: 0,      // 左边在 10% 的位置。
            end: 100         // 右边在 60% 的位置。
        }
    ],
    series: [
        {
            name:'新建',
            type:'line',
            stack: '新建',
            data:[120, 132, 101, 134, 90, 230, 210],
            symbolSize: 15,   //设定实心点的大小
            itemStyle: {
                normal: {
                    color: "#2ec7c9",
                    width:3,
                    lineStyle: {
                        color: "#2ec7c9",width:3
                    }
                }
            }
        },
        {
            name:'完成',
            type:'line',
            stack: '完成',
            data:[220, 182, 191, 234, 290, 330, 310],
            symbolSize: 15,   //设定实心点的大小
            itemStyle: {
                normal: {
                    color: "#ee7b1a",
                    width:3,
                    lineStyle: {
                        color: "#ee7b1a",width:3
                    }
                }
            }
        },
        {
            name:'关闭',
            type:'line',
            stack: '关闭',
            data:[150, 232, 201, 154, 190, 330, 410],
            symbolSize: 15,   //设定实心点的大小
            itemStyle: {
                normal: {
                    color: "#b8c2d3",
                    width:3,
                    lineStyle: {
                        color: "#b8c2d3",width:3
                    }
                }
            }
        }

    ]
};

//用于使chart自适应高度和宽度
window.onresize = function () {
    //重置容器高宽
    resizeWorldMapContainer();
    myChart.resize();
};

if (option && typeof option === "object") {
    //系统统计
    myChart.setOption(option, true);
    myChart2.setOption(option2, true);
    //业务线统计
    myChart4.setOption(option4, true);
    myChart5.setOption(option5, true);
    //汇总统计
    myChart3.setOption(option3, true);
}
var baseUrl = "/dispatch/service/systemDaily/";
var urlMap = {
    "orderUrl":baseUrl+"orderAnalysisReportChart",
    "businessUrl":baseUrl+"businessOrderAnalysisReportChart",
    "sysUrl":baseUrl+"systemOrderAnalysisReportChart",
};
//加载数据


// 异步加载数据
//系统数据图表

var active = {
    reload4sys:function () {
        //系统参数值
        var time_sys  = $('#date_sys').val()||date_in;
        var startDate_sys = time_sys.split(" - ")[0];
        var endDate_sys = time_sys.split(" - ")[1];
        var sys_param = {
            "beginTime":startDate_sys,
            "endTime":endDate_sys,

        };
        $.ajax({
            url:urlMap.sysUrl, //处理页面的路径
            data:JSON.stringify(sys_param), //要提交的数据是一个JSON
            type:"POST", //提交方式
            contentType: 'application/json; charset=utf-8',
            dataType:"JSON", //返回数据的类型
            //TEXT字符串 JSON返回JSON XML返回XML
            success:function(data){
                var result = data.data;
                // 填入数据
                myChart.setOption({
                    xAxis: [{
                        data: result.businessSystemList
                    }],
                    series: [{
                        data: result.orderCountList
                    }]
                });
                myChart2.setOption({
                    legend:{
                        data: result.businessSystemList
                    },
                    series: [{
                        data: result.orderAnalysisReportlist
                    }]
                });
            }
        });
    },
    reload4business:function () {
        //参数值
        var time_business  = $('#data_business').val()||date_in;
        var startDate_business = time_business.split(" - ")[0];
        var endDate_business = time_business.split(" - ")[1];

        var business_param = {
            "beginTime":startDate_business,
            "endTime":endDate_business,
            "businessSystemId":$("#selectSysId").val(),
        };
        //业务线数据图表
        $.ajax({
            url:urlMap.businessUrl, //处理页面的路径
            data:JSON.stringify(business_param), //要提交的数据是一个JSON
            type:"POST", //提交方式
            contentType: 'application/json; charset=utf-8',
            dataType:"JSON", //返回数据的类型
            //TEXT字符串 JSON返回JSON XML返回XML
            success:function(data) {
                var result = data.data;
                // 填入数据
                myChart4.setOption({
                    xAxis: [{
                        data: result.businessTypeList
                    }],
                    series: [{
                        data: result.orderCountList
                    }]
                });
                myChart5.setOption({
                    legend: {
                        data: result.businessTypeList
                    },
                    series: [{
                        data: result.orderAnalysisReportlist
                    }]
                });
            }
        });
    }
}
active.reload4sys();
active.reload4business();

