package com.medicai.pillpal.web.rest;

import com.medicai.pillpal.service.BreastFeedingService;
import com.medicai.pillpal.web.rest.errors.BadRequestAlertException;
import com.medicai.pillpal.service.dto.BreastFeedingDTO;

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
 * REST controller for managing {@link com.medicai.pillpal.domain.BreastFeeding}.
 */
@RestController
@RequestMapping("/api")
public class BreastFeedingResource {

    private final Logger log = LoggerFactory.getLogger(BreastFeedingResource.class);

    private static final String ENTITY_NAME = "breastFeeding";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BreastFeedingService breastFeedingService;

    public BreastFeedingResource(BreastFeedingService breastFeedingService) {
        this.breastFeedingService = breastFeedingService;
    }

    /**
     * {@code POST  /breast-feedings} : Create a new breastFeeding.
     *
     * @param breastFeedingDTO the breastFeedingDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new breastFeedingDTO, or with status {@code 400 (Bad Request)} if the breastFeeding has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/breast-feedings")
    public ResponseEntity<BreastFeedingDTO> createBreastFeeding(@RequestBody BreastFeedingDTO breastFeedingDTO) throws URISyntaxException {
        log.debug("REST request to save BreastFeeding : {}", breastFeedingDTO);
        if (breastFeedingDTO.getId() != null) {
            throw new BadRequestAlertException("A new breastFeeding cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BreastFeedingDTO result = breastFeedingService.save(breastFeedingDTO);
        return ResponseEntity.created(new URI("/api/breast-feedings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /breast-feedings} : Updates an existing breastFeeding.
     *
     * @param breastFeedingDTO the breastFeedingDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated breastFeedingDTO,
     * or with status {@code 400 (Bad Request)} if the breastFeedingDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the breastFeedingDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/breast-feedings")
    public ResponseEntity<BreastFeedingDTO> updateBreastFeeding(@RequestBody BreastFeedingDTO breastFeedingDTO) throws URISyntaxException {
        log.debug("REST request to update BreastFeeding : {}", breastFeedingDTO);
        if (breastFeedingDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BreastFeedingDTO result = breastFeedingService.save(breastFeedingDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, breastFeedingDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /breast-feedings} : get all the breastFeedings.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of breastFeedings in body.
     */
    @GetMapping("/breast-feedings")
    public ResponseEntity<List<BreastFeedingDTO>> getAllBreastFeedings(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of BreastFeedings");
        Page<BreastFeedingDTO> page = breastFeedingService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /breast-feedings/:id} : get the "id" breastFeeding.
     *
     * @param id the id of the breastFeedingDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the breastFeedingDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/breast-feedings/{id}")
    public ResponseEntity<BreastFeedingDTO> getBreastFeeding(@PathVariable Long id) {
        log.debug("REST request to get BreastFeeding : {}", id);
        Optional<BreastFeedingDTO> breastFeedingDTO = breastFeedingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(breastFeedingDTO);
    }

    /**
     * {@code DELETE  /breast-feedings/:id} : delete the "id" breastFeeding.
     *
     * @param id the id of the breastFeedingDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/breast-feedings/{id}")
    public ResponseEntity<Void> deleteBreastFeeding(@PathVariable Long id) {
        log.debug("REST request to delete BreastFeeding : {}", id);
        breastFeedingService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
