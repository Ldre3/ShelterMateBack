package com.abrodesdev.albergue.controller;


import com.abrodesdev.albergue.model.Paseo;
import com.abrodesdev.albergue.model.Perro;
import com.abrodesdev.albergue.service.PerroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/perros")
public class PerroController {
    @Autowired
    private PerroService perroService;

    @PostMapping("/registrar/{idAlbergue}")
    public ResponseEntity<Perro> registrarPerro(@RequestBody Perro perro, @PathVariable Long idAlbergue, @RequestHeader("Authorization") String auth) {
        try {
            Perro perroRegistrado = perroService.registrarPerroAlbergue(perro, idAlbergue, auth);
            return perroRegistrado != null ? ResponseEntity.ok(perroRegistrado) : ResponseEntity.status(401).build();
        } catch (RuntimeException e) {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<Perro> buscarPerro(@PathVariable Long id) {
        Perro perro = perroService.findById(id);
        return perro != null ? ResponseEntity.ok(perro) : ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<Perro>> buscarPerros() {
        return ResponseEntity.ok(perroService.findAll());
    }

    @PutMapping("/actualizar")
    public ResponseEntity<Perro> actualizarPerro(@RequestBody Perro perro, @RequestHeader("Authorization") String auth) {
        Perro perroActu = perroService.findById(perro.getId());
        if (perroActu == null) {
            return ResponseEntity.noContent().build();
        }
        perroActu.setNombre(perro.getNombre());
        perroActu.setPpp(perro.isPpp());
        perroActu.setFotoURL(perro.getFotoURL());
        perroActu.setFechaNacimiento(perro.getFechaNacimiento());
        perroActu.setPeso(perro.getPeso());
        Perro retorno = perroService.save(perroActu, perroActu.getAlbergue().getId(), auth);
        return retorno != null ? ResponseEntity.ok(retorno) : ResponseEntity.status(401).build();
    }



    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarPerro(@PathVariable Long id, @RequestHeader("Authorization") String auth) {
        try {
            boolean borrado = perroService.deleteById(id, auth);
            return borrado? ResponseEntity.ok().build() : ResponseEntity.status(401).build();
        } catch (Exception e) {
            return ResponseEntity.noContent().build();
        }
    }




    @GetMapping("/buscarnombre/{nombre}")
    public ResponseEntity<List<Perro>> buscarPerroPorNombre(@PathVariable String nombre) {
        List<Perro> perros = perroService.findByNombre(nombre);
        return !perros.isEmpty() ? ResponseEntity.ok(perros) : ResponseEntity.noContent().build();
    }



    @GetMapping("/albergue/{id}")
    public ResponseEntity<List<Perro>> buscarPerrosAlbergue(@PathVariable Long id) {
        return ResponseEntity.ok(perroService.findByAlbergueId(id));
    }

    @PostMapping("/pasear/{idPerro}/{idUsuario}")
    public ResponseEntity<Perro> pasearPerro(@PathVariable Long idPerro, @RequestBody Paseo paseo, @PathVariable Long idUsuario) {
        return ResponseEntity.ok(perroService.registrarPaseo(idPerro, paseo, idUsuario));
    }





}
