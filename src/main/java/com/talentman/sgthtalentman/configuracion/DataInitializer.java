package com.talentman.sgthtalentman.configuracion;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.talentman.sgthtalentman.models.UsuarioModel;
import com.talentman.sgthtalentman.models.InformacionPersonalModel;
import com.talentman.sgthtalentman.models.MunicipioModel;
import com.talentman.sgthtalentman.models.PaisModel;
import com.talentman.sgthtalentman.repositories.UsuarioRepository;
import com.talentman.sgthtalentman.services.InformacionPersonalService;
import com.talentman.sgthtalentman.services.MunicipioService;
import com.talentman.sgthtalentman.services.PaisService;
import com.talentman.sgthtalentman.services.UsuarioService;
import com.talentman.sgthtalentman.transversal.Genero;
import com.talentman.sgthtalentman.transversal.Rol;
import com.talentman.sgthtalentman.transversal.TipoDocumento;
import com.talentman.sgthtalentman.repositories.InformacionPersonalRepository;

@Configuration
public class DataInitializer {

    private PaisService paisService;
    private MunicipioService municipioService;
    private UsuarioService usuarioService;
    private InformacionPersonalService informacionPersonalService;

    public DataInitializer(PaisService paisService, MunicipioService municipioService, UsuarioService usuarioService, InformacionPersonalService informacionPersonalService) {
        this.paisService = paisService;
        this.municipioService = municipioService;
        this.usuarioService = usuarioService;
        this.informacionPersonalService = informacionPersonalService;
    }

    @Bean
    CommandLineRunner initDatabase(UsuarioRepository usuarioRepository, InformacionPersonalRepository informacionPersonalRepository) {
        return args -> {
            // Verificar si el usuario ya existe
            if (usuarioRepository.findByUsuario("valeria") == null) {
                BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    
                // Crear y guardar un usuario
                UsuarioModel usuario = new UsuarioModel();
                usuario.setUsuario("valeria");
                usuario.setContrasenaHash(passwordEncoder.encode("12345678"));
                usuario.setRol(Rol.Empleado);
                usuario.setActivo(true);   
                
    
                // Crear y guardar información personal
                InformacionPersonalModel infoPersonal = new InformacionPersonalModel();
                infoPersonal.setIdUsuario(usuario);
                infoPersonal.setPrimerNombre("Valeria");
                infoPersonal.setPrimerApellido("Guerrero");
                infoPersonal.setSegundoApellido("Jaramillo");
                infoPersonal.setGenero(Genero.Femenino);
                infoPersonal.setFechaNacimiento(java.time.LocalDate.of(1999, 10, 10));

                usuarioService.save(usuario);
                Thread.sleep(3000);

                PaisModel paisNacimiento = paisService.findByNombre("Colombia");
                infoPersonal.setPaisNacimiento(paisNacimiento);

                Thread.sleep(3000);
                MunicipioModel municipioNacimiento = municipioService.findbyNombre("Santa Rosa de Cabal");
                infoPersonal.setMunicipioNacimiento(municipioNacimiento);

                infoPersonal.setPaisResidencia(paisNacimiento);
                infoPersonal.setMunicipioResidencia(municipioNacimiento);                
                infoPersonal.setTipoDocumento(TipoDocumento.CC);
                infoPersonal.setNumeroDocumento("123456789");
                infoPersonal.setCorreo("valeria@correo.com");
                infoPersonal.setDireccion("Calle 123");
                infoPersonal.setTelefono("123456789");
                infoPersonal.setFechaRegistro(LocalDate.now());
                infoPersonal.setFechaIngreso(LocalDate.now());                
                
                informacionPersonalService.save(infoPersonal);
            }
        };
    }
}
