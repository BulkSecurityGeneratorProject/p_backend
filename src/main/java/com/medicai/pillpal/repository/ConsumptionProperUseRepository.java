package com.medicai.pillpal.repository;

import com.medicai.pillpal.domain.ConsumptionProperUse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


/**
 * Spring Data  repository for the ConsumptionProperUse entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConsumptionProperUseRepository extends JpaRepository<ConsumptionProperUse, Long> {

    @Query("select cpu from ConsumptionProperUse cpu " +
        "inner join cpu.applicationInfo ai  " +
        "where ai.genericName = :genericName ")
    Optional<ConsumptionProperUse> findByGenericName(@Param("genericName") String genericName);

    @Query("select cpu from ConsumptionProperUse cpu " +
        "inner join cpu.applicationInfo ai  " +
        "where ai.genericName in :genericNameList ")
    Page<ConsumptionProperUse> findByGenericNameList(Pageable pageable,
                                                     @Param("genericNameList") List<String> genericNameList);

}
