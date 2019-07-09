package com.medicai.pillpal.web.rest;

import com.medicai.pillpal.service.PregnancyService;
import com.medicai.pillpal.service.dto.PregnancyDTO;
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
 * REST controller for managing {@link com.medicai.pillpal.domain.Pregnancy}.
 */
@RestController
@RequestMapping("/api")
public class PregnancyResource {

    private final Logger log = LoggerFactory.getLogger(PregnancyResource.class);

    private static final String ENTITY_NAME = "pregnancy";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PregnancyService pregnancyService;

    public PregnancyResource(PregnancyService pregnancyService) {
        this.pregnancyService = pregnancyService;
    }

    /**
     * {@code POST  /pregnancies} : Create a new pregnancy.
     *
     * @param pregnancyDTO the pregnancyDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pregnancyDTO, or with status {@code 400 (Bad Request)} if the pregnancy has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/pregnancies")
    public ResponseEntity<PregnancyDTO> createPregnancy(@RequestBody PregnancyDTO pregnancyDTO) throws URISyntaxException {
        log.debug("REST request to save Pregnancy : {}", pregnancyDTO);
        if (pregnancyDTO.getId() != null) {
            throw new BadRequestAlertException("A new pregnancy cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PregnancyDTO result = pregnancyService.save(pregnancyDTO);
        return ResponseEntity.created(new URI("/api/pregnancies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /pregnancies} : Updates an existing pregnancy.
     *
     * @param pregnancyDTO the pregnancyDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pregnancyDTO,
     * or with status {@code 400 (Bad Request)} if the pregnancyDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pregnancyDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/pregnancies")
    public ResponseEntity<PregnancyDTO> updatePregnancy(@RequestBody PregnancyDTO pregnancyDTO) throws URISyntaxException {
        log.debug("REST request to update Pregnancy : {}", pregnancyDTO);
        if (pregnancyDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PregnancyDTO result = pregnancyService.save(pregnancyDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, pregnancyDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /pregnancies} : get all the pregnancies.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pregnancies in body.
     */
    @GetMapping("/pregnancies")
    public ResponseEntity<List<PregnancyDTO>> getAllPregnancies(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of Pregnancies");
        Page<PregnancyDTO> page = pregnancyService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /pregnancies/:id} : get the "id" pregnancy.
     *
     * @param id the id of the pregnancyDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pregnancyDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/pregnancies/{id}")
    public ResponseEntity<PregnancyDTO> getPregnancy(@PathVariable Long id) {
        log.debug("REST request to get Pregnancy : {}", id);
        Optional<PregnancyDTO> pregnancyDTO = pregnancyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(pregnancyDTO);
    }

    /**
     * {@code DELETE  /pregnancies/:id} : delete the "id" pregnancy.
     *
     * @param id the id of the pregnancyDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/pregnancies/{id}")
    public ResponseEntity<Void> deletePregnancy(@PathVariable Long id) {
        log.debug("REST request to delete Pregnancy : {}", id);
        pregnancyService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * Get a Generic Name
     * @param genericName
     * @return the ResponseEntity with status 200 (OK) and the list of notificationHistories in body
     */
    @GetMapping("/pregnancies/by-generic-name")
    public ResponseEntity<PregnancyDTO> getPregnancyByGenericName(@RequestBody String genericName) {
        log.debug("REST request to delete SideEffect : {}");
        Optional<PregnancyDTO> pregnancyDTO = pregnancyService.findByGenericName(genericName);
        return ResponseUtil.wrapOrNotFound(pregnancyDTO);
    }

    /**
     * Get List of Generic Name
     * @param pageable
     * @param genericName
     * @param uriBuilder
     * @return the ResponseEntity with status 200 (OK) and the list of notificationHistories in body
     */
    @GetMapping("/pregnancies/by-generic-name-list")
    public ResponseEntity<List<PregnancyDTO>> getGenericNameListAllergy(Pageable pageable, @RequestBody List<String> genericName, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to delete SideEffect : {}");
        Page<PregnancyDTO> page = pregnancyService.findByGenericNameList(pageable, genericName);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder, page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }


}
