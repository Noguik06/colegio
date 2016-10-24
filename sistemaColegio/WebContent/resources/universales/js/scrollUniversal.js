function loadScrollUniversal(){    
    if($(".claseDivContenedorTablaPrincipalBody").outerHeight() > 300){
        //Calculamos el porcentaje valor del width del div en porcentaje
        var valorProcentaje = (1000) /$(".claseDivContenedorTablaPrincipalBody").outerWidth()//Este es el valor del width del div que contiene el header de la tabla principal en porcentaje                
        $(".claseDivContenedorTablaPrincipalBody").addClass("claseOverFlowVertical")        
        $(".claseDivContenedorHeaderPrincipal").removeClass("claseWidth100CompletoUniversal")
        $(".claseDivContenedorHeaderPrincipal").attr("style", "width: "+(100 - parseFloat(valorProcentaje))+"%;")
    }
}

function loadScrollUniversalDOS(){    
    if($(".claseDivContenedorTablaPrincipalBody2").outerHeight() > 300){
        //Calculamos el porcentaje valor del width del div en porcentaje
        var valorProcentaje = (980) /$(".claseDivContenedorTablaPrincipalBody2").outerWidth()//Este es el valor del width del div que contiene el header de la tabla principal en porcentaje                
        $(".claseDivContenedorTablaPrincipalBody2").addClass("claseOverFlowVertical")        
        $(".claseDivContenedorHeaderPrincipal2").removeClass("claseWidth100CompletoUniversal")
        $(".claseDivContenedorHeaderPrincipal2").attr("style", "width: "+(98 - parseFloat(valorProcentaje))+"%; float: right;margin-right: 10px;")
        $(".claseDivIntermedioScrollDos").attr("style", "float: right; width: 98%")
    }
}