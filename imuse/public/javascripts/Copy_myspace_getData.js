$(document).ready(function() {

	
	getMyInfoByUserId(1,function(data){
		var html= juicer($('#tpl_info').html(),data);
		$(".banner_bottom_rel_introduce_block").append(html);
	});
	
	getMyEventsByDay(1,function(data){
		var html=juicer($('#tpl_events').html(),data);
		$(".big_container_rel_items").append(html);
	});

 });


function getMyEventsByDay(userId,successCB){
	$.get(Url+'UserWebService/getMyPublishInfo?userId='+userId,function(data){
	successCB(data);	
	});
}

function getMyInfoByUserId(userId,successCB){
	$.get(Url+'UserWebService/getMyCountedInfo?userId='+userId,function(data){
		successCB(data);
	});
}



