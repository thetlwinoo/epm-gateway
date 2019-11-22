package com.epmserver.gateway.service.impl;

import com.epmserver.gateway.service.WarrantyTypesService;
import com.epmserver.gateway.domain.WarrantyTypes;
import com.epmserver.gateway.repository.WarrantyTypesRepository;
import com.epmserver.gateway.repository.search.WarrantyTypesSearchRepository;
import com.epmserver.gateway.service.dto.WarrantyTypesDTO;
import com.epmserver.gateway.service.mapper.WarrantyTypesMapper;
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
 * Service Implementation for managing {@link WarrantyTypes}.
 */
@Service
@Transactional
public class WarrantyTypesServiceImpl implements WarrantyTypesService {

    private final Logger log = LoggerFactory.getLogger(WarrantyTypesServiceImpl.class);

    private final WarrantyTypesRepository warrantyTypesRepository;

    private final WarrantyTypesMapper warrantyTypesMapper;

    private final WarrantyTypesSearchRepository warrantyTypesSearchRepository;

    public WarrantyTypesServiceImpl(WarrantyTypesRepository warrantyTypesRepository, WarrantyTypesMapper warrantyTypesMapper, WarrantyTypesSearchRepository warrantyTypesSearchRepository) {
        this.warrantyTypesRepository = warrantyTypesRepository;
        this.warrantyTypesMapper = warrantyTypesMapper;
        this.warrantyTypesSearchRepository = warrantyTypesSearchRepository;
    }

    /**
     * Save a warrantyTypes.
     *
     * @param warrantyTypesDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public WarrantyTypesDTO save(WarrantyTypesDTO warrantyTypesDTO) {
        log.debug("Request to save WarrantyTypes : {}", warrantyTypesDTO);
        WarrantyTypes warrantyTypes = warrantyTypesMapper.toEntity(warrantyTypesDTO);
        warrantyTypes = warrantyTypesRepository.save(warrantyTypes);
        WarrantyTypesDTO result = warrantyTypesMapper.toDto(warrantyTypes);
        warrantyTypesSearchRepository.save(warrantyTypes);
        return result;
    }

    /**
     * Get all the warrantyTypes.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<WarrantyTypesDTO> findAll() {
        log.debug("Request to get all WarrantyTypes");
        return warrantyTypesRepository.findAll().stream()
            .map(warrantyTypesMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one warrantyTypes by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<WarrantyTypesDTO> findOne(Long id) {
        log.debug("Request to get WarrantyTypes : {}", id);
        return warrantyTypesRepository.findById(id)
            .map(warrantyTypesMapper::toDto);
    }

    /**
     * Delete the warrantyTypes by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete WarrantyTypes : {}", id);
        warrantyTypesRepository.deleteById(id);
        warrantyTypesSearchRepository.deleteById(id);
    }

    /**
     * Search for the warrantyTypes corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<WarrantyTypesDTO> search(String query) {
        log.debug("Request to search WarrantyTypes for query {}", query);
        return StreamSupport
            .stream(warrantyTypesSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(warrantyTypesMapper::toDto)
            .collect(Collectors.toList());
    }
}
