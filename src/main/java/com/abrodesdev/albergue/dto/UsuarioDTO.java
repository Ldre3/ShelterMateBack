package com.abrodesdev.albergue.dto;

import com.abrodesdev.albergue.model.Datos;
import com.abrodesdev.albergue.model.Usuario;
import lombok.Data;

// DTO para devolver un usuario con sus datos
@Data
public class UsuarioDTO {
    private long id;
    private Datos datos;
    private boolean licencia;

    public UsuarioDTO(Usuario usuario) {
        this.id = usuario.getId();
        this.datos = usuario.getDatos();
        this.licencia = usuario.isLicencia();
    }
}
