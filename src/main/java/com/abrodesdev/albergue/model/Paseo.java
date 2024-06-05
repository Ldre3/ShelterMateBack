package com.abrodesdev.albergue.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import jakarta.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Data
public class Paseo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name="fecha")
    private Date fecha;
    @Column(name="horaSalida")
    private Date horaSalida;
    @Column(name="duracion")
    private int duracion;
    @Column(name="observaciones")
    private String observaciones;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JoinColumn(name = "perro_id")
    private Perro perros;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;


}
