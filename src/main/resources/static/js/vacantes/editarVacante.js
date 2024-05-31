// Dependencias: jQuery
$(document).ready(function () {
    var errorModel = document.getElementById('errorModel').textContent;
    localStorage.setItem('error', errorModel);

    // Cuando se carga la página, verifica si se produjo un error
    var errorStorage = localStorage.getItem('error');

    if (errorStorage === 'true') {
        // Si se produjo un error, recupera las selecciones del almacenamiento local
        var paisSeleccionado = localStorage.getItem('paisSeleccionado');
        if (paisSeleccionado === '51') {
            cargarDepartamentosColombia().then(() => {
                var departamentoSeleccionado = localStorage.getItem('departamentoSeleccionado');
                if (departamentoSeleccionado) {
                    $('#divDepartamento').show();
                    $('#idDepartamento').val(departamentoSeleccionado);
                    $('#idDepartamento').change();
                }
                cargarMunicipioSeleccionado(departamentoSeleccionado).then(() => {
                    var municipioSeleccionado = localStorage.getItem('municipioSeleccionado');
                    if (municipioSeleccionado) {
                        $('#divMunicipio').show();
                        $('#idMunicipio').val(municipioSeleccionado);
                        $('#idMunicipio').change();
                    }
                });
            });

        } else if (paisSeleccionado != '51' && paisSeleccionado != '') {
            $('#divDepartamento').hide();
            $('#divMunicipio').hide();
            $('#divCiudad').show();
            localStorage.removeItem('departamentoSeleccionado');
            localStorage.removeItem('municipioSeleccionado');
        }
    }

    var paisSeleccionado = $('#idPais').val().trim().replace(/\s+/g, '');
    if (paisSeleccionado === "51") {
        cargarDepartamentosColombia().then(() => {
            var departamentoSeleccionado = $('#idDepartamentoInput').val();
            if (departamentoSeleccionado) {
                $('#idDepartamento').val(departamentoSeleccionado);
                $('#divDepartamento').show();
            }

            // Carga los municipios
            cargarMunicipioSeleccionado(departamentoSeleccionado).then(() => {
                // Después de que los municipios se hayan cargado, establece el valor del municipio
                var municipioSeleccionado = $('#idMunicipioInput').val();
                if (municipioSeleccionado) {
                    $('#idMunicipio').val(municipioSeleccionado);
                    $('#divMunicipio').show();
                }
            });
        });
    } else {
        localStorage.removeItem('departamentoSeleccionado');
        localStorage.removeItem('municipioSeleccionado');
        $('#divDepartamento').hide();
        $('#divMunicipio').hide();
        $('#divCiudad').show();        
    }

    $('#idPais').change(function () {
        var paisSeleccionado = $(this).val();
        localStorage.setItem('paisSeleccionado', paisSeleccionado);
        if (paisSeleccionado === '51') {
            $('#divCiudad').hide();
            cargarDepartamentosColombia();
        } else if (paisSeleccionado !== '51' && paisSeleccionado !== '') {
            $('#divDepartamento').hide();
            $('#divMunicipio').hide();
            $('#divCiudad').show();
        }
    });

    $('#idDepartamento').change(function () {
        var departamentoId = $(this).val();
        console.log("Departamento: ", departamentoId)
        localStorage.setItem('departamentoSeleccionado', departamentoId);
        if (departamentoId !== null && departamentoId !== "" && departamentoId !== '0') {
            cargarMunicipioSeleccionado(departamentoId);
        }
    });

    $('#idMunicipio').change(function () {
        var municipioSeleccionado = $(this).val();
        localStorage.setItem('municipioSeleccionado', municipioSeleccionado);
    });

    $('input, select, textarea').on('change', function () {
        var inputId = $(this).attr('id');
        if ($(this).val() !== '') {
            $('p[data-error-for="' + inputId + '"]').hide();
        } else {
            $('p[data-error-for="' + inputId + '"]').show();
        }
    });


});

// Funciones
function cargarDepartamentosColombia() {
    return $.ajax({
        url: '/departamentos',
        type: 'GET',
        success: function (data) {
            var select = $('#idDepartamento');
            select.empty();
            select.append('<option value="">Selecciona un departamento</option>');
            $.each(data, function (index, departamento) {
                select.append('<option value="' + departamento.id + '">' + departamento.nombre + '</option>');
            });
            $('#divDepartamento').show();
        },
        error: function (error) {
            console.log(error);
        }
    });
}

function cargarMunicipioSeleccionado(departamentoId) {
    return $.ajax({
        url: '/municipios/' + departamentoId,
        type: 'GET',
        dataType: 'json',
        success: function (data) {
            var select = $('#idMunicipio');
            select.empty();
            select.append('<option value="">Selecciona un municipio</option>');
            $.each(data, function (index, municipio) {
                select.append('<option value="' + municipio.id + '">' + municipio.nombre + '</option>');
            });
            $('#divMunicipio').show();
        },
        error: function (error) {
            console.log(error);
        }
    });
}
