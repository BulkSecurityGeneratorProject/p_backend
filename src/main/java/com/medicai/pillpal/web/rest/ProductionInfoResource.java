package com.medicai.pillpal.web.rest;

import com.medicai.pillpal.service.ProductionInfoService;
import com.medicai.pillpal.web.rest.errors.BadRequestAlertException;
import com.medicai.pillpal.service.dto.ProductionInfoDTO;

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
 * REST controller for managing {@link com.medicai.pillpal.domain.ProductionInfo}.
 */
@RestController
@RequestMapping("/api")
public class ProductionInfoResource {

    private final Logger log = LoggerFactory.getLogger(ProductionInfoResource.class);

    private static final String ENTITY_NAME = "productionInfo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductionInfoService productionInfoService;

    public ProductionInfoResource(ProductionInfoService productionInfoService) {
        this.productionInfoService = productionInfoService;
    }

    /**
     * {@code POST  /production-infos} : Create a new productionInfo.
     *
     * @param productionInfoDTO the productionInfoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productionInfoDTO, or with status {@code 400 (Bad Request)} if the productionInfo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/production-infos")
    public ResponseEntity<ProductionInfoDTO> createProductionInfo(@RequestBody ProductionInfoDTO productionInfoDTO) throws URISyntaxException {
        log.debug("REST request to save ProductionInfo : {}", productionInfoDTO);
        if (productionInfoDTO.getId() != null) {
            throw new BadRequestAlertException("A new productionInfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductionInfoDTO result = productionInfoService.save(productionInfoDTO);
        return ResponseEntity.created(new URI("/api/production-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /production-infos} : Updates an existing productionInfo.
     *
     * @param productionInfoDTO the productionInfoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productionInfoDTO,
     * or with status {@code 400 (Bad Request)} if the productionInfoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productionInfoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/production-infos")
    public ResponseEntity<ProductionInfoDTO> updateProductionInfo(@RequestBody ProductionInfoDTO productionInfoDTO) throws URISyntaxException {
        log.debug("REST request to update ProductionInfo : {}", productionInfoDTO);
        if (productionInfoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProductionInfoDTO result = productionInfoService.save(productionInfoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, productionInfoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /production-infos} : get all the productionInfos.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productionInfos in body.
     */
    @GetMapping("/production-infos")
    public ResponseEntity<List<ProductionInfoDTO>> getAllProductionInfos(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of ProductionInfos");
        Page<ProductionInfoDTO> page = productionInfoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /production-infos/:id} : get the "id" productionInfo.
     *
     * @param id the id of the productionInfoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productionInfoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/production-infos/{id}")
    public ResponseEntity<ProductionInfoDTO> getProductionInfo(@PathVariable Long id) {
        log.debug("REST request to get ProductionInfo : {}", id);
        Optional<ProductionInfoDTO> productionInfoDTO = productionInfoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productionInfoDTO);
    }

    /**
     * {@code DELETE  /production-infos/:id} : delete the "id" productionInfo.
     *
     * @param id the id of the productionInfoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/production-infos/{id}")
    public ResponseEntity<Void> deleteProductionInfo(@PathVariable Long id) {
        log.debug("REST request to delete ProductionInfo : {}", id);
        productionInfoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
