
//Variables para guardar el id del popUp que vamos a mostrar
varNombreElemento = ""

//Variables para guardar el id el input que vamos a mostrar
varNombreInput = ""

//Método para mostrar el popUp que agregar dimensiones nuevas 
function agregarElemento(nombre, input){
    
    var windowWidth = document.documentElement.clientWidth;
    var windowHeight = document.documentElement.clientHeight;
    var popupHeight = $("#" + nombre).height();
    var popupWidth = $("#" + nombre).width();
    //centering
    $("#" + nombre).css({
        "position": "absolute",
        "top": windowHeight / 2 - popupHeight / 2,
        "left": windowWidth / 2 - popupWidth / 2,
        "z-index":999
    });

    $("#backgroundPopup").css({
        "height": windowHeight,
        "width":"99%",
        "top":"0",
        "position":"absolute"
    });
    
    //loads popup only if it is disabled
    $("#backgroundPopup").css({
        "opacity": "0.7",
        "background":"white"
    });
    
    
    $("#backgroundPopup").fadeIn("fast")
    $("#" + nombre).fadeIn("fast")

    
    banderaClick = false
    $("#"+nombre+"\\:"+input).focus()
}


//función para centrar el popUP
function centerPopup(nombre) {
    //request data for centering
    
    var windowWidth = document.documentElement.clientWidth;
    var windowHeight = document.documentElement.clientHeight;
    var popupHeight = $("#" + varNombreElemento).height();
    var popupWidth = $("#" + varNombreElemento).width();
    //centering
    $("#" + nombre).css({
        "position": "absolute",
        "top": windowHeight / 2 - popupHeight / 2,
        "left": windowWidth / 2 - popupWidth / 2,
        "z-index":999
    });

    $("#backgroundPopup").css({
        "height": windowHeight,
        "width":"99%",
        "top":"0",
        "position":"absolute"
    });
}

//Función para cargar el PopUp
function loadPopup(nombre) {
    //loads popup only if it is disabled
    $("#backgroundPopup").css({
        "opacity": "0.7",
        "background":"white"
    });
    $("#backgroundPopup").fadeIn("fast")
    $("#" + nombre).fadeIn("fast")

}


//Función para terminar de agregar el popUp
function terminarAgregarPopUp(e){
    if(e.status=="complete"){
        banderaClick = true
    }
    if(e.status=="success"){
        $("#backgroundPopup").fadeOut("fast")
        $("#" + varNombreElemento).fadeOut("fast")
        $("#"+varNombreElemento+"\\:"+varNombreInput).val("")
    }
}


//Método para cerrar los popUp'
function escongerPopUP(){
    $("#backgroundPopup").fadeOut("fast")
    $("#logros").fadeOut("fast")
    $("#notasGlobales").fadeOut("fast")
    $("#formRegistroMatricula").fadeOut("fast")
    $("#formCrearNotasCalificables").fadeOut("fast")
}

//Método para seleccionar el elemento a editar
function seleccionarElementoEditar(nombreElemento, nombreInput){
    varNombreElemento = nombreElemento
    varNombreInput = nombreInput
}

//Método para cargar el popUP de editar un elemento
function cargarPopUPEditarElemento(e){
    if(e.status=="success"){
        $("#formRegistroMatricula").css("width", "30%")
        agregarElemento(varNombreElemento,varNombreInput)
    }
}

//##Método para cuando editan el valor de un input
var nombreClaseEditarValor
var indiceEditarValor
function despuesEditarValor(e){
    /*if(e.status=="begin"){
        $($(".cargando"+nombreClase)[$("."+e.source.className).index(e.source)]).hide()
        $($(".eliminar"+nombreClase)[$("."+e.source.className).index(e.source)]).show()
        
        $($(".cargando"+nombreClase)[$("."+e.source.className).index(e.source)]).show()
        $($(".eliminar"+nombreClase)[$("."+e.source.className).index(e.source)]).hide()
    }
    if(e.status=="success"){
        $($(".cargando"+nombreClase)[$("."+e.source.className).index(e.source)]).hide()
        $($(".eliminar"+nombreClase)[$("."+e.source.className).index(e.source)]).show()
    }*/
}

//Método para validar 
function validarBlurCargando(e){
    if($($(".cargando"+e.className)[$("."+e.className).index(e)]).is(":visible")){
        return true;
    }
    return false
//    $($(".cargando"+e.className)[$("."+e.className).index(e)]).show()
}

