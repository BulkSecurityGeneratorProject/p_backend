package com.medicai.pillpal.repository;

import com.medicai.pillpal.domain.PharmaceuticalCode;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PharmaceuticalCode entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PharmaceuticalCodeRepository extends JpaRepository<PharmaceuticalCode, Long> {

}
