package com.talentman.sgthtalentman.models.factory;

import com.talentman.sgthtalentman.models.UsuarioModel;
import com.talentman.sgthtalentman.transversal.Rol;

public class UsuarioFactory {

    public UsuarioModel crearUsuarioEstandar(String usuario, String contrasenaHash) {
        UsuarioModel usuarioModel = new UsuarioModel();
        usuarioModel.setUsuario(usuario);
        usuarioModel.setContrasenaHash(contrasenaHash);
        usuarioModel.setRol(Rol.Estandar);
        usuarioModel.setActivo(true);
        return usuarioModel;
    }

}

