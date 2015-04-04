jQuery(document).ready(function($) {
	$('.upload_content_center_item3').hide();
	$('#first').click(function(event) {
		var text = $('#first_content').val().trim();
		if(text!=null&&text!=""){
			$('#title').attr('value', text);
			$('.upload_content_center_item1').addClass('none');
			$('.upload_content_center_item2').removeClass('none');
			$('.upload_content_top').removeClass('none');
		}
		else{
			alert("灵感名不能为空")
		}
		/* Act on the event */
	});

	$('.upload_content_top').click(function(event) {
			$('.upload_content_top').addClass('none');
			$('.upload_content_center_item1').removeClass('none');
			$('.upload_content_center_item3').addClass('none');
			$('.upload_content_center_item4').addClass('none');
			$('.upload_content_center_item2').addClass('none');
			$('.upload_content_center_item5').addClass('none');
			$('.upload_content_center_item6').addClass('none');

	});


	$('.upload_content_center_item2_select').click(function(event) {
		$('.upload_content_center_item2_select').removeClass('type_select');
		$(event.target).addClass('type_select');
	});

	$('#secend').click(function(event) {
		var type = $('.type_select').first().attr('alt');
		$('#upload_hidden').attr('value', type);
		$('.upload_content_center_item2').addClass('none');
		if(type==0){
		$('.upload_content_center_item3').show();
		}
		if(type==1){
		$('.upload_content_center_item4').removeClass('none');	
		}
		if(type==2){
		$('.upload_content_center_item5').removeClass('none');	
		}
	});

	$('#third_video').click(function(event) {
		var url = $('#videoinput').val().trim();
		if(url!=null&&url!=""){
			$.ajax({
				url: '/ideas/addidea',
				type: 'POST',
				data: $('#myform').serialize(),
			})
			.done(function(data) {
				console.log(data)
				$('.upload_content_center_item5').hide();
				$('.upload_content_center_item6 ').removeClass('none');
			})
			.fail(function(data) {
				console.log(data)
				alert("错误")
			})
			
		}
		else{
			alert("不能为空")
		}
	});

	$('#third_text').click(function(event) {
		var text = $('#editor').val().trim();
		if(text!=null&&text!=""){
			$.ajax({
				url: '/ideas/addidea',
				type: 'POST',
				data: $('#myform').serialize(),
			})
			.done(function(data) {
				console.log(data)
				$('.upload_content_center_item3').hide();
				$('.upload_content_center_item6 ').removeClass('none');
			})
			.fail(function(data) {
				console.log(data)
				alert("错误")
			})
			
			
		}
		else{
			alert("不能为空")
		}
	});

	

	$('#selcectsong').click(function(event) {
		$('#songinput').trigger('click');
	});
	$('#selcectlrc').click(function(event) {
		$('#lrcinput').trigger('click');
	});
	$('#third_track').click(function(event) {
		var songname = $('#songinput').val();
		var lrcname = $('#lrcinput').val();
		if(songname!=""&&lrcname!=""){
			var songtype = songname.substring(songname.lastIndexOf(".")+1,songname.length)
			var lrctype = lrcname.substring(lrcname.lastIndexOf(".")+1,lrcname.length)
			console.log(songtype);
			console.log(lrctype);
			if((songtype=="mp3"||songtype=="ogg")&&lrctype=="lrc"){
			$.ajax({
				url: '/ideas/addidea',
				type: 'POST',
				data: $('#myform').serialize(),
			})
			.done(function(data) {
				console.log(data)
				$('.upload_content_center_item4').hide();
				$('.upload_content_center_item6 ').removeClass('none');
			})
			.fail(function(data) {
				console.log(data)
				alert("错误")
			})
			}
			else{
				alert("文件格式错误")
			}
		}
		else{
			alert("文件不能为空")
		}
	});

});