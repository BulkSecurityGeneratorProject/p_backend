package com.medicai.pillpal.web.rest;

import com.medicai.pillpal.service.ConsumptionPrecoutionService;
import com.medicai.pillpal.web.rest.errors.BadRequestAlertException;
import com.medicai.pillpal.service.dto.ConsumptionPrecoutionDTO;

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
 * REST controller for managing {@link com.medicai.pillpal.domain.ConsumptionPrecoution}.
 */
@RestController
@RequestMapping("/api")
public class ConsumptionPrecoutionResource {

    private final Logger log = LoggerFactory.getLogger(ConsumptionPrecoutionResource.class);

    private static final String ENTITY_NAME = "consumptionPrecoution";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ConsumptionPrecoutionService consumptionPrecoutionService;

    public ConsumptionPrecoutionResource(ConsumptionPrecoutionService consumptionPrecoutionService) {
        this.consumptionPrecoutionService = consumptionPrecoutionService;
    }

    /**
     * {@code POST  /consumption-precoutions} : Create a new consumptionPrecoution.
     *
     * @param consumptionPrecoutionDTO the consumptionPrecoutionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new consumptionPrecoutionDTO, or with status {@code 400 (Bad Request)} if the consumptionPrecoution has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/consumption-precoutions")
    public ResponseEntity<ConsumptionPrecoutionDTO> createConsumptionPrecoution(@RequestBody ConsumptionPrecoutionDTO consumptionPrecoutionDTO) throws URISyntaxException {
        log.debug("REST request to save ConsumptionPrecoution : {}", consumptionPrecoutionDTO);
        if (consumptionPrecoutionDTO.getId() != null) {
            throw new BadRequestAlertException("A new consumptionPrecoution cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ConsumptionPrecoutionDTO result = consumptionPrecoutionService.save(consumptionPrecoutionDTO);
        return ResponseEntity.created(new URI("/api/consumption-precoutions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /consumption-precoutions} : Updates an existing consumptionPrecoution.
     *
     * @param consumptionPrecoutionDTO the consumptionPrecoutionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated consumptionPrecoutionDTO,
     * or with status {@code 400 (Bad Request)} if the consumptionPrecoutionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the consumptionPrecoutionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/consumption-precoutions")
    public ResponseEntity<ConsumptionPrecoutionDTO> updateConsumptionPrecoution(@RequestBody ConsumptionPrecoutionDTO consumptionPrecoutionDTO) throws URISyntaxException {
        log.debug("REST request to update ConsumptionPrecoution : {}", consumptionPrecoutionDTO);
        if (consumptionPrecoutionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ConsumptionPrecoutionDTO result = consumptionPrecoutionService.save(consumptionPrecoutionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, consumptionPrecoutionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /consumption-precoutions} : get all the consumptionPrecoutions.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of consumptionPrecoutions in body.
     */
    @GetMapping("/consumption-precoutions")
    public ResponseEntity<List<ConsumptionPrecoutionDTO>> getAllConsumptionPrecoutions(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of ConsumptionPrecoutions");
        Page<ConsumptionPrecoutionDTO> page = consumptionPrecoutionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /consumption-precoutions/:id} : get the "id" consumptionPrecoution.
     *
     * @param id the id of the consumptionPrecoutionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the consumptionPrecoutionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/consumption-precoutions/{id}")
    public ResponseEntity<ConsumptionPrecoutionDTO> getConsumptionPrecoution(@PathVariable Long id) {
        log.debug("REST request to get ConsumptionPrecoution : {}", id);
        Optional<ConsumptionPrecoutionDTO> consumptionPrecoutionDTO = consumptionPrecoutionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(consumptionPrecoutionDTO);
    }

    /**
     * {@code DELETE  /consumption-precoutions/:id} : delete the "id" consumptionPrecoution.
     *
     * @param id the id of the consumptionPrecoutionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/consumption-precoutions/{id}")
    public ResponseEntity<Void> deleteConsumptionPrecoution(@PathVariable Long id) {
        log.debug("REST request to delete ConsumptionPrecoution : {}", id);
        consumptionPrecoutionService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
