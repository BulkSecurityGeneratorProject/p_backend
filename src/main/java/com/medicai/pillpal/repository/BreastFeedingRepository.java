package com.medicai.pillpal.repository;

import com.medicai.pillpal.domain.BreastFeeding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the BreastFeeding entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BreastFeedingRepository extends JpaRepository<BreastFeeding, Long> {

}
