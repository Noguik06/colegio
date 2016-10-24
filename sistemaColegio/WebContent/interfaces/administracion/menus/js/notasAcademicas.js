
//Variables para guardar el id del popUp que vamos a mostrar
varNombreElemento = ""

//Variables para guardar el id el input que vamos a mostrar
varNombreInput = ""

//Método para mostrar el popUp que agregar dimensiones nuevas 
function agregarElemento(nombre, input){
    centerPopup(nombre)
    loadPopup(nombre)
    varNombreElemento = nombre
    varNombreInput = input
    banderaClick = false
    $("#"+nombre+"\\:"+varNombreInput).focus()
}

//función para centrar el popUP
function centerPopup(nombre) {
    //request data for centering

    var windowWidth = document.documentElement.clientWidth;
    var windowHeight = document.documentElement.clientHeight;
    var popupHeight = $("#dimensiones").height();
    var popupWidth = $("#dimensiones").width();
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
        $($(".cargando"+nombreClase)[indiceEditarValor]).show()
        $($("."+nombreClase)[indiceEditarValor]).hide()
    }
    if(e.status=="success"){
        $($(".cargando"+nombreClase)[indiceEditarValor]).hide()
//        $($("."+nombreClase)[indiceEditarValor]).hide()
        $($("."+nombreClase)[indiceEditarValor]).show()
//        indiceEditarValor = $("."+e.source.className).index(e.source)
//        nombreClase = e.source.className;
//        $($(".cargando"+nombreClase)[indiceEditarValor]).show()
//        $($("."+nombreClase)[indiceEditarValor]).hide()
//        alert(indiceEditarValor)
    }
}