package com.epmserver.gateway.service.impl;

import com.epmserver.gateway.service.SystemParametersService;
import com.epmserver.gateway.domain.SystemParameters;
import com.epmserver.gateway.repository.SystemParametersRepository;
import com.epmserver.gateway.repository.search.SystemParametersSearchRepository;
import com.epmserver.gateway.service.dto.SystemParametersDTO;
import com.epmserver.gateway.service.mapper.SystemParametersMapper;
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
 * Service Implementation for managing {@link SystemParameters}.
 */
@Service
@Transactional
public class SystemParametersServiceImpl implements SystemParametersService {

    private final Logger log = LoggerFactory.getLogger(SystemParametersServiceImpl.class);

    private final SystemParametersRepository systemParametersRepository;

    private final SystemParametersMapper systemParametersMapper;

    private final SystemParametersSearchRepository systemParametersSearchRepository;

    public SystemParametersServiceImpl(SystemParametersRepository systemParametersRepository, SystemParametersMapper systemParametersMapper, SystemParametersSearchRepository systemParametersSearchRepository) {
        this.systemParametersRepository = systemParametersRepository;
        this.systemParametersMapper = systemParametersMapper;
        this.systemParametersSearchRepository = systemParametersSearchRepository;
    }

    /**
     * Save a systemParameters.
     *
     * @param systemParametersDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public SystemParametersDTO save(SystemParametersDTO systemParametersDTO) {
        log.debug("Request to save SystemParameters : {}", systemParametersDTO);
        SystemParameters systemParameters = systemParametersMapper.toEntity(systemParametersDTO);
        systemParameters = systemParametersRepository.save(systemParameters);
        SystemParametersDTO result = systemParametersMapper.toDto(systemParameters);
        systemParametersSearchRepository.save(systemParameters);
        return result;
    }

    /**
     * Get all the systemParameters.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<SystemParametersDTO> findAll() {
        log.debug("Request to get all SystemParameters");
        return systemParametersRepository.findAll().stream()
            .map(systemParametersMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one systemParameters by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SystemParametersDTO> findOne(Long id) {
        log.debug("Request to get SystemParameters : {}", id);
        return systemParametersRepository.findById(id)
            .map(systemParametersMapper::toDto);
    }

    /**
     * Delete the systemParameters by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SystemParameters : {}", id);
        systemParametersRepository.deleteById(id);
        systemParametersSearchRepository.deleteById(id);
    }

    /**
     * Search for the systemParameters corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<SystemParametersDTO> search(String query) {
        log.debug("Request to search SystemParameters for query {}", query);
        return StreamSupport
            .stream(systemParametersSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(systemParametersMapper::toDto)
            .collect(Collectors.toList());
    }
}
