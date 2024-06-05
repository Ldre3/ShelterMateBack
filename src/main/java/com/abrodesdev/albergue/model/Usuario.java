package com.abrodesdev.albergue.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import jakarta.persistence.*;
import org.hibernate.annotations.Cascade;

import java.util.List;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_datos")
    private Datos datos;
    @Column(name="licencia")
    private boolean licencia;
    @Column(name="password")
    private String password;
    @OneToMany(mappedBy = "usuario", orphanRemoval = true)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonProperty (access = JsonProperty.Access.WRITE_ONLY)
    private List<Paseo> paseo;

    @OneToMany(mappedBy = "usuarioGestor", orphanRemoval = true)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Albergue> alberguesGestionados;

    @ManyToMany(mappedBy = "usuariosVoluntarios")
    @JsonProperty (access = JsonProperty.Access.WRITE_ONLY)
    private Set<Albergue> alberguesVoluntariado;

    public Usuario(Datos datos, boolean licencia, String password) {
        this.datos = datos;
        this.licencia = licencia;
        this.password = password;
    }
}
