package com.epmserver.gateway.service.impl;

import com.epmserver.gateway.service.WishlistsService;
import com.epmserver.gateway.domain.Wishlists;
import com.epmserver.gateway.repository.WishlistsRepository;
import com.epmserver.gateway.repository.search.WishlistsSearchRepository;
import com.epmserver.gateway.service.dto.WishlistsDTO;
import com.epmserver.gateway.service.mapper.WishlistsMapper;
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
 * Service Implementation for managing {@link Wishlists}.
 */
@Service
@Transactional
public class WishlistsServiceImpl implements WishlistsService {

    private final Logger log = LoggerFactory.getLogger(WishlistsServiceImpl.class);

    private final WishlistsRepository wishlistsRepository;

    private final WishlistsMapper wishlistsMapper;

    private final WishlistsSearchRepository wishlistsSearchRepository;

    public WishlistsServiceImpl(WishlistsRepository wishlistsRepository, WishlistsMapper wishlistsMapper, WishlistsSearchRepository wishlistsSearchRepository) {
        this.wishlistsRepository = wishlistsRepository;
        this.wishlistsMapper = wishlistsMapper;
        this.wishlistsSearchRepository = wishlistsSearchRepository;
    }

    /**
     * Save a wishlists.
     *
     * @param wishlistsDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public WishlistsDTO save(WishlistsDTO wishlistsDTO) {
        log.debug("Request to save Wishlists : {}", wishlistsDTO);
        Wishlists wishlists = wishlistsMapper.toEntity(wishlistsDTO);
        wishlists = wishlistsRepository.save(wishlists);
        WishlistsDTO result = wishlistsMapper.toDto(wishlists);
        wishlistsSearchRepository.save(wishlists);
        return result;
    }

    /**
     * Get all the wishlists.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<WishlistsDTO> findAll() {
        log.debug("Request to get all Wishlists");
        return wishlistsRepository.findAll().stream()
            .map(wishlistsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one wishlists by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<WishlistsDTO> findOne(Long id) {
        log.debug("Request to get Wishlists : {}", id);
        return wishlistsRepository.findById(id)
            .map(wishlistsMapper::toDto);
    }

    /**
     * Delete the wishlists by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Wishlists : {}", id);
        wishlistsRepository.deleteById(id);
        wishlistsSearchRepository.deleteById(id);
    }

    /**
     * Search for the wishlists corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<WishlistsDTO> search(String query) {
        log.debug("Request to search Wishlists for query {}", query);
        return StreamSupport
            .stream(wishlistsSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(wishlistsMapper::toDto)
            .collect(Collectors.toList());
    }
}
