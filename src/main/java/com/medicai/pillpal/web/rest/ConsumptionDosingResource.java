package com.medicai.pillpal.web.rest;

import com.medicai.pillpal.service.ConsumptionDosingService;
import com.medicai.pillpal.service.dto.ConsumptionDosingDTO;
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
 * REST controller for managing {@link com.medicai.pillpal.domain.ConsumptionDosing}.
 */
@RestController
@RequestMapping("/api")
public class ConsumptionDosingResource {

    private final Logger log = LoggerFactory.getLogger(ConsumptionDosingResource.class);

    private static final String ENTITY_NAME = "consumptionDosing";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ConsumptionDosingService consumptionDosingService;

    public ConsumptionDosingResource(ConsumptionDosingService consumptionDosingService) {
        this.consumptionDosingService = consumptionDosingService;
    }

    /**
     * {@code POST  /consumption-dosings} : Create a new consumptionDosing.
     *
     * @param consumptionDosingDTO the consumptionDosingDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new consumptionDosingDTO, or with status {@code 400 (Bad Request)} if the consumptionDosing has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/consumption-dosings")
    public ResponseEntity<ConsumptionDosingDTO> createConsumptionDosing(@RequestBody ConsumptionDosingDTO consumptionDosingDTO) throws URISyntaxException {
        log.debug("REST request to save ConsumptionDosing : {}", consumptionDosingDTO);
        if (consumptionDosingDTO.getId() != null) {
            throw new BadRequestAlertException("A new consumptionDosing cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ConsumptionDosingDTO result = consumptionDosingService.save(consumptionDosingDTO);
        return ResponseEntity.created(new URI("/api/consumption-dosings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /consumption-dosings} : Updates an existing consumptionDosing.
     *
     * @param consumptionDosingDTO the consumptionDosingDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated consumptionDosingDTO,
     * or with status {@code 400 (Bad Request)} if the consumptionDosingDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the consumptionDosingDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/consumption-dosings")
    public ResponseEntity<ConsumptionDosingDTO> updateConsumptionDosing(@RequestBody ConsumptionDosingDTO consumptionDosingDTO) throws URISyntaxException {
        log.debug("REST request to update ConsumptionDosing : {}", consumptionDosingDTO);
        if (consumptionDosingDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ConsumptionDosingDTO result = consumptionDosingService.save(consumptionDosingDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, consumptionDosingDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /consumption-dosings} : get all the consumptionDosings.
     *
     * @param pageable    the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder  a {@link UriComponentsBuilder} URI builder.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of consumptionDosings in body.
     */
    @GetMapping("/consumption-dosings")
    public ResponseEntity<List<ConsumptionDosingDTO>> getAllConsumptionDosings(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of ConsumptionDosings");
        Page<ConsumptionDosingDTO> page = consumptionDosingService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /consumption-dosings/:id} : get the "id" consumptionDosing.
     *
     * @param id the id of the consumptionDosingDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the consumptionDosingDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/consumption-dosings/{id}")
    public ResponseEntity<ConsumptionDosingDTO> getConsumptionDosing(@PathVariable Long id) {
        log.debug("REST request to get ConsumptionDosing : {}", id);
        Optional<ConsumptionDosingDTO> consumptionDosingDTO = consumptionDosingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(consumptionDosingDTO);
    }

    /**
     * {@code DELETE  /consumption-dosings/:id} : delete the "id" consumptionDosing.
     *
     * @param id the id of the consumptionDosingDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/consumption-dosings/{id}")
    public ResponseEntity<Void> deleteConsumptionDosing(@PathVariable Long id) {
        log.debug("REST request to delete ConsumptionDosing : {}", id);
        consumptionDosingService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
