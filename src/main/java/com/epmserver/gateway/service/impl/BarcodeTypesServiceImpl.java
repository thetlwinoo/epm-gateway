package com.epmserver.gateway.service.impl;

import com.epmserver.gateway.service.BarcodeTypesService;
import com.epmserver.gateway.domain.BarcodeTypes;
import com.epmserver.gateway.repository.BarcodeTypesRepository;
import com.epmserver.gateway.repository.search.BarcodeTypesSearchRepository;
import com.epmserver.gateway.service.dto.BarcodeTypesDTO;
import com.epmserver.gateway.service.mapper.BarcodeTypesMapper;
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
 * Service Implementation for managing {@link BarcodeTypes}.
 */
@Service
@Transactional
public class BarcodeTypesServiceImpl implements BarcodeTypesService {

    private final Logger log = LoggerFactory.getLogger(BarcodeTypesServiceImpl.class);

    private final BarcodeTypesRepository barcodeTypesRepository;

    private final BarcodeTypesMapper barcodeTypesMapper;

    private final BarcodeTypesSearchRepository barcodeTypesSearchRepository;

    public BarcodeTypesServiceImpl(BarcodeTypesRepository barcodeTypesRepository, BarcodeTypesMapper barcodeTypesMapper, BarcodeTypesSearchRepository barcodeTypesSearchRepository) {
        this.barcodeTypesRepository = barcodeTypesRepository;
        this.barcodeTypesMapper = barcodeTypesMapper;
        this.barcodeTypesSearchRepository = barcodeTypesSearchRepository;
    }

    /**
     * Save a barcodeTypes.
     *
     * @param barcodeTypesDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public BarcodeTypesDTO save(BarcodeTypesDTO barcodeTypesDTO) {
        log.debug("Request to save BarcodeTypes : {}", barcodeTypesDTO);
        BarcodeTypes barcodeTypes = barcodeTypesMapper.toEntity(barcodeTypesDTO);
        barcodeTypes = barcodeTypesRepository.save(barcodeTypes);
        BarcodeTypesDTO result = barcodeTypesMapper.toDto(barcodeTypes);
        barcodeTypesSearchRepository.save(barcodeTypes);
        return result;
    }

    /**
     * Get all the barcodeTypes.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<BarcodeTypesDTO> findAll() {
        log.debug("Request to get all BarcodeTypes");
        return barcodeTypesRepository.findAll().stream()
            .map(barcodeTypesMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one barcodeTypes by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<BarcodeTypesDTO> findOne(Long id) {
        log.debug("Request to get BarcodeTypes : {}", id);
        return barcodeTypesRepository.findById(id)
            .map(barcodeTypesMapper::toDto);
    }

    /**
     * Delete the barcodeTypes by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete BarcodeTypes : {}", id);
        barcodeTypesRepository.deleteById(id);
        barcodeTypesSearchRepository.deleteById(id);
    }

    /**
     * Search for the barcodeTypes corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<BarcodeTypesDTO> search(String query) {
        log.debug("Request to search BarcodeTypes for query {}", query);
        return StreamSupport
            .stream(barcodeTypesSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(barcodeTypesMapper::toDto)
            .collect(Collectors.toList());
    }
}
