package com.abrodesdev.albergue.controller;


import com.abrodesdev.albergue.model.Albergue;
import com.abrodesdev.albergue.model.Datos;
import com.abrodesdev.albergue.service.AlbergueService;
import com.abrodesdev.albergue.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;

@RestController
@RequestMapping("/albergues")
@CrossOrigin
public class AlbergueController {
    @Autowired
    private AlbergueService albergueService;

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/registrar/{idGestor}")
    public ResponseEntity<Albergue> registrarAlbergue(@RequestBody Albergue albergue, @PathVariable Long idGestor) {
        albergue.setUsuarioGestor(usuarioService.findById(idGestor));
        albergue.setUsuariosVoluntarios(new HashSet<>());
        albergue.getUsuariosVoluntarios().add(usuarioService.findById(idGestor));
        return ResponseEntity.ok(albergueService.save(albergue));
    }


    @GetMapping("/buscarVoluntarioId/{id}")
    public ResponseEntity<List<Albergue>> buscarAlberguePorVoluntarioId(@PathVariable Long id) {
        List<Albergue> albergues = albergueService.findByVoluntarioId(id);
        return !albergues.isEmpty() ? ResponseEntity.ok(albergues) : ResponseEntity.noContent().build();
    }

    @GetMapping("/buscarVoluntarioIdNotInAlbergue/{id}")
    public ResponseEntity<List<Albergue>> buscarAlberguePorVoluntarioIdNotInAlbergue(@PathVariable Long id) {
        List<Albergue> albergues = albergueService.findByVoluntarioIdNotInAlbergue(id);
        return !albergues.isEmpty() ? ResponseEntity.ok(albergues) : ResponseEntity.noContent().build();
    }

    @GetMapping("/buscarDatos/{id}")
    public ResponseEntity<List<Datos>> buscarDatos(@PathVariable Long id) {
        List<Datos> datos = albergueService.findDatos(id);
        return !datos.isEmpty() ? ResponseEntity.ok(datos) : ResponseEntity.noContent().build();
    }

}
