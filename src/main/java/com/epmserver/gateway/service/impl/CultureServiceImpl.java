package com.epmserver.gateway.service.impl;

import com.epmserver.gateway.service.CultureService;
import com.epmserver.gateway.domain.Culture;
import com.epmserver.gateway.repository.CultureRepository;
import com.epmserver.gateway.repository.search.CultureSearchRepository;
import com.epmserver.gateway.service.dto.CultureDTO;
import com.epmserver.gateway.service.mapper.CultureMapper;
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
 * Service Implementation for managing {@link Culture}.
 */
@Service
@Transactional
public class CultureServiceImpl implements CultureService {

    private final Logger log = LoggerFactory.getLogger(CultureServiceImpl.class);

    private final CultureRepository cultureRepository;

    private final CultureMapper cultureMapper;

    private final CultureSearchRepository cultureSearchRepository;

    public CultureServiceImpl(CultureRepository cultureRepository, CultureMapper cultureMapper, CultureSearchRepository cultureSearchRepository) {
        this.cultureRepository = cultureRepository;
        this.cultureMapper = cultureMapper;
        this.cultureSearchRepository = cultureSearchRepository;
    }

    /**
     * Save a culture.
     *
     * @param cultureDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public CultureDTO save(CultureDTO cultureDTO) {
        log.debug("Request to save Culture : {}", cultureDTO);
        Culture culture = cultureMapper.toEntity(cultureDTO);
        culture = cultureRepository.save(culture);
        CultureDTO result = cultureMapper.toDto(culture);
        cultureSearchRepository.save(culture);
        return result;
    }

    /**
     * Get all the cultures.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<CultureDTO> findAll() {
        log.debug("Request to get all Cultures");
        return cultureRepository.findAll().stream()
            .map(cultureMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one culture by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CultureDTO> findOne(Long id) {
        log.debug("Request to get Culture : {}", id);
        return cultureRepository.findById(id)
            .map(cultureMapper::toDto);
    }

    /**
     * Delete the culture by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Culture : {}", id);
        cultureRepository.deleteById(id);
        cultureSearchRepository.deleteById(id);
    }

    /**
     * Search for the culture corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<CultureDTO> search(String query) {
        log.debug("Request to search Cultures for query {}", query);
        return StreamSupport
            .stream(cultureSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(cultureMapper::toDto)
            .collect(Collectors.toList());
    }
}
