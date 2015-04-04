//for banner_gallery
(function () {

       function setPicScroll (event) {
        var pictures = document.getElementsByClassName("banner_gallery_item");
        var scroll_pictures=document.getElementsByClassName("banner_scroll")[0].getElementsByTagName("a");
        var $overCount = $('.banner_gallery_pics li').size()-1;

        var current=0;
        var intervar_id;
        var interval_id2;
         var timeDistance=4000;
        function  init() {
          //初始化首张图片
          $(pictures[current]).addClass("display");
          // 为滚轮按钮设置动作
          for(var i=0;i<scroll_pictures.length;i++){
                scroll_pictures[i].number=i;
               scroll_pictures[i].onmouseover=function () {
                       hide ();
                      current=this.number;
                      show();
                     clearInterval(intervar_id);                  
            };
            scroll_pictures[i].onmouseout=function () {
                      intervar_id=setInterval(repeat,timeDistance);
            };
    
          }
          
          
            $(".banner_scroll ul").css("width",($overCount+1)*22);
          for(var j=scroll_pictures.length-1;j>$overCount;j--){
            $(".banner_scroll ul li").eq(j).remove();
            
          }

        }

        function hide () { 
           
         /* removeClass(pictures[current],"display");
          addClass(pictures[current],"nodisplay");
          var className="banner_scroll_"+current+"_act";
          removeClass(scroll_pictures[current],className);*/
          $(pictures[current]).removeClass("display").addClass("nodisplay");
           var className="banner_scroll_"+current+"_act";
           $(scroll_pictures[current]).removeClass(className);
             
        }
        function show () {
           /*removeClass(pictures[current],"nodisplay");
           addClass(pictures[current],"display");
          var className="banner_scroll_"+current+"_act";
          addClass(scroll_pictures[current],className);*/
          $(pictures[current]).removeClass("nodisplay").addClass("display");
          var className="banner_scroll_"+current+"_act";
          $(scroll_pictures[current]).addClass(className);
          $('#banner_top').attr("href",$(".banner_gallery_pics .display").children("a").attr("href"));


        }
        function repeat () {
              hide();
              if(current==$overCount)
              current=0;
                else
              current++;
              show();

         }
        
          init();
        intervar_id = setInterval(repeat,timeDistance);
         event.preventDefault();

  }

$(document).bind("banner",setPicScroll);

})();

(function () {
  function banner_search(event){
  var label = document.getElementsByClassName("banner_search_block_form")[0].getElementsByTagName("label")[0];
  var input = document.getElementsByClassName("banner_search_block_form")[0].getElementsByTagName("input")[0];

  
   label.onclick=function  () {
    this.style.display="none";
    input.focus();
  };
  input.onfocus=function  () {
    label.style.display="none";
  }
  input.onblur=function  () {
    if(this.value.length==0)
   label.style.display="block";
  };

event.preventDefault();

}
 
$(document).bind("banner",banner_search);

})();
(function(){
     function removeAlpha(){

if(!document.documentMode)
  return;
if(document.documentMode===8||document.documentMode===9||document.documentMode===7){
  $(".banner_block_gradient").remove();
}

}

removeAlpha();




})();


  

