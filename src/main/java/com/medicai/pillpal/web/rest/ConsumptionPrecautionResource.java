package com.medicai.pillpal.web.rest;

import com.medicai.pillpal.service.ConsumptionPrecautionService;
import com.medicai.pillpal.service.dto.ConsumptionPrecautionDTO;
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
 * REST controller for managing {@link com.medicai.pillpal.domain.ConsumptionPrecaution}.
 */
@RestController
@RequestMapping("/api")
public class ConsumptionPrecautionResource {

    private final Logger log = LoggerFactory.getLogger(ConsumptionPrecautionResource.class);

    private static final String ENTITY_NAME = "consumptionPrecaution";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ConsumptionPrecautionService consumptionPrecautionService;

    public ConsumptionPrecautionResource(ConsumptionPrecautionService consumptionPrecautionService) {
        this.consumptionPrecautionService = consumptionPrecautionService;
    }

    /**
     * {@code POST  /consumption-precautions} : Create a new consumptionPrecaution.
     *
     * @param consumptionPrecautionDTO the consumptionPrecautionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new consumptionPrecautionDTO, or with status {@code 400 (Bad Request)} if the consumptionPrecaution has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/consumption-precautions")
    public ResponseEntity<ConsumptionPrecautionDTO> createConsumptionPrecaution(@RequestBody ConsumptionPrecautionDTO consumptionPrecautionDTO) throws URISyntaxException {
        log.debug("REST request to save ConsumptionPrecaution : {}", consumptionPrecautionDTO);
        if (consumptionPrecautionDTO.getId() != null) {
            throw new BadRequestAlertException("A new consumptionPrecaution cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ConsumptionPrecautionDTO result = consumptionPrecautionService.save(consumptionPrecautionDTO);
        return ResponseEntity.created(new URI("/api/consumption-precautions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /consumption-precautions} : Updates an existing consumptionPrecaution.
     *
     * @param consumptionPrecautionDTO the consumptionPrecautionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated consumptionPrecautionDTO,
     * or with status {@code 400 (Bad Request)} if the consumptionPrecautionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the consumptionPrecautionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/consumption-precautions")
    public ResponseEntity<ConsumptionPrecautionDTO> updateConsumptionPrecaution(@RequestBody ConsumptionPrecautionDTO consumptionPrecautionDTO) throws URISyntaxException {
        log.debug("REST request to update ConsumptionPrecaution : {}", consumptionPrecautionDTO);
        if (consumptionPrecautionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ConsumptionPrecautionDTO result = consumptionPrecautionService.save(consumptionPrecautionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, consumptionPrecautionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /consumption-precautions} : get all the consumptionPrecautions.
     *
     * @param pageable    the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder  a {@link UriComponentsBuilder} URI builder.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of consumptionPrecautions in body.
     */
    @GetMapping("/consumption-precautions")
    public ResponseEntity<List<ConsumptionPrecautionDTO>> getAllConsumptionPrecautions(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of ConsumptionPrecautions");
        Page<ConsumptionPrecautionDTO> page = consumptionPrecautionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /consumption-precautions/:id} : get the "id" consumptionPrecaution.
     *
     * @param id the id of the consumptionPrecautionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the consumptionPrecautionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/consumption-precautions/{id}")
    public ResponseEntity<ConsumptionPrecautionDTO> getConsumptionPrecaution(@PathVariable Long id) {
        log.debug("REST request to get ConsumptionPrecaution : {}", id);
        Optional<ConsumptionPrecautionDTO> consumptionPrecautionDTO = consumptionPrecautionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(consumptionPrecautionDTO);
    }

    /**
     * {@code DELETE  /consumption-precautions/:id} : delete the "id" consumptionPrecaution.
     *
     * @param id the id of the consumptionPrecautionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/consumption-precautions/{id}")
    public ResponseEntity<Void> deleteConsumptionPrecaution(@PathVariable Long id) {
        log.debug("REST request to delete ConsumptionPrecaution : {}", id);
        consumptionPrecautionService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
