// just for foot_scroll
(
	function  () {

	function getScrollTop()
     {
    var scrollTop=0;
    if(document.documentElement&&document.documentElement.scrollTop)
    {
        scrollTop=document.documentElement.scrollTop;
    }
    else if(document.body)
    {
        scrollTop=document.body.scrollTop;
    }
    return scrollTop;
    }
    
    function setFootScroll () {
    	var foot = document.getElementsByClassName("returnTop")[0];
    	var foot_returnTop = foot.getElementsByTagName("a")[0];
           foot_returnTop.onclick=function () {
       	    scroll (getScrollTop(),200);
       }
    }
       
     function scroll (scrollTop) {
     	scrollTop=scrollTop-200;
     	 window.scrollTo(0,scrollTop);
        var repeat= function(){
        	scroll(scrollTop);
        };
        if(scrollTop<0)
     		return;
        setTimeout(repeat,30);
       }

        setFootScroll();
	}
)();