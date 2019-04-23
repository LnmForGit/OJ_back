
$(document).ready(function () {

    testFunction();
});
var pieChart = echarts.init(document.getElementById("echarts-pie-chart"));
var pieoption = {
    title : {
        text: '某站点用户访问来源',
        subtext: '纯属虚构',
        x:'center'
    },
    tooltip : {
        trigger: 'item',
        formatter: "{a} <br/>{b} : {c} ({d}%)"
    },
    legend: {
        orient : 'vertical',
        x : 'left',
        data:['直接访问','邮件营销','联盟广告','视频广告','搜索引擎']
    },
    calculable : true,
    series : [
        {
            name:'访问来源',
            type:'pie',
            radius : '55%',
            center: ['50%', '60%'],
            data:[
                {value:335, name:'直接访问'},
                {value:310, name:'邮件营销'},
                {value:234, name:'联盟广告'},
                {value:135, name:'视频广告'},
                {value:1548, name:'搜索引擎'}
            ]
        }
    ]
};
//pieChart.setOption(pieoption);

function testFunction(){
    var tData = [
        {name: '完成0题人数', value: 12},
        {name: '完成1题人数', value: 17},
        {name: '完成3题人数', value: 31},
        {name: '完成4题人数', value: 6},
        {name: '完成2题人数', value: 19}
    ];
    pieChartCreate(tData);
}
function pieChartCreate(t){
    // 绘制图表
    echarts.init(document.getElementById('echarts-pie-chart')).setOption({
        title: {
            text: '测试饼图',
            subtext: 'testMessage',
            x: 'center'
        },
        tooltip : {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        legend: {
            orient : 'vertical',
            x : 'left',
            data:['完成0题人数','完成1题人数','完成3题人数','完成4题人数','完成2题人数']
        },
        series: {
            name: '考试情况',
            type: 'pie',
            radius: '60%', //可以影响饼的半径
            center: ['60%', '50%'], //可以影响饼的位置
            data: t  //饼图中的数据来源
        }
    });
}
