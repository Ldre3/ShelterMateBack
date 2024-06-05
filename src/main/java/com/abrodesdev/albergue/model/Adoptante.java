package com.abrodesdev.albergue.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;


@Entity
@Data
public class Adoptante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToOne (cascade = CascadeType.ALL)
    @JoinColumn(name = "id_datos")
    private Datos datos;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "adoptante" , cascade = CascadeType.PERSIST)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Perro> perros;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "albergue_id")
    @JsonProperty (access = JsonProperty.Access.WRITE_ONLY)
    private Albergue albergue;

}
