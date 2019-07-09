package com.medicai.pillpal.repository;

import com.medicai.pillpal.domain.Interaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Interaction entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InteractionRepository extends JpaRepository<Interaction, Long> {

}
