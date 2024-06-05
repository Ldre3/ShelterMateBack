package com.abrodesdev.albergue.model;

import com.abrodesdev.albergue.dto.UsuarioDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {

    private String mensaje;
    private boolean status;
    private UsuarioDTO usuario;

}
