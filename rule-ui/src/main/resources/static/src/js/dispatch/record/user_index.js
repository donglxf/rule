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

    dom3.style.width = window.innerWidth-100+'px';
    dom3.style.height = window.innerHeight-400+'px';
};
//设置容器高宽
resizeWorldMapContainer();
var myChart = echarts.init(dom);
var myChart3 = echarts.init(dom3);

var app = {};
var app2 = {};
option = null;

app.title = '坐标轴刻度与标签对齐';

//单量柱形图(无关)

app2.title = '环形图';

//无关

//无关

//无关
/**
 * 总体图
 */

option3 = {
    title: {
        text: '单量时长统计'
    },
    tooltip: {
        trigger: 'axis'
    },
    legend: {
        x:'center',
        data:['接单数','处理时长']
    },
    grid: {
        left: '5%',
        right: '5%',
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
        data: [],
        axisLabel: {
            formatter: '{value}'
            // ,
            // interval:0,
            // rotate:-30
        }
    },
    yAxis: [
        {
            type: 'value',
            name: '接单数',
            min: 0,
            // max: 500,
            // interval: 50,
            axisLabel: {
                formatter: '{value} 单'
            }
        },
        {
            type: 'value',
            name: '处理时长',
            min: 0,
            // max: 100,
            // interval: 10,
            axisLabel: {
                formatter: '{value} 分钟'
            }
        }
    ],
    dataZoom: [
        {   // 这个dataZoom组件，默认控制x轴。
            type: 'slider', // 这个 dataZoom 组件是 slider 型 dataZoom 组件
            start: 0,      // 左边在 10% 的位置。
            end: 100         // 右边在 60% 的位置。
        }
    ],
    series: [
        {
            name:'接单数',
            type:'line',
            yAxisIndex: 0,
            data:[],
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
            name:'处理时长',
            //type:'bar',
            type:'line',
            yAxisIndex: 1,
            barWidth:30,
            data:[],
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
        }
    ]
};

//初始化
function getData(requestDto) {
    $.ajax({
        url:'/dispatch/service/systemDaily/userAnalysisReportChart/back',
        type:'post',
        data:requestDto,
        async : false, //默认为true 异步
        error:function(){
            alert('error');
        },
        success:function(response){
            option3['xAxis']['data'] = response.data.statisticsDateList;
            option3['series'][0]['data'] = response.data.orderCountList;
            option3['series'][1]['data'] = response.data.orderHandleTimeList;
            if (option3 && typeof option3 === "object") {
                //汇总统计
                myChart3.setOption(option3, true);
            }
        }
    });
}

//帅选数据
function searchData() {
    var time = $("#timer").val();
    var beginTime = time.substring(0, 10);
    var endTime = time.substring(13, 24);
    var status = $("#status").val();
    var businessTypeId = $("#businessId_son").val();
    var businessSystemId = $("#businessId").val();
    alert(beginTime)
    var requestDto = {
        "businessSystemId": businessSystemId,
        "businessTypeId": businessTypeId,
        "orderStatus": status,
        "beginTime": beginTime,
        "endTime": endTime
    }
    getData(time)
}

//用于使chart自适应高度和宽度
window.onresize = function () {
    //重置容器高宽
    resizeWorldMapContainer();
    myChart.resize();
};

if (option3 && typeof option3 === "object") {
    //汇总统计
    myChart3.setOption(option3, true);
}

searchData();
