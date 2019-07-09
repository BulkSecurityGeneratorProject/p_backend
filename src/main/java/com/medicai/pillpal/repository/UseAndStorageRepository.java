package com.medicai.pillpal.repository;

import com.medicai.pillpal.domain.UseAndStorage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the UseAndStorage entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UseAndStorageRepository extends JpaRepository<UseAndStorage, Long> {

}
