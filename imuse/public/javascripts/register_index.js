function check1() {
    var email=$('#email').val();
    var myreg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
    if(!myreg.test(email)){
        alert("请输入有效邮箱");
    }else{
       $('ul.content_top').children().eq(0).addClass('hidden');
        $('ul.content_top').children().eq(1).removeClass('hidden');
        $('.content_center_item').addClass("none");
        $('.content_center_item2').removeClass("none");
    }
}

function check2(){
    var email=$('#email').val();
 var nickname=$('#nickname').val();
     var passwd=$('#passwd').val();
    var repeat=$('#repeat').val();
    var patrn=/^([a-zA-Z0-9]|[._]){1}([a-zA-Z0-9]|[._]){5,19}$/;
    if(nickname!=''&&passwd!=''&&repeat!=''){
    if(passwd!=repeat){
        alert("两次输入密码必须相同");
    }
    else {
    if(patrn.test(passwd)){
        alert("可行，即将提交");
        $.getJSON('/Register/addUser?email=' + email + '&passwd=' + passwd + '&nickname=' + nickname, function (data) {
            if (data[0] == "1") {
                $('ul.content_top').children().eq(1).addClass('hidden');
                $('ul.content_top').children().eq(2).removeClass('hidden');
                $('.content_center_item2').addClass("none");
                $('.content_center_item3').removeClass("none");
            }
            else
                alert(data[1]);
        });
    }else{
    alert("亲，密码格式有误");
    }
    }

    }else{
    alert("昵称密码不能为空");
}

}