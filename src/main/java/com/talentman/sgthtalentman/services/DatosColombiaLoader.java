package com.talentman.sgthtalentman.services;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.talentman.sgthtalentman.dto.ApiDepartamentoDTO;
import com.talentman.sgthtalentman.dto.ApiMunicipioDTO;
import com.talentman.sgthtalentman.models.DepartamentoModel;
import com.talentman.sgthtalentman.models.MunicipioModel;
import com.talentman.sgthtalentman.repositories.DepartamentoRepository;
import com.talentman.sgthtalentman.repositories.MunicipioRepository;
import com.talentman.sgthtalentman.repositories.PaisRepository;
import com.talentman.sgthtalentman.transversal.ApiResponse;
import com.talentman.sgthtalentman.models.PaisModel;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;

@Component
public class DatosColombiaLoader implements CommandLineRunner {
    @Autowired
    private DepartamentoRepository departamentoRepository;

    @Autowired
    private MunicipioRepository municipioRepository;

    @Autowired
    private PaisRepository paisRepository;

    private final String DEPARTAMENTOS_API_URL = "https://api-colombia.com/api/v1/Department";
    private final String MUNICIPIOS_API_URL = "https://api-colombia.com/api/v1/City";
    private final String PAISES_API_URL = "https://countriesnow.space/api/v0.1/countries/capital";

    // private static final Logger log =
    // LoggerFactory.getLogger(DatosColombiaLoader.class);

    @Override
    public void run(String... args) throws Exception {
        if (paisRepository.count() == 0) {
            poblarPaises();
        }
        if (departamentoRepository.count() == 0 && municipioRepository.count() == 0) {
            poblarDatos();
        }
    }

    private void poblarPaises() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<ApiResponse> response = restTemplate.exchange(
            PAISES_API_URL,
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<ApiResponse>() {
            });

        ApiResponse apiResponse = response.getBody();

        // Obtener y guardar países
        List<PaisModel> paises = apiResponse.getData().stream()
            .map(countryData -> {
                PaisModel pais = new PaisModel();
                pais.setNombre(countryData.getName());
                return pais;
            })
            .collect(Collectors.toList());

        paisRepository.saveAll(paises);
    }

    private void poblarDatos() {
        RestTemplate restTemplate = new RestTemplate();
        PaisModel pais = paisRepository.findByNombre("Colombia");

        // Obtener y guardar departamentos
        ParameterizedTypeReference<List<ApiDepartamentoDTO>> typeRefDepto = new ParameterizedTypeReference<List<ApiDepartamentoDTO>>() {
        };
        ResponseEntity<List<ApiDepartamentoDTO>> responseDepto = restTemplate.exchange(DEPARTAMENTOS_API_URL,
                HttpMethod.GET,
                null, typeRefDepto);
        List<ApiDepartamentoDTO> apiDepartamentos = responseDepto.getBody();


        List<DepartamentoModel> departamentos = apiDepartamentos.stream()
                .map(apiDepartamento -> {
                    DepartamentoModel departamento = new DepartamentoModel();
                    departamento.setId(apiDepartamento.getId());
                    departamento.setNombre(apiDepartamento.getNombre());
                    departamento.setPais(pais);
                    return departamento;
                })
                .collect(Collectors.toList());

        Collections.sort(departamentos, Comparator.comparing(DepartamentoModel::getId));
        departamentoRepository.saveAll(departamentos);

        // Crear un mapa de departamentos
        Map<Long, DepartamentoModel> departamentoMap = departamentos
                .stream()
                .collect(Collectors.toMap(DepartamentoModel::getId, departamento -> departamento));

        // Obtener y guardar municipios
        ParameterizedTypeReference<List<ApiMunicipioDTO>> typeRefMpio = new ParameterizedTypeReference<List<ApiMunicipioDTO>>() {
        };
        ResponseEntity<List<ApiMunicipioDTO>> responseMpio = restTemplate.exchange(MUNICIPIOS_API_URL, HttpMethod.GET,
                null, typeRefMpio);
        List<ApiMunicipioDTO> apiMunicipios = responseMpio.getBody();

        List<MunicipioModel> municipios = apiMunicipios.stream()
                .map(apiMunicipio -> {
                    MunicipioModel municipio = new MunicipioModel();
                    municipio.setId(apiMunicipio.getId());
                    municipio.setNombre(apiMunicipio.getNombre());
                    municipio.setDepartamento(departamentoMap.get(apiMunicipio.getDepartamentoId()));
                    return municipio;
                }).collect(Collectors.toList());

        Collections.sort(municipios, Comparator.comparing(MunicipioModel::getId));
        municipioRepository.saveAll(municipios);
    }
}
