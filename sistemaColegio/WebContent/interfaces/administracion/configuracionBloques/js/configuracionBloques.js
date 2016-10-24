function onEventBontonCrearBloques(data){
    if(data.status == "success"){        
        centerPoUpUniversalReal("clasePopUpCrearBloques")
        $(".clasePopUpCrearBloques").show()
        $(".clasePopUpBackGroundCrearBloques").show()
    }
}

var banderaOnErrorBotonCrearGrupoBloques = false;
function onErrorBotonCrearGrupoBloques(error){
    alert(error.errorMessage)
    banderaOnErrorBotonCrearGrupoBloques  = true;
}

function onEventBotonCrearGrupoBloques(data){
    if(data.status == "success"){        
        if(!banderaOnErrorBotonCrearGrupoBloques){
            $(".clasePopUpCrearBloques").hide()
            $(".clasePopUpBackGroundCrearBloques").hide()   
        }        
        banderaOnErrorBotonCrearGrupoBloques  = false;
    }
}

function onEventLabelBloquesSelected(data){
    if(data.status == "success"){
        $(document.getElementById($(data.source)[0].id)).addClass("claseCeldaSeleccionada")
        //Color curso deactivados Total
        $("#idFormPrincipal\\:idPanelGroupRelacionesBloquesCursosGeneral table tr td label.claseBanderaCursoDesactivadaTotal").parent().parent().find("td label.claseCeldaColorDesactivada").addClass("claseCeldaColorDesactivadaTotal")
    }
}

function onEventLabelCursosSelected(data){
    if(data.status == "success"){
        $("#idFormPrincipal\\:idPanelGroupRelacionesBloquesCursosGeneral table tr td label.claseBanderaCursoDesactivadaTotal").parent().find("td div.claseCeldaColorDesactivada").addClass("claseCeldaColorDesactivadaTotal")        
    }
}

function onClickLabelCursosGenerales(viene){
    if($(viene).parent().parent().parent().find("img.claseIMGBanderaCursosGeneral").length == 0){//No está seleccionada
        //        if(confirm("¿Desea Marcar el curso seleccionado con el  Bloques marcado?")){
        //            return true;
        //        }        
        return true
    }else{//ELSE = Está marcado como Seleccionado
        if(confirm("¿Desea DESMARCAR el curso seleccionado del Bloques marcado?")){
            return true;
        }        
    }
    return false
}

function onEventBotonCrearAsignaturasIntermedias(data){
    if(data.status == "success"){
        centerPoUpUniversalReal("clasePopUpCrearActividadesIntermedias")
        $(".clasePopUpCrearActividadesIntermedias").show()
        $(".clasePopUpBackGroundCrearActividadesIntermedias").show()       
        //Marcamos los dias que vienen por default seleccionados
        $(".clasePopUpCrearActividadesIntermedias label.claseBanderaDiaMarcadoByActividadIntermediaNEW").parent().find("div.claseCeldaColorDesactivada").addClass("claseCeldaSeleccionada")
        //Desmarca los bloques que sirven para asignar a una actividadIntermedia Nueva        
        $(".clasePanelGroupBloqueByNewActividadIntermedia").parent().parent().removeClass("claseColumnaHiddenByBloquesByNewActividad")
    }
}

function onEventClickBloquesByNewActividadesIntermedias(data){
    if(data.status == "success"){        
        $(".clasePanelGroupBloqueByNewActividadIntermedia").parent().parent().removeClass("claseColumnaHiddenByBloquesByNewActividad")
    }
}

function onEventClickLabelDiaByNewActividadIntermedia(data){
    if(data.status == "success"){
        $(".clasePopUpCrearActividadesIntermedias label.claseBanderaDiaMarcadoByActividadIntermediaNEW").parent().find("div.claseCeldaColorDesactivada").addClass("claseCeldaSeleccionada")        
    }
}

var banderaonErrorBotonCrearActividadIntermediaFinal = false;
function onErrorBotonCrearActividadIntermediaFinal(error){    
    if(error.errorMessage == 1){
        alert("El nombre  de la actividad intermedia es necesario.")
    }
    if(error.errorMessage == 2){
        alert("Es necesario marcar un día de la semana.")
    }
    if(error.errorMessage == 3){
        alert("Es necesario seleccionar un bloque.")
    }
    if(error.errorMessage == 4){
        alert("Ya existe una actividad con el nombre de la actividad que se quiere crear.")
    }
    banderaonErrorBotonCrearActividadIntermediaFinal  = true;
}

function onEventBotonCrearActividadIntermediaFinal(data){
    if(data.status == "success"){
        if(!banderaonErrorBotonCrearActividadIntermediaFinal){
            $(".clasePopUpCrearActividadesIntermedias").hide()
            $(".clasePopUpBackGroundCrearActividadesIntermedias").hide()              
        }
        banderaonErrorBotonCrearActividadIntermediaFinal  = false;
    }
}

function onEventLabelActividadesIntermediasSeleccionar(data){
    if(data.status == "success"){
        $(document.getElementById($(data.source)[0].id)).addClass("claseCeldaSeleccionada")
    }
}