package com.medicai.pillpal.web.rest;

import com.medicai.pillpal.service.SideEffectService;
import com.medicai.pillpal.service.dto.AllergyDTO;
import com.medicai.pillpal.service.dto.GeriatricDTO;
import com.medicai.pillpal.service.dto.SideEffectDTO;
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

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.medicai.pillpal.domain.SideEffect}.
 */
@RestController
@RequestMapping("/api")
public class SideEffectResource {

    private final Logger log = LoggerFactory.getLogger(SideEffectResource.class);

    private static final String ENTITY_NAME = "sideEffect";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SideEffectService sideEffectService;

    public SideEffectResource(SideEffectService sideEffectService) {
        this.sideEffectService = sideEffectService;
    }

    /**
     * {@code POST  /side-effects} : Create a new sideEffect.
     *
     * @param sideEffectDTO the sideEffectDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sideEffectDTO, or with status {@code 400 (Bad Request)} if the sideEffect has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/side-effects")
    public ResponseEntity<SideEffectDTO> createSideEffect(@Valid @RequestBody SideEffectDTO sideEffectDTO) throws URISyntaxException {
        log.debug("REST request to save SideEffect : {}", sideEffectDTO);
        if (sideEffectDTO.getId() != null) {
            throw new BadRequestAlertException("A new sideEffect cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SideEffectDTO result = sideEffectService.save(sideEffectDTO);
        return ResponseEntity.created(new URI("/api/side-effects/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /side-effects} : Updates an existing sideEffect.
     *
     * @param sideEffectDTO the sideEffectDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sideEffectDTO,
     * or with status {@code 400 (Bad Request)} if the sideEffectDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sideEffectDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/side-effects")
    public ResponseEntity<SideEffectDTO> updateSideEffect(@Valid @RequestBody SideEffectDTO sideEffectDTO) throws URISyntaxException {
        log.debug("REST request to update SideEffect : {}", sideEffectDTO);
        if (sideEffectDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SideEffectDTO result = sideEffectService.save(sideEffectDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, sideEffectDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /side-effects} : get all the sideEffects.
     *
     * @param pageable    the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder  a {@link UriComponentsBuilder} URI builder.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sideEffects in body.
     */
    @GetMapping("/side-effects")
    public ResponseEntity<List<SideEffectDTO>> getAllSideEffects(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of SideEffects");
        Page<SideEffectDTO> page = sideEffectService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /side-effects/:id} : get the "id" sideEffect.
     *
     * @param id the id of the sideEffectDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sideEffectDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/side-effects/{id}")
    public ResponseEntity<SideEffectDTO> getSideEffect(@PathVariable Long id) {
        log.debug("REST request to get SideEffect : {}", id);
        Optional<SideEffectDTO> sideEffectDTO = sideEffectService.findOne(id);
        return ResponseUtil.wrapOrNotFound(sideEffectDTO);
    }

    /**
     * {@code DELETE  /side-effects/:id} : delete the "id" sideEffect.
     *
     * @param id the id of the sideEffectDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/side-effects/{id}")
    public ResponseEntity<Void> deleteSideEffect(@PathVariable Long id) {
        log.debug("REST request to delete SideEffect : {}", id);
        sideEffectService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * Get a Generic Name
     *
     * @param genericName
     * @return the ResponseEntity with status 200 (OK) and the list of notificationHistories in body
     */
    @GetMapping("/side-effect/allergy/by-generic-name")
    public ResponseEntity<AllergyDTO> getGenericNameAllergy(@RequestBody String genericName) {
        log.debug("REST request to delete SideEffect : {}");
        Optional<AllergyDTO> allergyDTO = sideEffectService.findAllergyByGenericName(genericName);
        return ResponseUtil.wrapOrNotFound(allergyDTO);
    }

    /**
     * Get List of Generic Name
     *
     * @param pageable
     * @param genericName
     * @param uriBuilder
     * @return the ResponseEntity with status 200 (OK) and the list of notificationHistories in body
     */
    @GetMapping("/side-effect/allergy/by-generic-name-list")
    public ResponseEntity<List<AllergyDTO>> getGenericNameListAllergy(Pageable pageable,
                                                                      @RequestBody List<String> genericName, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to delete SideEffect : {}");
        Page<AllergyDTO> page = sideEffectService.findAllergyByGenericNameList(pageable, genericName);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder, page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * Get List of Generic Name
     *
     * @param pageable
     * @param genericName
     * @param uriBuilder
     * @return the ResponseEntity with status 200 (OK) and the list of notificationHistories in body
     */
    @GetMapping("/side-effects/geriatric")
    public ResponseEntity<List<GeriatricDTO>> getGenericNemeListGeriatric(Pageable pageable,
                                                                          @RequestBody List<String> genericName, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to delete SideEffect : {}");
        Page<GeriatricDTO> page = sideEffectService.findGeriatricByGenericNameList(pageable, genericName);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder, page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
