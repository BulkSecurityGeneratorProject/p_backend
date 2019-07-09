package com.medicai.pillpal.web.rest;

import com.medicai.pillpal.service.InteractionService;
import com.medicai.pillpal.web.rest.errors.BadRequestAlertException;
import com.medicai.pillpal.service.dto.InteractionDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.medicai.pillpal.domain.Interaction}.
 */
@RestController
@RequestMapping("/api")
public class InteractionResource {

    private final Logger log = LoggerFactory.getLogger(InteractionResource.class);

    private static final String ENTITY_NAME = "interaction";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InteractionService interactionService;

    public InteractionResource(InteractionService interactionService) {
        this.interactionService = interactionService;
    }

    /**
     * {@code POST  /interactions} : Create a new interaction.
     *
     * @param interactionDTO the interactionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new interactionDTO, or with status {@code 400 (Bad Request)} if the interaction has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/interactions")
    public ResponseEntity<InteractionDTO> createInteraction(@Valid @RequestBody InteractionDTO interactionDTO) throws URISyntaxException {
        log.debug("REST request to save Interaction : {}", interactionDTO);
        if (interactionDTO.getId() != null) {
            throw new BadRequestAlertException("A new interaction cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InteractionDTO result = interactionService.save(interactionDTO);
        return ResponseEntity.created(new URI("/api/interactions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /interactions} : Updates an existing interaction.
     *
     * @param interactionDTO the interactionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated interactionDTO,
     * or with status {@code 400 (Bad Request)} if the interactionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the interactionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/interactions")
    public ResponseEntity<InteractionDTO> updateInteraction(@Valid @RequestBody InteractionDTO interactionDTO) throws URISyntaxException {
        log.debug("REST request to update Interaction : {}", interactionDTO);
        if (interactionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        InteractionDTO result = interactionService.save(interactionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, interactionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /interactions} : get all the interactions.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of interactions in body.
     */
    @GetMapping("/interactions")
    public ResponseEntity<List<InteractionDTO>> getAllInteractions(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of Interactions");
        Page<InteractionDTO> page = interactionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /interactions/:id} : get the "id" interaction.
     *
     * @param id the id of the interactionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the interactionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/interactions/{id}")
    public ResponseEntity<InteractionDTO> getInteraction(@PathVariable Long id) {
        log.debug("REST request to get Interaction : {}", id);
        Optional<InteractionDTO> interactionDTO = interactionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(interactionDTO);
    }

    /**
     * {@code DELETE  /interactions/:id} : delete the "id" interaction.
     *
     * @param id the id of the interactionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/interactions/{id}")
    public ResponseEntity<Void> deleteInteraction(@PathVariable Long id) {
        log.debug("REST request to delete Interaction : {}", id);
        interactionService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
