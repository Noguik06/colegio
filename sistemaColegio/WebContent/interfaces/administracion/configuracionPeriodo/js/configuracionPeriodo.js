function handleComplete(xhr, status, args){
	/*alert('ola')
    var w = window.open();
    w.document.open();
    alert(xhr.responseText.substring(100));
    alert(status);
    alert(args.source);
    alert('olita')
    var d = '<head></head><body>'+ args.firstParam +'<body>';
    w.document.write(d);
    w.document.close();*/
}


function prueba(event) {
	   // "event.target.id" is the id of the inputText
	   console.log(event.target.id)
}

//Seleccionar ano academico
function seleccionarAnoAcademico(event){
        
        var boton = "botonAnoAcademicoSeleccionado"
        $("." + boton).removeClass("botonSeleccionadoVerde")
        $("." + boton).removeClass(boton)
        
        $(event.target).addClass("botonSeleccionadoVerde")
        $(event.target).addClass(boton)
    
}


//Seleccionar materia
function seleccionarMateria(event){
        
        var boton = "botonMateriaSeleccionada"
        $("." + boton).removeClass("botonSeleccionadoVerde")
        $("." + boton).removeClass(boton)
        
        $(event.target).addClass("botonSeleccionadoVerde")
        $(event.target).addClass(boton)
    
}