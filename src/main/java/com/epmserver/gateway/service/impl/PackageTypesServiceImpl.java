package com.epmserver.gateway.service.impl;

import com.epmserver.gateway.service.PackageTypesService;
import com.epmserver.gateway.domain.PackageTypes;
import com.epmserver.gateway.repository.PackageTypesRepository;
import com.epmserver.gateway.repository.search.PackageTypesSearchRepository;
import com.epmserver.gateway.service.dto.PackageTypesDTO;
import com.epmserver.gateway.service.mapper.PackageTypesMapper;
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
 * Service Implementation for managing {@link PackageTypes}.
 */
@Service
@Transactional
public class PackageTypesServiceImpl implements PackageTypesService {

    private final Logger log = LoggerFactory.getLogger(PackageTypesServiceImpl.class);

    private final PackageTypesRepository packageTypesRepository;

    private final PackageTypesMapper packageTypesMapper;

    private final PackageTypesSearchRepository packageTypesSearchRepository;

    public PackageTypesServiceImpl(PackageTypesRepository packageTypesRepository, PackageTypesMapper packageTypesMapper, PackageTypesSearchRepository packageTypesSearchRepository) {
        this.packageTypesRepository = packageTypesRepository;
        this.packageTypesMapper = packageTypesMapper;
        this.packageTypesSearchRepository = packageTypesSearchRepository;
    }

    /**
     * Save a packageTypes.
     *
     * @param packageTypesDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public PackageTypesDTO save(PackageTypesDTO packageTypesDTO) {
        log.debug("Request to save PackageTypes : {}", packageTypesDTO);
        PackageTypes packageTypes = packageTypesMapper.toEntity(packageTypesDTO);
        packageTypes = packageTypesRepository.save(packageTypes);
        PackageTypesDTO result = packageTypesMapper.toDto(packageTypes);
        packageTypesSearchRepository.save(packageTypes);
        return result;
    }

    /**
     * Get all the packageTypes.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<PackageTypesDTO> findAll() {
        log.debug("Request to get all PackageTypes");
        return packageTypesRepository.findAll().stream()
            .map(packageTypesMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one packageTypes by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PackageTypesDTO> findOne(Long id) {
        log.debug("Request to get PackageTypes : {}", id);
        return packageTypesRepository.findById(id)
            .map(packageTypesMapper::toDto);
    }

    /**
     * Delete the packageTypes by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PackageTypes : {}", id);
        packageTypesRepository.deleteById(id);
        packageTypesSearchRepository.deleteById(id);
    }

    /**
     * Search for the packageTypes corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<PackageTypesDTO> search(String query) {
        log.debug("Request to search PackageTypes for query {}", query);
        return StreamSupport
            .stream(packageTypesSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(packageTypesMapper::toDto)
            .collect(Collectors.toList());
    }
}
