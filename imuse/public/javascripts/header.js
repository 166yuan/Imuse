/*for header*/
(function(){
    var url=window.location.href;
    var navs=document.getElementsByClassName("header_nav")[0];
    var links=navs.getElementsByTagName('a');
    var lis=navs.getElementsByTagName('li');
    var href;
    for(var i=0;i<links.length;i++)
    {
       href=links[i].getAttributeNode("title").value;
       
      if(url.indexOf(href)!=-1){
        lis[i].style.borderBottomStyle="solid";
        lis[i].style.borderBottomColor="#3b55a1";
        lis[i].style.borderBottomWidth="3px";
      }
    }



})();