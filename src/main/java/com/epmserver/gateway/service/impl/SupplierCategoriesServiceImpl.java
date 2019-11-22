package com.epmserver.gateway.service.impl;

import com.epmserver.gateway.service.SupplierCategoriesService;
import com.epmserver.gateway.domain.SupplierCategories;
import com.epmserver.gateway.repository.SupplierCategoriesRepository;
import com.epmserver.gateway.repository.search.SupplierCategoriesSearchRepository;
import com.epmserver.gateway.service.dto.SupplierCategoriesDTO;
import com.epmserver.gateway.service.mapper.SupplierCategoriesMapper;
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
 * Service Implementation for managing {@link SupplierCategories}.
 */
@Service
@Transactional
public class SupplierCategoriesServiceImpl implements SupplierCategoriesService {

    private final Logger log = LoggerFactory.getLogger(SupplierCategoriesServiceImpl.class);

    private final SupplierCategoriesRepository supplierCategoriesRepository;

    private final SupplierCategoriesMapper supplierCategoriesMapper;

    private final SupplierCategoriesSearchRepository supplierCategoriesSearchRepository;

    public SupplierCategoriesServiceImpl(SupplierCategoriesRepository supplierCategoriesRepository, SupplierCategoriesMapper supplierCategoriesMapper, SupplierCategoriesSearchRepository supplierCategoriesSearchRepository) {
        this.supplierCategoriesRepository = supplierCategoriesRepository;
        this.supplierCategoriesMapper = supplierCategoriesMapper;
        this.supplierCategoriesSearchRepository = supplierCategoriesSearchRepository;
    }

    /**
     * Save a supplierCategories.
     *
     * @param supplierCategoriesDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public SupplierCategoriesDTO save(SupplierCategoriesDTO supplierCategoriesDTO) {
        log.debug("Request to save SupplierCategories : {}", supplierCategoriesDTO);
        SupplierCategories supplierCategories = supplierCategoriesMapper.toEntity(supplierCategoriesDTO);
        supplierCategories = supplierCategoriesRepository.save(supplierCategories);
        SupplierCategoriesDTO result = supplierCategoriesMapper.toDto(supplierCategories);
        supplierCategoriesSearchRepository.save(supplierCategories);
        return result;
    }

    /**
     * Get all the supplierCategories.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<SupplierCategoriesDTO> findAll() {
        log.debug("Request to get all SupplierCategories");
        return supplierCategoriesRepository.findAll().stream()
            .map(supplierCategoriesMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one supplierCategories by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SupplierCategoriesDTO> findOne(Long id) {
        log.debug("Request to get SupplierCategories : {}", id);
        return supplierCategoriesRepository.findById(id)
            .map(supplierCategoriesMapper::toDto);
    }

    /**
     * Delete the supplierCategories by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SupplierCategories : {}", id);
        supplierCategoriesRepository.deleteById(id);
        supplierCategoriesSearchRepository.deleteById(id);
    }

    /**
     * Search for the supplierCategories corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<SupplierCategoriesDTO> search(String query) {
        log.debug("Request to search SupplierCategories for query {}", query);
        return StreamSupport
            .stream(supplierCategoriesSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(supplierCategoriesMapper::toDto)
            .collect(Collectors.toList());
    }
}
