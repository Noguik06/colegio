
//Variables para guardar el id del popUp que vamos a mostrar
varNombreElemento = ""

//Variables para guardar el id el input que vamos a mostrar
varNombreInput = ""

$(function(){
    
})

//Método para mostrar el popUp que agregar dimensiones nuevas 
function agregarElemento(nombre, input){
    varNombreElemento = nombre
    varNombreInput = input
    
    centerPopup(nombre)
    loadPopup(nombre)
    
    banderaClick = false
    $("#"+nombre+"\\:"+varNombreInput).focus()
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
    $("#" + varNombreElemento).fadeOut("fast")
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
    if(e.status=="begin"){
        indiceEditarValor = $("."+e.source.className).index(e.source)
        nombreClase = e.source.className;
    }
    if(e.status=="success"){
        $($(".cargando"+nombreClase)[indiceEditarValor]).hide()
        $($(".eliminar"+nombreClase)[indiceEditarValor]).show()
    }
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

//###Metodo Para empezar la edición de algún campo
function empezarEdicion(e){
    if(!validarNumero(e)){
        nombreClase = $(e)[0].className;
//        alert(nombreClase)
        indiceEditarValor = $("."+$(e)[0].className).index(e)
        
        $($(".cargando"+nombreClase)[indiceEditarValor]).show()
        $($(".eliminar"+nombreClase)[indiceEditarValor]).hide()
    }
}

//Metodo para despues de haber seleccionado una tarea
function despuesSeleccionarTarea(e){
    if(e.status=="begin"){
        if($(".rowEstudiante").size() == 0){
            $("#backgroundCargando").css("height", $("#formListaEstudiantes\\:tableListaEstudiantes").height() + 50)
        }else{
            $("#backgroundCargando").css("height", $("#formListaEstudiantes\\:tableListaEstudiantes").height())
        }
        $("#backgroundCargando").css("width", $("#formListaEstudiantes\\:tableListaEstudiantes").width())
        $("#backgroundCargando").show()
    }
    
    if(e.status=="success"){
        $("#backgroundCargando").hide()
        
    }
}


//Metodo para despues de haber seleccionado una asignatura
function despuesSeleccionarAsignatura(e){
    if(e.status=="begin"){
        var div = "#formListaTareas"
        var div2 = "#formListaTareas\\:tableListaEstudiantes"
        if($(".rowTareas").size() == 0){
            $("#backgroundCargando").css("height", $(div).height() + 50)
        }else{
            $("#backgroundCargando").css("height", $(div).height())
        }
        $("#backgroundCargando").css("width", $(div2).width())
        $("#backgroundCargando").show()
    }
    
    if(e.status=="success"){
        $("#backgroundCargando").hide()
        
    }
}