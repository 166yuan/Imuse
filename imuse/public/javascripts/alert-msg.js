var MessageAlert = {
    
    showInfo : function(content){
        $("#msg-area").removeClass("alert-success");
        $("#msg-area").addClass("alert-success");
        $("#msg-area").removeClass("alert-error");
        $("#msg-area").children("p").text(content);
        $("#msg-area").fadeIn("slow").fadeTo(5000, 0.99).fadeOut("slow");
    },
    
    showError : function(content){
        $("#msg-area").removeClass("alert-success");
        $("#msg-area").removeClass("alert-error");
        $("#msg-area").addClass("alert-error");
        $("#msg-area").children("p").text(content);
        $("#msg-area").fadeIn("slow").fadeTo(5000, 0.99).fadeOut("slow");
    }
}

$(function(){
    $("input").attr("maxlength", 32);
});