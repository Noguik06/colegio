scroller = null

$(document).ready(function() {
    
    scroller = $('.box-wrap').antiscroll().data('antiscroll');
    $(".ui-datepicker").hide()

    
    $(document).keyup(function(e){
        if(e.keyCode==27){
            $("#idFormTablaBusquedaAlumnosPincipal\\:inputTextBuscar").focus()
        }
    })
    $("#idFormTablaBusquedaAlumnosPincipal\\:inputTextBuscar").focus()    
    
    
    
    //bind 'myForm' and provide a simple callback function 
    $('#formEnviaAdjuntoStandar').ajaxForm(function(e) { //Se acciona cuando termina el request del formulario adjuntar
        if(e.toString().trim().length > 0){
            alert(e)
        }
                    
        $("#formFoto\\:cambiar").click()
    });
            
})

//function colocarFecha(e){
//    if(e.status=="success"){
//        $(".ui-datepicker").hide()
//        loadScrollUniversal()
//    }
//}

function onEventInputTextBuscarEstudiantes(data) {
    if (data.status == "success") {
        //        loadScrollUniversal()

        scroller.refresh();
        //        
        var claseProductoSube = ".subeEstudiantes"
        var claseProductoBaja = ".bajaEstudiantes"
        var seudonimoId = "_estudiantes"
        var containerLista = "#idFormTablaBusquedaAlumnosPincipal\\:idPanelGroupTablaPedidosPrincipal"
        var claseFilas = ".filaEstudiante"
    
        if ($(claseFilas).size() > 0) {
            //Se agreg el href para que baje el scroll del div que contiene la tabla
            for (i = 0; i < $(claseFilas).size() - 1; i++) {
    
                var href = "#" + ($(claseProductoBaja)[i + 1].id).toString() + seudonimoId
                $($(claseProductoBaja)[i]).attr({
                    'href': href
                })
    
                if (i + 1 == $(claseFilas).size() - 1) {
                    var hrefNueva = "#" + ($(claseProductoBaja)[0].id).toString() + seudonimoId
                    $($(claseProductoBaja)[i + 1]).attr({
                        'href': hrefNueva
                    })
                }
            }
    
            //Se agreg el href para que suban el scroll del div que contiene la tabla 
            for (i = 0; i < $(claseFilas).size(); i++) {
                //             
                if (i != 0) {
                    href = "#" + ($(claseProductoBaja)[i - 1].id).toString() + seudonimoId
                    $($(claseProductoSube)[i]).attr({
                        'href': href
                    })
                } else {
                    hrefNueva = "#" + ($(claseProductoBaja)[$(claseFilas).size() - 1].id).toString() + seudonimoId
                    $($(claseProductoSube)[i]).attr({
                        'href': hrefNueva
                    })
                }
            }
    
            //Añadimos el trigger para que cargue los links
            $(containerLista).localScroll({
                duration: 300,
                target: containerLista
            });

        }
    }
}

function onEventClickFilaTablaEstudiantes(data) {
    if (data.status == "success") {
        $($(".inputEstudiantes")[1]).focus()
        $(".clasePanelGroupPrincipalDatosEstudiante").show()
        $(".claseInputTextFechaNacimiento").datepicker({
            changeMonth: true,
            changeYear: true,
            showOn: "button",
            dateFormat: 'dd-mm-yy',
            buttonImage: "/sistemaColegio/resources/imagenes/calendar.gif",
            buttonImageOnly: true,
            yearRange: '1950:2050'
        })
        $(".ui-datepicker").hide()
    }
}

var intevaloMensaje;
var banderaErrorGuardarProducto = false;
//Onevent para cuando se guarda el producto
function guardarEstudiante(e) {
    if (e.status == "success") {
        if (!banderaErrorGuardarProducto) {
            //Removemos cualquier otro mensaje de alerta que haya
            $("#mensajeAlerta").hide();

            $(".tituloAlert").hide();
            $(".contenidoAlert")[0].innerHTML = "El estudiante fue guardado satisfactoriamente";

            //Variable que cotiene el código html para colocar el di
            $("#mensajeAlerta").fadeIn();
            
            $("#formEnviaAdjuntoStandar").submit()
            
            $("#idFormTablaBusquedaAlumnosPincipal\\:inputTextBuscar").focus()    

            desaparecer();
            
            loadScrollUniversal();

        } else {
            banderaErrorGuardarProducto = false;
        }
    }
}

