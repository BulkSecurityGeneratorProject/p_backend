package com.medicai.pillpal.repository;

import com.medicai.pillpal.domain.ConsumptionMissedDose;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ConsumptionMissedDose entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConsumptionMissedDoseRepository extends JpaRepository<ConsumptionMissedDose, Long> {

}