//function despuesEditarValor(e){
//    if(e.status=="begin"){
//        indiceEditarValor = $("."+e.source.className).index(e.source)
//        nombreClase = e.source.className;
//        $($(".cargando"+nombreClase)[indiceEditarValor]).show()
//        $($("."+nombreClase)[indiceEditarValor]).hide()
//    }
//    if(e.status=="success"){
//        $($(".cargando"+nombreClase)[indiceEditarValor]).hide()
////        $($("."+nombreClase)[indiceEditarValor]).hide()
//        $($("."+nombreClase)[indiceEditarValor]).show()
////        indiceEditarValor = $("."+e.source.className).index(e.source)
////        nombreClase = e.source.className;
////        $($(".cargando"+nombreClase)[indiceEditarValor]).show()
////        $($("."+nombreClase)[indiceEditarValor]).hide()
////        alert(indiceEditarValor)
//    }
//}

//###Método Para empezar la edición de algún campo
function empezarEdicion(e){
    if(!validarNumero(e)){
        nombreClase = $(e)[0].className;
        //        alert(nombreClase)
        indiceEditarValor = $("."+$(e)[0].className).index(e)
        
        $($(".cargando"+nombreClase)[indiceEditarValor]).show()
        $($(".eliminar"+nombreClase)[indiceEditarValor]).hide()
    }
}

function terminarUpdate(e){
    if(e.status=="success"){
        try{
            
        }catch(e){
            
        }
    }
}

//###CURSOS
function seleccionarCurso(e){
    if(e.status=="success"){
        
        $(".botonCursoSeleccionado").removeClass("botonSeleccionadoVerde")
        $(".botonCursoSeleccionado").removeClass("botonCursoSeleccionado")
        
        $(e.source).addClass("botonSeleccionadoVerde")
        $(e.source).addClass("botonCursoSeleccionado")
        
    }
}

//###Asignaturas
function seleccionarAsignatura(e){
	if(e.status=="begin"){
		PF('statusDialog').show()
	}
    if(e.status=="success"){
        
        $(".botonAsignaturaSeleccionada").removeClass("botonSeleccionadoVerde")
        $(".botonAsignaturaSeleccionada").removeClass("botonAsignaturaSeleccionada")
        
        $(e.source).addClass("botonSeleccionadoVerde")
        $(e.source).addClass("botonAsignaturaSeleccionada")
        PF('statusDialog').hide()
    }
}

//##Periodos
function seleccionarPeriodos(e){
	if(e.status=="begin"){
		PF('statusDialog').show()
	}
    if(e.status=="success"){
        
        $(".botonPeriodoSeleccionado").removeClass("botonSeleccionadoVerde")
        $(".botonPeriodoSeleccionado").removeClass("botonPeriodoSeleccionado")
        
        $(e.source).addClass("botonSeleccionadoVerde")
        $(e.source).addClass("botonPeriodoSeleccionado")
        PF('statusDialog').hide()
    }
}


//##Dimensiones

//Método para selecionar y dejar seleccionada las dimensiones
function seleccionarDimension(e){
	if(e.status=="begin"){
		PF('statusDialog').show()
	}
    $(".botonDimensionSeleccionada").removeClass("botonSeleccionadoVerde")
    $(".botonDimensionSeleccionada").removeClass("botonDimensionSeleccionada")
        
    $(e.source).addClass("botonSeleccionadoVerde")
    $(e.source).addClass("botonDimensionSeleccionada")
    PF('statusDialog').hide()
}

//##LOGROS
function cargarEdicionLogros(e){
	if(e.status=="begin"){
		PF('statusDialog').show()
	}
    if(e.status=="success"){
        nombre = "logros"
        input = "textoLogro"
        
        var windowWidth = document.documentElement.clientWidth;
        var windowHeight = document.documentElement.clientHeight;
        var popupHeight = $("#" + nombre).height();
        var popupWidth = $("#" + nombre).width();
        //centering
        $("#" + nombre).css({
            "position": "absolute",
            "top": windowHeight / 2 - popupHeight / 2,
            "left": windowWidth / 2 - popupWidth / 2,
            "z-index":999
        });

        $("#backgroundPopup").css({
            "height": windowHeight,
            "width":"99%",
            "top":"0",
            "position":"absolute"
        });
    
        //loads popup only if it is disabled
        $("#backgroundPopup").css({
            "opacity": "0.7",
            "background":"white"
        });
    
    
        $("#backgroundPopup").fadeIn("fast")
        $("#" + nombre).fadeIn("fast")

    
        banderaClick = false
        $("#"+nombre+"\\:"+input).focus()
        
        PF('statusDialog').hide()
    }
}

