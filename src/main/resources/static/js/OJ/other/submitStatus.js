$(document).ready(function () {
    getStatusInfo();
});
//重置form内的标签
function resetForm() {
    $(".form-horizontal input").val("");
    $(".form-horizontal select").val("");
    getStatusInfo();
}
function getStatusInfo() {
    $.ajax({
        type: "POST",
        url: "/submitStatusMn/getSubmitStatusMaplist",
        dataType: "json",
        contentType: "application/json;charset=UTF-8",
        data:{
            "problem_id" : $('#problemId').val()
        },
        success: function (result) {
            console.log(result);
            $.each(result, function (index, value) {
                value.submit_code_length += "b";
                value.submit_memory += "kb";
                value.submit_time += "ms";
                value.submit_date = format(value.submit_date);
            })


            var dataTable = $('#StatusInfoTable');
            if ($.fn.dataTable.isDataTable(dataTable)) {
                dataTable.DataTable().destroy();
            }
            console.log('ok');
            dataTable.DataTable({
                "serverSide": true,
                "autoWidth": false,
                "bSort": false,
                "data": result,
                "columns": [{
                    "data": "problem_id"
                }, {
                    "data": "name"
                }, {
                    "data": "state_name"
                }, {
                    "data": "language_name"
                }, {
                    "data": "submit_time"
                }, {
                    "data": "submit_memory"
                }, {
                    "data": "submit_code_length"
                }, {
                    "data": "submit_date"
                },]
            });
        }
})
}

// function formatTime(time) {
//     time = time.split(".")[0];
//     time = time.replace("T", " ")
//     return time;
// }
//function add0(m){return m<10?'0'+m:m }
function format(time)
{
    return new Date(parseInt(time) * 1000).toLocaleString().replace(/:\d{1,2}$/,' ');
}



