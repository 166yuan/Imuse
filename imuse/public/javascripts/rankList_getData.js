/**
 * author 谢易成
 */
 $(document).ready(function() {
 	getHotList(1,0,function(data){
			var html = juicer($('#tpl').html(),data);
			$('#bg_daylist_div .bg_list_ul').first().append(html);
		
	});
	getHotList(1,0,function(data){
			var html = juicer($('#tpl').html(),data);
			$('#bg_weeklist_div .bg_list_ul').first().append(html);
	});
	getHotList(1,0,function(data){
			var html = juicer($('#tpl').html(),data);
			$('#bg_monthlist_div .bg_list_ul').first().append(html);
	});

 });

 function getHotList(type,timeType,successCB){
	$.get(Url+'HotLists/getHotListByType?type='+type+'&timeType='+timeType, function(data) {
		successCB(data);
	});

}