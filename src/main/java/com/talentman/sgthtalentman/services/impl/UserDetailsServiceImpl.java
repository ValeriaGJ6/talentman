package com.talentman.sgthtalentman.services.impl;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.talentman.sgthtalentman.models.UsuarioModel;
import com.talentman.sgthtalentman.services.UsuarioService;
import com.talentman.sgthtalentman.transversal.Rol;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UsuarioService usuarioService;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UsuarioModel usuario = usuarioService.findByUsuario(username);
        if (usuario == null) {
            throw new UsernameNotFoundException("El usuario no existe en el sistema.");
        }
        return new org.springframework.security.core.userdetails.User(usuario.getUsuario(), usuario.getContrasenaHash(),
                obtenerRol(usuario));
    }

    private Collection<? extends GrantedAuthority> obtenerRol(UsuarioModel usuario) {
        Rol rol = usuario.getRol();
        return Collections.singletonList(new SimpleGrantedAuthority(rol.name()));
    }
}
