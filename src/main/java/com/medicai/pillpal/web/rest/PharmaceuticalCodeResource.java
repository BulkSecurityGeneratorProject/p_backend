package com.medicai.pillpal.web.rest;

import com.medicai.pillpal.service.PharmaceuticalCodeService;
import com.medicai.pillpal.web.rest.errors.BadRequestAlertException;
import com.medicai.pillpal.service.dto.PharmaceuticalCodeDTO;

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
 * REST controller for managing {@link com.medicai.pillpal.domain.PharmaceuticalCode}.
 */
@RestController
@RequestMapping("/api")
public class PharmaceuticalCodeResource {

    private final Logger log = LoggerFactory.getLogger(PharmaceuticalCodeResource.class);

    private static final String ENTITY_NAME = "pharmaceuticalCode";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PharmaceuticalCodeService pharmaceuticalCodeService;

    public PharmaceuticalCodeResource(PharmaceuticalCodeService pharmaceuticalCodeService) {
        this.pharmaceuticalCodeService = pharmaceuticalCodeService;
    }

    /**
     * {@code POST  /pharmaceutical-codes} : Create a new pharmaceuticalCode.
     *
     * @param pharmaceuticalCodeDTO the pharmaceuticalCodeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pharmaceuticalCodeDTO, or with status {@code 400 (Bad Request)} if the pharmaceuticalCode has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/pharmaceutical-codes")
    public ResponseEntity<PharmaceuticalCodeDTO> createPharmaceuticalCode(@RequestBody PharmaceuticalCodeDTO pharmaceuticalCodeDTO) throws URISyntaxException {
        log.debug("REST request to save PharmaceuticalCode : {}", pharmaceuticalCodeDTO);
        if (pharmaceuticalCodeDTO.getId() != null) {
            throw new BadRequestAlertException("A new pharmaceuticalCode cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PharmaceuticalCodeDTO result = pharmaceuticalCodeService.save(pharmaceuticalCodeDTO);
        return ResponseEntity.created(new URI("/api/pharmaceutical-codes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /pharmaceutical-codes} : Updates an existing pharmaceuticalCode.
     *
     * @param pharmaceuticalCodeDTO the pharmaceuticalCodeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pharmaceuticalCodeDTO,
     * or with status {@code 400 (Bad Request)} if the pharmaceuticalCodeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pharmaceuticalCodeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/pharmaceutical-codes")
    public ResponseEntity<PharmaceuticalCodeDTO> updatePharmaceuticalCode(@RequestBody PharmaceuticalCodeDTO pharmaceuticalCodeDTO) throws URISyntaxException {
        log.debug("REST request to update PharmaceuticalCode : {}", pharmaceuticalCodeDTO);
        if (pharmaceuticalCodeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PharmaceuticalCodeDTO result = pharmaceuticalCodeService.save(pharmaceuticalCodeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, pharmaceuticalCodeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /pharmaceutical-codes} : get all the pharmaceuticalCodes.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pharmaceuticalCodes in body.
     */
    @GetMapping("/pharmaceutical-codes")
    public ResponseEntity<List<PharmaceuticalCodeDTO>> getAllPharmaceuticalCodes(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of PharmaceuticalCodes");
        Page<PharmaceuticalCodeDTO> page = pharmaceuticalCodeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /pharmaceutical-codes/:id} : get the "id" pharmaceuticalCode.
     *
     * @param id the id of the pharmaceuticalCodeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pharmaceuticalCodeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/pharmaceutical-codes/{id}")
    public ResponseEntity<PharmaceuticalCodeDTO> getPharmaceuticalCode(@PathVariable Long id) {
        log.debug("REST request to get PharmaceuticalCode : {}", id);
        Optional<PharmaceuticalCodeDTO> pharmaceuticalCodeDTO = pharmaceuticalCodeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(pharmaceuticalCodeDTO);
    }

    /**
     * {@code DELETE  /pharmaceutical-codes/:id} : delete the "id" pharmaceuticalCode.
     *
     * @param id the id of the pharmaceuticalCodeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/pharmaceutical-codes/{id}")
    public ResponseEntity<Void> deletePharmaceuticalCode(@PathVariable Long id) {
        log.debug("REST request to delete PharmaceuticalCode : {}", id);
        pharmaceuticalCodeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
