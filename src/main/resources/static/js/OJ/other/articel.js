var replyList=[];
var j=0;
replyList=Postinfo.replylist;
$(document).ready(function () {
    showarticel();
    queryreplyInfo();
    loadSummernote();
    reply_btn();
    // $(function(){
    //     //页面加载完毕后开始执行的事件
    //     $(".reply_btn").click(function(){
    //         $(".reply_textarea").remove();
    //         $(this).parent().append("<div class='reply_textarea'><textarea placeholder='在这里发表你的观点...' name='' cols='100' rows='5' ></textarea><br/><input type='submit' value='发表' onclick='addreplyson()'/></div>");
    //     });
    // });
});
function reply_btn(id,level) {

    $(".reply_btn").click(function(){
        $(".reply_textarea").remove();
        $(this).parent().append("<div class='reply_textarea'><textarea class='content' placeholder='在这里发表你的观点...' name='' cols='100' rows='5' ></textarea><br/><input type='submit' value='发表' onclick='addreplyson("+id+","+level+")'/></div>");
    });
}
function showarticel() {
    console.log(Postinfo)
    $("#title").html(Postinfo.post.title);
    $("#content").html(Postinfo.post.content);
    $("#username").html(Postinfo.name)
    $("#time").html(formatTime(Postinfo.post.time));
    $("#comment").html(Postinfo.post.reply_num);
    $("#view").html(Postinfo.post.view_num);
    $("#zan").html(Postinfo.post.zan_num);
    $("#iszan").html(Postinfo.zan);
}

function queryreplyInfo(){
     var Test="";
     for(var i in replyList){
         var s="";
         Test+=' <li class="media">\n' +
             '                            <a class="pull-left" href="#">\n' +
             '                                <i class="fa fa-comments-o"></i>\n' +
             '                            </a>\n' +
             '                            <div class="media-body">\n' +
             '                                <h4 class="media-heading">'+replyList[i].name+'</h4>\n' +
             '<p>'+replyList[i].content+'</p>\n'+
             '<span class="text-muted"><i class="fa fa-clock-o"></i>发表于 :'+formatTime(replyList[i].time)+'</span>\n'+
             '<div class="small text-right">\n' +
             '                                    <div><i class="fa fa-comments-o"> </i>' +
             '<a class="reply_btn" onclick="reply_btn('+replyList[i].id+','+replyList[i].level+')">回复('+ replyList[i].sum+')</a>|\n' +
             '                                        <i class="fa fa-thumbs-up"></i><a onclick="replyzan('+replyList[i].id+')">赞('+replyList[i].zannum+')</a></div>\n' +
             '                                </div>\n';
              Test+=replysoninfo(replyList[i].id);
             Test+='                            </div>\n' +
             '                        </li>\n';
    }
     $('.media-list').append(Test);
}
function replysoninfo(level) {
    var newtest="";
    $.ajax({
        type: "POST",
        url: "/block/replysoninfo",
        dataType: "json",
        async:false,
        contentType: "application/json;charset=UTF-8",
        data:JSON.stringify({
            level:level
        }),success(result){

           for(var j in result){
               newtest+=' <div class="media">\n' +
                   '                            <a class="pull-left" href="#">\n' +
                   '                                <i class="fa fa-comments-o"></i>\n' +
                   '                            </a>\n' +
                   '                            <div class="media-body">\n' +
                   '                                <h4 class="media-heading">'+result[j].name+' 回复 '+ result[j].replyedname+'</h4>\n' +
                   '<p>'+result[j].content+'</p>\n'+
                   '<span ><i class="fa fa-clock-o"></i>发表于 :'+formatTime(result[j].time)+'</span>\n'+
                   '<div class="small text-right">\n' +
                   '                                    <div><i class="fa fa-comments-o"> </i>' +
                   '<a class="reply_btn" onclick="reply_btn('+result[j].id+','+result[j].level+')">回复('+ result[j].sum+')</a>|\n' +
                   '                                        <i class="fa fa-thumbs-up"></i><a onclick="replyzan('+result[j].id+')">赞('+result[j].zannum+')</a></div>\n' +
                   '                                </div>'+
                   '                            </div>\n' +
                   '                        </div>\n';
           }
           //console.log(newtest);
            return newtest;
        }
    });

    return newtest;
}
function loadSummernote() {
    $("#reply").summernote({
        height: 200,
        minHeight: 200,
        maxHeight: 200,
        lang: 'zh-CN',
       // placeholder: '在这里畅所欲言吧...',
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
function replyPost(){
    console.log($("#reply").code());
    if($("#reply").code()==undefined){
        toastr.error("","回复内容不能为空");
        return;
    }
    $.ajax({
        type: "POST",
        url: "/block/addreply",
        dataType: "json",
        contentType: "application/json;charset=UTF-8",
        data:JSON.stringify({
            post_id:Postinfo.post_id,
            content: $("#reply").code(),
        }),success(result){
            if(result == true){
                swal("回复成功！");
            }else{
                swal("发布失败！");
            }
            $('#reply').summernote('code','');
        }
    })
}
function postzan() {
    $.ajax({
        type: "POST",
        url: "/block/postzan",
        dataType: "json",
        contentType: "application/json;charset=UTF-8",
        data:JSON.stringify({
            post_id:Postinfo.post.id,
        }),success(result){
            if(result ==0){
           swal("点赞成功！");

            }else{
                swal("取消点赞！");
            }
            setTimeout( function(){
                history.go(0);
            }, 1* 1000 );

        }
    })
}
function replyzan(id){
    $.ajax({
        type: "POST",
        url: "/block/replyzan",
        dataType: "json",
        contentType: "application/json;charset=UTF-8",
        data:JSON.stringify({
            id:id,
        }),success(result){
            if(result ==true){
                swal("点赞成功！");

            }else{
                swal("取消点赞！");
            }
            setTimeout( function(){
                history.go(0);
            }, 500 );

        }
    })
}
function addreplyson(id,level) {
    console.log(id);
    console.log($(".content").val());
    if($(".content").val()==""){
        toastr.error("","回复内容不能为空");
        return;
    }
    $.ajax({
        type: "POST",
        url: "/block/addreplyson",
        dataType: "json",
        contentType: "application/json;charset=UTF-8",
        data:JSON.stringify({
            post_id:Postinfo.post.id,
            pid:id,
            level:level,
            content:$(".content").val()
        }),success(result){
            if(result ==true){
                swal("回复成功！");

            }else{
                swal("回复失败！");
            }
            setTimeout( function(){
                history.go(0);
            }, 1* 1000 );

        }
    })
}
function formatTime(time) {
    time = time.split(".")[0];
    time = time.replace("T", " ")
    return time;
}