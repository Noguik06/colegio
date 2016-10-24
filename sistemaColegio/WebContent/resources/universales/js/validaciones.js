/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

var popupStatusP = 0

//Variable que guarda los valores iniciales de los campos.
var variableChange = 0;

//Variable para guardar los valores temporales de las letras
var variableLetras = null

//Variable para guardar los valores temporales de las letras 2
var variableLetras2


//metodo para guardar los valores string hayan cambiado
function validarLetras(e){
    if((variableLetras == null || variableLetras == "") && e.value.replace(" ","").length ==0){
        variableLetras = e.value
        return true;
    }
    if(variableLetras == e.value){
        return true;
    }
    variableLetras = e.value
    
}



//Funcion para guardar valores para guardarlos despues
function guardarValorEspecial(e){
    variableLetras = e.value
}


function guardarLetras(e){
    variableLetras = e.value
}

//M������todos para guardar y validar el cambio en las letras
function validarLetras2(e){
    if(variableLetras2 == e.value){
        return true;
    }
    variableLetras2 = e.value
    
}

function guardarLetras2(e){
    variableLetras2 = e.value
}


//Funci������n para guardar valores para guardarlos despu������s
function guardarValor(e){
    variableChange = e.value
}

function reiniciarValor(){
	variableChange = ""
}

//Funci������n para guardar los valores de los campos que tienen letras
function guardarValorLetras(e){
    //    if(e.value=="ingrese Valor"){
    //        e.value = ""
    //    }
    variableChange = e.value
}

//###BLUR
var variableBlur = ""
//Validar focus
function guardarValorBlur(e){
    variableBlur = e.value
}

//Validar focus
function validarNumeroBlur(e){
    if(variableBlur == e.value){
        return true;
    }
    return false
}

//Funci������n que controlar el blur de los campos con letras
function validarSalidaLetras(e){
    if(e.value.length==0 || e.value.replace(/\s*/,"").length == 0){
        e.value= "ingrese Valor"
    }
}

//Funci������n para no dejar que la cantidad de los productos
//que conforman un kit sea igual a cero.
function salirCantidadKit(e){
    if(e.value==0){
        e.value = 1
    }
}

function isInt(n) {
   return n % 1 === 0;
}

//Funci������n para validar que lo que se ingrese sea de tipo num������rico
function validarNumero(e){
    if(e.value.replace(" ","").length == 0){
        e.value = 0;
        $(e).select()
        return false;
    }
    if(e.value!=variableChange){        
        posicion = doGetCaretPosition(e)
        
        nopuntos = e.value.split(".").length
        resta = 0
        
        quitarPuntosParticular(e)
        if(e.value.length == 0){
            e.value = 0
            doSetCaretPosition(e,1)
            if(variableChange !=0 ){
                return false;
            }
            return true;
        }else{
            if(!isInt(e.value)){
                e.value = 0
                return true
            }
        }
        if(/[a-zA-Z]/.test(e.value)){
            e.value = e.value.replace(/[a-zA-Z]/,"")
            if(e.value.length == 0){
                e.value = 0
                doSetCaretPosition(e,1)
                if(variableChange !=0 ){
                    return false;
                }
                return true;
            }
            
            resta = 1
            colocarPuntosParticular(e)
            doSetCaretPosition(e,posicion-resta)
            return true;
        }else{
            if(e.value.length>1){
                if(e.value.split(" ").length>1){
                    e.value = e.value.replace(" ","")  
                    resta = 1
                }else{
                    if(e.value.split("-").length>1){
                        e.value = e.value.replace("-","")
                        e.value = parseFloat(e.value)
                        resta = 1
                    }else{
                        e.value = parseFloat(e.value)
                    }
                }
            }
        }
        
        colocarPuntosParticular(e)
        if(e.value.split(".").length>nopuntos){
            resta = -1
        }else{
            if(e.value.split(".").length<nopuntos){
                resta = 1
            }
        }
        
        doSetCaretPosition(e,posicion-resta)
    }else{
        return true
    }
    if(e.value.split(" ").length>1){
        e.value = e.value.replace(" ","")  
        doSetCaretPosition(e,posicion-1)
    }
    if(/[a-zA-Z]/.test(e.value)){
        e.value = e.value.replace(/[a-zA-Z]/,"")
        doSetCaretPosition(e,posicion-1)
    }
    variableChange = e.value
}

