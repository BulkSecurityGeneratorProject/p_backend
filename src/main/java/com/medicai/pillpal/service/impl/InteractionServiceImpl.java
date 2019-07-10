package com.medicai.pillpal.service.impl;

import com.medicai.pillpal.domain.Interaction;
import com.medicai.pillpal.repository.InteractionRepository;
import com.medicai.pillpal.service.InteractionService;
import com.medicai.pillpal.service.dto.InteractionDTO;
import com.medicai.pillpal.service.mapper.InteractionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Interaction}.
 */
@Service
@Transactional
public class InteractionServiceImpl implements InteractionService {

    private final Logger log = LoggerFactory.getLogger(InteractionServiceImpl.class);

    private final InteractionRepository interactionRepository;

    private final InteractionMapper interactionMapper;

    public InteractionServiceImpl(InteractionRepository interactionRepository, InteractionMapper interactionMapper) {
        this.interactionRepository = interactionRepository;
        this.interactionMapper = interactionMapper;
    }

    /**
     * Save a interaction.
     *
     * @param interactionDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public InteractionDTO save(InteractionDTO interactionDTO) {
        log.debug("Request to save Interaction : {}", interactionDTO);
        Interaction interaction = interactionMapper.toEntity(interactionDTO);
        interaction = interactionRepository.save(interaction);
        return interactionMapper.toDto(interaction);
    }

    /**
     * Get all the interactions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<InteractionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Interactions");
        return interactionRepository.findAll(pageable)
            .map(interactionMapper::toDto);
    }


    /**
     * Get one interaction by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<InteractionDTO> findOne(Long id) {
        log.debug("Request to get Interaction : {}", id);
        return interactionRepository.findById(id)
            .map(interactionMapper::toDto);
    }

    /**
     * Delete the interaction by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Interaction : {}", id);
        interactionRepository.deleteById(id);
    }

    /**
     * get a  genericName
     *
     * @param genericName
     * @return persisted entity
     */
    public Optional<InteractionDTO> findInteractionByGenericName(String genericName) {
        log.debug("Request to delete Interaction : {}", genericName);
        return interactionRepository.findInteractionByGenericName(genericName)
            .map(interactionMapper::toDto);

    }

    /**
     * get a  genericName
     *
     * @param genericNameList
     * @param pageable
     * @return persisted entity
     */

    @Override
    public Page<InteractionDTO> findInteractionByGenericNameList(Pageable pageable, List<String> genericNameList) {
        log.debug("Request to delete Interaction : {}", pageable);
        return interactionRepository.findInteractionByGenericNameList(pageable, genericNameList)
            .map(interactionMapper::toDto);
    }
}
