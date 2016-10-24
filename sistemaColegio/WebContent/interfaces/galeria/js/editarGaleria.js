$(function(){
    $("#buscarCategoria\\:filtroProductos").focus()
    
    $(document).keyup(function(e){
        if(e.keyCode==27){
            $("#buscarCategoria\\:filtroProductos").focus()
        }
    })
})


function final(){
//	alert('ola')
}

///############PRODUCTOS ##############
//Método para colocar elementos para poder recorrer la lista de los Categorias
function cargarCategorias(e){
    if(e.status=="success"){
        
        var claseCategoriasube = ".subeCategorias"
        var claseProductoBaja = ".bajaCategorias"
        var seudonimoId = "_Categorias"
        var containerLista = "#contenedorCategorias"
        var claseFilas = ".rowPB"
        if($(claseFilas).size()>0){
            //Se agreg el href para que baje el scroll del div que contiene la tabla
            for(i=0; i<$(claseFilas).size()-1; i++){
                var href = "#" + ($(claseProductoBaja)[i+1].id).toString() + seudonimoId
                $($(claseProductoBaja)[i]).attr({
                    'href': href
                })
                
                if(i+1 == $(claseFilas).size()-1){
                    var hrefNueva = "#" + ($(claseProductoBaja)[0].id).toString() + seudonimoId
                    $($(claseProductoBaja)[i+1]).attr({
                        'href': hrefNueva
                    })
                }
            }
            
            //Se agreg el href para que suban el scroll del div que contiene la tabla 
            for(i=0; i<$(claseFilas).size(); i++){
                //             
                if(i!=0){
                    href = "#" + ($(claseProductoBaja)[i-1].id).toString() + seudonimoId
                    $($(claseCategoriasube)[i]).attr({
                        'href': href
                    })
                }else{
                    hrefNueva = "#" + ($(claseProductoBaja)[$(claseFilas).size()-1].id).toString() + seudonimoId
                    $($(claseCategoriasube)[i]).attr({
                        'href': hrefNueva
                    })
                }
            }
            
            //Añadimos el trigger para que cargue los links
            $(containerLista).localScroll({
                duration: 300,
                target:containerLista
            });
        }
    
        
    }
}



//Método para recorrer la lista de búsqueda de las categorias
function recorrerListaBusquedaCategorias(keyCode){
    //Función para recorrer la tabla de 
    
    clase = ".rowPB"
    claseSeleccionado = ".selected1"
    popUP = "#contenedorCategorias";
    index = $(clase).index($(claseSeleccionado)[0])
    
    
    claseSubida = ".subeCategorias"
    claseBajada = ".bajaCategorias"
    
    
    //Si se da enter
    if(keyCode==13){
        if($(claseSeleccionado).size()>0){
            if($(clase).size() > 0){
                //Damos click sobre el elemento seleccionado
                index = $(clase).index($(claseSeleccionado)[0])
                $($(".referenciaCategoria")[index]).mousedown()
            }else{
                
                $(claseSeleccionado).click()
            }
        }else{
            if($(".categoriaNueva").size()>0){
                $("#buscarCategoria\\:labelCategoriaNueva").click()
            }
        }
    }
    
    
    
    //Si se sube
    if(keyCode==38){
        if($(".rowPB").size() == 0){
            $("#buscarCategoria\\:divCategoriaNueva").addClass("categoriaNueva")
            $("#buscarCategoria\\:divCategoriaNueva").css({
                "background-color":"yellow", 
                "font-weight":"bolder"
            });
        }else{
            if($(claseSeleccionado).size()==0){
                $($(clase)[$(clase).size()-1]).addClass(claseSeleccionado.split(".")[1])
            }else{
                $(claseSeleccionado).removeClass(claseSeleccionado.split(".")[1])
                if((index-1)==-1){
                    $($(clase)[$(clase).size()-1]).addClass(claseSeleccionado.split(".")[1])
                }else{
                    $($(clase)[index-1]).addClass(claseSeleccionado.split(".")[1])   
                }
            }
        
        
            if(Math.abs($(popUP).scrollTop() - $(claseSeleccionado).position().top) > 1
                && Math.abs($(popUP).scrollTop() - $(claseSeleccionado).position().top) < 80
                && $(popUP).scrollTop() < $(claseSeleccionado).position().top){
            }else{
                $($(claseSubida)[index]).click()
            }
        }
    
    }
    
    
    //Si se baja
    if(keyCode==40){
        if($(clase).size()==0){
            $("#buscarCategoria\\:divCategoriaNueva").addClass("categoriaNueva")
            $("#buscarCategoria\\:divCategoriaNueva").css({
                "background-color":"yellow", 
                "font-weight":"bolder"
            });
        }else{
            if($(claseSeleccionado).size()==0){
                $($(clase)[0]).addClass(claseSeleccionado.split(".")[1])
            }else{
                $(claseSeleccionado).removeClass(claseSeleccionado.split(".")[1])
                if((index+1)==$(clase).size()){
                    $($(clase)[0]).addClass(claseSeleccionado.split(".")[1])   
                }else{
                    $($(clase)[index+1]).addClass(claseSeleccionado.split(".")[1])   
                }
            }
            if(($(claseSeleccionado).position().top) > 206 || index == $(clase).size()-1){
                $($(claseBajada)[index]).click()
            }
        }
    }
}


//Función para después de haber cargado una categoría
function despuesSeleccionarCategoria(e){
    if(e.status=="success"){
        claseSeleccionado = "selected1"
        $(".selected1").removeClass(claseSeleccionado)
        
        
        $(e.source).parent().parent().addClass(claseSeleccionado)
        
        $("#Categorias\\:inputCategoria").focus()
        
        
    }
}



//FINALIZAR LA ACTUALIZACIÓN DE LA CATEGORÍA
var banderaErrorGuardarProducto = false

function errorEliminarCategoria(e){
    banderaErrorGuardarProducto = true
    alert(e.errorMessage)
}


function despuesActualizarCatogoria(e){
    if(e.status=="success"){
        if(!banderaErrorGuardarProducto){
            //Removemos cualquier otro mensaje de alerta que haya
            $("#mensajeAlerta").remove()
            //Código que contiene el div para las alertas
            var div = "<div class='fieldSetUniversal labelSinResaltar' style='position:fixed; background: #F9EDBE; border:solid 1px #F0C36D; -webkit-border-radius: 6px;top:0; left:37%;' id='mensajeAlerta'>La categor&iacute;a se guard&oacute; satisfactoriamente</div>"
            $("#Categorias").append(div)
            desaparecer()
        }else{
            banderaErrorGuardarProducto = false
        }
    }
}


function ocultar(){
    $("#mensajeAlerta").fadeOut(500)
}
            
function desaparecer(){
    intevaloMensaje = setTimeout("ocultar()", 3000) 
}