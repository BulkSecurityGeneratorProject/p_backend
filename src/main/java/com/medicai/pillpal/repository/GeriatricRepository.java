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

    @Query("select geriatric from Geriatric geriatric " +
        "inner join geriatric.applicationInfo ge " +
        "where ge.genericName in :genericNameList ")
    Page<Geriatric> findByGenericNameList(Pageable pageable,
                                                   @Param("genericNameList") List<String> genericNameList);

}
