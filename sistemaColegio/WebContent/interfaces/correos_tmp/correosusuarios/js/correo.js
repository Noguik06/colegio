//Metodo para cuadrar el scroll luego de haber hecho un resize
$(window).resize(function() {
	cuadrarScroll(".colTablaCorreos", ".colTablaCorreosBody")
});

jQuery().ready(function() {
	metodoInicial()
});

function metodoInicial() {

	var h = $("body").outerHeight();
	document.getElementById("formCorreos").style.height = screen.height * 0.68
			+ "px"
	document.getElementById("columnaIzquierda").style.height = screen.height
			* 0.67 + "px"
	document.getElementById("columnaDerecha").style.height = screen.height
			* 0.68 + "px"

	// Cuadramos las medidasde la tabla de recibir correos

	divCorreos()

	cuadrarScroll(".colTablaCorreos", ".colTablaCorreosBody")
	
//	$("#escribirCorreo").hide()

	// cargarToolBarEditor()
}

// metodo para cargar el toolbar
function cargarToolBarEditor() {
	// Disable filtering chains
	// elRTE.prototype.filter.prototype.chains = { }

	/* elRTE 'custom' toolbar */
	elRTE.prototype.options.panels.custom = [ 'bold', 'italic', 'underline',
			'forecolor', 'fontsize', 'formatblock', 'insertorderedlist',
			'insertunorderedlist', 'docstructure' ];
	elRTE.prototype.options.toolbars.custom = [ 'custom', 'eol', 'undoredo',
			'alignment', 'eol', 'links', 'images' ];

	var opts = {
		// doctype : '<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
		// "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">',
		cssClass : 'el-rte',
		// lang : 'en',
		height : screen.height * 0.37,

		lang : 'es',

		toolbar : 'juani', // custom
		cssfiles : [ 'elrte/css/elrte-inner.css', 'elrte/css/inner-example.css' ],
		// elfinder
		fmOpen : function(callback) {
			$('<div id="myelfinder"></div>').elfinder({
				url : '../../elfinder/connectors/php/connector.php',
				lang : 'en',
				dialog : {
					width : 900,
					modal : true,
					title : 'Files'
				}, // open in dialog
				closeOnEditorCallback : true, // close elFinder after file
												// select
				editorCallback : callback
			// pass callback to editor
			});
		},
		// example of user replacement
		// restricts edit of blocks like <!-- BEGIN MY_BLOCK -->anything<!-- END
		// MY_BLOCK -->
		replace : [ function(html) {
			var self = this;
			return html
					.replace(
							/(<!--\s*BEGIN\s*([^>]+)\s*-->([\s\S]*?)<!--\s*END\s*(\2)\s*-->)/gi,
							function(t, a, b, c, d) {

								if (b == d) {
									// self.rte.log([b, d]);
									var id = 'MYTAG'
											+ Math.random().toString()
													.substring(2);
									self.scripts[id] = t;
									return '<img id="'
											+ id
											+ '" src="'
											+ self.url
											+ 'pixel.gif" class="elrte-protected elrte-MYTAG">';
								}
								return html;
							});
		} ],
		restore : [ function(html) {
			var self = this;
			return html.replace(this.serviceClassRegExp, function(t, n, a) {
				a = self.parseAttrs(a);
				if (a["class"]['elrte-MYTAG']) {
					return self.scripts[a.id] + "\n" || '';
				}
				return t;
			});
		} ]
	};

	// create elRTE
	var rte = $('#editor').elrte(opts);

	// elRTE in dialog example
	$('#dialog').click(function() { // open dialog on click
		var dialog = $('<div/>').dialog({
			title : 'Editor in dialog',
			width : 600
		});
		// create element for elRTE
		var rteInDialog = $('<div>Content for editor in dialog</div>');
		// put element inside dialog
		dialog.append(rteInDialog);
		// create elRTE on element
		rteInDialog.elrte(opts);
	});
	$('#getContent').click(function() {
		alert($('#editor').elrte('val'));
	});
	$('#setContent').click(function() {
		$('#editor').elrte('val', 'This is new <b>content</b> for editor');
	});
	$('#destroy').click(function() {
		$('#editor').elrte('destroy');
	});
}

