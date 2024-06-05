package com.abrodesdev.albergue.repository;


import com.abrodesdev.albergue.model.Albergue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlbergueRepository extends JpaRepository<Albergue, Long> {

    List<Albergue> findByUsuariosVoluntarios_Id(long id);

    List<Albergue> findByLocalidadIgnoreCase(String localidad);



}
