package com.medicai.pillpal.repository;

import com.medicai.pillpal.domain.ConsumptionDosing;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


/**
 * Spring Data  repository for the ConsumptionDosing entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConsumptionDosingRepository extends JpaRepository<ConsumptionDosing, Long> {

    @Query("select cd from ConsumptionDosing cd " +
        "inner join cd.applicationInfo ai  " +
        "where ai.genericName = :genericName ")
    Optional<ConsumptionDosing> findByGenericName(@Param("genericName") String genericName);

    @Query("select cd from ConsumptionDosing cd " +
        "inner join cd.applicationInfo ai  " +
        "where ai.genericName in :genericNameList ")
    Page<ConsumptionDosing> findByGenericNameList(Pageable pageable,
                                                  @Param("genericNameList") List<String> genericNameList);


}
