package com.abrodesdev.albergue.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;


import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Data
public class Albergue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name="localidad")
    private String localidad;
    @Column(name="numTelefono")
    private String numTelefono;

    @Column(name="password")
    private String password;

    @ManyToOne (cascade = CascadeType.ALL)
    @JoinColumn(name = "usuario_gestor_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Usuario usuarioGestor;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany (cascade = CascadeType.ALL)
    @JoinTable(
            name = "albergue_voluntario",
            joinColumns = @JoinColumn(name = "albergue_id"),
            inverseJoinColumns = @JoinColumn(name = "usuario_id")
    )
    private Set<Usuario> usuariosVoluntarios;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "albergue",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Perro> perros;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "albergue",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Adoptante> adoptantes;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "albergue", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Anuncio> ancuncios;
}
