//加载函数
function addLoadEvent(func)
{
	var old=window.onload;
	if(typeof window.onload != 'function')
	window.onload=func;
	else{
		window.onload= function(){
		old();
		func();
		}
	}
}
//增加加类操作
 if(!document.getElementsByClassName){  
        document.getElementsByClassName = function(className, element){  
            var children = (element || document).getElementsByTagName('*');  
            var elements = new Array();  
            for (var i=0; i<children.length; i++){  
                var child = children[i];  
                var classNames = child.className.split(' ');  
                for (var j=0; j<classNames.length; j++){  
                    if (classNames[j] == className){   
                        elements.push(child);  
                        break;  
                    }  
                }  
            }   
            return elements;  
        };  
    } 
 //移动元素函数
function moveElement(elementID,final_x,final_y,interval){
	if(!document.getElementById) return false;
	if(!document.getElementById(elementID)) return false;
	var elem=document.getElementById(elementID);
	if(elem.movement){
	clearTimeout(elem.movement);	
	}
	// 一开始它在JavaScript没有。    
	if(!elem.style.left)
	elem.style.left = "0px";
	   var xpos = parseInt(elem.style.left);
	    if(xpos===final_x) return;
	 if(xpos < final_x){
	 	var dist = Math.ceil((final_x-xpos)/5);
	 	xpos = xpos + dist;
	 }
	if(xpos > final_x){
		var dist = Math.ceil((xpos-final_x)/5);
		xpos = xpos - dist;
	}
	elem.style.left=xpos + "px";
	var repeat ="moveElement('"+elementID+"',"+final_x+","+final_y+","+interval+")";
	elem.movement = setTimeout(repeat,interval);
}
// 加载函数
 function addLoadEvent(func){
	var oldonload=window.onload;
	if(typeof window.onload !='function')
		window.onload=func;
	else{
		window.onload=function(){
			oldonload();
			func();
		}
	}
}
function hasClass(ele,cls) {
    return ele.className.match(new RegExp('(\\s|^)'+cls+'(\\s|$)'));
}
  
function addClass(ele,cls) {
    if (!this.hasClass(ele,cls)) ele.className += " "+cls;
}

function removeClass(ele,cls) {
    if (hasClass(ele,cls)) {
        var reg = new RegExp('(\\s|^)'+cls+'(\\s|$)');
        ele.className=ele.className.replace(reg,'');
    }
}





