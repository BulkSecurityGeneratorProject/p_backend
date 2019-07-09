package com.medicai.pillpal.repository;

import com.medicai.pillpal.domain.ConsumptionDosing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ConsumptionDosing entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConsumptionDosingRepository extends JpaRepository<ConsumptionDosing, Long> {

}
