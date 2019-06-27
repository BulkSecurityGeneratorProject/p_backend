package com.medicai.pillpal.web.rest;

import com.medicai.pillpal.service.ApplInfoSideEffectService;
import com.medicai.pillpal.web.rest.errors.BadRequestAlertException;
import com.medicai.pillpal.service.dto.ApplInfoSideEffectDTO;

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

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.medicai.pillpal.domain.ApplInfoSideEffect}.
 */
@RestController
@RequestMapping("/api")
public class ApplInfoSideEffectResource {

    private final Logger log = LoggerFactory.getLogger(ApplInfoSideEffectResource.class);

    private static final String ENTITY_NAME = "applInfoSideEffect";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ApplInfoSideEffectService applInfoSideEffectService;

    public ApplInfoSideEffectResource(ApplInfoSideEffectService applInfoSideEffectService) {
        this.applInfoSideEffectService = applInfoSideEffectService;
    }

    /**
     * {@code POST  /appl-info-side-effects} : Create a new applInfoSideEffect.
     *
     * @param applInfoSideEffectDTO the applInfoSideEffectDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new applInfoSideEffectDTO, or with status {@code 400 (Bad Request)} if the applInfoSideEffect has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/appl-info-side-effects")
    public ResponseEntity<ApplInfoSideEffectDTO> createApplInfoSideEffect(@RequestBody ApplInfoSideEffectDTO applInfoSideEffectDTO) throws URISyntaxException {
        log.debug("REST request to save ApplInfoSideEffect : {}", applInfoSideEffectDTO);
        if (applInfoSideEffectDTO.getId() != null) {
            throw new BadRequestAlertException("A new applInfoSideEffect cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ApplInfoSideEffectDTO result = applInfoSideEffectService.save(applInfoSideEffectDTO);
        return ResponseEntity.created(new URI("/api/appl-info-side-effects/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /appl-info-side-effects} : Updates an existing applInfoSideEffect.
     *
     * @param applInfoSideEffectDTO the applInfoSideEffectDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated applInfoSideEffectDTO,
     * or with status {@code 400 (Bad Request)} if the applInfoSideEffectDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the applInfoSideEffectDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/appl-info-side-effects")
    public ResponseEntity<ApplInfoSideEffectDTO> updateApplInfoSideEffect(@RequestBody ApplInfoSideEffectDTO applInfoSideEffectDTO) throws URISyntaxException {
        log.debug("REST request to update ApplInfoSideEffect : {}", applInfoSideEffectDTO);
        if (applInfoSideEffectDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ApplInfoSideEffectDTO result = applInfoSideEffectService.save(applInfoSideEffectDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, applInfoSideEffectDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /appl-info-side-effects} : get all the applInfoSideEffects.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of applInfoSideEffects in body.
     */
    @GetMapping("/appl-info-side-effects")
    public ResponseEntity<List<ApplInfoSideEffectDTO>> getAllApplInfoSideEffects(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of ApplInfoSideEffects");
        Page<ApplInfoSideEffectDTO> page = applInfoSideEffectService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /appl-info-side-effects/:id} : get the "id" applInfoSideEffect.
     *
     * @param id the id of the applInfoSideEffectDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the applInfoSideEffectDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/appl-info-side-effects/{id}")
    public ResponseEntity<ApplInfoSideEffectDTO> getApplInfoSideEffect(@PathVariable Long id) {
        log.debug("REST request to get ApplInfoSideEffect : {}", id);
        Optional<ApplInfoSideEffectDTO> applInfoSideEffectDTO = applInfoSideEffectService.findOne(id);
        return ResponseUtil.wrapOrNotFound(applInfoSideEffectDTO);
    }

    /**
     * {@code DELETE  /appl-info-side-effects/:id} : delete the "id" applInfoSideEffect.
     *
     * @param id the id of the applInfoSideEffectDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/appl-info-side-effects/{id}")
    public ResponseEntity<Void> deleteApplInfoSideEffect(@PathVariable Long id) {
        log.debug("REST request to delete ApplInfoSideEffect : {}", id);
        applInfoSideEffectService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
