
$(document).ready(function () {

    testFunction();
});
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
    echarts.init(document.getElementById('scorePie')).setOption({
        title: {
            text: '测试饼图',
            subtext: 'testMessage',
            x: 'center'
        },
        tooltip : {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        series: {
            name: '考试情况',
            type: 'pie',
            radius: '30%', //可以影响饼的半径
            center: ['50%', '50%'], //可以影响饼的位置
            data: t  //饼图中的数据来源
        }
    });
}
