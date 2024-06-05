package com.abrodesdev.albergue.repository;

import com.abrodesdev.albergue.model.Adoptante;
import com.abrodesdev.albergue.model.Datos;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DatosRepository extends JpaRepository<Datos, Long> {
}
