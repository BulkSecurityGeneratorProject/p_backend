package com.medicai.pillpal.web.rest;

import com.medicai.pillpal.service.GeriatricService;
import com.medicai.pillpal.service.dto.GeriatricDTO;
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
 * REST controller for managing {@link com.medicai.pillpal.domain.Geriatric}.
 */
@RestController
@RequestMapping("/api")
public class GeriatricResource {

    private final Logger log = LoggerFactory.getLogger(GeriatricResource.class);

    private static final String ENTITY_NAME = "geriatric";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GeriatricService geriatricService;

    public GeriatricResource(GeriatricService geriatricService) {
        this.geriatricService = geriatricService;
    }

    /**
     * {@code POST  /geriatrics} : Create a new geriatric.
     *
     * @param geriatricDTO the geriatricDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new geriatricDTO, or with status {@code 400 (Bad Request)} if the geriatric has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/geriatrics")
    public ResponseEntity<GeriatricDTO> createGeriatric(@RequestBody GeriatricDTO geriatricDTO) throws URISyntaxException {
        log.debug("REST request to save Geriatric : {}", geriatricDTO);
        if (geriatricDTO.getId() != null) {
            throw new BadRequestAlertException("A new geriatric cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GeriatricDTO result = geriatricService.save(geriatricDTO);
        return ResponseEntity.created(new URI("/api/geriatrics/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /geriatrics} : Updates an existing geriatric.
     *
     * @param geriatricDTO the geriatricDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated geriatricDTO,
     * or with status {@code 400 (Bad Request)} if the geriatricDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the geriatricDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/geriatrics")
    public ResponseEntity<GeriatricDTO> updateGeriatric(@RequestBody GeriatricDTO geriatricDTO) throws URISyntaxException {
        log.debug("REST request to update Geriatric : {}", geriatricDTO);
        if (geriatricDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        GeriatricDTO result = geriatricService.save(geriatricDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, geriatricDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /geriatrics} : get all the geriatrics.
     *
     * @param pageable    the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder  a {@link UriComponentsBuilder} URI builder.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of geriatrics in body.
     */
    @GetMapping("/geriatrics")
    public ResponseEntity<List<GeriatricDTO>> getAllGeriatrics(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of Geriatrics");
        Page<GeriatricDTO> page = geriatricService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /geriatrics/:id} : get the "id" geriatric.
     *
     * @param id the id of the geriatricDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the geriatricDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/geriatrics/{id}")
    public ResponseEntity<GeriatricDTO> getGeriatric(@PathVariable Long id) {
        log.debug("REST request to get Geriatric : {}", id);
        Optional<GeriatricDTO> geriatricDTO = geriatricService.findOne(id);
        return ResponseUtil.wrapOrNotFound(geriatricDTO);
    }

    /**
     * {@code DELETE  /geriatrics/:id} : delete the "id" geriatric.
     *
     * @param id the id of the geriatricDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/geriatrics/{id}")
    public ResponseEntity<Void> deleteGeriatric(@PathVariable Long id) {
        log.debug("REST request to delete Geriatric : {}", id);
        geriatricService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * Get List of Generic Name
     *
     * @param pageable
     * @param genericNameList
     * @param uriBuilder
     * @return the ResponseEntity with status 200 (OK) and the list of notificationHistories in body
     */
    @GetMapping("/geriatric/by-generic-name-list")
    public ResponseEntity<List<GeriatricDTO>> getGenericNameListGeriatric(Pageable pageable,
                                                                          @RequestBody List<String> genericNameList, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to delete SideEffect : {}");
        Page<GeriatricDTO> page = geriatricService.findGeriatricByGenericNameList(pageable, genericNameList);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder, page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * get a generic name
     *
     * @param genericName
     * @return the ResponseEntity with status 200 (OK) and the list of notificationHistories in body
     */
    @GetMapping("/geriatric/by-generic-name")
    public ResponseEntity<GeriatricDTO> getGenericNameGeriatric(@RequestBody String genericName) {
        log.debug("REST request to delete SideEffect : {}");
        Optional<GeriatricDTO> geriatricDTO = geriatricService.findGeriatricByGenericName(genericName);
        return ResponseUtil.wrapOrNotFound(geriatricDTO);
    }
}
