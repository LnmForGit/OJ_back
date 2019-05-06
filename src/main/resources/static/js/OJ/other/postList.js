var PostList=[];
var ZanList=[];
if(postinfo.topicId!=null){
    PostList=postinfo.postList;
    console.log(PostList)

}
console.log(PostList);
$(document).ready(function () {
    showpostList();
});

function showpostList(){
    if(PostList.length>0){
        $('#shafa').hide();
    }
    newTest=""
  for(var i in PostList){
      newTest+= '                <div class="ibox-content">\n' +
          '                    <a href="/block/showarticle/'+PostList[i].id+'/'+PostList[i].name+'/'+PostList[i].sub_id+'" class="btn-link">\n' +
          '                        <h2>\n' +PostList[i].title+

          '                        </h2>\n' +
          '                    </a>\n' +
          '                    <div class="small m-b-xs">\n' +
          '                        <strong>' +PostList[i].name+
          '</strong> <span ><i class="fa fa-clock-o"></i> ' +formatTime(PostList[i].time)+
          '</span>\n' +
          '                    </div>\n' +
          '                    <p >\n' +PostList[i].content.replace(/<[^>]+>/g,"").substring(0,50)+
         '       ...             </p>\n' +
          '                    <div class="row">\n' +
          '                        <div class="col-md-6">\n' +
          '\n' +
          '                        </div>\n' +
          '                        <div class="col-md-6">\n' +
          '                            <div class="small text-right">\n' +
          '                              <div><i class="fa fa-comments-o"> </i>&nbsp&nbsp'+PostList[i].reply_num+' 评论 |\n' +
          '                                <i class="fa fa-eye"> </i>&nbsp&nbsp'+PostList[i].view_num+' 浏览|\n' +
          '                               <i class="fa fa-thumbs-up"></i>&nbsp&nbsp'+PostList[i].zan_num+' 点赞</div>\n' +
          '                            </div>\n' +
          '                        </div>\n' +
          '                    </div>\n' +
          '                </div>';
  }
  $('#PostList').append(newTest);
}
function formatTime(time) {
    time = time.split(".")[0];
    time = time.replace("T", " ")
    return time;
}
