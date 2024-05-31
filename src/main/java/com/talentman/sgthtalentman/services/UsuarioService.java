package com.talentman.sgthtalentman.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.talentman.sgthtalentman.dto.RegistroDTO;
import com.talentman.sgthtalentman.models.UsuarioModel;
import com.talentman.sgthtalentman.models.factory.UsuarioFactory;
import com.talentman.sgthtalentman.repositories.UsuarioRepository;


@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UsuarioModel findByUsuario(String usuario) {
        return usuarioRepository.findByUsuario(usuario);
    }

    public Long getIdUsuarioByUsuario(String usuario) {
        UsuarioModel usuarioModel = usuarioRepository.findByUsuario(usuario);
        return usuarioModel != null ? usuarioModel.getId() : null;
    }

    public UsuarioModel findById(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    public UsuarioModel save(UsuarioModel nuevoUsuario) {
        String hashedPassword = passwordEncoder.encode(nuevoUsuario.getContrasenaHash());
        nuevoUsuario.setContrasenaHash(hashedPassword);
        usuarioRepository.save(nuevoUsuario);
        return nuevoUsuario;
    }

    public UsuarioModel convertToModel(RegistroDTO registroDTO) {
        UsuarioFactory usuarioFactory = new UsuarioFactory();
        UsuarioModel usuario = usuarioFactory.crearUsuarioEstandar(registroDTO.getUsuario(), registroDTO.getContrasena());

        return usuario;        
    }

    public void delete(UsuarioModel usuarioGuardado) {
        usuarioRepository.delete(usuarioGuardado);
    }

    public String getRolById(Long idUsuario) {
        return usuarioRepository.getRolById(idUsuario);
    }
    
}
