function onClickBotonCerrarAnoAcademico(){
    if(confirm("¿Desea Cerrar el presente año académico?")){
        return true;
    }
    return false
}

var banderaOnErrorIMGRemoverAsignaturaByAnoAcademico = false;
function onErrorIMGRemoverAsignaturaByAnoAcademico(error){
    if(error.errorMessage == 1){
        alert("La presente asignatura se encuentra asignada a algún profesor.")
    }
    if(error.errorMessage == 2){
        alert("La presente asignatura se le está asignando algún curso actualmente.")
    }
    banderaOnErrorIMGRemoverAsignaturaByAnoAcademico  = true;
}

function onEventIMGRemoverAsignaturaByAnoAcademico(data){
    if(data.status == "success"){
        if(!banderaOnErrorIMGRemoverAsignaturaByAnoAcademico){
            $(".claseLabelMarcadoGreenDiv").parent().find("div.claseCeldaColorDesactivada").addClass("claseCeldaSeleccionada")
        }
        banderaOnErrorIMGRemoverAsignaturaByAnoAcademico = false;
    }
}

var banderaOnErrorCeldaAsignatura = false;
function onErrorClickAsignaturaByAsignarGeneral(error){
    if(error.errorMessage == 1){
        alert("La cantidad de horas por semana no puede ser 0.")
        banderaOnErrorCeldaAsignatura = true;
    }
}

function onEventClickCeldaAsignaturaBySelected(data){
    if(data.status == "success"){
        if(!banderaOnErrorCeldaAsignatura){
            $(document.getElementById($(data.source)[0].id)).parent().addClass("claseCeldaSeleccionada")
        }
        banderaOnErrorCeldaAsignatura = false;        
    }
}

function onEventClickCeldaCursosBySelected(data){
    if(data.status == "success"){
        $(document.getElementById($(data.source)[0].id)).parent().addClass("claseCeldaSeleccionada")
    }
}

function onEventClickCeldaProfesoresSelected(data){
    if(data.status == "success"){
        $(".claseLabelMarcadoGreenDiv").parent().find("div.claseCeldaColorDesactivada").addClass("claseCeldaSeleccionada")
        $(document.getElementById($(data.source)[0].id)).parent().addClass("claseCeldaSeleccionada")
    }
}

function onEventIMGAgregarASignaturaByAno(data){
    if(data.status == "success"){            
        $(".claseLabelMarcadoGreenDiv").parent().find("div.claseCeldaColorDesactivada").addClass("claseCeldaSeleccionada")
    }
}

function onEventClickLabelCrearAsignatura(data){
    if(data.status == "success"){            
        $(".claseDivPantallaUniversal").show()
        $(".clasePopUpCrearAsignatura").show()
        centerPoUpUniversalReal("clasePopUpCrearAsignatura")        
        $("#idFormCrearAsignatura\\:idInputTextNombreAsignatura").focus()
    }
}

var banderaOnErrorBotonCrearAsignatura = false;
function onErrorBotonCrearAsignatura(error){
    if(error.errorMessage == 1){//El nombre de la asignatura es necesario.
        alert("El nombre de la asignatura es necesario.")
        $("#idFormCrearAsignatura\\:idInputTextNombreAsignatura").focus()
    }
    if(error.errorMessage == 2){//Hay una asignatura con el presente nombre
        alert("Hay una asignatura con el presente nombre.")
        $("#idFormCrearAsignatura\\:idInputTextNombreAsignatura").focus()
    }
    banderaOnErrorBotonCrearAsignatura = true;
}

function onEventBotonCrearAsignatura(data){
    if(data.status == "success"){
        if(!banderaOnErrorBotonCrearAsignatura){
            onClickDivPantallaUniversal()
        }
        banderaOnErrorBotonCrearAsignatura = false;
    }
}

function onClickIMGEliminarAsignaturaGeneral(){
    if(confirm("¿Desea realmente eliminar la asignatura seleccionada?")){
        return true;
    }
    return false
}

function onErrorIMGEliminarAsignaturaGeneral(error){
    if(error.errorMessage == 1){//El nombre de la asignatura es necesario.
        alert("Imposible eliminar la presente asignatura, se encuentrá asignada en algún periodo académico.")
    }
}

function onEventClickLabelCrearCurso(data){
    if(data.status == "success"){
        $(".claseDivPantallaUniversal").show()
        $(".clasePopUpCrearCurso").show()
        centerPoUpUniversalReal("clasePopUpCrearCurso")                
    }
}

var banderaOnErrorBotonCrearCurso = false;
function onErrorBotonCrearCurso(error){
    if(error.errorMessage == 1){//Es necesario activar un año académico
        alert("Es necesario activar un año académico.")
    }
    if(error.errorMessage == 2){//Es necesario seleccionar un grado.
        alert("Es necesario seleccionar un grado.")
    }
    if(error.errorMessage == 3){//Es necesario activar un año académico
        alert("El nombre del curso es necesario.")
    }
    if(error.errorMessage == 4){//El presente curso ya se encuentra en el grado seleccionado.
        alert("El presente curso ya se encuentra en el grado seleccionado.")
    }
    if(error.errorMessage == 5){//El presente curso ya se encuentra en el grado seleccionado.
        alert("! Imposible ¡ el director seleccionado ya se encuentra en otro curso.")
    }
    banderaOnErrorBotonCrearCurso = true;
}

function onEventBotonCrearCurso(data){
    if(data.status == "success"){
        if(!banderaOnErrorBotonCrearCurso ){
            onClickDivPantallaUniversal()           
        }
        banderaOnErrorBotonCrearCurso  = false;
    }
}

function onEventIMGEliminarCurso(data){
    if(data.status == "success"){
        $(".claseLabelMarcadoGreenDiv").parent().find("div.claseCeldaColorDesactivada").addClass("claseCeldaSeleccionada")       
    }
}

function onClickIMGEliminaCurso(){
    if(confirm("¿Desea eliminar el curso seleccionado?")){
        return true
    }
    return false
}