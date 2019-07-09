package com.medicai.pillpal.web.rest;

import com.medicai.pillpal.service.PediatricService;
import com.medicai.pillpal.service.dto.PediatricDTO;
import com.medicai.pillpal.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.medicai.pillpal.domain.Pediatric}.
 */
@RestController
@RequestMapping("/api")
public class PediatricResource {

    private final Logger log = LoggerFactory.getLogger(PediatricResource.class);

    private static final String ENTITY_NAME = "pediatric";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PediatricService pediatricService;

    public PediatricResource(PediatricService pediatricService) {
        this.pediatricService = pediatricService;
    }

    /**
     * {@code POST  /pediatrics} : Create a new pediatric.
     *
     * @param pediatricDTO the pediatricDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pediatricDTO, or with status {@code 400 (Bad Request)} if the pediatric has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/pediatrics")
    public ResponseEntity<PediatricDTO> createPediatric(@RequestBody PediatricDTO pediatricDTO) throws URISyntaxException {
        log.debug("REST request to save Pediatric : {}", pediatricDTO);
        if (pediatricDTO.getId() != null) {
            throw new BadRequestAlertException("A new pediatric cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PediatricDTO result = pediatricService.save(pediatricDTO);
        return ResponseEntity.created(new URI("/api/pediatrics/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /pediatrics} : Updates an existing pediatric.
     *
     * @param pediatricDTO the pediatricDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pediatricDTO,
     * or with status {@code 400 (Bad Request)} if the pediatricDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pediatricDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/pediatrics")
    public ResponseEntity<PediatricDTO> updatePediatric(@RequestBody PediatricDTO pediatricDTO) throws URISyntaxException {
        log.debug("REST request to update Pediatric : {}", pediatricDTO);
        if (pediatricDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PediatricDTO result = pediatricService.save(pediatricDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, pediatricDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /pediatrics} : get all the pediatrics.
     *
     * @param pageable    the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder  a {@link UriComponentsBuilder} URI builder.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pediatrics in body.
     */
    @GetMapping("/pediatrics")
    public ResponseEntity<List<PediatricDTO>> getAllPediatrics(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of Pediatrics");
        Page<PediatricDTO> page = pediatricService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /pediatrics/:id} : get the "id" pediatric.
     *
     * @param id the id of the pediatricDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pediatricDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/pediatrics/{id}")
    public ResponseEntity<PediatricDTO> getPediatric(@PathVariable Long id) {
        log.debug("REST request to get Pediatric : {}", id);
        Optional<PediatricDTO> pediatricDTO = pediatricService.findOne(id);
        return ResponseUtil.wrapOrNotFound(pediatricDTO);
    }

    /**
     * {@code DELETE  /pediatrics/:id} : delete the "id" pediatric.
     *
     * @param id the id of the pediatricDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/pediatrics/{id}")
    public ResponseEntity<Void> deletePediatric(@PathVariable Long id) {
        log.debug("REST request to delete Pediatric : {}", id);
        pediatricService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * Get a Generic Name
     *
     * @param genericName
     * @return the ResponseEntity with status 200 (OK) and the list of notificationHistories in body
     */
    @GetMapping("/pediatric/by-generic-name")
    public ResponseEntity<PediatricDTO> getPediatricByGenericName(@RequestBody String genericName) {
        log.debug("REST request to delete SideEffect : {}");
        Optional<PediatricDTO> pediatricDTO = pediatricService.findPediatricByGenericName(genericName);
        return ResponseUtil.wrapOrNotFound(pediatricDTO);
    }

    /**
     * Get List of Generic Name
     *
     * @param pageable
     * @param genericName
     * @param uriBuilder
     * @return the ResponseEntity with status 200 (OK) and the list of notificationHistories in body
     */
    @GetMapping("/pediatric/by-generic-name-list")
    public ResponseEntity<List<PediatricDTO>> getPediatricByGenericNameList(Pageable pageable,
                                                                            @RequestBody List<String> genericName, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to delete SideEffect : {}");
        Page<PediatricDTO> page = pediatricService.findPediatricByGenericNameList(pageable, genericName);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder, page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
