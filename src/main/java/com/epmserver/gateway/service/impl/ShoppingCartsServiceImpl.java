package com.epmserver.gateway.service.impl;

import com.epmserver.gateway.service.ShoppingCartsService;
import com.epmserver.gateway.domain.ShoppingCarts;
import com.epmserver.gateway.repository.ShoppingCartsRepository;
import com.epmserver.gateway.repository.search.ShoppingCartsSearchRepository;
import com.epmserver.gateway.service.dto.ShoppingCartsDTO;
import com.epmserver.gateway.service.mapper.ShoppingCartsMapper;
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
 * Service Implementation for managing {@link ShoppingCarts}.
 */
@Service
@Transactional
public class ShoppingCartsServiceImpl implements ShoppingCartsService {

    private final Logger log = LoggerFactory.getLogger(ShoppingCartsServiceImpl.class);

    private final ShoppingCartsRepository shoppingCartsRepository;

    private final ShoppingCartsMapper shoppingCartsMapper;

    private final ShoppingCartsSearchRepository shoppingCartsSearchRepository;

    public ShoppingCartsServiceImpl(ShoppingCartsRepository shoppingCartsRepository, ShoppingCartsMapper shoppingCartsMapper, ShoppingCartsSearchRepository shoppingCartsSearchRepository) {
        this.shoppingCartsRepository = shoppingCartsRepository;
        this.shoppingCartsMapper = shoppingCartsMapper;
        this.shoppingCartsSearchRepository = shoppingCartsSearchRepository;
    }

    /**
     * Save a shoppingCarts.
     *
     * @param shoppingCartsDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ShoppingCartsDTO save(ShoppingCartsDTO shoppingCartsDTO) {
        log.debug("Request to save ShoppingCarts : {}", shoppingCartsDTO);
        ShoppingCarts shoppingCarts = shoppingCartsMapper.toEntity(shoppingCartsDTO);
        shoppingCarts = shoppingCartsRepository.save(shoppingCarts);
        ShoppingCartsDTO result = shoppingCartsMapper.toDto(shoppingCarts);
        shoppingCartsSearchRepository.save(shoppingCarts);
        return result;
    }

    /**
     * Get all the shoppingCarts.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ShoppingCartsDTO> findAll() {
        log.debug("Request to get all ShoppingCarts");
        return shoppingCartsRepository.findAll().stream()
            .map(shoppingCartsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one shoppingCarts by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ShoppingCartsDTO> findOne(Long id) {
        log.debug("Request to get ShoppingCarts : {}", id);
        return shoppingCartsRepository.findById(id)
            .map(shoppingCartsMapper::toDto);
    }

    /**
     * Delete the shoppingCarts by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ShoppingCarts : {}", id);
        shoppingCartsRepository.deleteById(id);
        shoppingCartsSearchRepository.deleteById(id);
    }

    /**
     * Search for the shoppingCarts corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ShoppingCartsDTO> search(String query) {
        log.debug("Request to search ShoppingCarts for query {}", query);
        return StreamSupport
            .stream(shoppingCartsSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(shoppingCartsMapper::toDto)
            .collect(Collectors.toList());
    }
}
