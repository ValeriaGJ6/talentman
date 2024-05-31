// Dependencias: jQuery
$(document).ready(function () {
    var errorModel = document.getElementById('errorModel').textContent;
    localStorage.setItem('error', errorModel);

    // Cuando se carga la página, verifica si se produjo un error
    var errorStorage = localStorage.getItem('error');

    if (errorStorage === 'true') {
        // Si se produjo un error, recupera las selecciones del almacenamiento local
        var paisSeleccionado = localStorage.getItem('paisNacimientoSeleccionado');
        if (paisSeleccionado === '51') {
            cargarDepartamentosColombiaNac().then(() => {
                var departamentoSeleccionado = localStorage.getItem('departamentoNacimientoSeleccionado');
                if (departamentoSeleccionado) {
                    $('#divDepartamentoNacimiento').show();
                    $('#idDepartamentoNacimiento').val(departamentoSeleccionado);
                    $('#idDepartamentoNacimiento').change();
                }
                cargarMunicipioSeleccionadoNac(departamentoSeleccionado).then(() => {
                    var municipioSeleccionado = localStorage.getItem('municipioNacimientoSeleccionado');
                    if (municipioSeleccionado) {
                        $('#divMunicipioNacimiento').show();
                        $('#idMunicipioNacimiento').val(municipioSeleccionado);
                        $('#idMunicipioNacimiento').change();
                    }
                });
            });

        } else if (paisSeleccionado != '51' && paisSeleccionado != '') {
            $('#divDepartamentoNacimiento').hide();
            $('#divMunicipioNacimiento').hide();
            $('#divCiudadOtroPaisNacimiento').show();
        }
    }

    $('#idPaisNacimiento').change(function () {
        var paisSeleccionado = $(this).val();
        localStorage.setItem('paisNacimientoSeleccionado', paisSeleccionado);
        if (paisSeleccionado === '51') {
            $('#divCiudadOtroPaisNacimiento').hide();
            cargarDepartamentosColombiaNac();
        } else if (paisSeleccionado !== '51' && paisSeleccionado !== '') {
            $('#divDepartamentoNacimiento').hide();
            $('#divMunicipioNacimiento').hide();
            $('#divCiudadOtroPaisNacimiento').show();
        }
    });

    $('#idDepartamentoNacimiento').change(function () {
        var departamentoId = $(this).val();
        localStorage.setItem('departamentoNacimientoSeleccionado', departamentoId);
        if (departamentoId !== null && departamentoId !== "" && departamentoId !== '0') {
            cargarMunicipioSeleccionadoNac(departamentoId);
        }
    });

    $('#idMunicipioNacimiento').change(function () {
        var municipioSeleccionado = $(this).val();
        localStorage.setItem('municipioNacimientoSeleccionado', municipioSeleccionado);
    });

    $('input, select').on('change', function () {
        var inputId = $(this).attr('id');
        if ($(this).val() !== '') {
            $('p[data-error-for="' + inputId + '"]').hide();
        } else {
            $('p[data-error-for="' + inputId + '"]').show();
        }
    });
});

// Funciones
function cargarDepartamentosColombiaNac() {
    return $.ajax({
        url: '/departamentos',
        type: 'GET',
        dataType: 'json',
        success: function (data) {
            var select = $('#idDepartamentoNacimiento');
            $.each(data, function (index, departamento) {
                select.append('<option value="' + departamento.id + '">' + departamento.nombre + '</option>');
            });
            $('#divDepartamentoNacimiento').show();
            $('#divMunicipioNacimiento').show();
        },
        error: function (error) {
            console.log(error);
        }
    });
}

function cargarMunicipioSeleccionadoNac(departamentoId) {
    return $.ajax({
        url: '/municipios/' + departamentoId,
        type: 'GET',
        dataType: 'json',
        success: function (data) {
            var select = $('#idMunicipioNacimiento');
            select.empty();
            select.append('<option value="" selected>Seleccione un municipio</option>')
            $.each(data, function (index, municipio) {
                select.append('<option value="' + municipio.id + '">' + municipio.nombre + '</option>');
            });
            $('#divMunicipioNacimiento').show();
        },
        error: function (error) {
            console.log(error);
        }
    });
}
