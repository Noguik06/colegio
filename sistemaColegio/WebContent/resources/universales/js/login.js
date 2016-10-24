$(function(){
    $("#formularioContenedorLoguear\\:usuario").focus()
})


var banderaErrorLogin = true
//Función para después de haberse logueado
function despuesLoguearse(e){
    if(e.status=="success"){
        if(banderaErrorLogin){
            $("#ProductosVentas\\:filtroProductosVentas").focus()
        }else{
            banderaErrorLogin = true
        }
    }   
}   

//Fución para manejar los errores
function errorLoguear(e){
    alert(e.errorMessage)
    banderaErrorLogin = false
}