// cuadrar leer correos
function divCorreos() {
	document.getElementById("recibirLeerCorreos").style.height = ($(
			"#divCorreos").outerHeight() * 29 / 100)
			+ "px"
	document.getElementById("leerCorreos").style.height = ($("#divCorreos")
			.outerHeight() * 71.2 / 100)
			+ "px"
}

// Metodo para cargar los usuarios para enviar el correo
function cargarUsuarios(e) {
	if (e.status == "success") {
		// Llamamos la funcion para posicionar el popup de los usuarios
		posicionarPopUP()

		if ($(".rowUsuariosBusqueda").size() == 0
				|| $("#formCorreos\\:inputBusquedaUsuarios").val().length == 0) {
			if ($(".rowCursosBusqueda").size() > 0) {
				$("#formPrincipal\\:tablaUsuarios").hide()
			} else {
				$("#subPopUpBuscar").hide()
			}
		} else {
			$("#subPopUpBuscar").show()
		}

		$("#sobreParaCorreo").show()
	}
}

// Metodo globarl para posicionar el popup de los usuarios
function posicionarPopUP() {
	// Evento para cargar el popUp y para manejar los eventos de subir y bajar
	topFormPK = $("#bajoParaCorreo").parent().position().top
	leftFormPK = $("#bajoParaCorreo").parent().position().left

	$("#subPopUpBuscar")
			.css(
					{
						"position" : "absolute",
						"background" : "white",
						"top" : topFormPK
								+ 2
								* $("#formCorreos\\:inputBusquedaUsuarios")
										.height()
								+ $("#formCorreos\\:inputBusquedaUsuarios")
										.position().top,
						"left" : leftFormPK
					});
}

var banderaDesactivarEsconderUsuarios = false

function despuesSeleccionarUsuarios(e) {
	if (e.status == "success") {
		// cargarUsuarios(e)
		$("#formPrincipal\\:inputBusquedaUsuarios").focus()
		banderaDesactivarEsconderUsuarios = false
	}
}

function esconderUsuarios() {
	$("#subPopUpBuscar").hide()

	if (!banderaDesactivarEsconderUsuarios) {
		$("#sobreParaCorreo").show()

		$("#formPrincipal\\:panelUsuariosCorreo").css("height", "25px");
		$("#formPrincipal\\:panelUsuariosCorreo").css("min-height", "25px");
		$("#formPrincipal\\:panelUsuariosCorreo").css("overflow-y", "hidden");

		return false
	}
	$("#formPrincipal\\:inputBusquedaUsuarios").focus()
	banderaDesactivarEsconderUsuarios = false
	return true

}

function despuesSalirBusqueda(e) {
	if (e.status == "success") {
		$("#formPrincipal\\:panelUsuariosCorreo").css("height", "50px");
		$("#formPrincipal\\:panelUsuariosCorreo").css("min-height", "50px");
		$("#formPrincipal\\:panelUsuariosCorreo").css("overflow-y", "hidden");

		$("#sobreParaCorreo").css("max-height", "50px");
		$("#sobreParaCorreo").css("height", "50px");
		$("#sobreParaCorreo").css("min-height", "50px");

		banderaDesactivarEsconderUsuarios = false
	}
}

function desactivarEsconderUsuarios() {
	banderaDesactivarEsconderUsuarios = true;
}

function activarBanderaEsconderUsuarios_false() {
	banderaDesactivarEsconderUsuarios = false;
}

function desactivarEsconderUsuarios2() {

	$("#subPopUpBuscar").hide()

	$("#backgroundPopup").show()
	$("#backgroundPopup").height(2000)

	banderaDesactivarEsconderUsuarios = true;
}

function esconderPopUp() {
	// Verificamos si la bandera para esconder los usuarios esta activa o no
	if (!banderaDesactivarEsconderUsuarios) {
		$("#subPopUpBuscar").hide()
		$("#formCorreos\\:inputBusquedaUsuarios").val("")
		$("#formCorreos\\:inputBusquedaUsuarios").hide()
		esconderPopUppara()
	}
	banderaDesactivarEsconderUsuarios = false
}

