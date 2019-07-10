package com.medicai.pillpal.repository;

import com.medicai.pillpal.domain.ConsumptionBeforeUsing;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


/**
 * Spring Data  repository for the ConsumptionBeforeUsing entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConsumptionBeforeUsingRepository extends JpaRepository<ConsumptionBeforeUsing, Long> {

    @Query("select cbu from ConsumptionBeforeUsing cbu " +
        "inner join cbu.applicationInfo ai  " +
        "where ai.genericName = :genericName ")
    Optional<ConsumptionBeforeUsing> findByGenericName(@Param("genericName") String genericName);

    @Query("select cbu from ConsumptionBeforeUsing cbu " +
        "inner join cbu.applicationInfo ai  " +
        "where ai.genericName in :genericNameList ")
    Page<ConsumptionBeforeUsing> findByGenericNameList(Pageable pageable,
                                                       @Param("genericNameList") List<String> genericNameList);

}
