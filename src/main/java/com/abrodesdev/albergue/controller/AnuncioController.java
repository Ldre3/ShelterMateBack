package com.abrodesdev.albergue.controller;


import com.abrodesdev.albergue.model.Albergue;
import com.abrodesdev.albergue.model.Anuncio;
import com.abrodesdev.albergue.service.AnuncioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/anuncios")
public class AnuncioController {
    @Autowired
    private AnuncioService anuncioService;

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarAnuncio(@PathVariable Long id, @RequestHeader("Authorization") String auth){
        try {
            boolean borrado = anuncioService.deleteById(id, auth);
            return borrado? ResponseEntity.ok().build() : ResponseEntity.status(401).build();
        } catch (Exception e) {
            return ResponseEntity.noContent().build();
        }

    }

    @GetMapping("/buscarAlbergueId/{id}")
    public ResponseEntity<List<Anuncio>> buscarAnuncioPorAlbergueId(@PathVariable Long id) {
        List<Anuncio> anuncio = anuncioService.findByAlbergueId(id);
        return !anuncio.isEmpty() ? ResponseEntity.ok(anuncio) : ResponseEntity.noContent().build();
    }

    @PostMapping("/registrarEnAlbergue/{idAlbergue}")
    public ResponseEntity<Anuncio> registrarAnuncioEnAlbergue(@PathVariable Long idAlbergue, @RequestBody Anuncio anuncio, @RequestHeader("Authorization") String auth) {
        try {
            Albergue albergue = anuncioService.registrarAnuncioenAlbergue(anuncio, idAlbergue, auth);

            return albergue != null ? ResponseEntity.ok(albergue.getAncuncios().get(albergue.getAncuncios().size()-1)) : ResponseEntity.status(401).build();
        } catch (RuntimeException e) {
            return ResponseEntity.noContent().build();
        }
    }


}
