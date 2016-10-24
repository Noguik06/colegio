function inputCostoReal(viene){//funcion se activa al hacer click, elimina los signos pesos ($) y los tres últimos letras (.00)    
    if($(viene).val().match(/,/) != null && $(viene).val().match(/\$/) != null){
        $(viene).val($(viene).val().replace( /.{3}$/, ""))
        $(viene).val($(viene).val().replace( /^\$/, ""))
    }    
    onFocusInputTextValor(viene)
}

function onFocusInputTextValor(viene){
    if($(viene).val().length == 1 && $(viene).val() == "0"){//TRUE = El el número que se encuentra es el "0" pasamos a eliminarlo
        $(viene).val("")
    }
}

function onBlurInputTextValue(viene){//Evento que se acciona al salir del inputText y poner el número "0"  en el caso que no haya encontrado valor alguno
    if($(viene).val().length == 0){
        $(viene).val("0")
    }
}

var vieneCostoCompara = "";
function inputKeyPress(viene){
    posicion = doGetCaretPosition(viene)
    var banderaIngresoDatoNoPErmitido = false;
    if(viene.value != vieneCostoCompara){
        $(viene).val($(viene).val().replace(/\.*/g, ""))//busca todos los "." (puntos) y los elimina
        if($(viene).val().match(/^([0-9])*$/) != null ){//si en el input se encuentra sólo números ingresa al if para poner puntos
            if($(viene).val().length > 3){//separa los numeros en un vector
                puntosInput(viene)
            }
        }else{
            alert("Dato no permitido, Ingrese s\xf3lo n\xfAmeros.")
            $(viene).val($(viene).val().match(/\d*/g))
            $(viene).val($(viene).val().replace(/\,*/g, ""))
            banderaIngresoDatoNoPErmitido = true;
            puntosInput(viene)
        }
        if(vieneCostoCompara.length > 0){
            //            alert(viene.value + " --!-- " + vieneCostoCompara + " tamaños: " + viene.value.length + " - " + vieneCostoCompara.length + " posicion: " + posicion)
            var resultadoTEMP = viene.value.length - vieneCostoCompara.length                        
            if(resultadoTEMP == -2 || banderaIngresoDatoNoPErmitido){
                posicion = posicion - 1
            }        
            if(resultadoTEMP == 2){
                posicion = posicion + 1;
            }
        }                
        vieneCostoCompara = viene.value
        doSetCaretPosition(viene,posicion)
    }
}

function puntosDouble(viene){            
    var vectorDouble = viene.toString().split(".")
    var vectorDoubleParteInt = puntosInputValorINT(vectorDouble[0])                    
    if(vectorDouble.length == 2){             
        return (vectorDoubleParteInt + "," + vectorDouble[1])    
    }else{
        return (vectorDoubleParteInt)
    }
}

//Funcion que retorna la parte entera con puntos miles, para separar los enteros de los decimales se tiene encuenta la coma ",""
//Retorna el número enviado pero con la parte entera procesada por los puntos miles
function puntosDoubleConComa(vieneValue){
    var vectorDatoVienePorComa = vieneValue.split(",")
    var datoParteEnteraProcesada = puntosInputValorINT(vectorDatoVienePorComa[0])
    if(vectorDatoVienePorComa.length == 2){
        return (datoParteEnteraProcesada + "," + vectorDatoVienePorComa[1])
    }else{
        return datoParteEnteraProcesada;
    }
}

function puntosInput(viene){//Funcion para asignar los puntos (miles) para el input costo Real
    var vectInput = $(viene).val().split("")
    var f = 0
    var nuevoInput = ""
    for(var i= vectInput.length - 1; i >= 0 ; i--){
        f++;        
        if(f%3 == 0 && vectInput[i - 1] != undefined){//si va en la tercer valor y el siguiente valor es diferente de "undefined"
            nuevoInput += vectInput[i] +".";
        }else{
            nuevoInput += vectInput[i]
        }
    }
    var tempVect = nuevoInput.split("")
    var finalInput = "";
    for(i= nuevoInput.length - 1; i>=0 ; i--){
        finalInput += tempVect[i]
    }
    $(viene).val(finalInput)    
    return finalInput;
}




function puntosInputValorINT(viene){//Funcion para asignar los puntos (miles) para el input costo Real //PARAMETRO: Integer        
    var vectInput = viene.toString().split("")        
    var f = 0
    var nuevoInput = ""
    for(var i= vectInput.length - 1; i >= 0 ; i--){
        f++;        
        if(f%3 == 0 && vectInput[i - 1] != undefined){//si va en la tercer valor y el siguiente valor es diferente de "undefined"
            nuevoInput += vectInput[i] +".";
        }else{
            nuevoInput += vectInput[i]
        }
    }
    var tempVect = nuevoInput.split("")
    var finalInput = "";
    for(i= nuevoInput.length - 1; i>=0 ; i--){
        finalInput += tempVect[i]
    }        
    return finalInput;
}


