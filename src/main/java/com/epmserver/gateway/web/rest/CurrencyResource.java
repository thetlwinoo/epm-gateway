package com.epmserver.gateway.web.rest;

import com.epmserver.gateway.service.CurrencyService;
import com.epmserver.gateway.web.rest.errors.BadRequestAlertException;
import com.epmserver.gateway.service.dto.CurrencyDTO;
import com.epmserver.gateway.service.dto.CurrencyCriteria;
import com.epmserver.gateway.service.CurrencyQueryService;

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
 * REST controller for managing {@link com.epmserver.gateway.domain.Currency}.
 */
@RestController
@RequestMapping("/api")
public class CurrencyResource {

    private final Logger log = LoggerFactory.getLogger(CurrencyResource.class);

    private static final String ENTITY_NAME = "currency";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CurrencyService currencyService;

    private final CurrencyQueryService currencyQueryService;

    public CurrencyResource(CurrencyService currencyService, CurrencyQueryService currencyQueryService) {
        this.currencyService = currencyService;
        this.currencyQueryService = currencyQueryService;
    }

    /**
     * {@code POST  /currencies} : Create a new currency.
     *
     * @param currencyDTO the currencyDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new currencyDTO, or with status {@code 400 (Bad Request)} if the currency has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/currencies")
    public ResponseEntity<CurrencyDTO> createCurrency(@Valid @RequestBody CurrencyDTO currencyDTO) throws URISyntaxException {
        log.debug("REST request to save Currency : {}", currencyDTO);
        if (currencyDTO.getId() != null) {
            throw new BadRequestAlertException("A new currency cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CurrencyDTO result = currencyService.save(currencyDTO);
        return ResponseEntity.created(new URI("/api/currencies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /currencies} : Updates an existing currency.
     *
     * @param currencyDTO the currencyDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated currencyDTO,
     * or with status {@code 400 (Bad Request)} if the currencyDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the currencyDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/currencies")
    public ResponseEntity<CurrencyDTO> updateCurrency(@Valid @RequestBody CurrencyDTO currencyDTO) throws URISyntaxException {
        log.debug("REST request to update Currency : {}", currencyDTO);
        if (currencyDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CurrencyDTO result = currencyService.save(currencyDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, currencyDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /currencies} : get all the currencies.
     *

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of currencies in body.
     */
    @GetMapping("/currencies")
    public ResponseEntity<List<CurrencyDTO>> getAllCurrencies(CurrencyCriteria criteria) {
        log.debug("REST request to get Currencies by criteria: {}", criteria);
        List<CurrencyDTO> entityList = currencyQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * {@code GET  /currencies/count} : count all the currencies.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/currencies/count")
    public ResponseEntity<Long> countCurrencies(CurrencyCriteria criteria) {
        log.debug("REST request to count Currencies by criteria: {}", criteria);
        return ResponseEntity.ok().body(currencyQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /currencies/:id} : get the "id" currency.
     *
     * @param id the id of the currencyDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the currencyDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/currencies/{id}")
    public ResponseEntity<CurrencyDTO> getCurrency(@PathVariable Long id) {
        log.debug("REST request to get Currency : {}", id);
        Optional<CurrencyDTO> currencyDTO = currencyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(currencyDTO);
    }

    /**
     * {@code DELETE  /currencies/:id} : delete the "id" currency.
     *
     * @param id the id of the currencyDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/currencies/{id}")
    public ResponseEntity<Void> deleteCurrency(@PathVariable Long id) {
        log.debug("REST request to delete Currency : {}", id);
        currencyService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/currencies?query=:query} : search for the currency corresponding
     * to the query.
     *
     * @param query the query of the currency search.
     * @return the result of the search.
     */
    @GetMapping("/_search/currencies")
    public List<CurrencyDTO> searchCurrencies(@RequestParam String query) {
        log.debug("REST request to search Currencies for query {}", query);
        return currencyService.search(query);
    }
}
