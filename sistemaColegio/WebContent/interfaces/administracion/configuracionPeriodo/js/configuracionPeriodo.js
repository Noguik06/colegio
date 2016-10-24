
//Variables para guardar el id del popUp que vamos a mostrar
varNombreElemento = ""

//Variables para guardar el id el input que vamos a mostrar
varNombreInput = ""

$(document).ready(function() {

	$(".claseInputTextFechaNacimiento").datepicker({
		changeMonth : true,
		changeYear : true,
		showOn : "button",
		dateFormat : 'dd-mm-yy',
		buttonImage : "/sistemaColegio/resources/imagenes/calendar.gif",
		buttonImageOnly : true,
		yearRange : '1950:2020'
	})

})

	
	
// Metodo para mostrar el popUp que agregar dimensiones nuevas
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
    
    
    $("#" + nombre).fadeIn("fast")
    $("#backgroundPopup").fadeIn("fast")

    
    banderaClick = false
    $("#"+nombre+"\\:"+input).focus()
    
}

//funcion para centrar el popUP
function centerPopup(nombre) {
    //request data for centering

    var windowWidth = document.documentElement.clientWidth;
    var windowHeight = document.documentElement.clientHeight;
    var popupHeight = $("#dimensiones").height();
    var popupWidth = $("#dimensiones").width();
    //centering
    $("#" + nombre).css({
        "position": "absolute",
        "top": 100,
        "left": windowWidth / 2 - popupWidth / 2,
        "z-index":999
    });

    $("#backgroundPopup").css({
        "height": windowHeight,
        "width":"1270px",
        "top":"0",
        "position":"absolute"
    });
}

//Funcion para cargar el PopUp
function loadPopup(nombre) {
    //loads popup only if it is disabled
    $("#backgroundPopup").css({
        "opacity": "0.7",
        "background":"white"
    });
    $("#backgroundPopup").fadeIn("fast")
    $("#" + nombre).fadeIn("fast")

}


//Funcion para terminar de agregar el popUp
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


//Metodo para cerrar los popUp'
function escongerPopUP(){

    $("#backgroundPopup").fadeOut("fast")
    $("#dimensiones").fadeOut("fast")
    $("#logros").fadeOut("fast")
    $("#notasGlobales").fadeOut("fast")
    $("#formCrearNotasCalificables").fadeOut("fast")
    $("#formRegistroMatricula").fadeOut("fast")
}

//Metodo para seleccionar el elemento a editar
function seleccionarElementoEditar(nombreElemento, nombreInput){
    varNombreElemento = nombreElemento
    varNombreInput = nombreInput
}

//Metodo para cargar el popUP de editar un elemento
function cargarPopUPEditarElemento(e){
    if(e.status=="success"){
        agregarElemento(varNombreElemento,varNombreInput)
    }
}

//##Metodo para cuando editan el valor de un input
var nombreClaseEditarValor
var indiceEditarValor
function despuesEditarValor(e){
    if(e.status=="begin"){
        indiceEditarValor = $("."+e.source.className).index(e.source)
        nombreClase = e.source.className;
    }
    if(e.status=="success"){
        $($(".cargando"+nombreClase)[indiceEditarValor]).hide()
        $($(".eliminar"+nombreClase)[indiceEditarValor]).show()
    }
}

//###Metodo Para empezar la edicion de algún campo
function empezarEdicion(e){
    if(!validarNumero(e)){
        nombreClase = $(e)[0].className;
        //        alert(nombreClase)
        indiceEditarValor = $("."+$(e)[0].className).index(e)
        
        $($(".cargando"+nombreClase)[indiceEditarValor]).show()
        $($(".eliminar"+nombreClase)[indiceEditarValor]).hide()
    }
}

//##CURSOS
//Metodo para seleccionar un curso
function seleccionarCursos(e){
	if(e.status=="begin"){
		PF('statusDialog').show();
			
	}
    if(e.status=="success"){
        $(".botonCursosSeleccionado").removeClass("botonSeleccionadoVerde")
        $(".botonCursosSeleccionado").removeClass("botonCursosSeleccionado")
        
        $(e.source).addClass("botonSeleccionadoVerde")
        $(e.source).addClass("botonCursosSeleccionado")
        PF('statusDialog').hide();
    }
}


//##ASIGNATURAS
//Metodo para seleccionar las asignaturas
function seleccionarAsignaturas(e){
	if(e.status=="begin"){
		PF('statusDialog').show();
			
	}
    if(e.status=="success"){
        
        var boton = "botonAsignaturasSeleccionado"
        $("." + boton).removeClass("botonSeleccionadoVerde")
        $("." + boton).removeClass(boton)
        
        $(e.source).addClass("botonSeleccionadoVerde")
        $(e.source).addClass(boton)
        PF('statusDialog').hide();
        
        
        var altura = 0;
        var i= 0;
        
        for(i=0; i<$('.filaNotaEstudiante').size(); i++){
        	if(altura<$($('.filaNotaEstudiante')[i]).height()){
        		altura = $($('.filaNotaEstudiante')[i]).height()
        	}
        }
        $('.filaNotaCalificable').height(50)
        $('.filaNotaEstudiante').height(50)
    }
}


