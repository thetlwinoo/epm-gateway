package com.epmserver.gateway.service.impl;

import com.epmserver.gateway.service.MaterialsService;
import com.epmserver.gateway.domain.Materials;
import com.epmserver.gateway.repository.MaterialsRepository;
import com.epmserver.gateway.repository.search.MaterialsSearchRepository;
import com.epmserver.gateway.service.dto.MaterialsDTO;
import com.epmserver.gateway.service.mapper.MaterialsMapper;
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
 * Service Implementation for managing {@link Materials}.
 */
@Service
@Transactional
public class MaterialsServiceImpl implements MaterialsService {

    private final Logger log = LoggerFactory.getLogger(MaterialsServiceImpl.class);

    private final MaterialsRepository materialsRepository;

    private final MaterialsMapper materialsMapper;

    private final MaterialsSearchRepository materialsSearchRepository;

    public MaterialsServiceImpl(MaterialsRepository materialsRepository, MaterialsMapper materialsMapper, MaterialsSearchRepository materialsSearchRepository) {
        this.materialsRepository = materialsRepository;
        this.materialsMapper = materialsMapper;
        this.materialsSearchRepository = materialsSearchRepository;
    }

    /**
     * Save a materials.
     *
     * @param materialsDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public MaterialsDTO save(MaterialsDTO materialsDTO) {
        log.debug("Request to save Materials : {}", materialsDTO);
        Materials materials = materialsMapper.toEntity(materialsDTO);
        materials = materialsRepository.save(materials);
        MaterialsDTO result = materialsMapper.toDto(materials);
        materialsSearchRepository.save(materials);
        return result;
    }

    /**
     * Get all the materials.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<MaterialsDTO> findAll() {
        log.debug("Request to get all Materials");
        return materialsRepository.findAll().stream()
            .map(materialsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one materials by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<MaterialsDTO> findOne(Long id) {
        log.debug("Request to get Materials : {}", id);
        return materialsRepository.findById(id)
            .map(materialsMapper::toDto);
    }

    /**
     * Delete the materials by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Materials : {}", id);
        materialsRepository.deleteById(id);
        materialsSearchRepository.deleteById(id);
    }

    /**
     * Search for the materials corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<MaterialsDTO> search(String query) {
        log.debug("Request to search Materials for query {}", query);
        return StreamSupport
            .stream(materialsSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(materialsMapper::toDto)
            .collect(Collectors.toList());
    }
}
