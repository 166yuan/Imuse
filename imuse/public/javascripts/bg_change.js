/*
音乐榜单切换效果方法
author:梁栩健
*/
function bg_change(li){
  //一次切换的帧数，请确保能整除960
  var interval = 12;
  //循环次数等于帧数
  var j = interval;

  //动画执行方法,参数:offset指位移量，正向右，负向左
  function go(offset){       
        var step = offset/interval; 
        if(j>0){
          j--;
          list.style.left =parseInt(list.style.left)+step+'px';
          setTimeout(function(){go(offset);},1);
        };
  };

  //计算位移方法,参数：terminal是指鼠标最后位于的那个li的下标。
  function animate(terminal){
    var offset;
    var start;
    start = parseInt(list.style.left)/(-960);
    offset = (start-terminal)*960;
    go(offset);
  };
  
  //获取bg_list里面的div
  var list = document.getElementById("bg_list");
  //获取bg_tab里面的li
  var lis = document.getElementById("bg_tabs").getElementsByTagName("li");
  //获取bg_list位置
  var location=parseInt(list.style.left);
  if(location==0||location==-960||location==-1920){
    for (var i = 0; i < lis.length; i++) {
      if(li==lis[i]){
        animate(i);
        lis[i].className = "bg_tab_on";
      }else{
        lis[i].className = "bg_tab";
      }
    };
  };   
}