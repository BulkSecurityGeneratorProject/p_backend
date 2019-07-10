package com.medicai.pillpal.repository;

import com.medicai.pillpal.domain.Interaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


/**
 * Spring Data  repository for the Interaction entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InteractionRepository extends JpaRepository<Interaction, Long> {

    @Query("select intraction from Interaction interaction " +
        "inner join interaction.baseApplicarionInfo baseapp " +
        "inner join interaction.descApplicarionInfo descapp " +
        "where baseapp.genericName in :genericName")
    Page<Interaction> findInteractionByGenericNameList(Pageable pageable
        , @Param("genericName") List<String> genericName);


    @Query("select intraction from Interaction interaction " +
        "inner join interaction.baseApplicarionInfo baseapp " +
        "inner join interaction.descApplicarionInfo descapp " +
        "where baseapp.genericName = :genericName")
    Optional<Interaction> findInteractionByGenericName(@Param("genericName") String genericName);
}
