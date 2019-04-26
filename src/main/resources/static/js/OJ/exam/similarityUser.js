$(document).ready(function () {

    testFunction();

});

function testFunction() {
    var JData = [
        {
            id: 1,
            result: 90,
            Saccount: '2016081523',
            Sname: '潘通',
            SclassName: '大佬165',
            Taccount: '2016081523',
            Tname: '郑祈航',
            TclassName: '大佬166',

        },
    ]
    loadResultList(JData);
    // JData={name:'测试样例002'}
    // loadResultTitle(JData);
}

//加载考试名称
// function loadResultTitle(t) {
//     $('#resultTitle').text(t.name+"-判重结果");
// }
//加载结果列表
function loadResultList(t) {
    var dataTable = $('#resultTable');
    if ($.fn.dataTable.isDataTable(dataTable)) {
        dataTable.DataTable().destroy();
    }
    dataTable.DataTable({
        // "searching":false,
        //"paging": false,
        "serverSide": false,
        "autoWidth": false,
        "bSort": false,
        //"scrollY": "150px",
        //"scrollCollapse": "true",
        "data": t,
        "columns": [{
           "data":"id"
        },{
            "data": "result"
        }, {
            "data": "Saccount"
        }, {
            "data": "Sname"
        }, {
            "data": "SclassName"
        }, {
            "data": "Taccount"
        }, {
            "data": "Tname"
        }, {
            "data": "TclassName"
        },],
        "columnDefs": [{
            "render" : function(data, type, row) {
                var a = "";
                console.log(row);
                a += "<button type='button' class='btn btn-primary' onclick='showSimilarity(\""+row+"\")' title='show' data-toggle='modal' data-target='#courseListDialog' style='margin-right:15px; margin-bottom: -1px;'><i class='fa fa-eject'></i>&nbsp;查看</button>"
                return a;
            },
            "targets" :8
        }]
    });
}
function showSimilarity(row) {
    if(row!=''){
        console.log(row)
        $("#dialogTitle").html("代码比较")
        $("#dialogUserAccount").attr("readonly",true)
        $("#CopyedclassName").val(row.TclassName)
        $("#CopyclassName").val(row.SclassName)
        $("#CopyedName").val(row.Tname)
        $("#CopyName").val(row.Sname)
        $("#CopyedAccount").val(row.Taccount)
        $("#CopyAccount").val(row.Saccount)
        $("#proID").text(row.result)
        $("#proID1").text(row.result)

    }
}