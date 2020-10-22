package com.black.mono.web.rest;

import com.black.mono.service.DependentService;
import com.black.mono.web.rest.errors.BadRequestAlertException;
import com.black.mono.service.dto.DependentDTO;

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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.black.mono.domain.Dependent}.
 */
@RestController
@RequestMapping("/api")
public class DependentResource {

    private final Logger log = LoggerFactory.getLogger(DependentResource.class);

    private static final String ENTITY_NAME = "dependent";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DependentService dependentService;

    public DependentResource(DependentService dependentService) {
        this.dependentService = dependentService;
    }

    /**
     * {@code POST  /dependents} : Create a new dependent.
     *
     * @param dependentDTO the dependentDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dependentDTO, or with status {@code 400 (Bad Request)} if the dependent has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/dependents")
    public ResponseEntity<DependentDTO> createDependent(@Valid @RequestBody DependentDTO dependentDTO) throws URISyntaxException {
        log.debug("REST request to save Dependent : {}", dependentDTO);
        if (dependentDTO.getId() != null) {
            throw new BadRequestAlertException("A new dependent cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DependentDTO result = dependentService.save(dependentDTO);
        return ResponseEntity.created(new URI("/api/dependents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /dependents} : Updates an existing dependent.
     *
     * @param dependentDTO the dependentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dependentDTO,
     * or with status {@code 400 (Bad Request)} if the dependentDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dependentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/dependents")
    public ResponseEntity<DependentDTO> updateDependent(@Valid @RequestBody DependentDTO dependentDTO) throws URISyntaxException {
        log.debug("REST request to update Dependent : {}", dependentDTO);
        if (dependentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DependentDTO result = dependentService.save(dependentDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, dependentDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /dependents} : get all the dependents.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dependents in body.
     */
    @GetMapping("/dependents")
    public ResponseEntity<List<DependentDTO>> getAllDependents(Pageable pageable) {
        log.debug("REST request to get a page of Dependents");
        Page<DependentDTO> page = dependentService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /dependents/:id} : get the "id" dependent.
     *
     * @param id the id of the dependentDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dependentDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/dependents/{id}")
    public ResponseEntity<DependentDTO> getDependent(@PathVariable Long id) {
        log.debug("REST request to get Dependent : {}", id);
        Optional<DependentDTO> dependentDTO = dependentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dependentDTO);
    }

    /**
     * {@code DELETE  /dependents/:id} : delete the "id" dependent.
     *
     * @param id the id of the dependentDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/dependents/{id}")
    public ResponseEntity<Void> deleteDependent(@PathVariable Long id) {
        log.debug("REST request to delete Dependent : {}", id);
        dependentService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
