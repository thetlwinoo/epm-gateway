package com.epmserver.gateway.service.impl;

import com.epmserver.gateway.service.WishlistProductsService;
import com.epmserver.gateway.domain.WishlistProducts;
import com.epmserver.gateway.repository.WishlistProductsRepository;
import com.epmserver.gateway.repository.search.WishlistProductsSearchRepository;
import com.epmserver.gateway.service.dto.WishlistProductsDTO;
import com.epmserver.gateway.service.mapper.WishlistProductsMapper;
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
 * Service Implementation for managing {@link WishlistProducts}.
 */
@Service
@Transactional
public class WishlistProductsServiceImpl implements WishlistProductsService {

    private final Logger log = LoggerFactory.getLogger(WishlistProductsServiceImpl.class);

    private final WishlistProductsRepository wishlistProductsRepository;

    private final WishlistProductsMapper wishlistProductsMapper;

    private final WishlistProductsSearchRepository wishlistProductsSearchRepository;

    public WishlistProductsServiceImpl(WishlistProductsRepository wishlistProductsRepository, WishlistProductsMapper wishlistProductsMapper, WishlistProductsSearchRepository wishlistProductsSearchRepository) {
        this.wishlistProductsRepository = wishlistProductsRepository;
        this.wishlistProductsMapper = wishlistProductsMapper;
        this.wishlistProductsSearchRepository = wishlistProductsSearchRepository;
    }

    /**
     * Save a wishlistProducts.
     *
     * @param wishlistProductsDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public WishlistProductsDTO save(WishlistProductsDTO wishlistProductsDTO) {
        log.debug("Request to save WishlistProducts : {}", wishlistProductsDTO);
        WishlistProducts wishlistProducts = wishlistProductsMapper.toEntity(wishlistProductsDTO);
        wishlistProducts = wishlistProductsRepository.save(wishlistProducts);
        WishlistProductsDTO result = wishlistProductsMapper.toDto(wishlistProducts);
        wishlistProductsSearchRepository.save(wishlistProducts);
        return result;
    }

    /**
     * Get all the wishlistProducts.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<WishlistProductsDTO> findAll() {
        log.debug("Request to get all WishlistProducts");
        return wishlistProductsRepository.findAll().stream()
            .map(wishlistProductsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one wishlistProducts by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<WishlistProductsDTO> findOne(Long id) {
        log.debug("Request to get WishlistProducts : {}", id);
        return wishlistProductsRepository.findById(id)
            .map(wishlistProductsMapper::toDto);
    }

    /**
     * Delete the wishlistProducts by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete WishlistProducts : {}", id);
        wishlistProductsRepository.deleteById(id);
        wishlistProductsSearchRepository.deleteById(id);
    }

    /**
     * Search for the wishlistProducts corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<WishlistProductsDTO> search(String query) {
        log.debug("Request to search WishlistProducts for query {}", query);
        return StreamSupport
            .stream(wishlistProductsSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(wishlistProductsMapper::toDto)
            .collect(Collectors.toList());
    }
}