function validarNumeroEspecial(e){
    //   
    
    if(e.value!=variableChange){        
        posicion = doGetCaretPosition(e)
        
        nopuntos = e.value.split(".").length
        resta = 0
        
        quitarPuntosParticular(e)
        if(e.value.length == 0){
            e.value = 0
            doSetCaretPosition(e,1)
            return false;
        }
        if(/[a-zA-Z]/.test(e.value)){
            e.value = e.value.replace(/[a-zA-Z]/,"")
            resta = 1
            colocarPuntosParticular(e)
            doSetCaretPosition(e,posicion-resta)
            return true;
        }else{
            if(e.value.length>1){
                if(e.value.split(" ").length>1){
                    e.value = e.value.replace(" ","")  
                    resta = 1
                }else{
                    if(e.value.split("-").length>1){
                        e.value = e.value.replace("-","")
                        e.value = parseFloat(e.value)
                        resta = 1
                    }else{
                        e.value = parseFloat(e.value)
                    }
                }
            }
        }
        
        colocarPuntosParticular(e)
        if(e.value.split(".").length>nopuntos){
            resta = -1
        }else{
            if(e.value.split(".").length<nopuntos){
                resta = 1
            }
        }
        
        doSetCaretPosition(e,posicion-resta)
    }else{
        return true
    }
    if(e.value.split(" ").length>1){
        e.value = e.value.replace(" ","")  
        doSetCaretPosition(e,posicion-1)
    }
    if(/[a-zA-Z]/.test(e.value)){
        e.value = e.value.replace(/[a-zA-Z]/,"")
        doSetCaretPosition(e,posicion-1)
    }
    variableChange = e.value
}

//Funci������n para validar si lo que se va a borrar es nuevo y ya ha sido grabado
function verificarId(e){
    index = $('.borrarRegistro').index(e)
    if($(".id")[index].value == 0){
        $(".id")[index].value = "x"
    }
}

//Funci������n para obetener la posici������n del mouse dentro del input
function doGetCaretPosition (oField) {

    // Initialize
    var iCaretPos = 0;

    // IE Support
    if (document.selection) { 

        // Set focus on the element
        oField.focus ();
  
        // To get cursor position, get empty selection range
        var oSel = document.selection.createRange ();
  
        // Move selection start to 0 position
        oSel.moveStart ('character', -oField.value.length);
  
        // The caret position is selection length
        iCaretPos = oSel.text.length;
    }

    // Firefox support
    else if (oField.selectionStart || oField.selectionStart == '0')
        iCaretPos = oField.selectionStart;

    // Return results
    return (iCaretPos);
}

/*
 **  Sets the caret (cursor) position of the specified text field.
 **  Valid positions are 0-oField.length.
 */
function doSetCaretPosition (oField, iCaretPos) {

    // IE Support
    if (document.selection) { 

        // Set focus on the element
        oField.focus ();
  
        // Create empty selection range
        var oSel = document.selection.createRange ();
  
        // Move selection start and end to 0 position
        oSel.moveStart ('character', -oField.value.length);
  
        // Move selection start and end to desired position
        oSel.moveStart ('character', iCaretPos);
        oSel.moveEnd ('character', 0);
        oSel.select ();
    }

    // Firefox support
    else if (oField.selectionStart || oField.selectionStart == '0') {
        oField.selectionStart = iCaretPos;
        oField.selectionEnd = iCaretPos;
        oField.focus ();
    }
}

//funcion para quitar los puntos de un input en particular
function quitarPuntosParticular(e){        
    e.value = e.value.replace(/\.*/g, "")
    e.value = e.value.replace(",",".")
}

//funcion para colocar todos los puntos de un input en particular
function colocarPuntosParticular(e){      
    //Colocar puntos del valor total del producto
    var input = e.value.split(".")[0].split("")
    var input2 = ""
    var input3 = ""
    var f = 0
    for(j=input.length-1; j>=0; j--){
        f++
        if(f%3 == 0 && j!=0){
            input2 = "." + input[j] + input2 
        }else{
            input2 = input[j] + input2 
        }
    }
    if(e.value.split(".").length>1){
        input3 = "," + e.value.split(".")[1]
    }
    e.value = input2 + input3
}

//Funci������n para colocar los puntos de la tabla       
function colocarPuntosParticularInnerhtml(e){      
    //Colocar puntos del valor total del producto
    var input = e.innerHTML.split(".")[0].split("")
    var input2 = ""
    var input3 = ""
    var f = 0
    for(j=input.length-1; j>=0; j--){
        f++
        if(f%3 == 0 && j!=0){
            input2 = "." + input[j] + input2 
        }else{
            input2 = input[j] + input2 
        }
    }
    if(e.innerHTML.split(".").length>1){
        input3 = "," + e.innerHTML.split(".")[1]
    }
    e.innerHTML = input2 + input3
}


//Validaciones Nuevas Focus quitarValor si es cero, seleccionar si no es cero (N������MEROS)
var variableNumerica
function guardarValorNumerico(e){
    if(e.value.length > 0 || e.value != ""){
        variableNumerica = e.value
    }
    if(e.value == 0){
        e.value = ""
    }else{
        $(e).select()
    }
}

function blurNumerico(e){
    if(e.value.length == 0 || e.value == null){
        e.value = variableNumerica
    }
}

///FUNCION PARA MANEJAR LAS B������SQUEDAS
var estadoBusqueda = false
var tiempoBusqueda = null
var variable2 = false

