package com.epmserver.gateway.web.rest;

import com.epmserver.gateway.service.ProductChoiceService;
import com.epmserver.gateway.web.rest.errors.BadRequestAlertException;
import com.epmserver.gateway.service.dto.ProductChoiceDTO;
import com.epmserver.gateway.service.dto.ProductChoiceCriteria;
import com.epmserver.gateway.service.ProductChoiceQueryService;

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
 * REST controller for managing {@link com.epmserver.gateway.domain.ProductChoice}.
 */
@RestController
@RequestMapping("/api")
public class ProductChoiceResource {

    private final Logger log = LoggerFactory.getLogger(ProductChoiceResource.class);

    private static final String ENTITY_NAME = "productChoice";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductChoiceService productChoiceService;

    private final ProductChoiceQueryService productChoiceQueryService;

    public ProductChoiceResource(ProductChoiceService productChoiceService, ProductChoiceQueryService productChoiceQueryService) {
        this.productChoiceService = productChoiceService;
        this.productChoiceQueryService = productChoiceQueryService;
    }

    /**
     * {@code POST  /product-choices} : Create a new productChoice.
     *
     * @param productChoiceDTO the productChoiceDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productChoiceDTO, or with status {@code 400 (Bad Request)} if the productChoice has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/product-choices")
    public ResponseEntity<ProductChoiceDTO> createProductChoice(@Valid @RequestBody ProductChoiceDTO productChoiceDTO) throws URISyntaxException {
        log.debug("REST request to save ProductChoice : {}", productChoiceDTO);
        if (productChoiceDTO.getId() != null) {
            throw new BadRequestAlertException("A new productChoice cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductChoiceDTO result = productChoiceService.save(productChoiceDTO);
        return ResponseEntity.created(new URI("/api/product-choices/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /product-choices} : Updates an existing productChoice.
     *
     * @param productChoiceDTO the productChoiceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productChoiceDTO,
     * or with status {@code 400 (Bad Request)} if the productChoiceDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productChoiceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/product-choices")
    public ResponseEntity<ProductChoiceDTO> updateProductChoice(@Valid @RequestBody ProductChoiceDTO productChoiceDTO) throws URISyntaxException {
        log.debug("REST request to update ProductChoice : {}", productChoiceDTO);
        if (productChoiceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProductChoiceDTO result = productChoiceService.save(productChoiceDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productChoiceDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /product-choices} : get all the productChoices.
     *

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productChoices in body.
     */
    @GetMapping("/product-choices")
    public ResponseEntity<List<ProductChoiceDTO>> getAllProductChoices(ProductChoiceCriteria criteria) {
        log.debug("REST request to get ProductChoices by criteria: {}", criteria);
        List<ProductChoiceDTO> entityList = productChoiceQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * {@code GET  /product-choices/count} : count all the productChoices.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/product-choices/count")
    public ResponseEntity<Long> countProductChoices(ProductChoiceCriteria criteria) {
        log.debug("REST request to count ProductChoices by criteria: {}", criteria);
        return ResponseEntity.ok().body(productChoiceQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /product-choices/:id} : get the "id" productChoice.
     *
     * @param id the id of the productChoiceDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productChoiceDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/product-choices/{id}")
    public ResponseEntity<ProductChoiceDTO> getProductChoice(@PathVariable Long id) {
        log.debug("REST request to get ProductChoice : {}", id);
        Optional<ProductChoiceDTO> productChoiceDTO = productChoiceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productChoiceDTO);
    }

    /**
     * {@code DELETE  /product-choices/:id} : delete the "id" productChoice.
     *
     * @param id the id of the productChoiceDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/product-choices/{id}")
    public ResponseEntity<Void> deleteProductChoice(@PathVariable Long id) {
        log.debug("REST request to delete ProductChoice : {}", id);
        productChoiceService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/product-choices?query=:query} : search for the productChoice corresponding
     * to the query.
     *
     * @param query the query of the productChoice search.
     * @return the result of the search.
     */
    @GetMapping("/_search/product-choices")
    public List<ProductChoiceDTO> searchProductChoices(@RequestParam String query) {
        log.debug("REST request to search ProductChoices for query {}", query);
        return productChoiceService.search(query);
    }
}
