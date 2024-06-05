package com.abrodesdev.albergue.repository;



import com.abrodesdev.albergue.model.Perro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PerroRepository extends JpaRepository<Perro, Long> {
    List<Perro> findByNombre(String nombre);

    List<Perro> findByAlbergue_Id(long id);
}
