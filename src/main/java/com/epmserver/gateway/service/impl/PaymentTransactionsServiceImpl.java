package com.epmserver.gateway.service.impl;

import com.epmserver.gateway.service.PaymentTransactionsService;
import com.epmserver.gateway.domain.PaymentTransactions;
import com.epmserver.gateway.repository.PaymentTransactionsRepository;
import com.epmserver.gateway.repository.search.PaymentTransactionsSearchRepository;
import com.epmserver.gateway.service.dto.PaymentTransactionsDTO;
import com.epmserver.gateway.service.mapper.PaymentTransactionsMapper;
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
 * Service Implementation for managing {@link PaymentTransactions}.
 */
@Service
@Transactional
public class PaymentTransactionsServiceImpl implements PaymentTransactionsService {

    private final Logger log = LoggerFactory.getLogger(PaymentTransactionsServiceImpl.class);

    private final PaymentTransactionsRepository paymentTransactionsRepository;

    private final PaymentTransactionsMapper paymentTransactionsMapper;

    private final PaymentTransactionsSearchRepository paymentTransactionsSearchRepository;

    public PaymentTransactionsServiceImpl(PaymentTransactionsRepository paymentTransactionsRepository, PaymentTransactionsMapper paymentTransactionsMapper, PaymentTransactionsSearchRepository paymentTransactionsSearchRepository) {
        this.paymentTransactionsRepository = paymentTransactionsRepository;
        this.paymentTransactionsMapper = paymentTransactionsMapper;
        this.paymentTransactionsSearchRepository = paymentTransactionsSearchRepository;
    }

    /**
     * Save a paymentTransactions.
     *
     * @param paymentTransactionsDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public PaymentTransactionsDTO save(PaymentTransactionsDTO paymentTransactionsDTO) {
        log.debug("Request to save PaymentTransactions : {}", paymentTransactionsDTO);
        PaymentTransactions paymentTransactions = paymentTransactionsMapper.toEntity(paymentTransactionsDTO);
        paymentTransactions = paymentTransactionsRepository.save(paymentTransactions);
        PaymentTransactionsDTO result = paymentTransactionsMapper.toDto(paymentTransactions);
        paymentTransactionsSearchRepository.save(paymentTransactions);
        return result;
    }

    /**
     * Get all the paymentTransactions.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<PaymentTransactionsDTO> findAll() {
        log.debug("Request to get all PaymentTransactions");
        return paymentTransactionsRepository.findAll().stream()
            .map(paymentTransactionsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one paymentTransactions by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PaymentTransactionsDTO> findOne(Long id) {
        log.debug("Request to get PaymentTransactions : {}", id);
        return paymentTransactionsRepository.findById(id)
            .map(paymentTransactionsMapper::toDto);
    }

    /**
     * Delete the paymentTransactions by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PaymentTransactions : {}", id);
        paymentTransactionsRepository.deleteById(id);
        paymentTransactionsSearchRepository.deleteById(id);
    }

    /**
     * Search for the paymentTransactions corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<PaymentTransactionsDTO> search(String query) {
        log.debug("Request to search PaymentTransactions for query {}", query);
        return StreamSupport
            .stream(paymentTransactionsSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(paymentTransactionsMapper::toDto)
            .collect(Collectors.toList());
    }
}
