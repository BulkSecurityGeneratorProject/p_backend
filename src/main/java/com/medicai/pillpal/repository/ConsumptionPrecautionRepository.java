package com.medicai.pillpal.repository;

import com.medicai.pillpal.domain.ConsumptionPrecaution;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ConsumptionPrecaution entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConsumptionPrecautionRepository extends JpaRepository<ConsumptionPrecaution, Long> {

}
