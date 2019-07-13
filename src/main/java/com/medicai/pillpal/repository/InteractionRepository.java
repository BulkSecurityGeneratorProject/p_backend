package com.medicai.pillpal.repository;

import com.medicai.pillpal.domain.Interaction;
import com.medicai.pillpal.domain.enumeration.RecommendationType;
import com.medicai.pillpal.service.dto.InteractionListDTO;
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


    //TODO
    // return destination application info data which is containing
    // ApplicationInfo
    // ID , fda_application_no ,  generic_name ,
    // Interaction
    // all attributes

    @Query("select new com.medicai.pillpal.service.dto.InteractionListDTO(interaction.id, " +
        "interaction.recommendationType, " +
        "interaction.description, " +
        "descapp.fdaApplicationNo, " +
        "descapp.genericName) " +
        "from Interaction interaction " +
        "inner join interaction.baseApplicationInfo baseapp " +
        "inner join interaction.descApplicationInfo descapp " +
        "where baseapp.genericName in :genericName")
    Page<InteractionListDTO> findInteractionByGenericNameList(Pageable pageable
        , @Param("genericName") List<String> genericName);


    @Query("select new com.medicai.pillpal.service.dto.InteractionListDTO(interaction.id, " +
        "interaction.recommendationType, " +
        "interaction.description, " +
        "descapp.fdaApplicationNo, " +
        "descapp.genericName) " +
        "from Interaction interaction " +
        "inner join interaction.baseApplicationInfo baseapp " +
        "inner join interaction.descApplicationInfo descapp " +
        "where baseapp.genericName = :genericName")
    Optional<InteractionListDTO> findInteractionByGenericName(@Param("genericName") String genericName);
}
