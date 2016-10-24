//@Funci������n que se acciona con un click sobre el respectivo label; para cambiarlo por un inputText
var valorUniverInicio;
function onclickLabelTOInputText(viene) {
	$(viene).hide()
	$(viene).parent().find(".inputTextCostoConPorcentaje").show()
	$(viene).parent().find(".claseInputOcultoMostrarUniv").show()
	$(viene).parent().find(".inputTextCostoConPorcentaje").focus()
	$(viene).parent().find(".claseInputOcultoMostrarUniv").focus()
	// $("#form_FacturaReal\\:idBotonGuardaEdicionFactura").show()//Boton para
	// el div "Factura Pedido": Boton Guardar
}

function onBlurInputTextTOLabel(viene) {
	$(viene).parent().find("label.classLabelMostrarInputText").show()
	$(viene).parent().parent().find("label.classLabelMostrarInputText").show()
	$(viene).hide()
	// $("#form_FacturaReal\\:idBotonGuardaEdicionFactura").hide()
}

function onKeyUpHideLabel(viene) {
	valorUniverInicio = viene;
	$(viene).parent().parent().find("label.classLabelMostrarInputText").hide()
}

function onevenKeyUpHideLabel(data) {
	if (data.status == "success") {
		$(valorUniverInicio).parent().parent().find(
				"label.classLabelMostrarInputText").hide()
	}
}

// FUNCIONES PARA LOS INPUT TEXT REFERENTES A LOS PORCENTAJES: DOUBLES
function onFocusInputTextPorcentajeDouble(viene) {// Funci������n para
													// eliminar los ceros
													// despu������s de el "."
	if ($(viene).val() == "0") {// Si no se ha iniciado el porcentaje =>
								// procedemos a limpiarlo para que ingresen
								// datos
		$(viene).val("")
	}
}

function onBlurInputTextPorcentajeDouble(viene) {
	if ($(viene).val().length == 0) {
		$(viene).val("0")
	}
}

// FUNCIONES REFERENTES AL POPUP QUE MUESTRA LA DESCRIPCION DEL PRODUCTO

var objetoGlobalDescripcionProducto;
function onclickLabelCategoriaProducto(viene) {
	objetoGlobalDescripcionProducto = viene;
	var leftCategoria = $(viene).parent().position().left // Captur������ el
															// lef del td que
															// contiene la
															// categor������a
	var topCategoria = $(viene).parent().position().top // Captur������ el Top
														// del TD que contien la
														// categor������a del
														// productoPedido
														// seleccionadp
	$(viene).parent().find("div.claseDivFlotanteCategoria").addClass(
			"divAumentoDescripcionProducto")
	$(viene).parent().find("div.claseDivFlotanteCategoria").addClass(
			"divHechosPopUp")
	$(viene).parent().find("div.claseDivFlotanteCategoria").delay(50).fadeIn()
	$(viene).parent().find("div.claseDivFlotanteCategoria").css(
			"left",
			leftCategoria
					- (($(".divAumentoDescripcionProducto").outerWidth() - $(
							viene).parent().outerWidth()) / 2))
	$(viene).parent().find("div.claseDivFlotanteCategoria").css(
			"top",
			topCategoria
					- (($(".divAumentoDescripcionProducto").outerHeight() - $(
							viene).parent().outerHeight()) / 2))
	$(viene).parent().parent().find("td.selectTemp").addClass(
			"estiloBordeTdDescripcion")
	banderaEscDescripcionProducto = true;
	$(".divAumentoDescripcionProducto").mouseleave(
			function() {
				$(this).delay(100).fadeOut()
				$(objetoGlobalDescripcionProducto).parent().parent().find(
						"td.selectTemp")
						.removeClass("estiloBordeTdDescripcion")
				banderaEscDescripcionProducto = false;
			})
}

function ocultarUniversal() {
	$(idClaseElementoGlobal).fadeOut(500)
}

var idClaseElementoGlobal;
function funcionGestionaMensajeFlotanteSuperiorBordeAmarillo(idClaseElemento,
		clasetituloAlert, mensajeTitulo, clasecontenidoAlert, mensajeContenido,
		time) {
	idClaseElementoGlobal = idClaseElemento;
	$(clasetituloAlert).html("")
	$(clasecontenidoAlert).html("")
	$(clasetituloAlert).append(mensajeTitulo)
	$(clasecontenidoAlert).append(mensajeContenido)
	$(idClaseElemento).fadeIn(100)
	setTimeout("ocultarUniversal()", parseInt(time))
}

function metodoOcultarElmentoFadeOut() {
	$(idClaseElementoOcultarFadeOut).fadeOut(500)
}

var idClaseElementoOcultarFadeOut;
function metodoRetrasarYocultarElemento(idClaseElemento, time) {
	idClaseElementoOcultarFadeOut = idClaseElemento;
	setTimeout("metodoOcultarElmentoFadeOut()", parseInt(time))
}

function onFocusSelect(viene) {
	$(viene).select()
}

// NUEVAS

function onClickFilaTablaUniversal(viene, code, Stringid) {
	if (code == 13) {// TRUE = enter
		$(Stringid).first().click()
	}
}

function onKeyUpEnterMouseDownBotonesUniversal(viene, codigo) {
	if (codigo == 13) {
		$(viene).mousedown()
	}
}

function onEventLabelCerrarSesion(data) {
	if (data.status == "success") {
		javascript: location.reload();
	}
}

function onKeyUpKeyCodeEscapeProductoNuevo(viene) {
	if (viene == 27) {
		$(".claseTablasPopUpProductoRapidoPedido").hide()
	}
}

function onKeyUpInputTextClickLabelUnivesal(viene, eventoCode, rutaIdLabelClick) {
	if (eventoCode == 13) {
		$(rutaIdLabelClick).first().mousedown()
	}
}

function onClickDivPantallaUniversal() {
	$(".claseDivPantallaUniversal").hide()
	$(".claseContenedorHideByDivPantallaUniversal").hide()
}

// Metodo para cuadrar los scroll
function cuadrarScroll(claseHeader, claseBody) {
	for (i = 0; i < $(claseHeader).size(); i++) {
		widthCelda = $($($(claseHeader)[i])[0]).width()
		$($($(claseBody)[i])[0]).width(widthCelda)
		$($($(claseBody)[i])[0]).css({
			"width" : widthCelda,
			"min-width" : widthCelda
		})
	}
}

function cuadrarScrollRepeat(claseHeader, claseBody, claseRow) {
	for (i = 0; i < $(claseHeader).size(); i++) {
		widthCelda = $($($(claseHeader)[i])[0]).width()
		$(claseBody +"_" + i).width(widthCelda)
		$(claseBody +"_" + i).css({
			"width" : widthCelda,
			"min-width" : widthCelda,
			"max-width" : widthCelda
		})
	}
}

function cuadrarScrollGeneral(claseHeader, claseBody) {
	for (i = 0; i < $(claseHeader).size(); i++) {
		widthCelda = $($($(claseHeader)[i])[0]).width()
		$(claseBody).width(widthCelda)
		$(claseBody).css({
			"width" : widthCelda,
			"min-width" : widthCelda
		})
	}
}