//##DIMENSIONES
//Metodo para mostrar el popUP de las dimensiomes
function mostrarElementoDimensiones(e){
    if(e.status=="success"){
        nombre = "dimensiones"
        input = "nombreDimension"
        
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
    
    
        $("#" + nombre).fadeIn("fast")
        $("#backgroundPopup").fadeIn("fast")

    
        banderaClick = false
        $("#"+nombre+"\\:"+input).focus()
    }
}

//Metodo para esconder el popUp luego de haber agregado la dimension
function terminarAgregarDimensiones(e){
    if(e.status=="begin"){
        $("#backgroundPopup").fadeOut("fast")
        $("#dimensiones").fadeOut("fast")
    }
}

function seleccionarDimensiones(e){
    if(e.status=="success"){
        
        var boton = "botonDimensionesSeleccionado"
        $("." + boton).removeClass("botonSeleccionadoVerde")
        $("." + boton).removeClass(boton)
        
        $(e.source).addClass("botonSeleccionadoVerde")
        $(e.source).addClass(boton)
    }
}

//###LOGROS
//Metodo para mostrar el popUP de las dimensiomes
function mostrarElementoLogros(e){
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
    
    
        $("#" + nombre).fadeIn("fast")
        $("#backgroundPopup").fadeIn("fast")

    
        banderaClick = false
        $("#"+nombre+"\\:"+input).focus()
    }
}

//Metodo para esconder el popUp luego de haber agregado la dimension
function terminarAgregarLogros(e){
    if(e.status=="begin"){
        $("#backgroundPopup").fadeOut("fast")
        $("#logros").fadeOut("fast")
    }
}

function seleccionarLogros(e){
    if(e.status=="success"){
        var boton = "botonLogroSeleccionado"
        $("." + boton).removeClass("botonSeleccionadoVerde")
        $("." + boton).removeClass(boton)
        
        $(e.source).addClass("botonSeleccionadoVerde")
        $(e.source).addClass(boton)
    }
}

//##NOTAS 
//Metodo para mostrar el popUp
function mostrarElementoNotas(e){
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
    
    
        $("#" + nombre).fadeIn("fast")
        $("#backgroundPopup").fadeIn("fast")

    
        banderaClick = false
        $("#"+nombre+"\\:"+input).focus()
    }
}

//Metodo para esconder el popUP
function terminarAgregarNotasGlobales(e){
    if(e.status=="begin"){
        $("#backgroundPopup").fadeOut("fast")
        $("#notasGlobales").fadeOut("fast")
    }
}

function seleccionarNotaGlobal(e){
     if(e.status=="success"){
                
        var boton = "botonNotaGlobalSeleccionado"
        $("." + boton).removeClass("botonSeleccionadoVerde")
        $("." + boton).removeClass(boton)
        
        $(e.source).addClass("botonSeleccionadoVerde")
        $(e.source).addClass(boton)
    }
}

//##Actividades
//Metodo para mostrar el popUp
function mostrarElementoActividades(e){
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
    
    
        $("#" + nombre).fadeIn("fast")
        $("#backgroundPopup").fadeIn("fast")

    
        banderaClick = false
        $("#"+nombre+"\\:"+input).focus()
    }
}

//Metodo para esconder el popUP
function terminarAgregarActividades(e){
    if(e.status=="begin"){
        $("#backgroundPopup").fadeOut("fast")
        $("#formCrearNotasCalificables").fadeOut("fast")
    }
}

//##LISTA DE NIÑOS CALIFICABLES
function mostrarElementoNotasCalificables(e){
    if(e.status=="success"){
        nombre = "formRegistroMatricula"
        
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
    
    
        $("#" + nombre).fadeIn("fast")
        $("#backgroundPopup").fadeIn("fast")
    }
}

//##PERIODOS
function seleccionarPeriodos(e){
    if(e.status=="success"){
                
        var boton = "botonPeriodoSeleccionado"
        $("." + boton).removeClass("botonSeleccionadoVerde")
        $("." + boton).removeClass(boton)
        
        $(e.source).addClass("botonSeleccionadoVerde")
        $(e.source).addClass(boton)
    }
}


//Metodo para mostrar el popUP de las dimensiomes
function mostrarElementoPeriodos(e){
    if(e.status=="success"){
        nombre = "periodos"
        input = "nombrePeriodos"
        
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
    
    
        $("#" + nombre).fadeIn("fast")
        $("#backgroundPopup").fadeIn("fast")

    
        banderaClick = false
        $("#"+nombre+"\\:"+input).focus()
    }
}

//Metodo para esconder el popUp luego de haber agregado la dimension
function terminarAgregarPeriodos(e){
    if(e.status=="begin"){
        $("#backgroundPopup").fadeOut("fast")
        $("#logros").fadeOut("fast")
    }
}