function esconderPopUppara() {
	var ancho = 0

	$("#bajoParaCorreo").hide()
	$("#fueraInput").show()
	//	
	$('.emailclass').hide()
	//	

	$('.emailclass').each(function(index, elemento) {
		ancho += ($(elemento).width())
	});

	var faltantes = 0;

	diferencia = ancho / $("#panelUsuariosCorreo").width()
			- parseInt(ancho / $("#panelUsuariosCorreo").width())
	// alert(diferencia)

	var anchoFinal = 0;
	var index = 0;
	if (ancho > $("#panelUsuariosCorreo").width()) {
		while (true) {
			anchoFinal += $($('.emailclass')[index]).width()
			var diferencia = anchoFinal * 100
					/ $("#panelUsuariosCorreo").width()
			if (diferencia > 90) {
				$('.emailclass')[index - 1].innerHTML = $('.emailclass')[index - 1].innerHTML
						.replace(",", "")
				break;
			}
			$($('.emailclass')[index]).show()
			index++
		}
		faltantes = $(".emailclass").size() - index
		$("#formCorreos\\:listaUsuariosParaFueraInput")[0].innerHTML += '<span class="aDp">'
				+ faltantes + ' m&aacute;s</span>'
	} else {
		$('.emailclass').show()
		$('.emailclass')[$('.emailclass').size() - 1].innerHTML = $('.emailclass')[$(
				'.emailclass').size() - 1].innerHTML.replace(",", "")
	}

}

// Metodo para mostrar los correos
function mostrarCorreos() {
	$(".aDp").remove()
	$("#bajoParaCorreo").show()
	$("#fueraInput").hide()
	$('.emailclass').show()
	banderaDesactivarEsconderUsuarios = false
}

function activarBanderaEsconderUsuarios() {
	banderaDesactivarEsconderUsuarios = true
}

function escribirPara(e) {
	$(e).hide()
	$("#formPrincipal\\:panelUsuariosCorreo").css("max-height", "auto");
	$("#formPrincipal\\:panelUsuariosCorreo").css("height", "auto");
	$("#formPrincipal\\:panelUsuariosCorreo").css("min-height", "auto");
	//    
	// $(e).css("max-height","100px");
	// $(e).css("height","100px");
	// $(e).css("min-height","100px");

	$("#formPrincipal\\:inputBusquedaUsuarios").focus()
}

// Metodo usado para poder tomar el contenido del correo
function colocarContenido() {
	if($(".emailclass").size() == 0){
		//Validamos que hayan usuarios para poder enviar el correo
		alert("No hay usuarios para enviar el correo")
		return true
	}
	$("#formCorreos\\:contenido").val($('#editor').elrte('val'))
}

function despuesEnviarCorreo(e) {
	if (e.status == "begin") {
		$("#backEnviadoCorreo").show()
		cargarEstadosMail(true, "#formCorreos\\:enviandoMail")
	}
	if (e.status == "success") {
		metodoInicial()
	}
}

// Despues de escoger el usuario
function despuesEscoger(e) {
	if (e.status == "begin") {
		banderaDesactivarEsconderUsuarios = true
	}
	if (e.status == "success") {
		posicionarPopUP()
		$("#subPopUpBuscar").hide()
		$("#formCorreos\\:inputBusquedaUsuarios").focus()
	}
}

// Metodo para cuando damos click sobre el cuadro para escribir
function seleccionarInputBuscar() {
	$("#formCorreos\\:inputBusquedaUsuarios").show()
	$("#formCorreos\\:inputBusquedaUsuarios").focus()
}

// Metodo para despues de eliminar un usuario
function despuesEliminarUsuario(e) {
	if (e.status == "begin") {
		$(e.source).parent().parent().remove()
		banderaDesactivarEsconderUsuarios = true
	}
	if (e.status == "success") {
		banderaDesactivarEsconderUsuarios = false
		$("#formCorreos\\:inputBusquedaUsuarios").focus()
	}
}

// Metodo para escoger leer correos
function leerCorreos(e) {
	if (e.status == "success") {
		alert("ola")
		$("#recibirLeerCorreos").show()
		$("#leerCorreos").show()
		$("#escribirCorreo").hide()
		$("#divCorreos").show()
		// Cuadramos las medidasde la tabla de recibir correos
		cuadrarScroll(".colTablaCorreos", ".colTablaCorreosBody")

		metodoInicial()
	}
}

