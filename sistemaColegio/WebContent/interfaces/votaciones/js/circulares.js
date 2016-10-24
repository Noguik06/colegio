//scroller = null

$(document).ready(function() {
    $("#idFormTablaBusquedaAlumnosPincipal\\:contenido").hide()
})

//Despues de haber escogido
function despuesEscoger(){
	$($(".ui-editor")[0]).css("width","100%")
	$(".ui-editor iframe").css("width","100%")
}

//Despues de haber guardado la circular
function despuesGuardarCircular(e){
    if(e.status=="begin"){
        $("#backgroundCargando").css("height", $("#idFormTablaBusquedaAlumnosPincipal\\:contenido").height() + 20)
        $("#backgroundCargando").css("width", $("#idFormTablaBusquedaAlumnosPincipal\\:contenido").width())
        $("#backgroundCargando").css("margin-left", "10px")
        $("#backgroundCargando").show()
    }
    
    if(e.status=="success"){
        $("#backgroundCargando").hide()
        if($(".ui-growl-image-error").size() == 0){
            $("#idFormTablaBusquedaAlumnosPincipal\\:contenido").hide()
        }
    }
}

function despueseliminar(e){
    if(e.status=="success"){
        
        $("#idFormTablaBusquedaAlumnosPincipal\\:contenido").hide()
    }
}


//
function prueba(){
//    for (var i=0;i<whizzies.length;i++){
        
//        var t=whizzies[i];
//        
//        var d=o("whizzy"+t).contentWindow.document;
//        o(t).value = d.body.innerHTML
//        
//        $("#idFormTablaBusquedaAlumnosPincipal\\:contenidoCircular")[0].value = d.body.innerHTML;
    //        if (o(t).style.display=='block'){
    //            d.body.innerHTML=o(t).valueF
    //        }
    //        if((o(t).nodeName!="TEXTAREA")){
    //            alert("ola")
    //        }else{
    //            alert(o('wzhid_'+o(t).id))
    //        }
    //        var ret=(o(t).nodeName!="TEXTAREA") ? o('wzhid_'+o(t).id) : o(t);
    //        ret.value=tidyH(d)
//    }
}


function seleccionarPeriodos(e){
    if(e.status=="begin"){
        //        if($(".row1").size() == 0 && $(".row1").size() == 0){
        //            $("#backgroundCargando").css("height", $("#formPrincipal\\:contenedorCursos").height() + 20)
        //        }else{
        //            $("#backgroundCargando").css("height", $("#formPrincipal\\:contenedorEstudiantes").height())
        //        }
        //        $("#backgroundCargando").css("width", $("#formPrincipal\\:contenedorEstudiantes").width())
        $("#backgroundCargando").show()
    }
    
    if(e.status=="success"){
        
        $("#backgroundCargando").hide()
        
    //        $(".botonPeriodoSeleccionado").removeClass("botonSeleccionadoVerde")
    //        $(".botonPeriodoSeleccionado").removeClass("botonPeriodoSeleccionado")
    //        
    //        $(e.source).addClass("botonSeleccionadoVerde")
    //        $(e.source).addClass("botonPeriodoSeleccionado")
        
    }
}

function despuesEscogerFin(){
	$("#idFormTablaBusquedaAlumnosPincipal\\:contenido").show()
	$(".ui-editor").css("width","100%")
	$(".ui-editor iframe").css("width","100%")
}