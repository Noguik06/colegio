
/*Función que permite recorrer una tabla a través de sus fila, y al presionar "enter" hace un click en la fila que se encuentre seleccionada, accionado el evento al navegador
 *   
 *   @parametros : 
 *   @idInputTextKeyUp= Es el id del inputText de donde se desea tomar el evento keyup
 *   @idTablaANavegar = es el id de la tabla donde se quiere navegar a travé de las filas
 *   @classeJ = es el nombre de la "class" que representa que indica por que fila se está navegando 
 *  */
function keyUpNavegarPorTablaFilas(idInputTexKeyUp , idTablaANavegar, classeJ){
    var comparaDatoFilter=null;
    $("#"+idInputTexKeyUp+"").keydown(function(e){  //Funcion que acciona el evento "keyUp" del respectivo input                
        //        claseSeleccionado = ".selected1"
        //        claseSubida = ".subeProductoBusqueda"
        //        claseBajada = ".bajaProductoBusqueda"                
        //        index = $('.evenClassUniversal').index($(".selected1")[0])                        
        var temp=$(this).val().replace(/\s*$/, " ") //var string querecibe lo que encuentre en el inputText de la busqueda
        temp=temp.replace(/^\s*/, " ")                
        if(temp!=comparaDatoFilter){//If reinicia en el caso que los datos del input sea diferentes y ya se haya ingresado algun dato
            if($("." + classeJ).length > 0){
                $("." + classeJ).removeClass(classeJ)
            }
            comparaDatoFilter= temp
        }        
        
        if(e.keyCode==40){// Función que se acciona ante el evento "Flecha Arriva" 
            if($("." + classeJ).length==0){                                                
                $("#"+idTablaANavegar+" tr").eq(0).addClass(classeJ)                
            }else{
                if($("." + classeJ).next("tr").val()!=undefined){
                    $("." + classeJ).next("tr").addClass(classeJ)
                    $("." + classeJ).first().removeClass(classeJ)
                    $("." + classeJ)[0].scrollIntoView(false)
                }
                comparaDatoFilter= temp
            }
            
        //            if(Math.abs(($(claseSeleccionado).position().top)) > 80 || index == $(".evenClassUniversal").size()-1){            
        //                $($(".bajaProductoBusqueda")[index ]).click()
        //            }
        }

        if(e.keyCode==38){ //Función que se acciona ante el evento "flecha arriba"; seguidamente agregá la clase a la siguiente fila y elimina la anterior
            if($("." + classeJ).length !=0){
                if($("." + classeJ).prev("tr").val()!=undefined){
                    $("." + classeJ).prev("tr").addClass(classeJ)
                    $("." + classeJ).last().removeClass(classeJ)
                    $("." + classeJ)[0].scrollIntoView(false)
                }
            }
            comparaDatoFilter= temp
        }
        
    //        if(temp==comparaDatoFilter && e.keyCode==13){//Función que se acciona ante el evento enter; seguidamente hace un click sobre la fila seleccionada                        
    //            $("." + classeJ).find("td label").eq(0).click()                        
    //        }
    })        
}



function keyUpNavegarPorTablaFilasSINScroll(idInputTexKeyUp , idTablaANavegar, classeJ){       
    var comparaDatoFilter=null;
    $("#"+idInputTexKeyUp+"").keyup(function(e){  //Funcion que acciona el evento "keyUp" del respectivo input                
        var temp=$(this).val().replace(/\s*$/, " ") //var string querecibe lo que encuentre en el inputText de la busqueda
        temp=temp.replace(/^\s*/, " ")                
        if(temp!=comparaDatoFilter){//If reinicia en el caso que los datos del input sea diferentes y ya se haya ingresado algun dato
            if($("." + classeJ).length > 0){
                $("." + classeJ).removeClass(classeJ)
            }
            comparaDatoFilter= temp
        }        
        
        if(e.keyCode==40){// Función que se acciona ante el evento "Flecha Arriva" 
            if($("." + classeJ).length==0){                                                
                $("#"+idTablaANavegar+" tr").eq(0).addClass(classeJ)                
            }else{
                if($("." + classeJ).next("tr").val()!=undefined){
                    $("." + classeJ).next("tr").addClass(classeJ)
                    $("." + classeJ).first().removeClass(classeJ)                    
                }
                comparaDatoFilter= temp
            }
        }

        if(e.keyCode==38){ //Función que se acciona ante el evento "flecha arriba"; seguidamente agregá la clase a la siguiente fila y elimina la anterior
            if($("." + classeJ).length !=0){
                if($("." + classeJ).prev("tr").val()!=undefined){
                    $("." + classeJ).prev("tr").addClass(classeJ)
                    $("." + classeJ).last().removeClass(classeJ)                    
                }
            }
            comparaDatoFilter= temp
        }
        
    //        if(temp==comparaDatoFilter && e.keyCode==13){//Función que se acciona ante el evento enter; seguidamente hace un click sobre la fila seleccionada                                                
    //            $("." + classeJ).find("td label").eq(0).click()                        
    //        }
    })        
}

function onclickRowTablaNavegacion(viene){ //Función para marcar la fila con la clase "select1" al hacer click sobre cualquier fila de la respectiva tabla
    if($(".selected1").length > 0){
        $(".selected1").removeClass("selected1")
    }        
    $(viene).parent().parent().addClass("selected1")    
}

function onclickRowTablaNavegacionSegunda(viene){ //Función para marcar la fila con la clase "select2" al hacer click sobre cualquier fila de la respectiva tabla
    if($(".selected2").length > 0){
        $(".selected2").removeClass("selected2")
    }        
    $(viene).parent().parent().parent().addClass("selected2")    
}

function onclickRowTablaNavegacionUniversal(viene, nombreClase){ //Función para marcar la fila con la clase "select2" al hacer click sobre cualquier fila de la respectiva tabla    
    if($("."+nombreClase+"").length > 0){
        $("."+nombreClase+"").removeClass(nombreClase)
    }            
    $(viene).parent().parent().addClass(nombreClase)        
}

function inputKeyUpFilterTeclas(e){        // Función que se activa con el evento "keyUp" de los inputText "buscar"; su finalidad es evitar que se ejecute el ajax cuando se presiones alguna de las teclas dentro del if                
    if(e == 40 || e == 37 || e == 38 || e == 39 || e == 9 ||  e == 13 || e == 16 || e == 17 || e == 18 || e == 27 ){        
        return false
    }else{
        return true
    }    
}