//NUEVAS: REFERENTES PARA OTENER EL FOCUS.--

//Función para obetener la posición del mouse dentro del input
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


//NUEVAS FUNCIONES REFERENTES A LOS INPUT TEXT -¡@- DOUBLES -!@-
function onKeyUpInputDOUBLE(viene){
    posicion = doGetCaretPosition(viene)
    var banderaIngresoDatoNoPErmitido = false;    
    $(viene).val($(viene).val().replace(/\.*/g, ""))//busca todos los "." (puntos) y los elimina        
    var vectorDatosInput = $(viene).val().split(","); 
    if(viene.value != vieneCostoCompara){                               
        if(vectorDatosInput.length <= 2){//Validamos que sólo ingresen una coma
            var banderaDecimales = true;            
            if(vectorDatosInput.length == 2){
                if(vectorDatosInput[1].match(/^([0-9])*$/) == null && vectorDatosInput[1] != ""){
                    banderaDecimales = false;
                }
            }            
            if(vectorDatosInput[0].match(/^([0-9])*$/) != null && banderaDecimales){//si en el input se encuentra sólo números ingresa al if para poner puntos            
                if(vectorDatosInput[0].length > 3){//separa los numeros en un vector
                    if(vectorDatosInput.length == 2){
                        $(viene).val(puntosInputValorINT(vectorDatosInput[0]) + "," + vectorDatosInput[1])
                    }
                    if(vectorDatosInput.length == 1){
                        $(viene).val(puntosInputValorINT(vectorDatosInput[0]));
                    }
                }
            }else{
                alert("Dato no permitido, Ingrese s\xf3lo n\xfAmeros.h")
                if(vectorDatosInput.length  == 2){
                    var datosEnteros = vectorDatosInput[0].match(/\d*/g).toString();
                    datosEnteros = datosEnteros.replace(/\,*/g, "")
                    datosEnteros = puntosInputValorINT(datosEnteros)
                    $(viene).val(datosEnteros + "," + vectorDatosInput[1])                    
                }   
                if(vectorDatosInput.length  == 1){
                    $(viene).val($(viene).val().match(/\d*/g))
                    $(viene).val($(viene).val().replace(/\,*/g, ""))                
                    puntosInput(viene)
                }
                banderaIngresoDatoNoPErmitido = true;                
            }
        }                
        if(vieneCostoCompara.length > 0){
            var resultadoTEMP = viene.value.length - vieneCostoCompara.length                        
            if(resultadoTEMP == -2 || banderaIngresoDatoNoPErmitido){
                posicion = posicion - 1
            }        
            if(resultadoTEMP == 2){
                posicion = posicion + 1;
            }
        }                
        vieneCostoCompara = viene.value
        doSetCaretPosition(viene,posicion)
    }    
}

var banderaMaximoCaracteres = true;
function validaMinimoYMaximoInput(maximo, vieneObjeto){//Método para validar el maximo de caracteres de un input text: universal
    if(vieneObjeto.value.length >maximo){
        //        $(vieneObjeto).val(vieneObjeto.value.replace(/.$/, ""))
        if(banderaMaximoCaracteres){
            $(vieneObjeto).after("<span class = 'claseMensajeMaximoVal errorMesageCP errorMesageCPAux' style = 'color: red'>M\xE1ximo " + maximo + " caracteres</span>")        
            banderaMaximoCaracteres = false;
        }        
    }else{         
        if(!banderaMaximoCaracteres ){
            banderaMaximoCaracteres = true;
            $(vieneObjeto).parent().find(".claseMensajeMaximoVal").remove()
        }
    }    
}

//NUEVAS FUNCIONES 19 de MAYO del 2012

function onBlurInputTextRevisaCantCero(viene){
    if($(viene).val().length == 0){
        $(viene).val("0")
        $(viene).removeClass("clasePruebaErrorInputText")
    }
}

