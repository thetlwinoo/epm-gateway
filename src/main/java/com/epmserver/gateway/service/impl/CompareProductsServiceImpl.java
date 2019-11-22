package com.epmserver.gateway.service.impl;

import com.epmserver.gateway.service.CompareProductsService;
import com.epmserver.gateway.domain.CompareProducts;
import com.epmserver.gateway.repository.CompareProductsRepository;
import com.epmserver.gateway.repository.search.CompareProductsSearchRepository;
import com.epmserver.gateway.service.dto.CompareProductsDTO;
import com.epmserver.gateway.service.mapper.CompareProductsMapper;
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
 * Service Implementation for managing {@link CompareProducts}.
 */
@Service
@Transactional
public class CompareProductsServiceImpl implements CompareProductsService {

    private final Logger log = LoggerFactory.getLogger(CompareProductsServiceImpl.class);

    private final CompareProductsRepository compareProductsRepository;

    private final CompareProductsMapper compareProductsMapper;

    private final CompareProductsSearchRepository compareProductsSearchRepository;

    public CompareProductsServiceImpl(CompareProductsRepository compareProductsRepository, CompareProductsMapper compareProductsMapper, CompareProductsSearchRepository compareProductsSearchRepository) {
        this.compareProductsRepository = compareProductsRepository;
        this.compareProductsMapper = compareProductsMapper;
        this.compareProductsSearchRepository = compareProductsSearchRepository;
    }

    /**
     * Save a compareProducts.
     *
     * @param compareProductsDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public CompareProductsDTO save(CompareProductsDTO compareProductsDTO) {
        log.debug("Request to save CompareProducts : {}", compareProductsDTO);
        CompareProducts compareProducts = compareProductsMapper.toEntity(compareProductsDTO);
        compareProducts = compareProductsRepository.save(compareProducts);
        CompareProductsDTO result = compareProductsMapper.toDto(compareProducts);
        compareProductsSearchRepository.save(compareProducts);
        return result;
    }

    /**
     * Get all the compareProducts.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<CompareProductsDTO> findAll() {
        log.debug("Request to get all CompareProducts");
        return compareProductsRepository.findAll().stream()
            .map(compareProductsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one compareProducts by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CompareProductsDTO> findOne(Long id) {
        log.debug("Request to get CompareProducts : {}", id);
        return compareProductsRepository.findById(id)
            .map(compareProductsMapper::toDto);
    }

    /**
     * Delete the compareProducts by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CompareProducts : {}", id);
        compareProductsRepository.deleteById(id);
        compareProductsSearchRepository.deleteById(id);
    }

    /**
     * Search for the compareProducts corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<CompareProductsDTO> search(String query) {
        log.debug("Request to search CompareProducts for query {}", query);
        return StreamSupport
            .stream(compareProductsSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(compareProductsMapper::toDto)
            .collect(Collectors.toList());
    }
}
