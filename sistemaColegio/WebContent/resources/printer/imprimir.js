/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

//Funcion que me va a agregando el texto tanto al input hidden 
function agregarTexto(e){
    if(e.status=="success"){    
        var tmp = "";
        var linea = 0;
        var div = 0
        var size = 0
        var bandera = false
        
        if($("#textPrint\\:textArea").val().length!=0){
            size = $("#textPrint\\:textArea").val().split("%cambio%").length
        }
        //        
        //    
        vector = $("#textPrint\\:textArea").val().split("%cambio%")
        var indice = 0
        
        if(size>0){
            if(vector[0].split("&").length>1){
                if(vector[0].split("&")[1]=="referencia"){
                    $($("#textPrintDiv").append("<div class='descripcion'/><br/>"))
                    $(".descripcion")[0].innerHTML ="<span style='font-size:15px; font-weight:bold;'>"+ vector[0].split("&")[0] + "</span>"+"<br/>";
                    indice++
                    linea++
                }else{
                    if(vector[0].split("&")[1]=="categoria"){
                        $($("#textPrintDiv").append("<div class='descripcion'/><br/>"))
                        $(".descripcion")[0].innerHTML ="<span style='font-size:10px;'>"+ vector[0].split("&")[0] + "</span>"+"<br/>";
                        indice++
                        linea++
                    } 
                }
            //        
            }
        }
        
        if(size>1){
            if(vector[1].split("&").length>1){
                if(vector[1].split("&")[1]=="categoria"){
                    $(".descripcion")[0].innerHTML +="<span style='font-size:11px;'>"+ vector[1].split("&")[0] + "</span>"+"<br/>";
                    indice++
                    linea++
                } 
            }
        }
        for(i=indice; i<size; i++){
            
            if($(".descripcion")[div]==null){
                $($("#textPrintDiv").append("<div class='descripcion'/><br/>"))
            }
            
            if(linea == 6){
                $(".descripcion")[div].innerHTML += tmp;
                tmp = ""
                linea = 0
                div++
            }else{
                if(i+1 == size){
                    $(".descripcion")[div].innerHTML += tmp;
                }
            }
           
            tmp += vector[i] + "<br/>"
            linea ++    
        }
    }
}


