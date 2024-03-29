package com.epmserver.gateway.web.rest;

import com.epmserver.gateway.service.CitiesService;
import com.epmserver.gateway.web.rest.errors.BadRequestAlertException;
import com.epmserver.gateway.service.dto.CitiesDTO;
import com.epmserver.gateway.service.dto.CitiesCriteria;
import com.epmserver.gateway.service.CitiesQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link com.epmserver.gateway.domain.Cities}.
 */
@RestController
@RequestMapping("/api")
public class CitiesResource {

    private final Logger log = LoggerFactory.getLogger(CitiesResource.class);

    private static final String ENTITY_NAME = "cities";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CitiesService citiesService;

    private final CitiesQueryService citiesQueryService;

    public CitiesResource(CitiesService citiesService, CitiesQueryService citiesQueryService) {
        this.citiesService = citiesService;
        this.citiesQueryService = citiesQueryService;
    }

    /**
     * {@code POST  /cities} : Create a new cities.
     *
     * @param citiesDTO the citiesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new citiesDTO, or with status {@code 400 (Bad Request)} if the cities has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cities")
    public ResponseEntity<CitiesDTO> createCities(@Valid @RequestBody CitiesDTO citiesDTO) throws URISyntaxException {
        log.debug("REST request to save Cities : {}", citiesDTO);
        if (citiesDTO.getId() != null) {
            throw new BadRequestAlertException("A new cities cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CitiesDTO result = citiesService.save(citiesDTO);
        return ResponseEntity.created(new URI("/api/cities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cities} : Updates an existing cities.
     *
     * @param citiesDTO the citiesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated citiesDTO,
     * or with status {@code 400 (Bad Request)} if the citiesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the citiesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cities")
    public ResponseEntity<CitiesDTO> updateCities(@Valid @RequestBody CitiesDTO citiesDTO) throws URISyntaxException {
        log.debug("REST request to update Cities : {}", citiesDTO);
        if (citiesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CitiesDTO result = citiesService.save(citiesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, citiesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /cities} : get all the cities.
     *

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cities in body.
     */
    @GetMapping("/cities")
    public ResponseEntity<List<CitiesDTO>> getAllCities(CitiesCriteria criteria) {
        log.debug("REST request to get Cities by criteria: {}", criteria);
        List<CitiesDTO> entityList = citiesQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * {@code GET  /cities/count} : count all the cities.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/cities/count")
    public ResponseEntity<Long> countCities(CitiesCriteria criteria) {
        log.debug("REST request to count Cities by criteria: {}", criteria);
        return ResponseEntity.ok().body(citiesQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /cities/:id} : get the "id" cities.
     *
     * @param id the id of the citiesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the citiesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cities/{id}")
    public ResponseEntity<CitiesDTO> getCities(@PathVariable Long id) {
        log.debug("REST request to get Cities : {}", id);
        Optional<CitiesDTO> citiesDTO = citiesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(citiesDTO);
    }

    /**
     * {@code DELETE  /cities/:id} : delete the "id" cities.
     *
     * @param id the id of the citiesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cities/{id}")
    public ResponseEntity<Void> deleteCities(@PathVariable Long id) {
        log.debug("REST request to delete Cities : {}", id);
        citiesService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/cities?query=:query} : search for the cities corresponding
     * to the query.
     *
     * @param query the query of the cities search.
     * @return the result of the search.
     */
    @GetMapping("/_search/cities")
    public List<CitiesDTO> searchCities(@RequestParam String query) {
        log.debug("REST request to search Cities for query {}", query);
        return citiesService.search(query);
    }
}
