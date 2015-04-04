/*
对在音乐榜里超出长度的字符串进行截取，多出的部分用指定的字符代替
譬如：爸爸去哪儿第二季，截取后：爸爸去哪儿...
参数：需要截取的字符串所在的class名，截取后长度，代替字符串
*/
function cutString(classesName,cutLength,replaceString){
    var nameArray = document.getElementById("bg_list").getElementsByTagName("span");
        for (var i = 0; i < nameArray.length; i++) {
           	if(nameArray[i].className == classesName){
              	var s = nameArray[i].innerHTML;
              	if(s.length>cutLength){
                nameArray[i].innerHTML= s.substring(0,cutLength)+replaceString;
              	};
            };
       };
}
//音乐榜歌名规格化
cutString("bg_song",5,"...");
//音乐榜歌手规格化
cutString("bg_singer",6,"...");
//音乐榜填词规格化
cutString("bg_lyricist",6,"...");
//音乐榜作曲规格化
cutString("bg_composer",6,"...");