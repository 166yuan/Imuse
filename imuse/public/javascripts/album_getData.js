/**
 * author 谢易成
 */
 $(document).ready(function() {
    getAlbumById(21,function(data){
            var html = juicer($('#albimTpl').html(),data);
            $('.banner_introduce_block').first().prepend(html);
            $('#note').text(data['data']['note'])

    });
    getAlbumTrack(21,function(data){
            var html = juicer($('#albumTrackTpl').html(),data);
             $('.ablum_content_list_items').first().append(html);

    });
   

 });

 function getAlbumById(id,successCB){
    $.get(Url+'albums/getAlbumById?albumId='+id, function(data) {
        successCB(data);
    });

}
 function getAlbumTrack(id,successCB){
    $.get(Url+'albums/getAlbumTracks?albumId='+id+'&curPage=0', function(data) {
        successCB(data);
    });

}