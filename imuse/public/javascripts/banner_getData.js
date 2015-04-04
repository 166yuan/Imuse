/**前端取得首页大图的jquery函数
 * author 陈思远
 */
$(document).ready(function(){
	var webservice = function(url, method, params, cb, err){
		cb = cb || function(data){console.log(data);}
		err = err || function(data){alert(data);}
		$.ajax(url, {
			type : method,
			data: params,
			success: function(resultData){
				if(resultData.result == 0){
					cb(resultData.data);
				}else{
					err(resultData.data);
				}
			},
			error: err
		});
	}
	
	function getBannerByOrder(fetchAll){
		webservice(Url+'Banners/GetBanner','GET', {},fetchAll);
	}
	
	getBannerByOrder(function(data){
		var html = juicer($("#pic_tpl").html(),{list: data});
		$('.banner_gallery ul').append(html);
		$(document).trigger("banner");
		console.log(html);
	});
});
