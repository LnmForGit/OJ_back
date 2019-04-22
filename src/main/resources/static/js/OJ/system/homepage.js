$(function() {
    showhomepage();
    check();
});
function formatDate (date) {
    date*=1000;
    var tt = new Date(date)
    var date= new Date(Date.parse(tt));
    var y = date.getFullYear();
    var m = date.getMonth()+1;
    var d = date.getDate();
    var hour = date.getHours();
    var min = date.getMinutes();
    var scend = date.getSeconds();
    var time = y+'-'+m+'-'+d+' '+hour+':'+min+':'+scend;
    return time;
}
function showhomepage() {
    $.ajax({
        type: "",
        url: "/homepage",
    })
}
function check() {
    $.ajax({
        type: "POST",
        url: "/homepage/check",
        success: function (result) {
            if(result=="1")
            {
                showecharts();
                showpending();
            }
        }
    });
}
function showecharts() {
    $.ajax({
        type: "POST",
        url: "/homepage/getSubmit",
        dataType: "json",
        contentType: "application/json;charset=UTF-8",
        data:JSON.stringify({
            "ip" : ' ',
        }),
        success:function (result) {
            var len = result.length;
            var datetime = new Array(len);
            var ac = new Array(len);
            var num = new Array(len);
            for(var i=0;i<len;i++)
            {
                datetime[i] = result[i].date;
            }
            for(var i=0;i<len;i++)
            {
                ac[i] = result[i].AC;
            }
            for(var i=0;i<len;i++)
            {
                num[i] = result[i].number;
            }
            var lineChart = echarts.init(document.getElementById("echarts-line-chart"));
            var lineoption = {
                title : {
                    text: '本月提交概括'
                },
                tooltip : {
                    trigger: 'axis'
                },
                legend: {
                    data:['提交数','AC数']
                },
                grid:{
                    x:40,
                    x2:40,
                    y2:24
                },
                calculable : true,
                xAxis : [
                    {
                        type : 'category',
                        boundaryGap : false,
                        data : datetime
                    }
                ],
                yAxis : [
                    {
                        type : 'value',
                        axisLabel : {
                            formatter: '{value} 次'
                        }
                    }
                ],
                series : [
                    {
                        name:'提交数',
                        type:'line',
                        data : num,
                        markPoint : {
                            data : [
                                {type : 'max', name: '最大值'},
                                {type : 'min', name: '最小值'}
                            ]
                        },
                        markLine : {
                            data : [
                                {type : 'average', name: '平均值'}
                            ]
                        }
                    },
                    {
                        name:'AC数',
                        type:'line',
                        data : ac,
                        markPoint : {
                            data : [
                                {type : 'max', name: '最大值'},
                                {type : 'min', name: '最小值'}
                            ]
                        },
                        markLine : {
                            data : [
                                {type : 'average', name : '平均值'}
                            ]
                        }
                    }
                ]
            };
            lineChart.setOption(lineoption);
            $(window).resize(lineChart.resize);
        }
    })
}

//待办项显示
function showpending() {
    $.ajax({
        type: "POST",
        url: "/homepage/getPending",
        dataType: "json",
        contentType: "application/json;charset=UTF-8",
        data: JSON.stringify({
            "ip": $('#userAccount').val(),
        }),
        success: function (result) {
            var len = result.length;
            if(len>8)
            {
                len = 5;
            }
            for(var i=0;i<len;i++)
            {
                if(result[i].state=="已结束")
                {
                    //var s = "<h2 style='color: #9999cc;background-color: #aa6659'>已结束</h2>";
                    var s = "<span class=\"label label-danger\">已结束</span>";
                }
                else if(result[i].state=="正在进行")
                {
                    //var s = "<h2 style='color: #131e26;background-color: #2D93CA'>正在进行</h2>"
                    var s = "<span class=\"label label-info\">正在进行</span>";
                }
                else if(result[i].state=="未开始")
                {
                    //var s = "<h2 style='color: #131e26;background-color: #4cae4c'>未开始</h2>"
                    var s = "<span class=\"label label-warning\">未开始</span>";
                }
                var str = "";
                if(result[i].NAME[0]=="实")
                {
                    str = "/experimentMn/"
                }
                else
                {
                    str = "/testMn/"
                }
                document.getElementById("notes").innerHTML +="<li><div class=\"ibox\">\n" +
                    "        <div class=\"ibox-title\">\n" +s+
                    "            <h3><a href='"+str+"'>"+result[i].NAME+"</a></h3>\n" +
                    "        </div>\n" +
                    "        <div class=\"ibox-content\">\n" +
                    "            <h3>开始时间:"+formatDate(result[i].start_time)+"<br>"+
                    "                结束时间:"+formatDate(result[i].end_time)+"<br>查看<a href='#' style='position:relative;right:0px;bottom:0px;color: #0d8ddb'>成绩</a></h3>\n" +
                    "        </div>\n" +
                    "    </div></li>";
                // document.getElementById("notes").innerHTML += "<li>\n" +
                //     "                <div>\n" +s+
                //     "                    <h3><a href='"+str+"' style='position:relative;left: 10px;\n" +
                //     "    top: 0px;color: #2d6ca1'>"+result[i].NAME+"</a></h3>\n" +
                //     "                    <h4>开始时间:"+formatDate(result[i].start_time)+"<br>"+
                //     "结束时间:"+formatDate(result[i].end_time)+"<br>查看<a href='#' style='position:relative;right:0px;bottom:0px;color: #2d6ca1'>成绩</a></h4>\n" +
                //     "                </div>\n" +
                //     "            </li>";
            }
        }
    })
}