var valueAnteriorViene = null;
function onKeyUoInputTextRevisarCantCero(viene){    
    if(viene.value != valueAnteriorViene){
        posicion = doGetCaretPosition(viene)    
        if(viene.value.length == 0){//TRUE = No vienen datos pasamos a gregar el cero por default
            $(viene).val("0")
            posicion = posicion + 1;
        }else{
            metodoRevisaCerosInicio(viene);//Pasamos a eliminar los ceros a la izquierda    
            //Primero: validamos que se haya ingresaro números            
            if($(viene).val().match(/^([0-9])*$/) != null){//TRUE = El value ingresado es sólo números
        
            }else{//ELSE = Se ingreso un caracter diferente a números
                //SEGUNDO: Pasamos a revisar si el caracter ingresado corresponde a un "punto (.)"                
                if($(viene).val().search(/\./) != -1){//TRUE = El caracter ingresado es un punto
                    //Ahora pasamos a revisar si es el primer punto ingresado o ya existe otro
                    //: Pasamos a revisar cuantas como existen en el string; para poder determinar si es la primera o segunda cóma que se quiere ingresar
                    if($(viene).val().search(/\,/) != -1){//TRUE = Ya existe un "punto y coma (;)"
                        $(viene).val($(viene).val().replace(/\.*/g, ""))
                        posicion = posicion -1;
                    }else{//ELSE = No existe punto y coma
                        metodoCambiaPuntoPorComa(viene); //Pasamos a intercambiar el punto por la coma
                    }
                }else{                    
                    $(viene).val($(viene).val().match(/\d*/g))
                    $(viene).val($(viene).val().replace(/\,*/g, ""))
                    posicion = posicion -1;
                }
            }            
        }
        doSetCaretPosition(viene,posicion)   
        valueAnteriorViene = viene.value;
    }
}

function metodoRevisaCerosInicio(viene){
    $(viene).val($(viene).val().replace(/^0*/, ""))
}

function metodoCambiaPuntoPorComa(viene){
    $(viene).val($(viene).val().replace(/\./, ","))
}

function onFocusInputTExtPorcentajeGlobal(viene){
//    vieneCostoCompara2 = $(viene).val()
}

var vieneCostoCompara2 = "";
//FUNCIÓN QUE SE ACCIONA EN EL INPUTTEXT DEL PORCENTAJE GLOBAL UBICADO EN EL HEAD
function onKeyUpInputPorcentajeGlobal(viene){    
    if(viene.value == ""){
        $(viene).val("0")
    }else{
        //        $(viene).val($(viene).val().replace(/^0*/, ""))            
        if($(viene).val().match(/^0(?=.)/) != null){                
            $(viene).val($(viene).val().replace(/^0(?=.)/, ""))                
        }        
    }
    if(viene.value != vieneCostoCompara2){        
        posicion2 = doGetCaretPosition(viene)//Obtenemos la posición del cursor    
        if(event.keyCode == 110){//TRUE = Se ingreso un punto "." y procedemos a cambiarlo por una coma ",".        
            posicion2 = onKeyCodePunto(viene, posicion2)        
        }    
        var banderaIngresoDatoNoPErmitido = false;    
            
        var vectorTemp = viene.value.split(",")        
        var banderaDosDecimales = true
        var numerosFixeDinamico = 2;        
        if(vectorTemp.length == 2){
            if(vectorTemp[1].length > 1){
                numerosFixeDinamico = vectorTemp[1].length                
            }
        }else{            
            if(vectorTemp.length > 2){                     
                $(viene).val(functionRevisaVacioYSignoNegativo(vieneCostoCompara2).replace(".", ","))                
                posicion2 = posicion2 -1 ;
                banderaDosDecimales = false;
                $(viene).val(puntosDoubleConComa(viene.value))
            }
        }
        
        if(banderaDosDecimales){                       
            var porcentajeViene = functionRevisaVacioYSignoNegativo(viene.value);            
            if(porcentajeViene > -100){              
                //Retornamos el porcentaje a el respectivo input porcentaje global                                
                if(viene.value != "-" && viene.value != ""){
                    $(viene).val(puntosDouble(porcentajeViene))                
                }
            }else{                
                reparaDatoNoPermitido(viene, posicion2)                
                banderaIngresoDatoNoPErmitido = true;     
            }
        }
        
        if(vieneCostoCompara2.length > 0){
            var resultadoTEMP2 = viene.value.length - vieneCostoCompara2.length
            if(resultadoTEMP2 == -2 || banderaIngresoDatoNoPErmitido){
                posicion2 = posicion2 - 1
            }        
            if(resultadoTEMP2 == 2){
                posicion2 = posicion2 + 1;
            }
            if(vieneCostoCompara2.indexOf(",") != -1 && resultadoTEMP2 == 0){
                if(viene.value.indexOf(",") == -1){                     
                    posicion2 = posicion2 + 1;   
                }            
            }
        }        
        doSetCaretPosition(viene,posicion2)  
    }
    vieneCostoCompara2 = viene.value              
}

