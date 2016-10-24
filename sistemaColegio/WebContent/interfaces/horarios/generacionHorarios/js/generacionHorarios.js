function onEventBotonGenerarHorarios(data){
    if(data.status == "begin"){
        $("#idDivImagenCargandoBotonVerHorarioPorCurso").show()
    }
    if(data.status == "success"){
        $(".claseShowTrBloqueHorarioHabilitado").parent().parent().removeClass("claseShowTrTableBloqueHorarioHabilitado")
        $(".clasePanelGroupValidoRender").parent().parent().removeClass("claseDisplayNoneTrHorarios")
        $("#idDivImagenCargandoBotonVerHorarioPorCurso").hide()
    }
}

function onEventClicDivCeldaEditHorario(data){
    if(data.status == "success"){
        $(document.getElementById($(data.source)[0].id)).parent().parent().parent().addClass("claseBloqueSeleccionadoByEdit")
        centerPoUpUniversalReal("clasePopUpEditarCeldaHorario")
        $(".claseDivPantallaUniversal").show()
        $(".clasePopUpEditarCeldaHorario").css("position", "fixed")
        $(".clasePopUpEditarCeldaHorario").show()
    }
}

function onClickLabelLimpiarBloqueSeleccionado(){
    if(confirm("¿Desea limpiar el bloque seleccionado?")){        
        return true;
    }
    return false
}

function onEventClickLabellimpiarBloque(data){
    if(data.status == "success"){
        $(".clasePanelGroupValidoRender").parent().parent().removeClass("claseDisplayNoneTrHorarios")
        onClickDivPantallaUniversal()       
    }    
}

var banderaOnErrorBotonRealizarIntercambiarByCeldaHorario = false;
function onErrorBotonRealizarIntercambiarByCeldaHorario(error){    
    if(error.errorMessage == 1){//Error: porque la relación entre (profesor - diaActual - bloque) ya se encuentra ocupada en otro curso
        alert("El profesor seleccionado tiene interterferencia horaria.")
    } 
    if(error.errorMessage == 2){//Erro no se ha seleecionado ningun profesor
        alert("No se ha seleccionado ningún profesor.")
    }
    if(error.errorMessage == 3){//Erro no se ha seleecionado ningun profesor
        alert("No se ha seleccionado ninguna asignatura.")
    }
    banderaOnErrorBotonRealizarIntercambiarByCeldaHorario  = true;
}

function onEventBotonRealizarIntercambiByCeldaEditHorario(data){
    if(data.status == "success"){        
        if(!banderaOnErrorBotonRealizarIntercambiarByCeldaHorario){
            $(".claseBloqueSeleccionadoByEdit").parent().parent().parent().parent().find("label.claseLabelRenderByEditBloque").click()        
            $(".claseBloqueSeleccionadoByEdit").parent().parent().parent().parent().parent().parent().parent().parent().find("label.claseRenderPanelGroupMateriasPendientesByCurso").click()        
            $(".clasePanelGroupValidoRender").parent().parent().removeClass("claseDisplayNoneTrHorarios")
            onClickDivPantallaUniversal()
        }
        banderaOnErrorBotonRealizarIntercambiarByCeldaHorario = false;
    }    
}

function onClickBotonRealizarIntercambioByCelda(){    
    if(confirm("¿Desea realizar el intercambio marcado?")){        
        return true;
    }
    return false        
}

function onEventMostrarHoraioProfeso(data){
    if(data.status == "success"){
        $(".clasePanelGroupValidoRender").parent().parent().removeClass("claseDisplayNoneTrHorarios")           
    }
}

function onEventLabelRenderByBloqueEditado(data){
    if(data.status == "success"){
        $(".clasePanelGroupValidoRender").parent().parent().removeClass("claseDisplayNoneTrHorarios")
    }
}

function OnclickDivPantallaEditarHorario(){
    $("tr.claseBloqueSeleccionadoByEdit").removeClass("claseBloqueSeleccionadoByEdit")
}