package com.epmserver.gateway.service.impl;

import com.epmserver.gateway.service.InvoiceLinesService;
import com.epmserver.gateway.domain.InvoiceLines;
import com.epmserver.gateway.repository.InvoiceLinesRepository;
import com.epmserver.gateway.repository.search.InvoiceLinesSearchRepository;
import com.epmserver.gateway.service.dto.InvoiceLinesDTO;
import com.epmserver.gateway.service.mapper.InvoiceLinesMapper;
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
 * Service Implementation for managing {@link InvoiceLines}.
 */
@Service
@Transactional
public class InvoiceLinesServiceImpl implements InvoiceLinesService {

    private final Logger log = LoggerFactory.getLogger(InvoiceLinesServiceImpl.class);

    private final InvoiceLinesRepository invoiceLinesRepository;

    private final InvoiceLinesMapper invoiceLinesMapper;

    private final InvoiceLinesSearchRepository invoiceLinesSearchRepository;

    public InvoiceLinesServiceImpl(InvoiceLinesRepository invoiceLinesRepository, InvoiceLinesMapper invoiceLinesMapper, InvoiceLinesSearchRepository invoiceLinesSearchRepository) {
        this.invoiceLinesRepository = invoiceLinesRepository;
        this.invoiceLinesMapper = invoiceLinesMapper;
        this.invoiceLinesSearchRepository = invoiceLinesSearchRepository;
    }

    /**
     * Save a invoiceLines.
     *
     * @param invoiceLinesDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public InvoiceLinesDTO save(InvoiceLinesDTO invoiceLinesDTO) {
        log.debug("Request to save InvoiceLines : {}", invoiceLinesDTO);
        InvoiceLines invoiceLines = invoiceLinesMapper.toEntity(invoiceLinesDTO);
        invoiceLines = invoiceLinesRepository.save(invoiceLines);
        InvoiceLinesDTO result = invoiceLinesMapper.toDto(invoiceLines);
        invoiceLinesSearchRepository.save(invoiceLines);
        return result;
    }

    /**
     * Get all the invoiceLines.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<InvoiceLinesDTO> findAll() {
        log.debug("Request to get all InvoiceLines");
        return invoiceLinesRepository.findAll().stream()
            .map(invoiceLinesMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one invoiceLines by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<InvoiceLinesDTO> findOne(Long id) {
        log.debug("Request to get InvoiceLines : {}", id);
        return invoiceLinesRepository.findById(id)
            .map(invoiceLinesMapper::toDto);
    }

    /**
     * Delete the invoiceLines by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete InvoiceLines : {}", id);
        invoiceLinesRepository.deleteById(id);
        invoiceLinesSearchRepository.deleteById(id);
    }

    /**
     * Search for the invoiceLines corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<InvoiceLinesDTO> search(String query) {
        log.debug("Request to search InvoiceLines for query {}", query);
        return StreamSupport
            .stream(invoiceLinesSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(invoiceLinesMapper::toDto)
            .collect(Collectors.toList());
    }
}
