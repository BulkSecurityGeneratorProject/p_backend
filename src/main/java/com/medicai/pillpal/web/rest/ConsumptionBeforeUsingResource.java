package com.medicai.pillpal.web.rest;

import com.medicai.pillpal.service.ConsumptionBeforeUsingService;
import com.medicai.pillpal.service.dto.ConsumptionBeforeUsingDTO;
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
 * REST controller for managing {@link com.medicai.pillpal.domain.ConsumptionBeforeUsing}.
 */
@RestController
@RequestMapping("/api")
public class ConsumptionBeforeUsingResource {

    private final Logger log = LoggerFactory.getLogger(ConsumptionBeforeUsingResource.class);

    private static final String ENTITY_NAME = "consumptionBeforeUsing";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ConsumptionBeforeUsingService consumptionBeforeUsingService;

    public ConsumptionBeforeUsingResource(ConsumptionBeforeUsingService consumptionBeforeUsingService) {
        this.consumptionBeforeUsingService = consumptionBeforeUsingService;
    }

    /**
     * {@code POST  /consumption-before-usings} : Create a new consumptionBeforeUsing.
     *
     * @param consumptionBeforeUsingDTO the consumptionBeforeUsingDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new consumptionBeforeUsingDTO, or with status {@code 400 (Bad Request)} if the consumptionBeforeUsing has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/consumption-before-usings")
    public ResponseEntity<ConsumptionBeforeUsingDTO> createConsumptionBeforeUsing(@RequestBody ConsumptionBeforeUsingDTO consumptionBeforeUsingDTO) throws URISyntaxException {
        log.debug("REST request to save ConsumptionBeforeUsing : {}", consumptionBeforeUsingDTO);
        if (consumptionBeforeUsingDTO.getId() != null) {
            throw new BadRequestAlertException("A new consumptionBeforeUsing cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ConsumptionBeforeUsingDTO result = consumptionBeforeUsingService.save(consumptionBeforeUsingDTO);
        return ResponseEntity.created(new URI("/api/consumption-before-usings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /consumption-before-usings} : Updates an existing consumptionBeforeUsing.
     *
     * @param consumptionBeforeUsingDTO the consumptionBeforeUsingDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated consumptionBeforeUsingDTO,
     * or with status {@code 400 (Bad Request)} if the consumptionBeforeUsingDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the consumptionBeforeUsingDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/consumption-before-usings")
    public ResponseEntity<ConsumptionBeforeUsingDTO> updateConsumptionBeforeUsing(@RequestBody ConsumptionBeforeUsingDTO consumptionBeforeUsingDTO) throws URISyntaxException {
        log.debug("REST request to update ConsumptionBeforeUsing : {}", consumptionBeforeUsingDTO);
        if (consumptionBeforeUsingDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ConsumptionBeforeUsingDTO result = consumptionBeforeUsingService.save(consumptionBeforeUsingDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, consumptionBeforeUsingDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /consumption-before-usings} : get all the consumptionBeforeUsings.
     *
     * @param pageable    the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder  a {@link UriComponentsBuilder} URI builder.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of consumptionBeforeUsings in body.
     */
    @GetMapping("/consumption-before-usings")
    public ResponseEntity<List<ConsumptionBeforeUsingDTO>> getAllConsumptionBeforeUsings(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of ConsumptionBeforeUsings");
        Page<ConsumptionBeforeUsingDTO> page = consumptionBeforeUsingService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /consumption-before-usings/:id} : get the "id" consumptionBeforeUsing.
     *
     * @param id the id of the consumptionBeforeUsingDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the consumptionBeforeUsingDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/consumption-before-usings/{id}")
    public ResponseEntity<ConsumptionBeforeUsingDTO> getConsumptionBeforeUsing(@PathVariable Long id) {
        log.debug("REST request to get ConsumptionBeforeUsing : {}", id);
        Optional<ConsumptionBeforeUsingDTO> consumptionBeforeUsingDTO = consumptionBeforeUsingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(consumptionBeforeUsingDTO);
    }

    /**
     * {@code DELETE  /consumption-before-usings/:id} : delete the "id" consumptionBeforeUsing.
     *
     * @param id the id of the consumptionBeforeUsingDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/consumption-before-usings/{id}")
    public ResponseEntity<Void> deleteConsumptionBeforeUsing(@PathVariable Long id) {
        log.debug("REST request to delete ConsumptionBeforeUsing : {}", id);
        consumptionBeforeUsingService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
