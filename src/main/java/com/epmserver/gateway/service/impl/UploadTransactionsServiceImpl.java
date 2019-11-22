package com.epmserver.gateway.service.impl;

import com.epmserver.gateway.service.UploadTransactionsService;
import com.epmserver.gateway.domain.UploadTransactions;
import com.epmserver.gateway.repository.UploadTransactionsRepository;
import com.epmserver.gateway.repository.search.UploadTransactionsSearchRepository;
import com.epmserver.gateway.service.dto.UploadTransactionsDTO;
import com.epmserver.gateway.service.mapper.UploadTransactionsMapper;
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
 * Service Implementation for managing {@link UploadTransactions}.
 */
@Service
@Transactional
public class UploadTransactionsServiceImpl implements UploadTransactionsService {

    private final Logger log = LoggerFactory.getLogger(UploadTransactionsServiceImpl.class);

    private final UploadTransactionsRepository uploadTransactionsRepository;

    private final UploadTransactionsMapper uploadTransactionsMapper;

    private final UploadTransactionsSearchRepository uploadTransactionsSearchRepository;

    public UploadTransactionsServiceImpl(UploadTransactionsRepository uploadTransactionsRepository, UploadTransactionsMapper uploadTransactionsMapper, UploadTransactionsSearchRepository uploadTransactionsSearchRepository) {
        this.uploadTransactionsRepository = uploadTransactionsRepository;
        this.uploadTransactionsMapper = uploadTransactionsMapper;
        this.uploadTransactionsSearchRepository = uploadTransactionsSearchRepository;
    }

    /**
     * Save a uploadTransactions.
     *
     * @param uploadTransactionsDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public UploadTransactionsDTO save(UploadTransactionsDTO uploadTransactionsDTO) {
        log.debug("Request to save UploadTransactions : {}", uploadTransactionsDTO);
        UploadTransactions uploadTransactions = uploadTransactionsMapper.toEntity(uploadTransactionsDTO);
        uploadTransactions = uploadTransactionsRepository.save(uploadTransactions);
        UploadTransactionsDTO result = uploadTransactionsMapper.toDto(uploadTransactions);
        uploadTransactionsSearchRepository.save(uploadTransactions);
        return result;
    }

    /**
     * Get all the uploadTransactions.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<UploadTransactionsDTO> findAll() {
        log.debug("Request to get all UploadTransactions");
        return uploadTransactionsRepository.findAll().stream()
            .map(uploadTransactionsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one uploadTransactions by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<UploadTransactionsDTO> findOne(Long id) {
        log.debug("Request to get UploadTransactions : {}", id);
        return uploadTransactionsRepository.findById(id)
            .map(uploadTransactionsMapper::toDto);
    }

    /**
     * Delete the uploadTransactions by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete UploadTransactions : {}", id);
        uploadTransactionsRepository.deleteById(id);
        uploadTransactionsSearchRepository.deleteById(id);
    }

    /**
     * Search for the uploadTransactions corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<UploadTransactionsDTO> search(String query) {
        log.debug("Request to search UploadTransactions for query {}", query);
        return StreamSupport
            .stream(uploadTransactionsSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(uploadTransactionsMapper::toDto)
            .collect(Collectors.toList());
    }
}
