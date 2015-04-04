$(function(){
	function musicPlay(){
		/*菜单控制*/
		$(".play_container_left ul li").click(function(){
			$(".play_container_left ul li").removeClass('active');
			$(this).addClass('active');
		});
		/*音乐播放*/
		audiojs.events.ready(function() {
			/*音乐进度属性*/
	        var clickMode = 0;
	        var proTotal = $('.center_panel_bar').width();
	        var proHead = $('.center_panel_bar').offset().left;
	        var proCur = $('.center_panel_bar').offset().left;
	        var progress = 0;
	        /*音量进度属性*/
	        var volMode = 0;
	        var volTotal = $('.right_panel_bar').width();
	        var volHead = $('.right_panel_bar').offset().left;
	        var volCur = $('.right_panel_bar_cur').offset().left;
	        var volPro = 0.5;
	        /*audio对象*/
	        var as = audiojs.createAll();
	        audio = as[0];
	        audio.setVolume(volPro);
	        /*歌曲播放初始化*/
	        var loadSongMsg = function(song){
	        	$(".center_panel_title").html(song.attr('song-name')+"-"+song.attr('author-name'));
	        }
	        var loadSong = function(song){
	        	$(".play_container_center_item").removeClass('active');
	       		song.addClass('active');
	        	var releaseSrc = song.attr('release-src');
	       		var audioSrc = song.attr('audio-src');
	       		/*下载歌词*/
	       		var lrcAddr = song.attr('lyricFile');
	       		var lrc = getLRC(lrcAddr);
	       		if(releaseSrc==null)
	       			audio.load(releaseSrc);
	       		else
	       			audio.load(audioSrc);
	    		audio.play();
	    		setInterval(playSong,200);
	    		loadSongMsg(song);
	    		$('.left_panel_play').css("background","url(/public/images/play/icon_suspend.png) center center no-repeat");
	        }
	        /*播放按钮*/
	        $('.play_container_center_content').on('click','ul',function(){
	       		loadSong($(this));
	      	});
	        /*开始/暂停播放*/
	        $(document).on('click','.play_music_content .left_panel_play',function(){
	        	if(audio.playing){
	        		$('.left_panel_play').css("background","url(/public/images/play/resouce.png) -370px -749px");
	        	}
	        	else{
	        		$('.left_panel_play').css("background","url(/public/images/play/icon_suspend.png) center center no-repeat");
	        	}
	        	if(audio.mp3=="#"){
	        		prevSong = $('.play_container_center_item').first();
	            	loadSong(prevSong);
	        	}
	        	else
	        		audio.playPause();
	        	return false;
	        });
	        /*上一首歌曲*/
	        $('.left_panel_prev').click(function(){
	        	var prevSong = $('.play_container_center_item.active').prev();
	        	if(!prevSong.hasClass('play_container_center_item'))
	        		prevSong = $('.play_container_center_item').first();
	        	loadSong(prevSong);
	        	return false;
	        });
	        /*下一首歌曲*/
	        $('.left_panel_next').click(function(){
	        	var nextSong = $('.play_container_center_item.active').next("");
	        	if(!nextSong.hasClass('play_container_center_item'))
	        		nextSong = $('.play_container_center_item').first();
	        	loadSong(nextSong);
	        	return false;
	        });
	        /*音量进度事件*/
	        var volDownEvent = function(){
	        	volMode = 1;
	        	volCur = $(this).offset().left-$('.right_panel_bar').offset().left;
	        	return false;
	        }
	        var volClickEvent = function(e){
	        	volCur = e.pageX-volHead;
	        	volPro = volCur/volTotal;
	    		if(volPro > 1){
	    			$('.right_panel_bar_cur').css("width","100%");
	        		$('.right_panel_bar a').css({"left":volTotal-6+"px"});
	        		volPro = 1;
	    		}
	    		else if(volPro < 0){
	    			$('.right_panel_bar_cur').css("width","0%");
	        		$('.right_panel_bar a').css("left","-6px");
	        		volPro = 0;
	    		}
	    		else{
	        		$('.right_panel_bar_cur').css("width",volPro*100+"%");
	        		$('.right_panel_bar a').css("left",volCur-6+"px");
	    		}
	    		audio.setVolume(volPro);
	        }
	        /*音乐播放事件*/
	        var playSong = function(){
	        	var currentTime = audio.element.currentTime;
	        	var m = Math.floor(currentTime/60);
	        	var s = Math.floor(currentTime%60);
	        	$('.center_panel_time_cur').html((m<10?'0':'')+m+':'+(s<10?'0':'')+s);
	        	$('.center_panel_cur_bar').css("width",audio.element.currentTime/audio.duration*100+"%");
	        	m = Math.floor(audio.duration/60);
	        	s = Math.floor(audio.duration%60);
	        	$(".center_panel_time_all").html((m<10?'0':'')+m+':'+(s<10?'0':'')+s);
	    		$('.center_panel_bar a').css("left",audio.element.currentTime/audio.duration*100+"%");
	    		if (audio.loadedPercent >= 1){
	    			clearInterval(playSong);
	    		}
	        }
	        /*音乐进度事件*/
	        var progDownEvent = function(){
	        	clickMode = 1;
	        	proCur = $(this).offset().left-$('.center_panel_bar').offset().left;
	        	return false;
	        }
	        var proUpEvent = function(){
	        	clickMode = 0;
	        	volMode = 0;
	        }
	        var proClickEvent = function(e){
	        	proCur = e.pageX-proHead;
	    		progress = proCur/proTotal;
	    		if(progress > 1){
	    			$('.center_panel_cur_bar').css("width","100%");
	        		$('.center_panel_bar a').css("left","100%");
	        		progress = 1;
	    		}
	    		else if(progress < 0){
	    			$('.center_panel_cur_bar').css("width","0%");
	        		$('.center_panel_bar a').css("left","-6px");
	        		progress = 0;
	    		}
	    		else{
	        		$('.center_panel_cur_bar').css("width",progress*100+"%");
	        		$('.center_panel_bar a').css("left",proCur-6+"px");
	    		}
	    		audio.skipTo(progress);
	        }
	        /*进度移动事件*/
	        var proMoveEvent = function(e){
	        	if(clickMode == 1){
	        		proCur = e.pageX-proHead;
	        		progress = proCur/proTotal;
	        		if(progress > 1){
	        			$('.center_panel_cur_bar').css("width","100%");
	            		$('.center_panel_bar a').css("left","100%");
	            		progress = 1;
	        		}
	        		else if(progress < 0){
	        			$('.center_panel_cur_bar').css("width","0%");
	            		$('.center_panel_bar a').css("left","-6px");
	            		progress = 0;
	        		}
	        		else{
		        		$('.center_panel_cur_bar').css("width",progress*100+"%");
		        		$('.center_panel_bar a').css("left",proCur-6+"px");
	        		}
	        		audio.skipTo(progress);
	        	}
	        	else if(volMode == 1){
	        		volCur = e.pageX-volHead;
	        		volPro = volCur/volTotal;
	        		if(volPro > 1){
	        			$('.right_panel_bar_cur').css("width","100%");
	            		$('.right_panel_bar a').css("left",volTotal-6+"px");
	            		volPro = 1;
	        		}
	        		else if(volPro < 0){
	        			$('.right_panel_bar_cur').css("width","0%");
	            		$('.right_panel_bar a').css("left","-6px");
	            		volPro = 0;
	        		}
	        		else{
		        		$('.right_panel_bar_cur').css("width",volPro*100+"%");
		        		$('.right_panel_bar a').css("left",volCur-6+"px");
	        		}
	        		audio.setVolume(volPro);
	        	}
	        }
	        /*定义数组的删除方法*/
	        Array.prototype.del = function(n){
	        	if(n<0)
	        		return this;
	        	else
	        		return this.slice(0,n).concat(this.slice(n+1,this.length));
	        }
	        /*LRC歌词格式化*/
	        parseLRC = function(lrcObj){
	        	var lines = lrcObj.split('\n');
				var pattern = /\[\d{2}:\d{2}.\d{2}\]/g;
				//匹配结果保存到数组中
				var result = [];
				//删除不含时间的行
				var arrLength = lines.length;
				for(var i = 0;i < arrLength;i++){
					if(!pattern.test(lines[i])){
						lines = lines.del(i);
						arrLength--;
						i--;
					}
				}
				lines.forEach(function(e,i,a){
					var timeStr = e.match(pattern).toString();
					var value = e.replace(pattern,'');
					
					var time = timeStr.slice(1,-1).split(':');
					var elem = new Array(parseInt(time[0],10)*60+parseInt(time[1]),value);
					result.push(elem);
				});
				//排序
				result.sort(function(a,b){
					return a[0] - b[0];
				});
				return result;
	        }
	        /*歌词下载*/
	        getLRC = function(url){
	        	url = "/res/121524167.lrc";
	        	$.ajax({
	        		url: url,
	        		type: 'GET',
	        		error: function(){
	        			console.log('加载失败');
	        		},
	        		success: function(lrcObj){
	        			var result = parseLRC(lrcObj);
	        			result.forEach(function(e,i,a){
	        				$('.play_lrc_container ul').append('<li class="ui-lrc-sentence">'+e[1]+'</li>');
	        			});
	        			audio.element.ontimeupdate = function(e){
	        				for(var i=0,l=result.length;i<l;i++){
	        					if(parseInt(this.currentTime) == result[i][0]){
	        						$('.play_lrc_container ul li').removeClass('ui-lrc-active');
	        						$('.play_lrc_container ul li:eq('+i+')').addClass('ui-lrc-active');
	        					}
	        				}
	        			}
	        			return result;
	        		}
	        	});
	        }
	        /*播放收藏*/
	        $(document).on('click','.play_container_center_item .right .buttons_collect',function(){
	        	var songid = $(this).parents(".play_container_center_item").attr("song-id");
	        	$.get("/PlayLists/addToCollection",{trackId:songid,userId:userId},function(){});
	        	return false;
	        });
	        $(document).on('click','.play_container_center_bottom .button_second',function(){
	        	var songArr = new Array();
	        	$("input:[type='checkbox']:checked").each(function(){
	        		songArr.push($(this).parents(".play_container_center_item").attr("song-id"));
	        	});
	        	$.get("/PlayLists/batchAddtoCollection",{userId:userId,trackId:songArr},function(data){
	    			alert("批量收藏成功!");
	        		console.log("批量收藏成功");
	    		});
	        	return false;
	        })
	        /*普通删除歌曲*/
	        $(document).on('click','.play_container_center_item #buttons_collect_first',function(){
	        	$(this).parents(".play_container_center_item").remove();
	        	audio.pause();
	        });
	        $(document).on('click','.play_container_center_bottom .button_first',function(){
	        	$("input:[type='checkbox']:checked").each(function(){
	        		$(this).parents(".play_container_center_item").remove();
	        	});
	        	audio.pause();
	        	return false;
	        })
	        /*从收藏歌曲中删除*/
	        $(document).on('click','.play_container_center_item #buttons_collect_first_dele',function(){
	        	var trackId = $(this).parents(".play_container_center_item").attr('song-id');
	        	$(this).parents(".play_container_center_item").remove();
	        	$.get("/PlayLists/deleteFromCollection",{userId:userId,trackId:trackId},function(data){
	    			console.log("删除成功");
	    		});
	        	audio.pause();
	        });
	        //TODO 收藏歌曲的批量删除
	        /*事件绑定*/
	        $('.center_panel_bar a').bind('mousedown',progDownEvent);
	        $('.center_panel_bar').click(proClickEvent);
	        $('.right_panel_bar a').bind('mousedown',volDownEvent);
	        $('.right_panel_bar').click(volClickEvent);
	        $(document).bind('mouseup',proUpEvent);
	        $(document).bind('mousemove',proMoveEvent);       
	      });
	}
	$(this).bind('musicPlay',musicPlay);
});
