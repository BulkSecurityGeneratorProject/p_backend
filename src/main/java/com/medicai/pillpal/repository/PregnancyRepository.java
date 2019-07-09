package com.medicai.pillpal.repository;

import com.medicai.pillpal.domain.Allergy;
import com.medicai.pillpal.domain.Pregnancy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the Pregnancy entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PregnancyRepository extends JpaRepository<Pregnancy, Long> {

    @Query("select pregnancy from Pregnancy pregnancy " +
        "inner join pregnancy.applicationInfo ai  " +
        "where ai.genericName = :genericName ")
    Page<Allergy> findByGenericName(Pageable pageable,
                                           @Param("genericName") String genericName);

    @Query("select pregnancy from Pregnancy pregnancy " +
        "inner join pregnancy.applicationInfo ai  " +
        "where ai.genericName in :genericNameList ")
    Page<Allergy> findByGenericNameList(Pageable pageable,
                                               @Param("genericNameList") List<String> genericNameList);


}
