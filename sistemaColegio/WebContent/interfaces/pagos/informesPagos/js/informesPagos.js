$(function(){
    agregarDatePicker()
    cuadrarScroll(".colUsuariosInformesHader", ".colUsuariosInformesBody")
})


//Método para colocar el calendario en los input's de las fechas
function agregarDatePicker(){
     //Agregar a input's de inicio y fin
        $(".fecha").datepicker({
            changeMonth: true,
            changeYear: true,
            showOn: "button",
            dateFormat: 'yy-mm-dd',
            buttonImage: "/sistemaColegio/resources/imagenes/calendar.gif",
            buttonImageOnly: true,
            yearRange: '1950:2020'
        })
}

//Método para cargar el los calendarios en los input de las fechas
function despuesGenerarInforme(e){
    agregarDatePicker()
}