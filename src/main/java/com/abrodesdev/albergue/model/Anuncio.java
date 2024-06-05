package com.abrodesdev.albergue.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import jakarta.persistence.*;
import java.util.Date;

@Data
@Entity
public class Anuncio {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "titulo")
    private String titulo;
    @Column(name = "contenido")
    private String contenido;
    @Column(name = "fecha_publicacion")
    private Date fechaPublicacion;

    @ManyToOne
    @JoinColumn(name = "albergue_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Albergue albergue;



}
