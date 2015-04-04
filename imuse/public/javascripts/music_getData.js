$(function(){
	/*获取url地址参数*/
    function getQueryString(name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
        var r = window.location.search.substr(1).match(reg);
        if (r != null) return unescape(r[2]); return null;
    }
    /*初始化操作*/
    function init(){
    	var albumId = getQueryString("albumId");
    	var trackId = getQueryString("trackId");
    	if(trackId==null){
    		//渲染专辑下的所有歌曲
    		$.get("/MusicBox/playByAlbum",{albumId:albumId},function(data){
    			/*通过juicer获取模板虚拟系*/
    			var html = juicer($("#songList_tpl").html(),{bean:data.data});
    			$(".play_container_center_content").html(html);
    		});
    	}
    	else{
    		//渲染歌曲信息
    		$.get("/MusicBox/playByTrack",{trackId:trackId},function(data){
    			/*通过juicer获取模板虚拟系*/
    			var html = juicer($("#singleSong_tpl").html(),{track:data.data});
    			$(".play_container_center_content").html(html);
    		});
    	}
    }
	/*正在播放*/
	$(".songPlayMenu").click(function(){
		/*$(".play_container_center_bottom").hide();*/
		window.location.href = "/MusicBox/play?albumId=11"
	})
	/*我的收藏*/
	$(".songPlayCollect").click(function(){
		/*$(".play_container_center_bottom").css("display","block");*/
		$.get("/PlayLists/getCollection",{userId:userId},function(data){
			/*通过juicer获取模板虚拟系*/
			var html = juicer($("#songList_collec").html(),{trackList:data.data});
			$(".play_container_center_content").html(html);
		});
	});
	 $(this).bind("init",init);
	 $(this).trigger('init');
	 $(this).trigger('musicPlay');
})