//Método para cerrar el popUp de agregar los logros
function terminarAgregarLogros(e){
	if(e.status=="begin"){
		PF('statusDialog').show()
	}
    if(e.status=="success"){
        $("#backgroundPopup").fadeOut("fast")
        $("#logros").fadeOut("fast")
        PF('statusDialog').hide()
    }
}

///Método para seleccionar el logro y dejarlo seleccionado
function seleccionarLogros(e){
	if(e.status=="begin"){
		PF('statusDialog').show()
	}
    if(e.status=="success"){
        
        $(".botonLogroSeleccionada").removeClass("botonSeleccionadoVerde")
        $(".botonLogroSeleccionada").removeClass("botonLogroSeleccionada")
        
        $(e.source).addClass("botonSeleccionadoVerde")
        $(e.source).addClass("botonLogroSeleccionada")
        PF('statusDialog').hide()
        
    }
}

//##NOTAS
//Método para cargar el popUp con el que creo o edito las notas
function cargarEdicionNotas(e){
	if(e.status=="begin"){
		PF('statusDialog').show()
	}
    if(e.status=="success"){
        nombre = "notasGlobales"
        input = "nombreNotaGlobal"
        
        var windowWidth = document.documentElement.clientWidth;
        var windowHeight = document.documentElement.clientHeight;
        var popupHeight = $("#" + nombre).height();
        var popupWidth = $("#" + nombre).width();
        //centering
        $("#" + nombre).css({
            "position": "absolute",
            "top": windowHeight / 2 - popupHeight / 2,
            "left": windowWidth / 2 - popupWidth / 2,
            "z-index":999
        });

        $("#backgroundPopup").css({
            "height": windowHeight,
            "width":"99%",
            "top":"0",
            "position":"absolute"
        });
    
        //loads popup only if it is disabled
        $("#backgroundPopup").css({
            "opacity": "0.7",
            "background":"white"
        });
    
    
        $("#backgroundPopup").fadeIn("fast")
        $("#" + nombre).fadeIn("fast")

    
        banderaClick = false
        $("#"+nombre+"\\:"+input).focus()
        
        PF('statusDialog').hide()
    }
}

//Método con el que escondo el popUp de las ntoas
function terminarAgregarNotas(e){
	if(e.status=="begin"){
		PF('statusDialog').show()
	}
    if(e.status=="success"){
        $("#backgroundPopup").fadeOut("fast")
        $("#notasGlobales").fadeOut("fast")
        
        PF('statusDialog').hide()
    }
}


//Método con el qe selecciono las notas y las dejo seleccionadas
function seleccionarNotas(e){
	if(e.status=="begin"){
		PF('statusDialog').show()
	}
	
    if(e.status=="success"){
        $(".botonNotasSeleccionado").removeClass("botonSeleccionadoVerde")
        $(".botonNotasSeleccionado").removeClass("botonNotasSeleccionado")
        
        $(e.source).addClass("botonSeleccionadoVerde")
        $(e.source).addClass("botonNotasSeleccionado")
        
        PF('statusDialog').hide()
    }
}

//##NOTAS CALIFICABLES
//Método para cargar el popUP para agregar las actividades
function cargarEdicionNotasCalificables(e){
	if(e.status=="begin"){
		PF('statusDialog').show()
	}
	
    if(e.status=="success"){
        nombre = "formCrearNotasCalificables"
        input = "nombreNotaCalificar"
        
        var windowWidth = document.documentElement.clientWidth;
        var windowHeight = document.documentElement.clientHeight;
        var popupHeight = $("#" + nombre).height();
        var popupWidth = $("#" + nombre).width();
        //centering
        $("#" + nombre).css({
            "position": "absolute",
            "top": windowHeight / 2 - popupHeight / 2,
            "left": windowWidth / 2 - popupWidth / 2,
            "z-index":999
        });

        $("#backgroundPopup").css({
            "height": windowHeight,
            "width":"99%",
            "top":"0",
            "position":"absolute"
        });
    
        //loads popup only if it is disabled
        $("#backgroundPopup").css({
            "opacity": "0.7",
            "background":"white"
        });
    
    
        $("#backgroundPopup").fadeIn("fast")
        $("#" + nombre).fadeIn("fast")

    
        banderaClick = false
        $("#"+nombre+"\\:"+input).focus()
        
        
        
        $(".fechaNotaCalificar").datepicker({
            changeMonth: true,
            changeYear: true,
            showOn: "button",
            dateFormat: 'yy-mm-dd',
            buttonImage: "/sistemaColegio/resources/imagenes/calendar.gif",
            buttonImageOnly: true,
            yearRange: '1950:2020'
        })
        
        PF('statusDialog').hide()
    }
}

