package com.abrodesdev.albergue.repository;


import com.abrodesdev.albergue.model.Adoptante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AdoptanteRepository extends JpaRepository<Adoptante, Long>{

    Adoptante findByDatos_Id(long id);

}
