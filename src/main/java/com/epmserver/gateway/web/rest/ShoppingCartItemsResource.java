package com.epmserver.gateway.web.rest;

import com.epmserver.gateway.service.ShoppingCartItemsService;
import com.epmserver.gateway.web.rest.errors.BadRequestAlertException;
import com.epmserver.gateway.service.dto.ShoppingCartItemsDTO;
import com.epmserver.gateway.service.dto.ShoppingCartItemsCriteria;
import com.epmserver.gateway.service.ShoppingCartItemsQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link com.epmserver.gateway.domain.ShoppingCartItems}.
 */
@RestController
@RequestMapping("/api")
public class ShoppingCartItemsResource {

    private final Logger log = LoggerFactory.getLogger(ShoppingCartItemsResource.class);

    private static final String ENTITY_NAME = "shoppingCartItems";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ShoppingCartItemsService shoppingCartItemsService;

    private final ShoppingCartItemsQueryService shoppingCartItemsQueryService;

    public ShoppingCartItemsResource(ShoppingCartItemsService shoppingCartItemsService, ShoppingCartItemsQueryService shoppingCartItemsQueryService) {
        this.shoppingCartItemsService = shoppingCartItemsService;
        this.shoppingCartItemsQueryService = shoppingCartItemsQueryService;
    }

    /**
     * {@code POST  /shopping-cart-items} : Create a new shoppingCartItems.
     *
     * @param shoppingCartItemsDTO the shoppingCartItemsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new shoppingCartItemsDTO, or with status {@code 400 (Bad Request)} if the shoppingCartItems has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/shopping-cart-items")
    public ResponseEntity<ShoppingCartItemsDTO> createShoppingCartItems(@RequestBody ShoppingCartItemsDTO shoppingCartItemsDTO) throws URISyntaxException {
        log.debug("REST request to save ShoppingCartItems : {}", shoppingCartItemsDTO);
        if (shoppingCartItemsDTO.getId() != null) {
            throw new BadRequestAlertException("A new shoppingCartItems cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ShoppingCartItemsDTO result = shoppingCartItemsService.save(shoppingCartItemsDTO);
        return ResponseEntity.created(new URI("/api/shopping-cart-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /shopping-cart-items} : Updates an existing shoppingCartItems.
     *
     * @param shoppingCartItemsDTO the shoppingCartItemsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated shoppingCartItemsDTO,
     * or with status {@code 400 (Bad Request)} if the shoppingCartItemsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the shoppingCartItemsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/shopping-cart-items")
    public ResponseEntity<ShoppingCartItemsDTO> updateShoppingCartItems(@RequestBody ShoppingCartItemsDTO shoppingCartItemsDTO) throws URISyntaxException {
        log.debug("REST request to update ShoppingCartItems : {}", shoppingCartItemsDTO);
        if (shoppingCartItemsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ShoppingCartItemsDTO result = shoppingCartItemsService.save(shoppingCartItemsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, shoppingCartItemsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /shopping-cart-items} : get all the shoppingCartItems.
     *

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of shoppingCartItems in body.
     */
    @GetMapping("/shopping-cart-items")
    public ResponseEntity<List<ShoppingCartItemsDTO>> getAllShoppingCartItems(ShoppingCartItemsCriteria criteria) {
        log.debug("REST request to get ShoppingCartItems by criteria: {}", criteria);
        List<ShoppingCartItemsDTO> entityList = shoppingCartItemsQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * {@code GET  /shopping-cart-items/count} : count all the shoppingCartItems.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/shopping-cart-items/count")
    public ResponseEntity<Long> countShoppingCartItems(ShoppingCartItemsCriteria criteria) {
        log.debug("REST request to count ShoppingCartItems by criteria: {}", criteria);
        return ResponseEntity.ok().body(shoppingCartItemsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /shopping-cart-items/:id} : get the "id" shoppingCartItems.
     *
     * @param id the id of the shoppingCartItemsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the shoppingCartItemsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/shopping-cart-items/{id}")
    public ResponseEntity<ShoppingCartItemsDTO> getShoppingCartItems(@PathVariable Long id) {
        log.debug("REST request to get ShoppingCartItems : {}", id);
        Optional<ShoppingCartItemsDTO> shoppingCartItemsDTO = shoppingCartItemsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(shoppingCartItemsDTO);
    }

    /**
     * {@code DELETE  /shopping-cart-items/:id} : delete the "id" shoppingCartItems.
     *
     * @param id the id of the shoppingCartItemsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/shopping-cart-items/{id}")
    public ResponseEntity<Void> deleteShoppingCartItems(@PathVariable Long id) {
        log.debug("REST request to delete ShoppingCartItems : {}", id);
        shoppingCartItemsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/shopping-cart-items?query=:query} : search for the shoppingCartItems corresponding
     * to the query.
     *
     * @param query the query of the shoppingCartItems search.
     * @return the result of the search.
     */
    @GetMapping("/_search/shopping-cart-items")
    public List<ShoppingCartItemsDTO> searchShoppingCartItems(@RequestParam String query) {
        log.debug("REST request to search ShoppingCartItems for query {}", query);
        return shoppingCartItemsService.search(query);
    }
}
