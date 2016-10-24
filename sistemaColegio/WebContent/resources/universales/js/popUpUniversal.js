function centerPoUpUniversalReal(clasePopUpUniversal){
    //request data for centeringalert()    
    var windowWidth = document.documentElement.clientWidth;
    var windowHeight = document.documentElement.clientHeight;
    var popupHeight = $("."+clasePopUpUniversal+"").height();
    var popupWidth = $("."+clasePopUpUniversal+"").width();
    //    alert("::windowHeight:: "+windowHeight+" ::popupHeight:: " + popupHeight + " ::popupWidth :: " + popupWidth +" ::windowWidth:: " +windowWidth)
    //centering
    $("."+clasePopUpUniversal+"").css({
        //        "position": "absolute",
        "top": windowHeight/2-popupHeight/2,
        "left": windowWidth/2-popupWidth/2
    //        "top": windowHeight/2,
    //        "left": windowWidth/2
    });
}

function centerPoUpUniversalRealFIXED(clasePopUpUniversal){
    //request data for centeringalert()    
    var windowWidth = document.documentElement.clientWidth;
    var windowHeight = document.documentElement.clientHeight;
    var popupHeight = $("."+clasePopUpUniversal+"").height();
    var popupWidth = $("."+clasePopUpUniversal+"").width();
    //    alert("::windowHeight:: "+windowHeight+" ::popupHeight:: " + popupHeight + " ::popupWidth :: " + popupWidth +" ::windowWidth:: " +windowWidth)
    //centering
    $("."+clasePopUpUniversal+"").css({
        //        "position": "absolute",
        "top": windowHeight/2-popupHeight/2,
        "left": windowWidth/2-popupWidth/2
    //        "top": windowHeight/2,
    //        "left": windowWidth/2
    });
}