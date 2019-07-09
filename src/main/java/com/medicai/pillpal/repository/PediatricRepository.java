package com.medicai.pillpal.repository;

import com.medicai.pillpal.domain.Pediatric;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


/**
 * Spring Data  repository for the Pediatric entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PediatricRepository extends JpaRepository<Pediatric, Long> {

    @Query("select pediatric from Pediatric pediatric " +
        "inner join pediatric.applicationInfo ai  " +
        "where ai.genericName = :genericName ")
    Optional<Pediatric> findByGenericName(@Param("genericName") String genericName);

    @Query("select pediatric from Pediatric pediatric " +
        "inner join pediatric.applicationInfo ai  " +
        "where ai.genericName in :genericNameList ")
    Page<Pediatric> findByGenericNameList(Pageable pageable,
                                          @Param("genericNameList") List<String> genericNameList);
}
