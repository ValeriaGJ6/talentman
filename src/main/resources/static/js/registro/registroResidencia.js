// Dependencias: jQuery
$(document).ready(function () {
    var errorModel = document.getElementById('errorModel').textContent;
    localStorage.setItem('error', errorModel);

    // Cuando se carga la página, verifica si se produjo un error
    var errorStorage = localStorage.getItem('error');

    if (errorStorage === 'true') {
        // Si se produjo un error, recupera las selecciones del almacenamiento local
        var paisSeleccionado = localStorage.getItem('paisResidenciaSeleccionado');
        if (paisSeleccionado === '51') {
            cargarDepartamentosColombiaRes().then(() => {
                var departamentoSeleccionado = localStorage.getItem('departamentoResidenciaSeleccionado');
                if (departamentoSeleccionado) {
                    $('#divDepartamentoResidencia').show();
                    $('#idDepartamentoResidencia').val(departamentoSeleccionado);
                    $('#idDepartamentoResidencia').change();
                }
                cargarMunicipioSeleccionadoRes(departamentoSeleccionado).then(() => {
                    var municipioSeleccionado = localStorage.getItem('municipioResidenciaSeleccionado');
                    if (municipioSeleccionado) {
                        $('#divMunicipioResidencia').show();
                        $('#idMunicipioResidencia').val(municipioSeleccionado);
                        $('#idMunicipioResidencia').change();
                    }
                });
            });

        } else if (paisSeleccionado != '51' && paisSeleccionado != '') {
            $('#divDepartamentoResidencia').hide();
            $('#divMunicipioResidencia').hide();
            $('#divCiudadOtroPaisResidencia').show();
        }
    }

    $('#idPaisResidencia').change(function () {
        var paisSeleccionado = $(this).val();
        localStorage.setItem('paisResidenciaSeleccionado', paisSeleccionado);
        if (paisSeleccionado === '51') {
            $('#divCiudadOtroPaisResidencia').hide();
            cargarDepartamentosColombiaRes();
        } else if (paisSeleccionado !== '51' && paisSeleccionado !== '') {
            $('#divDepartamentoResidencia').hide();
            $('#divMunicipioResidencia').hide();
            $('#divCiudadOtroPaisResidencia').show();
        }
    });

    $('#idDepartamentoResidencia').change(function () {
        var departamentoId = $(this).val();
        localStorage.setItem('departamentoResidenciaSeleccionado', departamentoId);
        if (departamentoId !== null && departamentoId !== "" && departamentoId !== '0') {
            cargarMunicipioSeleccionadoRes(departamentoId);
        }
    });

    $('#idMunicipioResidencia').change(function () {
        var municipioSeleccionado = $(this).val();
        localStorage.setItem('municipioResidenciaSeleccionado', municipioSeleccionado);
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
function cargarDepartamentosColombiaRes() {
    return $.ajax({
        url: '/departamentos',
        type: 'GET',
        success: function (data) {
            var select = $('#idDepartamentoResidencia');
            $.each(data, function (index, departamento) {
                select.append('<option value="' + departamento.id + '">' + departamento.nombre + '</option>');
            });
            $('#divDepartamentoResidencia').show();
            $('#divMunicipioResidencia').show();
        },
        error: function (error) {
            console.log(error);
        }
    });
}

function cargarMunicipioSeleccionadoRes(departamentoId) {
    return $.ajax({
        url: '/municipios/' + departamentoId,
        type: 'GET',
        dataType: 'json',
        success: function (data) {
            var select = $('#idMunicipioResidencia');
            select.empty();
            select.append('<option value="" selected>Seleccione un municipio</option>')
            $.each(data, function (index, municipio) {                
                select.append('<option value="' + municipio.id + '">' + municipio.nombre + '</option>');
            });
            $('#divMunicipioResidencia').show();
        },
        error: function (error) {
            console.log(error);
        }
    });
}
