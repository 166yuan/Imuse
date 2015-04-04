/**
 * 
 */
  $(document).ready(function() {

	 
	 getAlbumByOwnerIdAndType(1,1,0,function(data){
			var html = juicer($('#tpl_album').html(),data);
			$('#content_ablums').append(html);
	});
	getPhotoTagByOwnerIdAndType(0,1,function(data){
			var html = juicer($('#tpl_photoTag').html(),data);
			$('#photoTag').append(html);
	});
	getBandById(1,function(data){
			var html = juicer($('#tpl_info').html(),data);
			$('#band_tpl').prepend(html);
	});
	getTrackByOwnerIdAndType(1,1,0,function(data){
			var html = juicer($('#tpl').html(),data);
			$('#tpl_wrap').append(html);
	});
	getBandMemberById(1,function(data){
			var html = juicer($('#tpl_bandMember').html(),data);
	});

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
function getBandById(id,successCB){
	$.get(Url+'bands/getBandByMusician?id='+id, function(data) {
		successCB(data);
	});

}
function getBandMemberById(id,successCB){
	$.get(Url+'bands/getBandMember?id='+id, function(data) {
		successCB(data);
	});

}