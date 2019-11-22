package com.epmserver.gateway.service.impl;

import com.epmserver.gateway.service.InvoicesService;
import com.epmserver.gateway.domain.Invoices;
import com.epmserver.gateway.repository.InvoicesRepository;
import com.epmserver.gateway.repository.search.InvoicesSearchRepository;
import com.epmserver.gateway.service.dto.InvoicesDTO;
import com.epmserver.gateway.service.mapper.InvoicesMapper;
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
 * Service Implementation for managing {@link Invoices}.
 */
@Service
@Transactional
public class InvoicesServiceImpl implements InvoicesService {

    private final Logger log = LoggerFactory.getLogger(InvoicesServiceImpl.class);

    private final InvoicesRepository invoicesRepository;

    private final InvoicesMapper invoicesMapper;

    private final InvoicesSearchRepository invoicesSearchRepository;

    public InvoicesServiceImpl(InvoicesRepository invoicesRepository, InvoicesMapper invoicesMapper, InvoicesSearchRepository invoicesSearchRepository) {
        this.invoicesRepository = invoicesRepository;
        this.invoicesMapper = invoicesMapper;
        this.invoicesSearchRepository = invoicesSearchRepository;
    }

    /**
     * Save a invoices.
     *
     * @param invoicesDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public InvoicesDTO save(InvoicesDTO invoicesDTO) {
        log.debug("Request to save Invoices : {}", invoicesDTO);
        Invoices invoices = invoicesMapper.toEntity(invoicesDTO);
        invoices = invoicesRepository.save(invoices);
        InvoicesDTO result = invoicesMapper.toDto(invoices);
        invoicesSearchRepository.save(invoices);
        return result;
    }

    /**
     * Get all the invoices.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<InvoicesDTO> findAll() {
        log.debug("Request to get all Invoices");
        return invoicesRepository.findAll().stream()
            .map(invoicesMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one invoices by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<InvoicesDTO> findOne(Long id) {
        log.debug("Request to get Invoices : {}", id);
        return invoicesRepository.findById(id)
            .map(invoicesMapper::toDto);
    }

    /**
     * Delete the invoices by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Invoices : {}", id);
        invoicesRepository.deleteById(id);
        invoicesSearchRepository.deleteById(id);
    }

    /**
     * Search for the invoices corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<InvoicesDTO> search(String query) {
        log.debug("Request to search Invoices for query {}", query);
        return StreamSupport
            .stream(invoicesSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(invoicesMapper::toDto)
            .collect(Collectors.toList());
    }
}
