 
// just for i 榜单
// 加深色效果
(function  () {
    function getSingelStyle(event){

      getSingleElement ();
      event.preventDefault();
    }

    function getSingleElement () {
      var rank_rel_content_item=document.getElementsByClassName("rank_rel_content_item");
      for(var i=0;i<rank_rel_content_item.length;i++)
          {
                var rank_rel_content_item_list_items=rank_rel_content_item[i].getElementsByTagName("ul");
                 addSingle(rank_rel_content_item_list_items);
          }

    }

    function addSingle (elem) {
      for(var i=0;i<elem.length;i=i+2)
      {   
             addClass(elem[i],"rank_single");
      }
    }
     

    $(document).bind("getSingelStyle", getSingelStyle); 

})();





    

// for i榜单的滚动
(function(){
  function setIRoll(event){

   getRankDate ();
   event.preventDefault();

  }
function getRankDate () {
	var rank_rel_bar_date=document.getElementsByClassName("rank_rel_bar_date")[0];
	var rank_rel_bar_dates=rank_rel_bar_date.getElementsByTagName("li");
	var rank_rel_container=document.getElementById("rank_rel_container");
  var rank_rel_content_item=document.getElementsByClassName("rank_rel_content_item");
	rank_rel_container.style.left="-960px";
	var current=1;
	addClass(rank_rel_bar_dates[current],"act");
	addClass(rank_rel_content_item[current*2],"rank_main");
	addClass(rank_rel_content_item[current*2+1],"rank_main");

    for(var i=0;i<rank_rel_bar_dates.length;i++)
    {   rank_rel_bar_dates[i].number=i;
    	rank_rel_bar_dates[i].onclick=function (){
          
    		removeClass(rank_rel_content_item[current*2],"rank_main");
	        removeClass(rank_rel_content_item[current*2+1],"rank_main");
            removeClass(rank_rel_bar_dates[current],"act");
            current=this.number;
            addClass(rank_rel_bar_dates[current],"act");
            addClass(rank_rel_content_item[current*2],"rank_main");
	        addClass(rank_rel_content_item[current*2+1],"rank_main");
            move(this.number);
    		
    	};
    }
function move (number) {
  moveElement("rank_rel_container",number*(-960),50,20);
   }

   
}

$(document).bind("setIRoll", setIRoll); 

 
})();




// for recommd 滚动
(
	function  () {
	function setRecommdScroll () {
		var recomend_gallery_arrow_left =document.getElementsByClassName("recomend_gallery_arrow_left")[0];
		var recomend_gallery_arrow_right =document.getElementsByClassName("recomend_gallery_arrow_right")[0];
		var pictureNum = document.getElementById("recomend_gallery_items_container").getElementsByTagName("li").length;
		var recomend_gallery_items_container=document.getElementById("recomend_gallery_items_container");
	     recomend_gallery_items_container.style.left="0px";
        var current=0;
        var distance;
        if(pictureNum%8!=0)
        distance = Math.floor(pictureNum/8);
        else 
        distance=pictureNum/8-1;
        recomend_gallery_arrow_left.onclick=function () {
        	if(current>0)
        	current--;
            move(current);
        };
        recomend_gallery_arrow_right.onclick=function () {
        	if(current<distance)
        	current++;
            move(current);
        };
	}

	function move(number){
		moveElement("recomend_gallery_items_container",number*(-870),50,30);
	}
    
setRecommdScroll();




})();


/*just for content_list*/
(function(){
  function setContenList (event) {
    var items = document.getElementsByClassName("content_list_item");
  for(var i=0;i<items.length;i++)
  {
     
     var icon = items[i].getElementsByTagName('a')[0].getElementsByTagName('i')[0];
     var image = items[i].getElementsByTagName('img')[0];
      image.icon=icon;
     icon.onmouseover=function(){
        this.style.display="block";
     }
     icon.onmouseout=function(){
       this.style.display="none";
     }
     image.onmouseover=function(){
      this.icon.style.display="block";
     };
       image.onmouseout=function(){
       this.icon.style.display="none";
     };
  }
  event.preventDefault();

    }

 $(document).bind("setContenList",setContenList);
      
})();

