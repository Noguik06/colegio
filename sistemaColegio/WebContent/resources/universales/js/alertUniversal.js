function onClickMostrarAlert(mensaje){
    $("body").append("<div class = 'claseDivPantallaAlert'></div>")
    $(".claseDivPantallaAlert").append("<div class = 'claseDivInternoAlertUniversal claseSombrasPopUpUniversal' style = 'background: white; padding: 15px'> \n\
                                            <div style = 'display: block'>\n\
                                                 <div style = 'display: block;font-size: 20px; height: 30px;'><img src = '/sistemaRepuestos/resources/imagenes/ricardoNuevas/alerta32.png' style = 'float: left;'/><center><labelstyle = 'display: block;'>"+mensaje+"</label></center></div>\n\
                                                       <div style = 'margin-top: 20px;'><label class = 'botonVerde botonesFont' onclick = 'onClickLabelCerraAlertUniversal()' >aceptar</label><div>\n\
                                                 \n\
                                            </div>\n\
                                        </div>")
}

function onClickLabelCerraAlertUniversal(){
    $(".claseDivPantallaAlert").remove()
}