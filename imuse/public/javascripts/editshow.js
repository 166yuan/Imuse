$(document).ready(function() {
$(".prehidden").hide();
$("#showEdit").click(function(){
	if($(".prehidden").is(":hidden")) 
	{ 
		$(".prehidden").show();
	}
	else{
		$(".prehidden").hide();
	}
	}); 
}); 