// Metodo para eliminar correos
function eliminarCorreos(e) {
	if (e.status == "begin") {
		cargarEstadosMail(true, "#formCorreos\\:borrandoMail")
	}
	if (e.status == "success") {
		$("#recibirLeerCorreos").show()
		$("#leerCorreos").show()
		$("#escribirCorreo").hide()
		$("#divCorreos").show()
		// Cuadramos las medidasde la tabla de recibir correos
		cuadrarScroll(".colTablaCorreos", ".colTablaCorreosBody")

		metodoInicial()
	}
}

// Metodo para luego de haber seleccionado un correo
function despuesSeleccionarCorreo(e) {
	if (e.status == "begin") {
		$("#loading").show()
		$("#contenidoCorreoBack").show()
		$("#contenidoCorreo").hide()
		cargarEstadosMail(true, "#formCorreos\\:cargandoMail")
	}
	if (e.status == "success") {
		$(".selected").removeClass("selected")
		$(e.source).parent().parent().addClass("selected")
		$("#contenidoCorreo").html(
				$("#correosParaLeer\\:correoseleccionado").html())
		$("#contenidoCorreoBack").hide()
		$("#contenidoCorreo").show()
		cargarEstadosMail(false, "#formCorreos\\:cargandoMail")
		// Activamos la opcion de eliminar
		$(".delete").removeClass("disabled")
		clase = $(e.source)[0].className
		if (clase.split(" ").length > 3) {
			$("." + clase.split(" ")[0]).removeClass('leido')
		}
	}
}

function despuesSeleccionarMensaje(e) {
	if (e.status == "begin") {
		$("#loading").show()
		$("#contenidoCorreoBack").show()
		$("#contenidoCorreo").hide()
		cargarEstadosMail(true, "#formCorreos\\:cargandoMail")
	}
	if (e.status == "success") {
		$(".selected").removeClass("selected")
		$(e.source).parent().parent().addClass("selected")
		$("#formCorreos\\:contenidoCorreo").html(
				$("#correosParaLeer\\:mensajeseleccionado").html())
		$("#contenidoCorreoBack").hide()
		$("#contenidoCorreo").show()
		cargarEstadosMail(false, "#formCorreos\\:cargandoMail")
		// Activamos la opcion de eliminar
//		$(".delete").removeClass("disabled")
//		clase = $(e.source)[0].className
//		if (clase.split(" ").length > 3) {
//			$("." + clase.split(" ")[0]).removeClass('leido')
//		}
	}
}


// Metodo para cargar los estados del mail
function cargarEstadosMail(accion, estado) {
	if (accion) {
		$("#tablaStatus").show()
		$(estado).show()
	} else {
		$("#tablaStatus").hide()
		$(estado).hide()
	}
}

// Seleccionar para escribir un correo nuevo
function escribirCorreos(e) {

	if (e.status == "success") {
		$("#escribirCorreo").show()
		$("#formCorreos\\:editor").show()
//		$("#recibirLeerCorreos").hide()
//		$("#leerCorreos").hide()
//		
//		$("#formCorreos\\:panelLeerLosCorreos").hide()
//		$("#divCorreos").hide()

//		cargarToolBarEditor()
	}
}

// metodo para validar si hay correos seleccionados para eliminar
function despuesSeleccionarEliminar(e) {
	if (e.status == "success") {
		if ($("input:checked").length > 0 || $(".selected").size() > 0) {
			$(".delete").removeClass("disabled")
		} else {
			if (!$(".delete").hasClass("disabled")) {
				$(".delete").addClass("disabled")
			}
		}
	}
}

// metodo para actualizar la lista de correo
function despuesActualizarListaCorreo(e) {
	if (e.status == "begin") {
		cargarEstadosMail(true, "#formCorreos\\:actualizandoMail")
	}
	if (e.status == "success") {
		$("#recibirLeerCorreos").show()
		$("#leerCorreos").show()
		$("#escribirCorreo").hide()
		$("#divCorreos").show()
		// Cuadramos las medidasde la tabla de recibir correos
		cuadrarScroll(".colTablaCorreos", ".colTablaCorreosBody")

		metodoInicial()
	}
}

// metodo para validar si hay correos para eliminar
function validarCorreosEliminar() {
	if ($("input:checked").length > 0 || $(".selected").size() > 0) {
		return false
	}

	return true
}
