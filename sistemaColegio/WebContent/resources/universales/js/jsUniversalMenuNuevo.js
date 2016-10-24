$(document).ready(function(){    
    metodoAlumbraMenuActual()    
    $(".estilosLIMenuPrincipal").hover(function(){        
        if($(this).find(".estiloDivExternoAdminitrador").is(":hidden")){        
            metodoOcultarMenusExtendidos()
            metodoMostrarMenusExtendidos(this)
        }else{
            metodoOcultarMenusExtendidos()
            metodoAlumbraMenuActual()
        }
    }, function(){
        metodoOcultarMenusExtendidos()
        metodoAlumbraMenuActual()    
    })            
    
//    $(".estilosLIMenuPrincipal").mouseleave(function(){
//        metodoOcultarMenusExtendidos()
//        metodoAlumbraMenuActual()
//    })    
//    
//    $(".estilosLIMenuPrincipal").mouseenter(function(){        
//        if($(this).find(".estiloDivExternoAdminitrador").is(":hidden")){        
//            metodoOcultarMenusExtendidos()
//            metodoMostrarMenusExtendidos(this)
//        }else{
//            metodoOcultarMenusExtendidos()
//            metodoAlumbraMenuActual()
//        }
//    })
});

function metodoAlumbraMenuActual(){
    $(".estilosLIMenuPrincipal").eq($(".claseMenuActivado").html()).addClass("claseMenuPrincipalActivado")//Agregamos la clase que indica cual         
}

function onClickMenuPrincipalActivado(viene){    
    $(viene).addClass("clasePrueba")
}
//FUNCIONES REFERENTES CLICK TAB ADMINISTRADOR
function onclickTabAdministrador(viene){    
    //    $(viene).parent().find("li").removeClass("claseMenuPrincipalActivado")    
    //    alert($(viene).hasClass("claseMenuPrincipalActivado"))    
    if($(viene).find(".estiloDivExternoAdminitrador").is(":hidden")){        
        metodoOcultarMenusExtendidos()
        metodoMostrarMenusExtendidos(viene)
    }else{
        metodoOcultarMenusExtendidos()
        metodoAlumbraMenuActual()
    }
}

function metodoMostrarMenusExtendidos(viene){
    $(viene).addClass("claseMenuPrincipalActivado")
    $(viene).addClass("claseMenuAdministrador")
    $(viene).addClass("estiloLiPrincipalMostrar")
    //    $(viene).find("div").fadeIn("fast")
    $(viene).find("div").slideDown("fast")    
    $(viene).find(".estiloDivExternoAdminitrador").css("min-width",$(viene).outerWidth() - 2)        
}

function metodoOcultarMenusExtendidos(){    
    //    $(".estiloDivExternoAdminitrador").fadeOut("fast")            
    $(".estiloDivExternoAdminitrador").slideUp("fast")
    $(".claseMenuPrincipalActivado").removeClass("claseMenuPrincipalActivado")
    //    $(".claseMenuAdministrador").removeClass("claseMenuAdministrador")
    $(".estiloLiPrincipalMostrar").removeClass("estiloLiPrincipalMostrar")
}