//FUNCIÓN QUE SE ACCIONA EN EL INPUTTEXT DEL PORCENTAJE GLOBAL UBICADO EN EL HEAD
function onKeyUpInputPorcentajeGlobalPEDIDONUEVO(viene){    
    if(viene.value == ""){
    //        $(viene).val("0")
    }else{
        //        $(viene).val($(viene).val().replace(/^0*/, ""))            
        if($(viene).val().match(/^0(?=.)/) != null){                
            $(viene).val($(viene).val().replace(/^0(?=.)/, ""))                
        }        
    }
    if(viene.value != vieneCostoCompara2){        
        posicion2 = doGetCaretPosition(viene)//Obtenemos la posición del cursor    
        if(event.keyCode == 110){//TRUE = Se ingreso un punto "." y procedemos a cambiarlo por una coma ",".        
            posicion2 = onKeyCodePunto(viene, posicion2)        
        }    
        var banderaIngresoDatoNoPErmitido = false;    
            
        var vectorTemp = viene.value.split(",")        
        var banderaDosDecimales = true
        var numerosFixeDinamico = 2;        
        if(vectorTemp.length == 2){
            if(vectorTemp[1].length > 1){
                numerosFixeDinamico = vectorTemp[1].length                
            }
        }else{            
            if(vectorTemp.length > 2){                     
                $(viene).val(functionRevisaVacioYSignoNegativo(vieneCostoCompara2).replace(".", ","))                
                posicion2 = posicion2 -1 ;
                banderaDosDecimales = false;
                $(viene).val(puntosDoubleConComa(viene.value))
            }
        }
        
        if(banderaDosDecimales){                       
            var porcentajeViene = functionRevisaVacioYSignoNegativo(viene.value);            
            if(porcentajeViene > -100){              
                //Retornamos el porcentaje a el respectivo input porcentaje global                                
                if(viene.value != "-" && viene.value != ""){
                    $(viene).val(puntosDouble(porcentajeViene))                
                }
            }else{                
                reparaDatoNoPermitido(viene, posicion2)                
                banderaIngresoDatoNoPErmitido = true;     
            }
        }
        
        if(vieneCostoCompara2.length > 0){
            var resultadoTEMP2 = viene.value.length - vieneCostoCompara2.length
            if(resultadoTEMP2 == -2 || banderaIngresoDatoNoPErmitido){
                posicion2 = posicion2 - 1
            }        
            if(resultadoTEMP2 == 2){
                posicion2 = posicion2 + 1;
            }
            if(vieneCostoCompara2.indexOf(",") != -1 && resultadoTEMP2 == 0){
                if(viene.value.indexOf(",") == -1){                     
                    posicion2 = posicion2 + 1;   
                }            
            }
        }        
        doSetCaretPosition(viene,posicion2)  
    }
    vieneCostoCompara2 = viene.value              
}

function functionRevisaVacioYSignoNegativo(vieneValue){//Funcion universal para validar             
    var vectorPorcentajeVieneSPLITE = vieneValue.toString().split(",");    
    var valueTempPorcentajeAcutal = vectorPorcentajeVieneSPLITE[0].replace(/\.*/g, "");//A la parte Entera le eliminamos los puntos para poder realizar operaciones en javascript        
    if(vectorPorcentajeVieneSPLITE.length == 2){//TRUE = El valor que viene Double (Tiene decimales)
        valueTempPorcentajeAcutal += "." + vectorPorcentajeVieneSPLITE[1];                        
    }
    if(valueTempPorcentajeAcutal == "" || valueTempPorcentajeAcutal == "-"){
        return 0;
    }else{        
        return valueTempPorcentajeAcutal;
    }
}

//FUNCIONES PARA EL INPUT PORCENTA 
function onKeyCodePunto(viene, posicion){
    var subString = viene.value.substring(posicion - 1).replace(".","")    
    if(subString == ""){//True el punto se ingresó en el último dígito de la palabra            
        $(viene).val(viene.value.substr(0 , posicion -1) + ",")
    }else{//De lo contrario cambiamos el punto recien ingresado por la respectiva coma
        $(viene).val(viene.value.substr(0 , posicion -1) + "," + subString)
    }
    var stringFinalTest = puntosInputValorINT(viene.value.substr(0 , posicion -1).replace(/\.*/g,"")) + "," + subString
    if(posicion > (stringFinalTest.indexOf(",") + 1) && stringFinalTest.split(",").length <= 2){
        return posicion -1;
    }else{
        return posicion;
    }        
}

function reparaDatoNoPermitido(viene, posicion){
    posicion = parseFloat(posicion)
    var vectorDatoAReparar = viene.value.split("")
    var valorReparado = "";
    for(var i = 0; i < vectorDatoAReparar.length; i ++){
        if(i != (posicion -1)){
            valorReparado += vectorDatoAReparar[i]
        }
    }                
    $(viene).val(valorReparado)
}
