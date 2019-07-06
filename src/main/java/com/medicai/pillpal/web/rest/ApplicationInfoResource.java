package com.medicai.pillpal.web.rest;

import com.medicai.pillpal.domain.ApplicationInfo;
import com.medicai.pillpal.service.ApplicationInfoService;
import com.medicai.pillpal.web.rest.errors.BadRequestAlertException;
import com.medicai.pillpal.service.dto.ApplicationInfoDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.medicai.pillpal.domain.ApplicationInfo}.
 */
@RestController
@RequestMapping("/api")
public class ApplicationInfoResource {

    private final Logger log = LoggerFactory.getLogger(ApplicationInfoResource.class);

    private static final String ENTITY_NAME = "applicationInfo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ApplicationInfoService applicationInfoService;
    private Object List;

    public ApplicationInfoResource(ApplicationInfoService applicationInfoService) {
        this.applicationInfoService = applicationInfoService;
    }

    /**
     * {@code POST  /application-infos} : Create a new applicationInfo.
     *
     * @param applicationInfoDTO the applicationInfoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new applicationInfoDTO, or with status {@code 400 (Bad Request)} if the applicationInfo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/application-infos")
    public ResponseEntity<ApplicationInfoDTO> createApplicationInfo(@Valid @RequestBody ApplicationInfoDTO applicationInfoDTO) throws URISyntaxException {
        log.debug("REST request to save ApplicationInfo : {}", applicationInfoDTO);
        if (applicationInfoDTO.getId() != null) {
            throw new BadRequestAlertException("A new applicationInfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ApplicationInfoDTO result = applicationInfoService.save(applicationInfoDTO);
        return ResponseEntity.created(new URI("/api/application-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /application-infos} : Updates an existing applicationInfo.
     *
     * @param applicationInfoDTO the applicationInfoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated applicationInfoDTO,
     * or with status {@code 400 (Bad Request)} if the applicationInfoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the applicationInfoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/application-infos")
    public ResponseEntity<ApplicationInfoDTO> updateApplicationInfo(@Valid @RequestBody ApplicationInfoDTO applicationInfoDTO) throws URISyntaxException {
        log.debug("REST request to update ApplicationInfo : {}", applicationInfoDTO);
        if (applicationInfoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ApplicationInfoDTO result = applicationInfoService.save(applicationInfoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, applicationInfoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /application-infos} : get all the applicationInfos.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of applicationInfos in body.
     */
    @GetMapping("/application-infos")
    public ResponseEntity<List<ApplicationInfoDTO>> getAllApplicationInfos(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of ApplicationInfos");
        Page<ApplicationInfoDTO> page = applicationInfoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /application-infos/:id} : get the "id" applicationInfo.
     *
     * @param id the id of the applicationInfoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the applicationInfoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/application-infos/{id}")
    public ResponseEntity<ApplicationInfoDTO> getApplicationInfo(@PathVariable Long id) {
        log.debug("REST request to get ApplicationInfo : {}", id);
        Optional<ApplicationInfoDTO> applicationInfoDTO = applicationInfoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(applicationInfoDTO);
    }

    /**
     * {@code DELETE  /application-infos/:id} : delete the "id" applicationInfo.
     *
     * @param id the id of the applicationInfoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/application-infos/{id}")
    public ResponseEntity<Void> deleteApplicationInfo(@PathVariable Long id) {
        log.debug("REST request to delete ApplicationInfo : {}", id);
        applicationInfoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
    //TODO modify comments
    /**
     *{@Code get the neme of application
     * @param name
     * @return
     */
    @GetMapping("/application-infos/byName/{name}")
    public java.util.List<ApplicationInfo> getApplicationName(@PathVariable String name) {
        log.debug("REST request to get ApplicationInfo : {}", name);
        List<ApplicationInfo> applicationInfoDTO = applicationInfoService.findByName(name);
        return applicationInfoDTO;
    }
}
