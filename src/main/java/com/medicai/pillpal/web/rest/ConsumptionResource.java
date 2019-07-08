package com.medicai.pillpal.web.rest;

import com.medicai.pillpal.service.ConsumptionService;
import com.medicai.pillpal.web.rest.errors.BadRequestAlertException;
import com.medicai.pillpal.service.dto.ApplInfoSideEffectDTO;

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

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.medicai.pillpal.domain.ApplInfoSideEffect}.
 */
@RestController
@RequestMapping("/api")
public class ConsumptionResource {

    private final Logger log = LoggerFactory.getLogger( ConsumptionResource.class);


}
