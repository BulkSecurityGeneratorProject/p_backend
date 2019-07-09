package com.medicai.pillpal.repository;

    import com.medicai.pillpal.domain.Allergy;
    import org.springframework.data.domain.Page;
    import org.springframework.data.domain.Pageable;
    import org.springframework.data.jpa.repository.JpaRepository;
    import org.springframework.data.jpa.repository.Query;
    import org.springframework.data.repository.query.Param;
    import org.springframework.stereotype.Repository;

    import java.util.List;


/**
 * Spring Data  repository for the Allergy entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AllergyRepository extends JpaRepository<Allergy, Long> {

    @Query("select allergy from Allergy allergy " +
        "inner join allergy.applicationInfo ai  " +
        "where ai.applicationInfo.genericName in :genericName ")
    Page<Allergy> findAllergyByGenericName(Pageable pageable,
                                           @Param("genericName") String genericName);

    @Query("select allergy from Allergy allergy " +
        "inner join allergy.applicationInfo ai  " +
        "where ai.applicationInfo.genericName in :genericNameList ")
    Page<Allergy> findAllergyByGenericNameList(Pageable pageable,
                                        @Param("genericNameList") List<String> genericNameList);



}