function onEventCancelar(e){
    if(e.status=="success"){
        loadScrollUniversal();
        
        $("#idFormTablaBusquedaAlumnosPincipal\\:inputTextBuscar").focus()
    }
}

//
function errorGuardarEstudiante(e){
    error = e.errorMessage.split("//")[1]
    posicion = e.errorMessage.split("//")[0].split("##")[1]
    
    alert(error);
    
    $($(".inputEstudiantes")[posicion]).focus()
    $.scrollTo($($(".inputEstudiantes")[posicion]))
    banderaErrorGuardarProducto = true;
    
}

function ocultar() {
    $("#mensajeAlerta").fadeOut(500);
}

//
function desaparecer() {
    intevaloMensaje = setTimeout("ocultar()", 3000);
}


//Método para recorrer la lista de los productos 
function recorrerListaBusquedaProductos(keyCode) {
    //Función para recorrer la tabla de 
    clase = ".filaEstudiante"
    claseSeleccionado = ".claseSeleccionada"
    popUP = "#idFormTablaBusquedaAlumnosPincipal\\:idPanelGroupTablaPedidosPrincipal";
    index = $(clase).index($(claseSeleccionado)[0])
    claseClick = ".claseClickEstudiante"
    


    claseSubida = ".subeEstudiantes"
    claseBajada = ".bajaEstudiantes"


    //Si se da enter
    if (keyCode == 13) {
        if ($(claseSeleccionado).size() > 0) {
            if ($(clase).size() > 0) {
                //Damos click sobre el elemento seleccionado
                index = $(clase).index($(claseSeleccionado)[0])
                $($(claseClick)[index]).click()
            } else {

                $(claseSeleccionado).click()
            }
        }
    }



    //Si se sube
    if (keyCode == 38) {
        if ($(claseSeleccionado).size() == 0) {
            $($(clase)[$(clase).size() - 1]).addClass(claseSeleccionado.split(".")[1])
        } else {
            $(claseSeleccionado).removeClass(claseSeleccionado.split(".")[1])
            if ((index - 1) == -1) {
                $($(clase)[$(clase).size() - 1]).addClass(claseSeleccionado.split(".")[1])
            } else {
                $($(clase)[index - 1]).addClass(claseSeleccionado.split(".")[1])
            }
        }

        if (Math.abs($(popUP).scrollTop() - $(claseSeleccionado).position().top) > 1
            && Math.abs($(popUP).scrollTop() - $(claseSeleccionado).position().top) < 50
            && $(popUP).scrollTop() < $(claseSeleccionado).position().top) {
        } else {
            $($(claseSubida)[index]).click()
        }

    }


    //Si se baja
    if (keyCode == 40) {
        if ($(clase).size() == 0) {
            $("#ProductosVentas\\:agregarProductoNuevoVentaRapida").addClass(claseSeleccionado.split(".")[1])
        } else {
            if ($(claseSeleccionado).size() == 0) {
                $($(clase)[0]).addClass(claseSeleccionado.split(".")[1])
            } else {
                $(claseSeleccionado).removeClass(claseSeleccionado.split(".")[1])
                if ((index + 1) == $(clase).size()) {
                    $($(clase)[0]).addClass(claseSeleccionado.split(".")[1])
                } else {
                    $($(clase)[index + 1]).addClass(claseSeleccionado.split(".")[1])
                }
            }

            //            alert(Math.abs($(popUP).scrollTop())())
            //            alert($(claseBajada)[index])
            if (Math.abs($(popUP).scrollTop() - $(claseSeleccionado).position().top) > 20) {
                //            $($(".box1")[index]).click()
                //            $(claseSeleccionado)[0].scrollIntoView(false)
                $($(claseBajada)[index]).click()
            }
        }
    }
}

