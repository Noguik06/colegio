/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


//INICIA EL CÓDIGO REFERENTE AL POPUP--------------------------------------------------------------------INICIO POPUP-----------------------
//SETTING UP OUR POPUP
//0 means disabled; 1 means enabled;
var popupStatus = 0;
//loading popup with jQuery magic!
function loadPopup(){    
    //loads popup only if it is disabled    
    if(popupStatus==0){
        $("#backgroundPopup").css({
            "opacity": "0.50"
        });
        $("#backgroundPopup").fadeIn("fast");
        $("#popupContact").fadeIn("fast");
        popupStatus = 1;
    }
}

//disabling popup with jQuery magic!
function disablePopup(){
    //disables popup only if it is enabled
    if(popupStatus==1){
        $("#backgroundPopup").fadeOut("fast");
        $("#popupContact").fadeOut("fast");
        popupStatus = 0;
    }
}

function disablePopup(){
    //disables popup only if it is enabled
    if(popupStatus==1){
        $("#backgroundPopup").fadeOut("fast");
        $("#popupContact").fadeOut("fast");
        popupStatus = 0;
    }
}

//centering popup
function centerPopup(){    
    //request data for centering    
    var windowWidth = document.documentElement.clientWidth;
    var windowHeight = document.documentElement.clientHeight;    
    var popupHeight = $("#popupContact").height();
    var popupWidth = $("#popupContact").width();    
    //centering
    $("#popupContact").css({
        "position": "absolute",
        "top": windowHeight/2-popupHeight/2,
        "left": windowWidth/2-popupWidth/2
    });
    //only need force for IE6
    $("#backgroundPopup").css({
        "height": windowHeight
    });
    
}

//VALORES PARA EL POPUP2
var popupStatus2 = 0;
//loading popup with jQuery magic!
function loadPopup2(){
    //loads popup only if it is disabled
    if(popupStatus2==0){
        $("#backgroundPopup2").css({
            "opacity": "0.50"
        });
        $("#backgroundPopup2").fadeIn("fast");
        $("#popupContact2").fadeIn("fast");
        popupStatus2 = 1;
    }
}
//disabling popup with jQuery magic!
function disablePopup2(){
    //disables popup only if it is enabled
    if(popupStatus2==1){
        $("#backgroundPopup2").fadeOut("fast");
        $("#popupContact2").fadeOut("fast");
        popupStatus2 = 0;
    }
}
//centering popup
function centerPopup2(){
    //request data for centering
    var windowWidth = document.documentElement.clientWidth;
    var windowHeight = document.documentElement.clientHeight;
    var popupHeight = $("#popupContact2").height();
    var popupWidth = $("#popupContact2").width();
    //centering
    $("#popupContact2").css({
        "position": "absolute",
        "top": windowHeight/2-popupHeight/2,
        "left": windowWidth/2-popupWidth/2
    });
    //only need force for IE6

    $("#backgroundPopup2").css({
        "height": windowHeight
    });
}

//VALORES PARA EL POPUP3
var popupStatus3 = 0;
//loading popup with jQuery magic!
function loadPopup3(){
    //loads popup only if it is disabled
    if(popupStatus3==0){
        $("#backgroundPopup3").css({
            "opacity": "0.50"
        });
        $("#backgroundPopup3").fadeIn("fast");
        $("#popupContact3").fadeIn("fast");
        popupStatus3 = 1;
    }
}
//disabling popup with jQuery magic!
function disablePopup3(){
    //disables popup only if it is enabled
    if(popupStatus3 == 1){
        $("#backgroundPopup3").fadeOut("fast");
        $("#popupContact3").fadeOut("fast");
        popupStatus3 = 0;
    }
}
//centering popup
function centerPopup3(){
    //request data for centering
    var windowWidth = document.documentElement.clientWidth;
    var windowHeight = document.documentElement.clientHeight;
    var popupHeight = $("#popupContact3").height();
    var popupWidth = $("#popupContact3").width();
    //centering
    $("#popupContact3").css({
        "position": "absolute",
        "top": windowHeight/2-popupHeight/2,
        "left": windowWidth/2-popupWidth/2
    });
    //only need force for IE6

    $("#backgroundPopup3").css({
        "height": windowHeight
    });
}

////Función: revisa cuantos Error Messages hay y de existir uno o más retorna false para para el boton guardar
//function diabledButtonGuardarPopUp(){
//    if($(".columna1 input").val().match(/\w/)!=null && $(".columna2 input").val().match(/\w/)!=null && $(".columna3 input").val().match(/\w/)!=null && $(".columna4 input").val().match(/\w/)!=null ){
//        if($(".messagesExist2").length==0){            
//            //$("#formulario2\\:example2\\:0\\:botonHiddenPopUp").click()
//            //$("#formulario2\\:apellidoVendedorHiddenP").attr("value",$(".columna2 input").val())            
//            return true;
//        }
//    }
//    alert("Error: Campos en Blanco")
//    alert("true")
//    return false;
//}

