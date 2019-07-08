package com.medicai.pillpal.repository;

import com.medicai.pillpal.domain.Geriatric;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the Geriatric entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GeriatricRepository extends JpaRepository<Geriatric, Long> {

    @Query("select geriatric from Gerriatric geriatric where geriatric.genericName in :genericNameList")
    Page<Geriatric> findByGenericNameListGeriatric(Pageable pageable , @Param("genericNameList")List<String>genericName);

}
