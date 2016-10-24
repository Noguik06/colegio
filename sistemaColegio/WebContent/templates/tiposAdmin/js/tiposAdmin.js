/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

var popupStatusP = 0

$(function(){
   
   
    $(".rowAdmin").mousemove(function(){
        $($($(this).children()[0]).children()[0]).show()
        $($($(this).children()[0]).children()[1]).show()
    })
        
    $(".rowAdmin").mouseout(function(){
        $($($(this).children()[0]).children()[0]).hide()
        $($($(this).children()[0]).children()[1]).hide()
    })
    
})

//Método para agregar la función de mostrar las funciones de editar y borrar los entes
function agregarEdicionEntes(e){
    if(e.status=="success"){
        $(".rowAdmin").mousemove(function(){
            $($($(this).children()[0]).children()[0]).show()
            $($($(this).children()[0]).children()[1]).show()
        })
        
        $(".rowAdmin").mouseout(function(){
            $($($(this).children()[0]).children()[0]).hide()
            $($($(this).children()[0]).children()[1]).hide()
        })
    }
}


//Función para después de abrir el pop de presupuesto
function cargarPresupuesto(e){
    if(e.status=="success"){
        popupStatusP = 1 
        $("#popUpPresupuesto\\:presIninicial").focus()
    }
}

//Método para después de empezar a agregar un ente
function agregarTipo(e){
    
    //    if(e.status=="begin"){
    //    //        $("#footerAdminUsers").remove()
    //    }
    //    
    if(e.status=="success"){
        //        statusAddEnte = false;
        //        //        
        $($(".notipo")[0]).focus()
        //        //        
        //        //Cambiar el focus del nombre al tipo
        $($(".notipo")[0]).keyup(function(e){
            if(e.keyCode==13){
                $($(".nombreTipo")[0]).focus()
            }
        })

    }
}


//funcion para validar si le dieron enter
function validarFormato(e){

    
    if(event.keyCode == 13 && !/[a-zA-Z]|\d/.test(e.value)){    
        e.value = " "
        return false;
    }
    
    if(event.keyCode == 27){
        $(".notipo")[0].value = 0
        $($(".notipo")[0]).keyup()
        return false;
    }
    if(event.keyCode == 13){
        $($(".notipo")[0]).keyup()
        //        alert('ola')
        return false;
    }

    return true;
}


//función para validar el cancel
function validarCancel(){
    if(event.keyCode == 27){
        $(".notipo")[0].value = 0
        return false;
    }else{
        if(event.keyCode == 13){
            $($(".nombreTipo")[0]).focus()
            return true;
        }
    }
    return true;
}

//función para validar el cancel de la edicion
function validarCancelEdit(){
    if(event.keyCode == 27){
        $(".notipo")[0].value = 0
        return false;
    }else{
        if(event.keyCode == 13){
            $($(".nombreTipo")[0]).focus()
            return true;
        }
    }
    return true;
  
}