(function(){
    //菜单点击J_iframe
    $(".J_menuItem").on('click',function(){
        var url = $(this).attr('href');
        $("#J_iframe").attr('src',url);
        return false;
    });
});
// $("#a_logout").on('click',function(){
//         debugger
//         window.location.href='/logout';
//     });
$('#a_logout').click(function () {
    swal({
            title: "确认注销?",
            text: "",
            type: "warning",
            showCancelButton: true,
            confirmButtonColor: "#DD6B55",
            confirmButtonText: "确认",
            cancelButtonText: "取消",
            closeOnConfirm: false,
            closeOnCancel: false
        },
        function (isConfirm) {
            if (isConfirm) {
                window.location.href='/logout';
            }else {
                 swal("已取消", "", "error");
            }
        });
});