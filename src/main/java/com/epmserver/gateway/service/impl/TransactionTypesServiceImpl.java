package com.epmserver.gateway.service.impl;

import com.epmserver.gateway.service.TransactionTypesService;
import com.epmserver.gateway.domain.TransactionTypes;
import com.epmserver.gateway.repository.TransactionTypesRepository;
import com.epmserver.gateway.repository.search.TransactionTypesSearchRepository;
import com.epmserver.gateway.service.dto.TransactionTypesDTO;
import com.epmserver.gateway.service.mapper.TransactionTypesMapper;
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
 * Service Implementation for managing {@link TransactionTypes}.
 */
@Service
@Transactional
public class TransactionTypesServiceImpl implements TransactionTypesService {

    private final Logger log = LoggerFactory.getLogger(TransactionTypesServiceImpl.class);

    private final TransactionTypesRepository transactionTypesRepository;

    private final TransactionTypesMapper transactionTypesMapper;

    private final TransactionTypesSearchRepository transactionTypesSearchRepository;

    public TransactionTypesServiceImpl(TransactionTypesRepository transactionTypesRepository, TransactionTypesMapper transactionTypesMapper, TransactionTypesSearchRepository transactionTypesSearchRepository) {
        this.transactionTypesRepository = transactionTypesRepository;
        this.transactionTypesMapper = transactionTypesMapper;
        this.transactionTypesSearchRepository = transactionTypesSearchRepository;
    }

    /**
     * Save a transactionTypes.
     *
     * @param transactionTypesDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public TransactionTypesDTO save(TransactionTypesDTO transactionTypesDTO) {
        log.debug("Request to save TransactionTypes : {}", transactionTypesDTO);
        TransactionTypes transactionTypes = transactionTypesMapper.toEntity(transactionTypesDTO);
        transactionTypes = transactionTypesRepository.save(transactionTypes);
        TransactionTypesDTO result = transactionTypesMapper.toDto(transactionTypes);
        transactionTypesSearchRepository.save(transactionTypes);
        return result;
    }

    /**
     * Get all the transactionTypes.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<TransactionTypesDTO> findAll() {
        log.debug("Request to get all TransactionTypes");
        return transactionTypesRepository.findAll().stream()
            .map(transactionTypesMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one transactionTypes by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TransactionTypesDTO> findOne(Long id) {
        log.debug("Request to get TransactionTypes : {}", id);
        return transactionTypesRepository.findById(id)
            .map(transactionTypesMapper::toDto);
    }

    /**
     * Delete the transactionTypes by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TransactionTypes : {}", id);
        transactionTypesRepository.deleteById(id);
        transactionTypesSearchRepository.deleteById(id);
    }

    /**
     * Search for the transactionTypes corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<TransactionTypesDTO> search(String query) {
        log.debug("Request to search TransactionTypes for query {}", query);
        return StreamSupport
            .stream(transactionTypesSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(transactionTypesMapper::toDto)
            .collect(Collectors.toList());
    }
}
