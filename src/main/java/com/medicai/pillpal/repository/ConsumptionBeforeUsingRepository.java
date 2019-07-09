package com.medicai.pillpal.repository;

import com.medicai.pillpal.domain.ConsumptionBeforeUsing;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ConsumptionBeforeUsing entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConsumptionBeforeUsingRepository extends JpaRepository<ConsumptionBeforeUsing, Long> {

}
