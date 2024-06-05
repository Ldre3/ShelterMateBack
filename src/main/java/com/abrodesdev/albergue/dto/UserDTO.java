package com.abrodesdev.albergue.dto;

import com.abrodesdev.albergue.model.Datos;
import lombok.Data;


// DTO para la creación de un usuario
@Data
public class UserDTO {
    private Datos datos;
    private String password;
    private boolean licencia;
}
