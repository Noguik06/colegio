$(function() {
	cuadrarScroll(".colMisNotasHeader", ".colMisNotasBody")
	cuadrarScrollRepeat(".misNotasPeriodosHeader", ".misNotasPeriodosBody")
	cuadrarScrollRepeat(".misNotasRecuperacionesHeader", ".misNotasRecuperacionesBody")
})

$(window).resize(function() {
	cuadrarScroll(".colMisNotasHeader", ".colMisNotasBody")
	cuadrarScrollRepeat(".misNotasPeriodosHeader", ".misNotasPeriodosBody")
});

// método para mostrar la tabla que tiene las dimensiones
function mostrarTabla(e, label, tabla) {
	indice = $("." + e.className).index(e)

	$($("." + e.className)[indice]).hide()
	$($("." + label)[indice]).show()
	$($("." + tabla)[indice]).show()

	if (tabla == "tablaNotas") {
		$($(".filasDimensiones")[indice]).addClass("borderInferior")
	}

	// alert($($(".filaAsignaturas")[indice]).height())

}

function esconderTabla(e, label, tabla) {
	indice = $("." + e.className).index(e)

	$($("." + e.className)[indice]).hide()
	$($("." + label)[indice]).show()
	$($("." + tabla)[indice]).hide()

	if (tabla == "tablaNotas") {
		$($(".filasDimensiones")[indice]).removeClass("borderInferior")
	}
}

// Metodo para seleccionar el tipo de informe

function mostrarInformeResumido(e) {
	$(".botonAsignaturaSeleccionada").removeClass("botonSeleccionadoVerde")
	$(".botonAsignaturaSeleccionada")
			.removeClass("botonAsignaturaSeleccionada")

	$(e).addClass("botonSeleccionadoVerde")
	$(e).addClass("botonAsignaturaSeleccionada")

	$("#notasPrincipal\\:informeDetallado").hide()
	$("#notasPrincipal\\:informeResumido").show()

}

function mostrarInformeDetallado(e) {
	$(".botonAsignaturaSeleccionada").removeClass("botonSeleccionadoVerde")
	$(".botonAsignaturaSeleccionada")
			.removeClass("botonAsignaturaSeleccionada")

	$(e).addClass("botonSeleccionadoVerde")
	$(e).addClass("botonAsignaturaSeleccionada")

	$("#notasPrincipal\\:informeDetallado").show()
	$("#notasPrincipal\\:informeResumido").hide()

}