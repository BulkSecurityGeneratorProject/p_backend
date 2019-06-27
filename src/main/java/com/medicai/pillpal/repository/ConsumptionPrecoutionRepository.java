package com.medicai.pillpal.repository;

import com.medicai.pillpal.domain.ConsumptionPrecoution;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ConsumptionPrecoution entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConsumptionPrecoutionRepository extends JpaRepository<ConsumptionPrecoution, Long> {

}
