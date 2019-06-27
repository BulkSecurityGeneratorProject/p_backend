package com.medicai.pillpal.repository;

import com.medicai.pillpal.domain.Pregnancy;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Pregnancy entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PregnancyRepository extends JpaRepository<Pregnancy, Long> {

}
