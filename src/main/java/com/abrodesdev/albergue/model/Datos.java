package com.abrodesdev.albergue.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Datos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name="nombre")
    private String nombre;
    @Column(name="apellidos")
    private String apellidos;
    @Column(name="telefono")
    private String telefono;
    @Column(name="email")
    private String email;
}
