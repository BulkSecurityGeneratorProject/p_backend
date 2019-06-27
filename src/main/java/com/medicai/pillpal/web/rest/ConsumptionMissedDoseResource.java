package com.medicai.pillpal.web.rest;

import com.medicai.pillpal.service.ConsumptionMissedDoseService;
import com.medicai.pillpal.web.rest.errors.BadRequestAlertException;
import com.medicai.pillpal.service.dto.ConsumptionMissedDoseDTO;

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
 * REST controller for managing {@link com.medicai.pillpal.domain.ConsumptionMissedDose}.
 */
@RestController
@RequestMapping("/api")
public class ConsumptionMissedDoseResource {

    private final Logger log = LoggerFactory.getLogger(ConsumptionMissedDoseResource.class);

    private static final String ENTITY_NAME = "consumptionMissedDose";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ConsumptionMissedDoseService consumptionMissedDoseService;

    public ConsumptionMissedDoseResource(ConsumptionMissedDoseService consumptionMissedDoseService) {
        this.consumptionMissedDoseService = consumptionMissedDoseService;
    }

    /**
     * {@code POST  /consumption-missed-doses} : Create a new consumptionMissedDose.
     *
     * @param consumptionMissedDoseDTO the consumptionMissedDoseDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new consumptionMissedDoseDTO, or with status {@code 400 (Bad Request)} if the consumptionMissedDose has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/consumption-missed-doses")
    public ResponseEntity<ConsumptionMissedDoseDTO> createConsumptionMissedDose(@RequestBody ConsumptionMissedDoseDTO consumptionMissedDoseDTO) throws URISyntaxException {
        log.debug("REST request to save ConsumptionMissedDose : {}", consumptionMissedDoseDTO);
        if (consumptionMissedDoseDTO.getId() != null) {
            throw new BadRequestAlertException("A new consumptionMissedDose cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ConsumptionMissedDoseDTO result = consumptionMissedDoseService.save(consumptionMissedDoseDTO);
        return ResponseEntity.created(new URI("/api/consumption-missed-doses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /consumption-missed-doses} : Updates an existing consumptionMissedDose.
     *
     * @param consumptionMissedDoseDTO the consumptionMissedDoseDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated consumptionMissedDoseDTO,
     * or with status {@code 400 (Bad Request)} if the consumptionMissedDoseDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the consumptionMissedDoseDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/consumption-missed-doses")
    public ResponseEntity<ConsumptionMissedDoseDTO> updateConsumptionMissedDose(@RequestBody ConsumptionMissedDoseDTO consumptionMissedDoseDTO) throws URISyntaxException {
        log.debug("REST request to update ConsumptionMissedDose : {}", consumptionMissedDoseDTO);
        if (consumptionMissedDoseDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ConsumptionMissedDoseDTO result = consumptionMissedDoseService.save(consumptionMissedDoseDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, consumptionMissedDoseDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /consumption-missed-doses} : get all the consumptionMissedDoses.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of consumptionMissedDoses in body.
     */
    @GetMapping("/consumption-missed-doses")
    public ResponseEntity<List<ConsumptionMissedDoseDTO>> getAllConsumptionMissedDoses(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of ConsumptionMissedDoses");
        Page<ConsumptionMissedDoseDTO> page = consumptionMissedDoseService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /consumption-missed-doses/:id} : get the "id" consumptionMissedDose.
     *
     * @param id the id of the consumptionMissedDoseDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the consumptionMissedDoseDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/consumption-missed-doses/{id}")
    public ResponseEntity<ConsumptionMissedDoseDTO> getConsumptionMissedDose(@PathVariable Long id) {
        log.debug("REST request to get ConsumptionMissedDose : {}", id);
        Optional<ConsumptionMissedDoseDTO> consumptionMissedDoseDTO = consumptionMissedDoseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(consumptionMissedDoseDTO);
    }

    /**
     * {@code DELETE  /consumption-missed-doses/:id} : delete the "id" consumptionMissedDose.
     *
     * @param id the id of the consumptionMissedDoseDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/consumption-missed-doses/{id}")
    public ResponseEntity<Void> deleteConsumptionMissedDose(@PathVariable Long id) {
        log.debug("REST request to delete ConsumptionMissedDose : {}", id);
        consumptionMissedDoseService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