//Método con el que escondo el popUp de agregar una nota calificables
function terminarAgregarNotasCalificables(e){
    $("#backgroundPopup").fadeOut("fast")
    $("#formCrearNotasCalificables").fadeOut("fast")
}

//Método para cargar el popUp donde están los estudiantes
function cargarEdicionNotasEstudiantes(e){
	if(e.status=="begin"){
		PF('statusDialog').show()
	}
    if(e.status=="success"){
        nombre = "formRegistroMatricula"
        input = "nombreNotaCalificar"
        
        var windowWidth = document.documentElement.clientWidth;
        var windowHeight = document.documentElement.clientHeight;
        var popupHeight = $("#" + nombre).height();
        var popupWidth = $("#" + nombre).width();
        //centering
        $("#" + nombre).css({
            "position": "absolute",
            "top": windowHeight / 2 - popupHeight / 2,
            "left": windowWidth / 2 - popupWidth / 2,
            "z-index":999
        });

        $("#backgroundPopup").css({
            "height": windowHeight,
            "width":"99%",
            "top":"0",
            "position":"absolute"
        });
    
        //loads popup only if it is disabled
        $("#backgroundPopup").css({
            "opacity": "0.7",
            "background":"white"
        });
    
    
        $("#backgroundPopup").fadeIn("fast")
        $("#" + nombre).fadeIn("fast")

    
        banderaClick = false
        $("#"+nombre+"\\:"+input).focus()
        
        cuadrarScroll(".colHeaderEstudiantes", ".colBodyEstudiantes")
        
        PF('statusDialog').hide();
    }
}

//Después de cargar una nota
//
function despuesCargarNota(e){
    if(e.status=="success"){
        //        alert()
        //Removemos cualquier otro mensaje de alerta que haya
        $("#mensajeAlerta").hide();

        $(".tituloAlert").hide();
        
       
       if($(".exitoMensaje").size()>0){
             $(".contenidoAlert")[0].innerHTML = $(".exitoMensaje td")[0].innerHTML
        
            $(".ui-growl-image").show()
            $(".ui-messages-error-icon").hide()
            $(".ui-growl-image").removeClass("ui-growl-image-error")
            $(".ui-growl-image").addClass("ui-growl-image-info")
        }
        
        if($(".errorMensaje").size()>0){
            $(".contenidoAlert")[0].innerHTML = $(".errorMensaje td")[0].innerHTML
            $(".ui-growl-image").removeClass("ui-growl-image-info")
            $(".ui-growl-image").addClass("ui-growl-image-error")
            $(".ui-messages-error-icon").show()
            
//            $("#mensajeAlerta").removeClass("ui-messages-info")
//            $("#mensajeAlerta").addClass("ui-messages-error")
        }
        

        //Variable que cotiene el código html para colocar el di
        $("#mensajeAlerta").fadeIn();
        
            
        //            $("#formEnviaAdjuntoStandar").submit()
            
        $("#idFormTablaBusquedaAlumnosPincipal\\:inputTextBuscar").focus()    

        desaparecer();
    }
}


function ocultar() {
    $("#mensajeAlerta").fadeOut(500);
}

//
function desaparecer() {
    intevaloMensaje = setTimeout("ocultar()", 3000);
}


function cargarLogroAntiguo(e) {
	if (e.status == "begin") {
		$("#backgroundCargando").css("height", $("#logros\\:idPanelGroupTablaPedidosPrincipal").height())
		$("#backgroundCargando").css("width", $("#logros\\:idPanelGroupTablaPedidosPrincipal").width())
		$("#backgroundCargando").show()
	}
	if (e.status == "success") {
		$("#backgroundCargando").hide()
	}
}