function cambiarTiempos(){
    estadoBusqueda = true
    clearInterval(tiempoBusqueda)
    validarTiempoBusqueda()
}
            
function validarTiempoBusqueda(e){
    //Reiniciamos la variable con la que validamos el filtro
    if(variable2){
        //Validamos si el valor es el mismo que ha ingresado para que no renderice entonces nada
        clearInterval(tiempoBusqueda)
        variable2 = false
        return false
    }
    if(estadoBusqueda){
        variable2 = true
        estadoBusqueda = false
        $("#ProductosVentas\\:filtroProductosVentas").keyup()
    }else{
        //Esta es la parte indicada para colocar este c������digo por que si no he cambiado ning������n valor entonces me va a devolver true,
        if(validarLetras(e)){
            return true
        }
        clearInterval(tiempoBusqueda)
        tiempoBusqueda = setTimeout("cambiarTiempos()", 500) 
        return true    
    }
    
    return true
}



var inputTiempos

function ponderId(e){
    inputTiempos = $(e)
}

function cambiarTiempos2(){
    estadoBusqueda = true
    clearInterval(tiempoBusqueda)
    validarTiempoBusqueda2()
}

function validarTiempoBusqueda2(e){
    //Reiniciamos la variable con la que validamos el filtro
    if(variable2){
        //Validamos si el valor es el mismo que ha ingresado para que no renderice entonces nada
        clearInterval(tiempoBusqueda)
        variable2 = false
        return false
    }
    if(estadoBusqueda){
        variable2 = true
        estadoBusqueda = false
        
        inputTiempos.keyup()
    }else{
        //Esta es la parte indicada para colocar este c������digo por que si no he cambiado ning������n valor entonces me va a devolver true,
        if(validarLetras(e)){
            return true
        }
        clearInterval(tiempoBusqueda)
        tiempoBusqueda = setTimeout("cambiarTiempos2()", 500) 
        return true    
    }
    
    return true
}

function validarTiempoBusquedaConTiempo(e,tiempo){
    //Reiniciamos la variable con la que validamos el filtro
    if(variable2){
        //Validamos si el valor es el mismo que ha ingresado para que no renderice entonces nada
        clearInterval(tiempoBusqueda)
        variable2 = false
        return false
    }
    if(estadoBusqueda){
        variable2 = true
        estadoBusqueda = false
        
//        inputTiempos.keyup()
    }else{
        //Esta es la parte indicada para colocar este c������digo por que si no he cambiado ning������n valor entonces me va a devolver true,
        if(validarLetras(e)){
            return true
        }
        clearInterval(tiempoBusqueda)
        tiempoBusqueda = setTimeout("cambiarTiempos2()", tiempo) 
        return true    
    }
    
    return true
}

//M������todo para limpiar inputs con el blur
function limpiarInput(e){
    $(e).val("")
}


//M������todos para colocar el cursor donde lo hab������a dejado, cuando utilizamos las teclas
//Guardamos la posici������n del cursor
//Variable para guardar la posici������n del cursor
var posicionCursor = 0
function cursorInicial(e){
    posicionCursor = doGetCaretPosition(e)
}

//Colocamos de nuevo el cursor donde es
function cursorFinal(e){
    doSetCaretPosition(e,posicionCursor)
}




///###VALIDAR LETRAS
function validarLetrasValidador(e){
   
    valor = e.value
    valor = valor.replace(/[\/n/NiIdDyYaAmMrRtTzZcC]/g,"")
    if(valor.length > 0){
        e.value = variableChange
        return true
    }

    if(e.value == variableChange){
        return true;
    }
    
    valor = e.value.replace(/[\/cC]/g,"")
    valor2 = variableChange.replace(/[\/n/cC]/g,"")
    if(valor.length == 0 && valor2.length == 0){
        e.value = "C"
        return true;
    }
    
    variableChange = e.value
    return false

}

//##Validar los click dobles
var banderaClick = true
function clickRender(){
    if(banderaClick){
        return true
    }
    
    banderaClick = true
}


//Validaciones con tiemp

var inputTiempos

function ponderId(e){
    inputTiempos = $(e)
}


function validarTiempoBusquedaConTiempo(e,tiempo){
    //Reiniciamos la variable con la que validamos el filtro
    if(variable2){
        //Validamos si el valor es el mismo que ha ingresado para que no renderice entonces nada
        clearInterval(tiempoBusqueda)
        variable2 = false
        return false
    }
    if(estadoBusqueda){
        variable2 = true
        estadoBusqueda = false
        
        inputTiempos.keyup()
    }else{
        //Esta es la parte indicada para colocar este código por que si no he cambiado ningún valor entonces me va a devolver true,
        if(validarLetras(e)){
            return true
        }
        clearInterval(tiempoBusqueda)
        tiempoBusqueda = setTimeout("cambiarTiempos2()", tiempo) 
        return true    
    }
    
    return true
}