
//Variables para guardar el id del popUp que vamos a mostrar
varNombreElemento = ""

//Variables para guardar el id el input que vamos a mostrar
varNombreInput = ""

//
////FunciÃ³n para terminar de agregar el popUp
//function terminarAgregarPopUp(e){
//    if(e.status=="complete"){
//        banderaClick = true
//    }
//    if(e.status=="success"){
//        $("#backgroundPopup").fadeOut("fast")
//        $("#" + varNombreElemento).fadeOut("fast")
//        $("#"+varNombreElemento+"\\:"+varNombreInput).val("")
//    }
//}
//

//MÃ©todo para cerrar los popUp'
function escongerPopUP(){
    $("#backgroundPopup").fadeOut("fast")
    $("#logrosBoletines").fadeOut("fast")

}
//
////MÃ©todo para seleccionar el elemento a editar
//function seleccionarElementoEditar(nombreElemento, nombreInput){
//    varNombreElemento = nombreElemento
//    varNombreInput = nombreInput
//}
//
////MÃ©todo para cargar el popUP de editar un elemento
//function cargarPopUPEditarElemento(e){
//    if(e.status=="success"){
//        $("#formRegistroMatricula").css("width", "30%")
//        agregarElemento(varNombreElemento,varNombreInput)
//    }
//}
//
////##MÃ©todo para cuando editan el valor de un input
//var nombreClaseEditarValor
//var indiceEditarValor
//function despuesEditarValor(e){
//    if(e.status=="begin"){
//        $($(".cargando"+nombreClase)[$("."+e.source.className).index(e.source)]).hide()
//        $($(".eliminar"+nombreClase)[$("."+e.source.className).index(e.source)]).show()
//        
//        $($(".cargando"+nombreClase)[$("."+e.source.className).index(e.source)]).show()
//        $($(".eliminar"+nombreClase)[$("."+e.source.className).index(e.source)]).hide()
//    }
//    if(e.status=="success"){
//        $($(".cargando"+nombreClase)[$("."+e.source.className).index(e.source)]).hide()
//        $($(".eliminar"+nombreClase)[$("."+e.source.className).index(e.source)]).show()
//    }
//}
//
////MÃ©todo para validar 
//function validarBlurCargando(e){
//    if($($(".cargando"+e.className)[$("."+e.className).index(e)]).is(":visible")){
//        return true;
//    }
//    return false
////    $($(".cargando"+e.className)[$("."+e.className).index(e)]).show()
//}
//
////function despuesEditarValor(e){
////    if(e.status=="begin"){
////        indiceEditarValor = $("."+e.source.className).index(e.source)
////        nombreClase = e.source.className;
////        $($(".cargando"+nombreClase)[indiceEditarValor]).show()
////        $($("."+nombreClase)[indiceEditarValor]).hide()
////    }
////    if(e.status=="success"){
////        $($(".cargando"+nombreClase)[indiceEditarValor]).hide()
//////        $($("."+nombreClase)[indiceEditarValor]).hide()
////        $($("."+nombreClase)[indiceEditarValor]).show()
//////        indiceEditarValor = $("."+e.source.className).index(e.source)
//////        nombreClase = e.source.className;
//////        $($(".cargando"+nombreClase)[indiceEditarValor]).show()
//////        $($("."+nombreClase)[indiceEditarValor]).hide()
//////        alert(indiceEditarValor)
////    }
////}
//
////###MÃ©todo Para empezar la ediciÃ³n de algÃºn campo
//function empezarEdicion(e){
//    if(!validarNumero(e)){
//        nombreClase = $(e)[0].className;
//        //        alert(nombreClase)
//        indiceEditarValor = $("."+$(e)[0].className).index(e)
//        
//        $($(".cargando"+nombreClase)[indiceEditarValor]).show()
//        $($(".eliminar"+nombreClase)[indiceEditarValor]).hide()
//    }
//}
//
//function terminarUpdate(e){
//    if(e.status=="success"){
//        try{
//            
//        }catch(e){
//            
//        }
//    }
//}

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
function seleccionarPeriodos(e){
    if(e.status=="begin"){
        if($(".row1").size() == 0 && $(".row1").size() == 0){
            $("#backgroundCargando").css("height", $("#formPrincipal\\:contenedorCursos").height() + 20)
        }else{
            $("#backgroundCargando").css("height", $("#formPrincipal\\:contenedorEstudiantes").height())
        }
        $("#backgroundCargando").css("width", $("#formPrincipal\\:contenedorEstudiantes").width())
        $("#backgroundCargando").show()
    }
    
    if(e.status=="success"){
        
        $("#backgroundCargando").hide()
        
        $(".botonPeriodoSeleccionado").removeClass("botonSeleccionadoVerde")
        $(".botonPeriodoSeleccionado").removeClass("botonPeriodoSeleccionado")
        
        $(e.source).addClass("botonSeleccionadoVerde")
        $(e.source).addClass("botonPeriodoSeleccionado")
        
        cuadrarScrollGeneral(".colNotasEstudiantesFinal-Header", ".colNotasEstudiantesFinal-Body")
//        cuadrarScrollGeneral(".colRecEstudiantesFinal-Header", ".colRecEstudiantesFinal-Body")
        
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



///###METODO PARA MOSTRAR 
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