package com.medicai.pillpal.repository;

import com.medicai.pillpal.domain.Pediatric;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Pediatric entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PediatricRepository extends JpaRepository<Pediatric, Long> {

}
