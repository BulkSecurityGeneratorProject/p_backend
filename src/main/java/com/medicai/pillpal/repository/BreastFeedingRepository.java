package com.medicai.pillpal.repository;

import com.medicai.pillpal.domain.Allergy;
import com.medicai.pillpal.domain.BreastFeeding;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


/**
 * Spring Data  repository for the BreastFeeding entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BreastFeedingRepository extends JpaRepository<BreastFeeding, Long> {
    @Query("select breastFeeding from BreastFeeding breastFeeding " +
        "inner join breastFeeding.applicationInfo bfi  " +
        "where bfi.genericName = :genericName ")
    Optional<BreastFeeding> findBreastFeedingByGenericName(@Param("genericName") String genericName);

    @Query("select breastFeeding from BreastFeeding breastFeeding " +
        "inner join breastFeeding.applicationInfo bfi  " +
        "where bfi.genericName in :genericNameList ")
    Page<BreastFeeding> findBreastFeedingByGenericNameList(Pageable pageable,
                                        @Param("genericNameList") List<String> genericNameList);

}
