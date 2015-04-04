/**
 * 
 */
 $(document).ready(function() {

	 getTrackByOwnerIdAndType(0,21,0,function(data){
			var html = juicer($('#tpl').html(),data);
			$('#tpl_wrap').append(html);
	});
	 getAlbumByOwnerIdAndType(1,1,0,function(data){
			var html = juicer($('#tpl_album').html(),data);
			$('#content_ablums').append(html);
	});
	getPhotoTagByOwnerIdAndType(0,1,function(data){
			var html = juicer($('#tpl_photoTag').html(),data);
			$('#photoTag').append(html);
	});
	getMusicianById(1,function(data){
			var html = juicer($('#tpl_info').html(),data);
			$('#singer_tpl').prepend(html);
	})

 });

 function getTrackByOwnerIdAndType(type,ownerId,page,successCB){
	$.get(Url+'tracks/getTrackByOwnerId?id='+ownerId+'&curPage='+page+'&type='+type, function(data) {
		successCB(data);
	});

}
function getAlbumByOwnerIdAndType(type,ownerId,page,successCB){
	$.get(Url+'albums/getAlbum?ownerId='+ownerId+'&curPage='+page+'&type='+type, function(data) {
		successCB(data);
	});

}
function getPhotoTagByOwnerIdAndType(type,ownerId,successCB){
	$.get(Url+'Photos/getTags?ownerId='+ownerId+'&type='+type, function(data) {
		successCB(data);
	});

}
function getMusicianById(id,successCB){
	$.get(Url+'userwebservice/searchMusicianById?id='+id, function(data) {
		successCB(data);
	});

}