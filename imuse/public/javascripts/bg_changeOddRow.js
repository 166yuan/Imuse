/*
*  author:梁栩健
*  功能：使音乐榜单偶数行与奇数行样式不同
*/

//获取bg_list里面的li
	var lis = document.getElementById("bg_list").getElementsByTagName("li");
	function changeOddRow(){
		for(var i=0;i<lis.length;i++){
			if(i%2==0){
				lis[i].className="bg_odd";
			}
		}
	}
	changeOddRow();