package com.abrodesdev.albergue.controller;


import com.abrodesdev.albergue.model.Adoptante;
import com.abrodesdev.albergue.service.AdoptanteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/adoptantes")
public class AdoptanteController {
    @Autowired
    private AdoptanteService adoptanteService;

    // Registra un adoptante en un albergue, como es un metodo que requiere que el usuario sea el administrador del albergue, se le pasa el token de autenticacion
    // en el header de la peticion, lo que se hace para todos los metodos que requieren autenticacion de administrador
    @PostMapping("/registrar/{idAlbergue}")
    public ResponseEntity<Adoptante> registrarAdoptante(@RequestBody Adoptante adoptante, @PathVariable Long idAlbergue, @RequestHeader("Authorization") String auth){
        try {
            Adoptante adoptanteRegistrado = adoptanteService.save(adoptante, idAlbergue, auth);
            return adoptanteRegistrado != null ? ResponseEntity.ok(adoptanteRegistrado) : ResponseEntity.status(401).build();
        } catch (RuntimeException e) {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/adoptarPerro/{idDatos}/{idPerro}/{idAlbergue}")
    public ResponseEntity<Adoptante> adoptarPerro(@PathVariable Long idDatos, @PathVariable Long idPerro, @PathVariable Long idAlbergue, @RequestHeader("Authorization") String auth) {
        try {
            Adoptante adoptante = adoptanteService.adoptarPerro(idDatos, idPerro, idAlbergue, auth);
            return adoptante != null ? ResponseEntity.ok(adoptante) : ResponseEntity.status(401).build();
        } catch (Exception e) {
            return ResponseEntity.noContent().build();
        }
    }



}
