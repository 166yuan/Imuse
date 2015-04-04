$(function(){
	$("button#login-sign-button").click(function(){
      //  alert("login.js");
		var username = $("#username").val();
		var password = $("#password").val();
        alert(username);
		$.getJSON('/Login/ajax?email='+username+'&password='+password,function(data){
			if(data[0]=="1")
            { alert("验证成功");
               window.location.href="/home/index";
            }
			else
				alert(data[1]);
		});
	});

	function GetQueryString(name)
	{
	     var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
	     var r = window.location.search.substr(1).match(reg);
	     if(r!=null)	
	     	return  unescape(r[2]); 
	     return null;
	}
});

function redirect(){

    window.location.href="/home/register";
}