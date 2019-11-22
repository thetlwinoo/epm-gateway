package com.epmserver.gateway.service.impl;

import com.epmserver.gateway.service.UnitMeasureService;
import com.epmserver.gateway.domain.UnitMeasure;
import com.epmserver.gateway.repository.UnitMeasureRepository;
import com.epmserver.gateway.repository.search.UnitMeasureSearchRepository;
import com.epmserver.gateway.service.dto.UnitMeasureDTO;
import com.epmserver.gateway.service.mapper.UnitMeasureMapper;
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
 * Service Implementation for managing {@link UnitMeasure}.
 */
@Service
@Transactional
public class UnitMeasureServiceImpl implements UnitMeasureService {

    private final Logger log = LoggerFactory.getLogger(UnitMeasureServiceImpl.class);

    private final UnitMeasureRepository unitMeasureRepository;

    private final UnitMeasureMapper unitMeasureMapper;

    private final UnitMeasureSearchRepository unitMeasureSearchRepository;

    public UnitMeasureServiceImpl(UnitMeasureRepository unitMeasureRepository, UnitMeasureMapper unitMeasureMapper, UnitMeasureSearchRepository unitMeasureSearchRepository) {
        this.unitMeasureRepository = unitMeasureRepository;
        this.unitMeasureMapper = unitMeasureMapper;
        this.unitMeasureSearchRepository = unitMeasureSearchRepository;
    }

    /**
     * Save a unitMeasure.
     *
     * @param unitMeasureDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public UnitMeasureDTO save(UnitMeasureDTO unitMeasureDTO) {
        log.debug("Request to save UnitMeasure : {}", unitMeasureDTO);
        UnitMeasure unitMeasure = unitMeasureMapper.toEntity(unitMeasureDTO);
        unitMeasure = unitMeasureRepository.save(unitMeasure);
        UnitMeasureDTO result = unitMeasureMapper.toDto(unitMeasure);
        unitMeasureSearchRepository.save(unitMeasure);
        return result;
    }

    /**
     * Get all the unitMeasures.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<UnitMeasureDTO> findAll() {
        log.debug("Request to get all UnitMeasures");
        return unitMeasureRepository.findAll().stream()
            .map(unitMeasureMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one unitMeasure by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<UnitMeasureDTO> findOne(Long id) {
        log.debug("Request to get UnitMeasure : {}", id);
        return unitMeasureRepository.findById(id)
            .map(unitMeasureMapper::toDto);
    }

    /**
     * Delete the unitMeasure by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete UnitMeasure : {}", id);
        unitMeasureRepository.deleteById(id);
        unitMeasureSearchRepository.deleteById(id);
    }

    /**
     * Search for the unitMeasure corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<UnitMeasureDTO> search(String query) {
        log.debug("Request to search UnitMeasures for query {}", query);
        return StreamSupport
            .stream(unitMeasureSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(unitMeasureMapper::toDto)
            .collect(Collectors.toList());
    }
}
