package com.medicai.pillpal.repository;

import com.medicai.pillpal.domain.ConsumptionMissedDose;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


/**
 * Spring Data  repository for the ConsumptionMissedDose entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConsumptionMissedDoseRepository extends JpaRepository<ConsumptionMissedDose, Long> {

    @Query("select cmd from ConsumptionMissedDose cmd " +
        "inner join cmd.applicationInfo ai  " +
        "where ai.genericName = :genericName ")
    Optional<ConsumptionMissedDose> findByGenericName(@Param("genericName") String genericName);

    @Query("select cmd from ConsumptionMissedDose cmd " +
        "inner join cmd.applicationInfo ai  " +
        "where ai.genericName in :genericNameList ")
    Page<ConsumptionMissedDose> findByGenericNameList(Pageable pageable,
                                                      @Param("genericNameList") List<String> genericNameList);


}
