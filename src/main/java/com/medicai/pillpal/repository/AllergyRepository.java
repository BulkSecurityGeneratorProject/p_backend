package com.medicai.pillpal.repository;

import com.medicai.pillpal.domain.Allergy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


/**
 * Spring Data  repository for the Allergy entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AllergyRepository extends JpaRepository<Allergy, Long> {

    @Query("select allergy from Allergy allergy " +
        "inner join allergy.applicationInfo ai  " +
        "where ai.genericName = :genericName ")
    Optional<Allergy> findByGenericName(@Param("genericName") String genericName);

    @Query("select allergy from Allergy allergy " +
        "inner join allergy.applicationInfo ai  " +
        "where ai.genericName in :genericNameList ")
    Page<Allergy> findByGenericNameList(Pageable pageable,
                                        @Param("genericNameList") List<String> genericNameList);


}
