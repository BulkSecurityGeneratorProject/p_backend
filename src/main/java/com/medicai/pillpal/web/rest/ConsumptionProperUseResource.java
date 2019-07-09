package com.medicai.pillpal.web.rest;

import com.medicai.pillpal.service.ConsumptionProperUseService;
import com.medicai.pillpal.service.dto.ConsumptionProperUseDTO;
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
 * REST controller for managing {@link com.medicai.pillpal.domain.ConsumptionProperUse}.
 */
@RestController
@RequestMapping("/api")
public class ConsumptionProperUseResource {

    private final Logger log = LoggerFactory.getLogger(ConsumptionProperUseResource.class);

    private static final String ENTITY_NAME = "consumptionProperUse";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ConsumptionProperUseService consumptionProperUseService;

    public ConsumptionProperUseResource(ConsumptionProperUseService consumptionProperUseService) {
        this.consumptionProperUseService = consumptionProperUseService;
    }

    /**
     * {@code POST  /consumption-proper-uses} : Create a new consumptionProperUse.
     *
     * @param consumptionProperUseDTO the consumptionProperUseDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new consumptionProperUseDTO, or with status {@code 400 (Bad Request)} if the consumptionProperUse has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/consumption-proper-uses")
    public ResponseEntity<ConsumptionProperUseDTO> createConsumptionProperUse(@RequestBody ConsumptionProperUseDTO consumptionProperUseDTO) throws URISyntaxException {
        log.debug("REST request to save ConsumptionProperUse : {}", consumptionProperUseDTO);
        if (consumptionProperUseDTO.getId() != null) {
            throw new BadRequestAlertException("A new consumptionProperUse cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ConsumptionProperUseDTO result = consumptionProperUseService.save(consumptionProperUseDTO);
        return ResponseEntity.created(new URI("/api/consumption-proper-uses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /consumption-proper-uses} : Updates an existing consumptionProperUse.
     *
     * @param consumptionProperUseDTO the consumptionProperUseDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated consumptionProperUseDTO,
     * or with status {@code 400 (Bad Request)} if the consumptionProperUseDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the consumptionProperUseDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/consumption-proper-uses")
    public ResponseEntity<ConsumptionProperUseDTO> updateConsumptionProperUse(@RequestBody ConsumptionProperUseDTO consumptionProperUseDTO) throws URISyntaxException {
        log.debug("REST request to update ConsumptionProperUse : {}", consumptionProperUseDTO);
        if (consumptionProperUseDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ConsumptionProperUseDTO result = consumptionProperUseService.save(consumptionProperUseDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, consumptionProperUseDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /consumption-proper-uses} : get all the consumptionProperUses.
     *
     * @param pageable    the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder  a {@link UriComponentsBuilder} URI builder.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of consumptionProperUses in body.
     */
    @GetMapping("/consumption-proper-uses")
    public ResponseEntity<List<ConsumptionProperUseDTO>> getAllConsumptionProperUses(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of ConsumptionProperUses");
        Page<ConsumptionProperUseDTO> page = consumptionProperUseService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /consumption-proper-uses/:id} : get the "id" consumptionProperUse.
     *
     * @param id the id of the consumptionProperUseDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the consumptionProperUseDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/consumption-proper-uses/{id}")
    public ResponseEntity<ConsumptionProperUseDTO> getConsumptionProperUse(@PathVariable Long id) {
        log.debug("REST request to get ConsumptionProperUse : {}", id);
        Optional<ConsumptionProperUseDTO> consumptionProperUseDTO = consumptionProperUseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(consumptionProperUseDTO);
    }

    /**
     * {@code DELETE  /consumption-proper-uses/:id} : delete the "id" consumptionProperUse.
     *
     * @param id the id of the consumptionProperUseDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/consumption-proper-uses/{id}")
    public ResponseEntity<Void> deleteConsumptionProperUse(@PathVariable Long id) {
        log.debug("REST request to delete ConsumptionProperUse : {}", id);
        consumptionProperUseService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
