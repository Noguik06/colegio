//colTiposPagosHader-1

$(function(){
    cuadrarScroll(".colTiposPagosHader", ".colTiposPagosBody")
//    alert('ola')
})


function despuesAgregarNuevo(e){
    if(e.status=="success"){
        $(".fecha").datepicker({
            changeMonth: true,
            changeYear: true,
            showOn: "button",
            dateFormat: 'dd-mm-yy',
            buttonImage: "/sistemaColegio/resources/imagenes/calendar.gif",
            buttonImageOnly: true,
            yearRange: '1950:2020'
        })
        
        cuadrarScroll(".colTiposPagosHader", ".colTiposPagosBody")
    }
}


//Onevent despues de haber hecho la búsqueda
function despuesBuscar(e){
    if(e.status=="success"){
        cuadrarScroll(".colTiposPagosHader", ".colTiposPagosBody")
    }
}


//Onevenet despues de haber editado o creado un tipo de pago
function despuesEdicion(e){
    if(e.status=="success"){
        cuadrarScroll(".colTiposPagosHader", ".colTiposPagosBody")
        
        if($(".errorMensaje").size()>0){
            alert($(".errorMensaje td")[0].innerHTML)
        }
        
        if($(".exitoMensaje").size()>0){
            alert($(".exitoMensaje td")[0].innerHTML)
        }
    }
}

//Método para que vuelva a colocar el plugin del calendario en todos los inputs
function despuesSeleccionarPago(e){
    if(e.status=="success"){
        //Agregar a input's de inicio y fin
        $(".fecha").datepicker({
            changeMonth: true,
            changeYear: true,
            showOn: "button",
            dateFormat: 'dd-mm-yy',
            buttonImage: "/sistemaColegio/resources/imagenes/calendar.gif",
            buttonImageOnly: true,
            yearRange: '1950:2020'
        })
        
        cuadrarScroll(".colUsariosPagoHader", ".colUsariosPagoBody")
        cuadrarScroll(".colTiposPagosHader", ".colTiposPagosBody")
    }
}

//Método para mostrar el div que tiene la lista de los pagos que se hicieron
function mostrarHistorialPagos(e){
     
    if(e.status=="success"){
     
        topFormPK = $(e.source).position().top
        leftFormPK = $(e.source).position().left
        
        //        alert($($(".wrapperPanelFacturasPagoCuentaCobro")[0]).position().top)
        //        if($(".wrapperPanelFacturasPagoCuentaCobro").size() > 1){
        //            alert($($(".wrapperPanelFacturasPagoCuentaCobro")[1]).position().top)
        //        }
        
        
    
        //    alert(topFormPK)
    
        //        clase = $(e.source)[0].className
        //        index = $("."+clase).index(e.source)
        //        index2 = $(".indexCuentaDeCobro")[index].value
  
        //        var offset = $(e.source).offset().top;
        //    alert($("#containerFieldCuentaDeCobroSeleccionada .fieldSetUniversal #headerCuentaDeCobroParaPagar").find(e))
    
        //    alert($($(".divMostrarOtrosPagos")[index]).position().top)

        //Establecemos las variables para la posición de la tabla que muestra el div para agregar el producto de
        //de ventas rápidas
        $($(".tiposPagos")[0]).css({
            "position": "absolute",
            "top": topFormPK,
            "left": leftFormPK,
            "background":"white",
            "z-index": 999
        });
    
    
        //de ventas rápidas
        $("#containerPagarCuentasDeCobro").css({
            "z-index": 2
        });
    
        //Añadimos algunas características que debe el background que bloqueará las otras funciones
        $("#backgroundPopupTiposPagos").css({
            "position": "fixed", 
            "width": "100%",
            "opacity": 0,
            "height": "100%",
            "margin-bottom": "absolute", 
            "background": "black",
            "top": 0, 
            "left": 0,
            "z-index": 999
        });
    
    
        
        
        
    
        $($(".tiposPagos")[0]).show()
        $("#backgroundPopupTiposPagos").show() 
    
        $("#backgroundPopupTiposPagos").click(function(){
            
            //de ventas rápidas
            $("#containerPagarCuentasDeCobro").css({
                "z-index": 4
            });
            $("#backgroundPopupTiposPagos").hide() 
            $($(".tiposPagos")[0]).hide()
        
        })
    }
}



//Método para mostrar el input para arregalar el valor
function despuesEscogerUsuarioCambiarValor(e){
    if(e.status=="success"){
        clase = $(e.source)[0].className
        $($("."+clase)[1]).hide();
        $($("."+clase)[0]).show();
        $($("."+clase)[0]).focus()
        
        
    //        var listItem = $(e.source);
    ////        alert(e.source)
    ////        index = $("."+clase).index($(e.source)[0])
    ////        alert(index)
    //        
    ////        var listItem = $($('.valorRealInput')[0]);
    //        alert('Index: ' + $('label').index(e.source));
    }
}

//
//
////Método para cargar la lista de los usuarios
//function cargarUsuarios(e){
//    if(e.status=="success"){
//        $("#backgroundPopup").show()
//        
//        $("#backgroundPopup").click(function(){
//            $("#backgroundPopup").hide()
//            $("#subPopUpBuscar").hide()
//        })
//        
//        topFormPK = $("#formPagos\\:filtroBusquedaUsuarios").position().top
//        leftFormPK = $("#formPagos\\:filtroBusquedaUsuarios").parent().position().left
//        
//        $("#subPopUpBuscar").css({
//            "position": "absolute",
//            "background": "white",
//            "z-index": "10",
//            "top": topFormPK + $("#formPagos\\:filtroBusquedaUsuarios").height() + 4,
//            "left": leftFormPK
//        });
//        
//        if ($(".rowUsuariosBusqueda").size() == 0) {
//            if($(".rowCursosBusqueda").size() > 0){
//                $("#formPagos\\:tablaUsuarios").hide()  
//            }else{
//                $("#subPopUpBuscar").hide()
//            }
//        }
//        else {
//            $("#subPopUpBuscar").show()
//        }
//    }
//}

//Método para luego de haber seleccionado ver los pagos de un usuario
function despuesSeleccionarVerPagos(e){
    if(e.status=="success"){
        cuadrarScroll(".colAbonosIngresosHader", ".colAbonosIngresosBody")
    }
}


//Método para cuando haya acabado de busar usuarios
function despuesBuscarUsuarios(e){
    if(e.status=="success"){
        cuadrarScroll(".colUsariosPagoHader", ".colUsariosPagoBody")
        //        cuadrarScroll(".colTiposPagosHader", ".colTiposPagosBody")
        cuadrarScroll(".colUsariosPagoHader", ".colUsariosSinPagosPagoBody")
    }
}

//Método para después de haber eliminado un usuario
function despuesagregar(e){
    if(e.status=="success"){
        cuadrarScroll(".colUsariosPagoHader", ".colUsariosPagoBody")
        cuadrarScroll(".colUsariosPagoHader", ".colUsariosSinPagosPagoBody")
    }
}

//Método para después de haber agregado un grupo
function despuesagregargrupo(e){
    if(e.status=="success"){
        cuadrarScroll(".colUsariosPagoHader", ".colUsariosPagoBody")
        cuadrarScroll(".colUsariosPagoHader", ".colUsariosSinPagosPagoBody")
        alert("El grupo de usuarios fue agregado satisfactoriamente")
    }
}

function despueseliminar(e){
    if(e.status=="success"){
        cuadrarScroll(".colUsariosPagoHader", ".colUsariosPagoBody")
        cuadrarScroll(".colUsariosPagoHader", ".colUsariosSinPagosPagoBody")
    }
}