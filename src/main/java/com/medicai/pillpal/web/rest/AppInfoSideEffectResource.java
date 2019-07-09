package com.medicai.pillpal.web.rest;

import com.medicai.pillpal.service.AppInfoSideEffectService;
import com.medicai.pillpal.service.dto.AppInfoSideEffectDTO;
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
 * REST controller for managing {@link com.medicai.pillpal.domain.AppInfoSideEffect}.
 */
@RestController
@RequestMapping("/api")
public class AppInfoSideEffectResource {

    private final Logger log = LoggerFactory.getLogger(AppInfoSideEffectResource.class);

    private static final String ENTITY_NAME = "appInfoSideEffect";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AppInfoSideEffectService appInfoSideEffectService;

    public AppInfoSideEffectResource(AppInfoSideEffectService appInfoSideEffectService) {
        this.appInfoSideEffectService = appInfoSideEffectService;
    }

    /**
     * {@code POST  /app-info-side-effects} : Create a new appInfoSideEffect.
     *
     * @param appInfoSideEffectDTO the appInfoSideEffectDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new appInfoSideEffectDTO, or with status {@code 400 (Bad Request)} if the appInfoSideEffect has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/app-info-side-effects")
    public ResponseEntity<AppInfoSideEffectDTO> createAppInfoSideEffect(@RequestBody AppInfoSideEffectDTO appInfoSideEffectDTO) throws URISyntaxException {
        log.debug("REST request to save AppInfoSideEffect : {}", appInfoSideEffectDTO);
        if (appInfoSideEffectDTO.getId() != null) {
            throw new BadRequestAlertException("A new appInfoSideEffect cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AppInfoSideEffectDTO result = appInfoSideEffectService.save(appInfoSideEffectDTO);
        return ResponseEntity.created(new URI("/api/app-info-side-effects/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /app-info-side-effects} : Updates an existing appInfoSideEffect.
     *
     * @param appInfoSideEffectDTO the appInfoSideEffectDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated appInfoSideEffectDTO,
     * or with status {@code 400 (Bad Request)} if the appInfoSideEffectDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the appInfoSideEffectDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/app-info-side-effects")
    public ResponseEntity<AppInfoSideEffectDTO> updateAppInfoSideEffect(@RequestBody AppInfoSideEffectDTO appInfoSideEffectDTO) throws URISyntaxException {
        log.debug("REST request to update AppInfoSideEffect : {}", appInfoSideEffectDTO);
        if (appInfoSideEffectDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AppInfoSideEffectDTO result = appInfoSideEffectService.save(appInfoSideEffectDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, appInfoSideEffectDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /app-info-side-effects} : get all the appInfoSideEffects.
     *
     * @param pageable    the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder  a {@link UriComponentsBuilder} URI builder.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of appInfoSideEffects in body.
     */
    @GetMapping("/app-info-side-effects")
    public ResponseEntity<List<AppInfoSideEffectDTO>> getAllAppInfoSideEffects(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of AppInfoSideEffects");
        Page<AppInfoSideEffectDTO> page = appInfoSideEffectService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /app-info-side-effects/:id} : get the "id" appInfoSideEffect.
     *
     * @param id the id of the appInfoSideEffectDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the appInfoSideEffectDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/app-info-side-effects/{id}")
    public ResponseEntity<AppInfoSideEffectDTO> getAppInfoSideEffect(@PathVariable Long id) {
        log.debug("REST request to get AppInfoSideEffect : {}", id);
        Optional<AppInfoSideEffectDTO> appInfoSideEffectDTO = appInfoSideEffectService.findOne(id);
        return ResponseUtil.wrapOrNotFound(appInfoSideEffectDTO);
    }

    /**
     * {@code DELETE  /app-info-side-effects/:id} : delete the "id" appInfoSideEffect.
     *
     * @param id the id of the appInfoSideEffectDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/app-info-side-effects/{id}")
    public ResponseEntity<Void> deleteAppInfoSideEffect(@PathVariable Long id) {
        log.debug("REST request to delete AppInfoSideEffect : {}", id);
        appInfoSideEffectService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
