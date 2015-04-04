/**
 * author 谢易成
 */
$(document).ready(function() {

	function init(event){

getAlbumByOwnerIdAndType(1, 1, 0, function(data){
			var html = juicer($("#tpl").html(),data);
			$('.content_list').append(html);
			$(document).trigger("setContenList");
		});
		getHotList(1,function(data){
			var html = juicer($('#tpl_hotlist').html(),data);
			//console.log(html)
			$('#rank_rel_container').append(html);
			$('#rank_rel_container').append(html);
			$('#rank_rel_container').append(html);
			$(document).trigger("getSingelStyle");
			$(document).trigger("setIRoll");
		});
       event.preventDefault();
	}

	$(this).bind("init",init);
	$(this).trigger('init');
		
}); 
function getAlbumByOwnerIdAndType(id,type,curpage, successCB){
	
	$.get(Url+'albums/getAlbum?ownerId='+id+'&curPage='+curpage+'&type='+type, function(data) {
		successCB(data);
	});
}
function getHotList(type,successCB){
	$.get(Url+'HotLists/getHotListByType?type='+type+'&timeType=0', function(data) {
		successCB(data);
	});

}