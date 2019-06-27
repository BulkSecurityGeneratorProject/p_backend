package com.medicai.pillpal.web.rest;

import com.medicai.pillpal.service.UseAndStorageService;
import com.medicai.pillpal.web.rest.errors.BadRequestAlertException;
import com.medicai.pillpal.service.dto.UseAndStorageDTO;

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
 * REST controller for managing {@link com.medicai.pillpal.domain.UseAndStorage}.
 */
@RestController
@RequestMapping("/api")
public class UseAndStorageResource {

    private final Logger log = LoggerFactory.getLogger(UseAndStorageResource.class);

    private static final String ENTITY_NAME = "useAndStorage";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UseAndStorageService useAndStorageService;

    public UseAndStorageResource(UseAndStorageService useAndStorageService) {
        this.useAndStorageService = useAndStorageService;
    }

    /**
     * {@code POST  /use-and-storages} : Create a new useAndStorage.
     *
     * @param useAndStorageDTO the useAndStorageDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new useAndStorageDTO, or with status {@code 400 (Bad Request)} if the useAndStorage has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/use-and-storages")
    public ResponseEntity<UseAndStorageDTO> createUseAndStorage(@RequestBody UseAndStorageDTO useAndStorageDTO) throws URISyntaxException {
        log.debug("REST request to save UseAndStorage : {}", useAndStorageDTO);
        if (useAndStorageDTO.getId() != null) {
            throw new BadRequestAlertException("A new useAndStorage cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UseAndStorageDTO result = useAndStorageService.save(useAndStorageDTO);
        return ResponseEntity.created(new URI("/api/use-and-storages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /use-and-storages} : Updates an existing useAndStorage.
     *
     * @param useAndStorageDTO the useAndStorageDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated useAndStorageDTO,
     * or with status {@code 400 (Bad Request)} if the useAndStorageDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the useAndStorageDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/use-and-storages")
    public ResponseEntity<UseAndStorageDTO> updateUseAndStorage(@RequestBody UseAndStorageDTO useAndStorageDTO) throws URISyntaxException {
        log.debug("REST request to update UseAndStorage : {}", useAndStorageDTO);
        if (useAndStorageDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UseAndStorageDTO result = useAndStorageService.save(useAndStorageDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, useAndStorageDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /use-and-storages} : get all the useAndStorages.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of useAndStorages in body.
     */
    @GetMapping("/use-and-storages")
    public ResponseEntity<List<UseAndStorageDTO>> getAllUseAndStorages(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of UseAndStorages");
        Page<UseAndStorageDTO> page = useAndStorageService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /use-and-storages/:id} : get the "id" useAndStorage.
     *
     * @param id the id of the useAndStorageDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the useAndStorageDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/use-and-storages/{id}")
    public ResponseEntity<UseAndStorageDTO> getUseAndStorage(@PathVariable Long id) {
        log.debug("REST request to get UseAndStorage : {}", id);
        Optional<UseAndStorageDTO> useAndStorageDTO = useAndStorageService.findOne(id);
        return ResponseUtil.wrapOrNotFound(useAndStorageDTO);
    }

    /**
     * {@code DELETE  /use-and-storages/:id} : delete the "id" useAndStorage.
     *
     * @param id the id of the useAndStorageDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/use-and-storages/{id}")
    public ResponseEntity<Void> deleteUseAndStorage(@PathVariable Long id) {
        log.debug("REST request to delete UseAndStorage : {}", id);
        useAndStorageService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
