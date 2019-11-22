package com.epmserver.gateway.service.impl;

import com.epmserver.gateway.service.ColdRoomTemperaturesService;
import com.epmserver.gateway.domain.ColdRoomTemperatures;
import com.epmserver.gateway.repository.ColdRoomTemperaturesRepository;
import com.epmserver.gateway.repository.search.ColdRoomTemperaturesSearchRepository;
import com.epmserver.gateway.service.dto.ColdRoomTemperaturesDTO;
import com.epmserver.gateway.service.mapper.ColdRoomTemperaturesMapper;
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
 * Service Implementation for managing {@link ColdRoomTemperatures}.
 */
@Service
@Transactional
public class ColdRoomTemperaturesServiceImpl implements ColdRoomTemperaturesService {

    private final Logger log = LoggerFactory.getLogger(ColdRoomTemperaturesServiceImpl.class);

    private final ColdRoomTemperaturesRepository coldRoomTemperaturesRepository;

    private final ColdRoomTemperaturesMapper coldRoomTemperaturesMapper;

    private final ColdRoomTemperaturesSearchRepository coldRoomTemperaturesSearchRepository;

    public ColdRoomTemperaturesServiceImpl(ColdRoomTemperaturesRepository coldRoomTemperaturesRepository, ColdRoomTemperaturesMapper coldRoomTemperaturesMapper, ColdRoomTemperaturesSearchRepository coldRoomTemperaturesSearchRepository) {
        this.coldRoomTemperaturesRepository = coldRoomTemperaturesRepository;
        this.coldRoomTemperaturesMapper = coldRoomTemperaturesMapper;
        this.coldRoomTemperaturesSearchRepository = coldRoomTemperaturesSearchRepository;
    }

    /**
     * Save a coldRoomTemperatures.
     *
     * @param coldRoomTemperaturesDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ColdRoomTemperaturesDTO save(ColdRoomTemperaturesDTO coldRoomTemperaturesDTO) {
        log.debug("Request to save ColdRoomTemperatures : {}", coldRoomTemperaturesDTO);
        ColdRoomTemperatures coldRoomTemperatures = coldRoomTemperaturesMapper.toEntity(coldRoomTemperaturesDTO);
        coldRoomTemperatures = coldRoomTemperaturesRepository.save(coldRoomTemperatures);
        ColdRoomTemperaturesDTO result = coldRoomTemperaturesMapper.toDto(coldRoomTemperatures);
        coldRoomTemperaturesSearchRepository.save(coldRoomTemperatures);
        return result;
    }

    /**
     * Get all the coldRoomTemperatures.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ColdRoomTemperaturesDTO> findAll() {
        log.debug("Request to get all ColdRoomTemperatures");
        return coldRoomTemperaturesRepository.findAll().stream()
            .map(coldRoomTemperaturesMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one coldRoomTemperatures by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ColdRoomTemperaturesDTO> findOne(Long id) {
        log.debug("Request to get ColdRoomTemperatures : {}", id);
        return coldRoomTemperaturesRepository.findById(id)
            .map(coldRoomTemperaturesMapper::toDto);
    }

    /**
     * Delete the coldRoomTemperatures by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ColdRoomTemperatures : {}", id);
        coldRoomTemperaturesRepository.deleteById(id);
        coldRoomTemperaturesSearchRepository.deleteById(id);
    }

    /**
     * Search for the coldRoomTemperatures corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ColdRoomTemperaturesDTO> search(String query) {
        log.debug("Request to search ColdRoomTemperatures for query {}", query);
        return StreamSupport
            .stream(coldRoomTemperaturesSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(coldRoomTemperaturesMapper::toDto)
            .collect(Collectors.toList());
    }
}
