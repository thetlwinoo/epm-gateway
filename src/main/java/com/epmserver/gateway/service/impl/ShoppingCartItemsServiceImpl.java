package com.epmserver.gateway.service.impl;

import com.epmserver.gateway.service.ShoppingCartItemsService;
import com.epmserver.gateway.domain.ShoppingCartItems;
import com.epmserver.gateway.repository.ShoppingCartItemsRepository;
import com.epmserver.gateway.repository.search.ShoppingCartItemsSearchRepository;
import com.epmserver.gateway.service.dto.ShoppingCartItemsDTO;
import com.epmserver.gateway.service.mapper.ShoppingCartItemsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link ShoppingCartItems}.
 */
@Service
@Transactional
public class ShoppingCartItemsServiceImpl implements ShoppingCartItemsService {

    private final Logger log = LoggerFactory.getLogger(ShoppingCartItemsServiceImpl.class);

    private final ShoppingCartItemsRepository shoppingCartItemsRepository;

    private final ShoppingCartItemsMapper shoppingCartItemsMapper;

    private final ShoppingCartItemsSearchRepository shoppingCartItemsSearchRepository;

    public ShoppingCartItemsServiceImpl(ShoppingCartItemsRepository shoppingCartItemsRepository, ShoppingCartItemsMapper shoppingCartItemsMapper, ShoppingCartItemsSearchRepository shoppingCartItemsSearchRepository) {
        this.shoppingCartItemsRepository = shoppingCartItemsRepository;
        this.shoppingCartItemsMapper = shoppingCartItemsMapper;
        this.shoppingCartItemsSearchRepository = shoppingCartItemsSearchRepository;
    }

    /**
     * Save a shoppingCartItems.
     *
     * @param shoppingCartItemsDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ShoppingCartItemsDTO save(ShoppingCartItemsDTO shoppingCartItemsDTO) {
        log.debug("Request to save ShoppingCartItems : {}", shoppingCartItemsDTO);
        ShoppingCartItems shoppingCartItems = shoppingCartItemsMapper.toEntity(shoppingCartItemsDTO);
        shoppingCartItems = shoppingCartItemsRepository.save(shoppingCartItems);
        ShoppingCartItemsDTO result = shoppingCartItemsMapper.toDto(shoppingCartItems);
        shoppingCartItemsSearchRepository.save(shoppingCartItems);
        return result;
    }

    /**
     * Get all the shoppingCartItems.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ShoppingCartItemsDTO> findAll() {
        log.debug("Request to get all ShoppingCartItems");
        return shoppingCartItemsRepository.findAll().stream()
            .map(shoppingCartItemsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one shoppingCartItems by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ShoppingCartItemsDTO> findOne(Long id) {
        log.debug("Request to get ShoppingCartItems : {}", id);
        return shoppingCartItemsRepository.findById(id)
            .map(shoppingCartItemsMapper::toDto);
    }

    /**
     * Delete the shoppingCartItems by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ShoppingCartItems : {}", id);
        shoppingCartItemsRepository.deleteById(id);
        shoppingCartItemsSearchRepository.deleteById(id);
    }

    /**
     * Search for the shoppingCartItems corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ShoppingCartItemsDTO> search(String query) {
        log.debug("Request to search ShoppingCartItems for query {}", query);
        return StreamSupport
            .stream(shoppingCartItemsSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(shoppingCartItemsMapper::toDto)
            .collect(Collectors.toList());
    }
}
