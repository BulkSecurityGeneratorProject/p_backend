package com.medicai.pillpal.repository;

import com.medicai.pillpal.domain.ApplInfoSideEffect;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ApplInfoSideEffect entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ApplInfoSideEffectRepository extends JpaRepository<ApplInfoSideEffect, Long> {

}
