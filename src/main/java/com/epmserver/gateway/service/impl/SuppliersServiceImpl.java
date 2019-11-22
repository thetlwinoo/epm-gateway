package com.epmserver.gateway.service.impl;

import com.epmserver.gateway.service.SuppliersService;
import com.epmserver.gateway.domain.Suppliers;
import com.epmserver.gateway.repository.SuppliersRepository;
import com.epmserver.gateway.repository.search.SuppliersSearchRepository;
import com.epmserver.gateway.service.dto.SuppliersDTO;
import com.epmserver.gateway.service.mapper.SuppliersMapper;
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
 * Service Implementation for managing {@link Suppliers}.
 */
@Service
@Transactional
public class SuppliersServiceImpl implements SuppliersService {

    private final Logger log = LoggerFactory.getLogger(SuppliersServiceImpl.class);

    private final SuppliersRepository suppliersRepository;

    private final SuppliersMapper suppliersMapper;

    private final SuppliersSearchRepository suppliersSearchRepository;

    public SuppliersServiceImpl(SuppliersRepository suppliersRepository, SuppliersMapper suppliersMapper, SuppliersSearchRepository suppliersSearchRepository) {
        this.suppliersRepository = suppliersRepository;
        this.suppliersMapper = suppliersMapper;
        this.suppliersSearchRepository = suppliersSearchRepository;
    }

    /**
     * Save a suppliers.
     *
     * @param suppliersDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public SuppliersDTO save(SuppliersDTO suppliersDTO) {
        log.debug("Request to save Suppliers : {}", suppliersDTO);
        Suppliers suppliers = suppliersMapper.toEntity(suppliersDTO);
        suppliers = suppliersRepository.save(suppliers);
        SuppliersDTO result = suppliersMapper.toDto(suppliers);
        suppliersSearchRepository.save(suppliers);
        return result;
    }

    /**
     * Get all the suppliers.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<SuppliersDTO> findAll() {
        log.debug("Request to get all Suppliers");
        return suppliersRepository.findAll().stream()
            .map(suppliersMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one suppliers by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SuppliersDTO> findOne(Long id) {
        log.debug("Request to get Suppliers : {}", id);
        return suppliersRepository.findById(id)
            .map(suppliersMapper::toDto);
    }

    /**
     * Delete the suppliers by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Suppliers : {}", id);
        suppliersRepository.deleteById(id);
        suppliersSearchRepository.deleteById(id);
    }

    /**
     * Search for the suppliers corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<SuppliersDTO> search(String query) {
        log.debug("Request to search Suppliers for query {}", query);
        return StreamSupport
            .stream(suppliersSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(suppliersMapper::toDto)
            .collect(Collectors.toList());
    }
}
