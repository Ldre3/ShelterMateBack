package com.abrodesdev.albergue.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import lombok.Data;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import jakarta.persistence.*;

@Entity
@Data
public class Perro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name="nombre")
    private String nombre;
    @Column(name="ppp")
    private boolean ppp;
    @Column(name="fotoURL")
    private String fotoURL;

    @Column(name="fecha_nacimiento")
    private Date fechaNacimiento;


    @Column(name="pesoActual")
    private double peso;


    @OneToMany(mappedBy = "perros", cascade = CascadeType.ALL)
    private Set<Paseo> paseos;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "albergue_id")
    @JsonProperty (access = JsonProperty.Access.WRITE_ONLY)
    private Albergue albergue;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "adoptante_id")
    private Adoptante adoptante;
}
