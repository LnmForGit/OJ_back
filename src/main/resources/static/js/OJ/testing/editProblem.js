$(document).ready(function () {
    loadSummernote();
    indexSubjectSelect();
    editProblem();

});

var icon = "<i class='fa fa-times-circle'></i>";
var rank;
//重置form内的标签
function resetForm() {
    $(".form-horizontal input").val("");
    $(".form-horizontal select").val("");
    queryProblemsInfo();
}


//题目编辑详情
function editProblem(){
    var id = getUrlParam("id");
    if(id != null && id != ""){
        $("#dialogTitle").html("编辑题目")
        // $("#dialogProblemId").val(id)
        $.ajax({
            type: "POST",
            url: "/problemsMn/getProblemDetails",
            dataType: "json",
            async:false,
            data:{
                "id" : id
            },success:function (result){
                $("#dialogProblemId").id,
                $("#dialogProblemName").val(result[0].name)
                $('#problemSubject').html('<option value="' + result[0].subjectid + '">' + result[0].subject + '</option>').trigger("change");
                if(result[0].public == "")
                    $("#dialogProblemPublic").attr("checked", false);
                else
                    $("#dialogProblemPublic").attr("checked", true);
                $("#experDesc").code(result[0].description)
                $("#dialogProblemInType").val(delHtml(result[0].intype))
                $("#dialogProblemOutType").val(delHtml(result[0].outtype))
                $("#dialogProblemInSample").val(delHtml(result[0].insample))
                $("#dialogProblemOutSample").val(delHtml(result[0].outsample))
                $("#dialogProblemMaxMemory").val(result[0].maxmemory)
                $("#dialogProblemMaxTime").val(result[0].maxtime)
                rank = (result[0].rank);
                $("#dialogProblemRank").ionRangeSlider({
                    min: 1,
                    max: 5,
                    from: result[0].rank,
                    type: 'single',
                    step: 1,
                    prettify: false,
                    hasGrid: true,
                    onFinish: function(obj){        // function-callback, is called once, after slider finished it's work
                        rank = obj.fromNumber;
                    }
                });
                 $("#dialogCodeLanguage").val("GCC")
            }
        })
    }else{
        $("#dialogTitle").html("新建题目");
    }
}


//初始化题目详情编辑器
function loadSummernote() {
    $("#experDesc").summernote({
        height: 200,
        minHeight: 200,
        maxHeight: 200,
        lang: 'zh-CN',
        toolbar: [
            ['style', ['style']],
            ['font', ['bold', 'underline', 'clear']],
            ['fontname', ['fontname']],
            ['color', ['color']],
            ['para', ['ul', 'ol', 'paragraph']],
            ['table', ['table']],
            ['view', ['fullscreen', 'codeview']]
        ]

    })
}


//添加新的测试用例
function newTest(){

    var newTest = '<div class="row">\n' +
        '                                    <div class="col-sm-2"></div>\n' +
        '                                    <div class="col-sm-4">\n' +
        '                                         <textarea id="dialogProblemInTest" name="dialogProblemInTest" placeholder="输入用例" class="form-control" rows="6"></textarea>\n' +
        '                                    </div>\n' +
        '                                    <div class="col-sm-4">\n' +
        '                                        <textarea id="dialogProblemOutTest" name="dialogProblemOutTest" placeholder="输出用例" class="form-control" rows="6"></textarea>\n' +
        '                                    </div>\n' +
        '                                     <button class="btn btn-white col-md-1" onclick="deleteTest(this)">删除用例</button>\n' +
        '                                </div>';


  $("#parentTest").append(newTest);
}

//删除测试用例
function deleteTest(Obj) {
    Obj.parentNode.parentNode.removeChild(Obj.parentNode);
}

//获取url中的参数
function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg);  //匹配目标参数
    if (r != null) return unescape(r[2]); return null; //返回参数值
}


//编辑题目后进行存储
function saveOrUpdateProblem() {
    var id = getUrlParam("id");
    var timestamp = Date.parse( new Date() ).toString();
    timestamp = timestamp.substr(0,10);
    if($("#dialogProblemInfo").validate({
        rules: {
            dialogProblemName: {
                required: true,
                maxlength: 40
            },
            problemSubject: {
                required: true,
            },
            dialogProblemMaxTime: {
                required: true
            },
            dialogProblemMaxMemory: {
                required: true
            },
        },
        messages: {
            dialogProblemName: {
                required: icon + "题目名称不能为空",
                minlength: icon + "题目名称最长为40"
            },
            dialogProblemSubject: {
                required: icon + "请选择题目类别",
            },
        }
    }).form()){
        $.ajax({
            type: "POST",
            url: "/problemsMn/saveOrUpdateProblem",
            dataType: "json",
            contentType: "application/json;charset=UTF-8",
            data:JSON.stringify({
                "id" : id,
                "name" : $("#dialogProblemName").val(),
                "subjectid" : $("#problemSubject").val(),
                "isPublic" : $("#dialogProblemPublic").is(':checked') ? "on" : "",
                "description" : $("#experDesc").code(),
                "intype" :$("#dialogProblemInType").val(),
                "outtype": $("#dialogProblemOutType").val(),
                "insample": $("#dialogProblemInSample").val(),
                "outsample": $("#dialogProblemOutSample").val(),
                "maxmemory":  $("#dialogProblemMaxMemory").val(),
                "maxtime" : $("#dialogProblemMaxTime").val(),
                "rank" : rank,
                "time" : timestamp,
                "author" : $("#user").val(),
                "kind" : 1,
                "test_data_id" :1,
                "submit_id" : 1

            }),
            success:function (result){
                if(result.flag == 1){
                    swal({
                        title: "保存成功！",
                        type: "success",
                        confirmButtonText:"确认",
                    },function (isConfirm) {
                            if(isConfirm)
                                window.location.href="/problemsMn/";
                        })
                }else{
                    swal("保存失败！", result.message, "error");
                }
            }
        });
    }
}

//初始化题目类型下拉框
function indexSubjectSelect() {
    SubjectTree('problemSubject');

}

//过滤文本中的html标签
function delHtml(msg) {

        var msg = msg.replace(/<\/?[^>]*>/g, ''); //去除HTML Tag
        msg = msg.replace(/[|]*\n/, '') //去除行尾空格
        msg = msg.replace(/&npsp;/ig, ''); //去掉npsp
        return msg;

}

