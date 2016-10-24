
//Variables para guardar el id del popUp que vamos a mostrar
varNombreElemento = ""

//Variables para guardar el id el input que vamos a mostrar
varNombreInput = ""


//Metodo para cerrar los popUp'
function escongerPopUP(){
    $("#backgroundPopup").fadeOut("fast")
    $("#logrosBoletines").fadeOut("fast")

}


//##ANOS ACADEMICOS
function seleccionarAnoAcademico(e){
    if(e.status=="success"){
        
        $(".botonAnoSeleccionado").removeClass("botonSeleccionadoVerde")
        $(".botonAnoSeleccionado").removeClass("botonAnoSeleccionado")
        
        $(e.source).addClass("botonSeleccionadoVerde")
        $(e.source).addClass("botonAnoSeleccionado")
        
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
    if(e.status=="success"){
        $(".botonAsignaturaSeleccionada").removeClass("botonSeleccionadoVerde")
        $(".botonAsignaturaSeleccionada").removeClass("botonAsignaturaSeleccionada")
        
        $(e.source).addClass("botonSeleccionadoVerde")
        $(e.source).addClass("botonAsignaturaSeleccionada")   
    }
}

////##Periodos
function seleccionarEstudiante(e){
    if(e.status=="begin"){
        if($(".row1").size() == 0 && $(".row1").size() == 0){
            $("#backgroundCargando").css("height", $("#formPrincipal\\:contenedorEstudiantes").height())
        }else{
            $("#backgroundCargando").css("height", $("#formPrincipal\\:contenedorInformes").height())
        }
        $("#backgroundCargando").css("width", $("#formPrincipal\\:contenedorInformes").width() + $("#formPrincipal\\:observaciones").width())
        $("#backgroundCargando").css("z-index", 1000)
        $("#backgroundCargando").show()
    }
    
    if(e.status=="success"){
        
        $("#backgroundCargando").css("z-index", 0)
        
        $("#backgroundCargando").hide()
        
        $(".labelSeleccionado").removeClass("labelSeleccionado")
        $(e.source).addClass("labelSeleccionado")
        
    }
}

function seleccionarPeriodos(e){
    if(e.status=="begin"){
        if($(".row1").size() == 0 && $(".row1").size() == 0){
            $("#backgroundCargando").css("height", $("#formPrincipal\\:contenedorEstudiantes").height())
        }else{
            $("#backgroundCargando").css("height", $("#formPrincipal\\:contenedorInformes").height())
        }
        $("#backgroundCargando").css("width", $("#formPrincipal\\:contenedorInformes").width() + $("#formPrincipal\\:observaciones").width())
        $("#backgroundCargando").css("z-index", 1000)
        $("#backgroundCargando").show()
    }
    
    if(e.status=="success"){
        
        $("#backgroundCargando").css("z-index", 0)
        
        $("#backgroundCargando").hide()
        
        if(!$(".botonPeriodoSeleccionado").hasClass("botonGrisClaro-Verde ")){
        	$(".botonPeriodoSeleccionado").addClass("botonGrisClaro-Verde ")
        }
        $(".botonPeriodoSeleccionado").removeClass("botonSeleccionadoVerde")
        $(".botonPeriodoSeleccionado").removeClass("botonPeriodoSeleccionado")
//        
        $(e.source).addClass("botonSeleccionadoVerde")
        $(e.source).addClass("botonPeriodoSeleccionado")
        
    }
}
//
//
////##Dimensiones
//
////MÃ©todo para selecionar y dejar seleccionada las dimensiones
//function seleccionarDimension(e){
//      
//    $(".botonDimensionSeleccionada").removeClass("botonSeleccionadoVerde")
//    $(".botonDimensionSeleccionada").removeClass("botonDimensionSeleccionada")
//        
//    $(e.source).addClass("botonSeleccionadoVerde")
//    $(e.source).addClass("botonDimensionSeleccionada")
//}
//
////##LOGROS
//function cargarEdicionLogros(e){
//    if(e.status=="success"){
//        nombre = "logros"
//        input = "textoLogro"
//        
//        var windowWidth = document.documentElement.clientWidth;
//        var windowHeight = document.documentElement.clientHeight;
//        var popupHeight = $("#" + nombre).height();
//        var popupWidth = $("#" + nombre).width();
//        //centering
//        $("#" + nombre).css({
//            "position": "absolute",
//            "top": windowHeight / 2 - popupHeight / 2,
//            "left": windowWidth / 2 - popupWidth / 2,
//            "z-index":999
//        });
//
//        $("#backgroundPopup").css({
//            "height": windowHeight,
//            "width":"99%",
//            "top":"0",
//            "position":"absolute"
//        });
//    
//        //loads popup only if it is disabled
//        $("#backgroundPopup").css({
//            "opacity": "0.7",
//            "background":"white"
//        });
//    
//    
//        $("#backgroundPopup").fadeIn("fast")
//        $("#" + nombre).fadeIn("fast")
//
//    
//        banderaClick = false
//        $("#"+nombre+"\\:"+input).focus()
//    }
//}
//
////MÃ©todo para cerrar el popUp de agregar los logros
//function terminarAgregarLogros(e){
//    if(e.status=="success"){
//        $("#backgroundPopup").fadeOut("fast")
//        $("#logros").fadeOut("fast")
//    }
//}
//
/////MÃ©todo para seleccionar el logro y dejarlo seleccionado
//function seleccionarLogros(e){
//    if(e.status=="success"){
//        
//        $(".botonLogroSeleccionada").removeClass("botonSeleccionadoVerde")
//        $(".botonLogroSeleccionada").removeClass("botonLogroSeleccionada")
//        
//        $(e.source).addClass("botonSeleccionadoVerde")
//        $(e.source).addClass("botonLogroSeleccionada")
//        
//    }
//}
//
////##NOTAS
////MÃ©todo para cargar el popUp con el que creo o edito las notas
//function cargarEdicionNotas(e){
//    if(e.status=="success"){
//        nombre = "notasGlobales"
//        input = "nombreNotaGlobal"
//        
//        var windowWidth = document.documentElement.clientWidth;
//        var windowHeight = document.documentElement.clientHeight;
//        var popupHeight = $("#" + nombre).height();
//        var popupWidth = $("#" + nombre).width();
//        //centering
//        $("#" + nombre).css({
//            "position": "absolute",
//            "top": windowHeight / 2 - popupHeight / 2,
//            "left": windowWidth / 2 - popupWidth / 2,
//            "z-index":999
//        });
//
//        $("#backgroundPopup").css({
//            "height": windowHeight,
//            "width":"99%",
//            "top":"0",
//            "position":"absolute"
//        });
//    
//        //loads popup only if it is disabled
//        $("#backgroundPopup").css({
//            "opacity": "0.7",
//            "background":"white"
//        });
//    
//    
//        $("#backgroundPopup").fadeIn("fast")
//        $("#" + nombre).fadeIn("fast")
//
//    
//        banderaClick = false
//        $("#"+nombre+"\\:"+input).focus()
//    }
//}
//
////MÃ©todo con el que escondo el popUp de las ntoas
//function terminarAgregarNotas(e){
//    if(e.status=="success"){
//        $("#backgroundPopup").fadeOut("fast")
//        $("#notasGlobales").fadeOut("fast")
//    }
//}
//
//
////MÃ©todo con el qe selecciono las notas y las dejo seleccionadas
//function seleccionarNotas(e){
//    if(e.status=="success"){
//        $(".botonNotasSeleccionado").removeClass("botonSeleccionadoVerde")
//        $(".botonNotasSeleccionado").removeClass("botonNotasSeleccionado")
//        
//        $(e.source).addClass("botonSeleccionadoVerde")
//        $(e.source).addClass("botonNotasSeleccionado")
//    }
//}
//
////##NOTAS CALIFICABLES
////MÃ©todo para cargar el popUP para agregar las actividades
//function cargarEdicionNotasCalificables(e){
//    if(e.status=="success"){
//        nombre = "formCrearNotasCalificables"
//        input = "nombreNotaCalificar"
//        
//        var windowWidth = document.documentElement.clientWidth;
//        var windowHeight = document.documentElement.clientHeight;
//        var popupHeight = $("#" + nombre).height();
//        var popupWidth = $("#" + nombre).width();
//        //centering
//        $("#" + nombre).css({
//            "position": "absolute",
//            "top": windowHeight / 2 - popupHeight / 2,
//            "left": windowWidth / 2 - popupWidth / 2,
//            "z-index":999
//        });
//
//        $("#backgroundPopup").css({
//            "height": windowHeight,
//            "width":"99%",
//            "top":"0",
//            "position":"absolute"
//        });
//    
//        //loads popup only if it is disabled
//        $("#backgroundPopup").css({
//            "opacity": "0.7",
//            "background":"white"
//        });
//    
//    
//        $("#backgroundPopup").fadeIn("fast")
//        $("#" + nombre).fadeIn("fast")
//
//    
//        banderaClick = false
//        $("#"+nombre+"\\:"+input).focus()
//    }
//}
//
////MÃ©todo con el que escondo el popUp de agregar una nota calificables
//function terminarAgregarNotasCalificables(e){
//    $("#backgroundPopup").fadeOut("fast")
//    $("#formCrearNotasCalificables").fadeOut("fast")
//}
//
////MÃ©todo para cargar el popUp donde estÃ¡n los estudiantes
//function cargarEdicionNotasEstudiantes(e){
//    if(e.status=="success"){
//        nombre = "formRegistroMatricula"
//        input = "nombreNotaCalificar"
//        
//        var windowWidth = document.documentElement.clientWidth;
//        var windowHeight = document.documentElement.clientHeight;
//        var popupHeight = $("#" + nombre).height();
//        var popupWidth = $("#" + nombre).width();
//        //centering
//        $("#" + nombre).css({
//            "position": "absolute",
//            "top": windowHeight / 2 - popupHeight / 2,
//            "left": windowWidth / 2 - popupWidth / 2,
//            "z-index":999
//        });
//
//        $("#backgroundPopup").css({
//            "height": windowHeight,
//            "width":"99%",
//            "top":"0",
//            "position":"absolute"
//        });
//    
//        //loads popup only if it is disabled
//        $("#backgroundPopup").css({
//            "opacity": "0.7",
//            "background":"white"
//        });
//    
//    
//        $("#backgroundPopup").fadeIn("fast")
//        $("#" + nombre).fadeIn("fast")
//
//    
//        banderaClick = false
//        $("#"+nombre+"\\:"+input).focus()
//    }
//}
//
////DespuÃ©s de cargar una nota
////
//function despuesCargarNota(e){
//    if(e.status=="success"){
//        //        alert()
//        //Removemos cualquier otro mensaje de alerta que haya
//        $("#mensajeAlerta").hide();
//
//        $(".tituloAlert").hide();
//        
//       
//        if($(".ui-messages-info-summary")[0] != null){
//            $(".contenidoAlert")[0].innerHTML = $(".ui-messages-info-summary")[0].innerHTML;
//            $(".ui-growl-image").show()
//            $(".ui-messages-error-icon").hide()
//            $("#mensajeAlerta").addClass("ui-messages-info")
//            $("#mensajeAlerta").removeClass("ui-messages-error")
//        }else{
//            $(".contenidoAlert")[0].innerHTML = $(".ui-messages-error-summary")[0].innerHTML;
//            $(".ui-growl-image").hide()
//            $(".ui-messages-error-icon").show()
//            $("#mensajeAlerta").removeClass("ui-messages-info")
//            $("#mensajeAlerta").addClass("ui-messages-error")
//        }
//        
//
//        //Variable que cotiene el cÃ³digo html para colocar el di
//        $("#mensajeAlerta").fadeIn();
//        
//            
//        //            $("#formEnviaAdjuntoStandar").submit()
//            
//        $("#idFormTablaBusquedaAlumnosPincipal\\:inputTextBuscar").focus()    
//
//        desaparecer();
//    }
//}
//
//
//function ocultar() {
//    $("#mensajeAlerta").fadeOut(500);
//}
//
////
//function desaparecer() {
//    intevaloMensaje = setTimeout("ocultar()", 3000);
//}



///###MÃ‰TODO PARA MOSTRAR 
function cargarVerboletines(e){
    if(e.status=="success"){
        nombre = "logrosBoletines"
//        input = "nombreNotaCalificar"
        
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
        $("#" + nombre).show()

    
        banderaClick = false
//        $("#"+nombre+"\\:"+input).focus()
    }
}


//Problema para el error de las observaciones
function errorObservaciones(e){
    
}