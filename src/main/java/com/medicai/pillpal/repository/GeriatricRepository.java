package com.medicai.pillpal.repository;

import com.medicai.pillpal.domain.Geriatric;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Geriatric entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GeriatricRepository extends JpaRepository<Geriatric, Long> {

}
