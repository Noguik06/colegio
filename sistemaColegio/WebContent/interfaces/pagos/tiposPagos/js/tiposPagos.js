
//colTiposPagosHader-1

$(function(){
    cuadrarScroll(".colTiposPagosHader", ".colTiposPagosBody")
//    alert('ola')
})

//Onevent despues de haber hecho la búsqueda
function despuesBuscar(e){
    if(e.status=="success"){
        cuadrarScroll(".colTiposPagosHader", ".colTiposPagosBody")
    }
}


//Onevenet despues de haber editado o creado un tipo de pago
function despuesEdicion(e){
    if(e.status=="success"){
        cuadrarScroll(".colTiposPagosHader", ".colTiposPagosBody")
        
        if($(".errorMensaje").size()>0){
            alert($(".errorMensaje td")[0].innerHTML)
        }
        
        if($(".exitoMensaje").size()>0){
            alert($(".exitoMensaje td")[0].innerHTML)
        }
    }
}