package com.medicai.pillpal.repository;

import com.medicai.pillpal.domain.ConsumptionProperUse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ConsumptionProperUse entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConsumptionProperUseRepository extends JpaRepository<ConsumptionProperUse, Long> {

}
