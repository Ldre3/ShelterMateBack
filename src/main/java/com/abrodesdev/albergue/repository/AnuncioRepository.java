package com.abrodesdev.albergue.repository;


import com.abrodesdev.albergue.model.Anuncio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnuncioRepository extends JpaRepository<Anuncio, Long> {
    List<Anuncio> findByAlbergue_Id(long id);
}
