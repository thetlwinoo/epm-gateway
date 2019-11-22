package com.epmserver.gateway.service.impl;

import com.epmserver.gateway.service.UploadActionTypesService;
import com.epmserver.gateway.domain.UploadActionTypes;
import com.epmserver.gateway.repository.UploadActionTypesRepository;
import com.epmserver.gateway.repository.search.UploadActionTypesSearchRepository;
import com.epmserver.gateway.service.dto.UploadActionTypesDTO;
import com.epmserver.gateway.service.mapper.UploadActionTypesMapper;
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
 * Service Implementation for managing {@link UploadActionTypes}.
 */
@Service
@Transactional
public class UploadActionTypesServiceImpl implements UploadActionTypesService {

    private final Logger log = LoggerFactory.getLogger(UploadActionTypesServiceImpl.class);

    private final UploadActionTypesRepository uploadActionTypesRepository;

    private final UploadActionTypesMapper uploadActionTypesMapper;

    private final UploadActionTypesSearchRepository uploadActionTypesSearchRepository;

    public UploadActionTypesServiceImpl(UploadActionTypesRepository uploadActionTypesRepository, UploadActionTypesMapper uploadActionTypesMapper, UploadActionTypesSearchRepository uploadActionTypesSearchRepository) {
        this.uploadActionTypesRepository = uploadActionTypesRepository;
        this.uploadActionTypesMapper = uploadActionTypesMapper;
        this.uploadActionTypesSearchRepository = uploadActionTypesSearchRepository;
    }

    /**
     * Save a uploadActionTypes.
     *
     * @param uploadActionTypesDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public UploadActionTypesDTO save(UploadActionTypesDTO uploadActionTypesDTO) {
        log.debug("Request to save UploadActionTypes : {}", uploadActionTypesDTO);
        UploadActionTypes uploadActionTypes = uploadActionTypesMapper.toEntity(uploadActionTypesDTO);
        uploadActionTypes = uploadActionTypesRepository.save(uploadActionTypes);
        UploadActionTypesDTO result = uploadActionTypesMapper.toDto(uploadActionTypes);
        uploadActionTypesSearchRepository.save(uploadActionTypes);
        return result;
    }

    /**
     * Get all the uploadActionTypes.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<UploadActionTypesDTO> findAll() {
        log.debug("Request to get all UploadActionTypes");
        return uploadActionTypesRepository.findAll().stream()
            .map(uploadActionTypesMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one uploadActionTypes by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<UploadActionTypesDTO> findOne(Long id) {
        log.debug("Request to get UploadActionTypes : {}", id);
        return uploadActionTypesRepository.findById(id)
            .map(uploadActionTypesMapper::toDto);
    }

    /**
     * Delete the uploadActionTypes by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete UploadActionTypes : {}", id);
        uploadActionTypesRepository.deleteById(id);
        uploadActionTypesSearchRepository.deleteById(id);
    }

    /**
     * Search for the uploadActionTypes corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<UploadActionTypesDTO> search(String query) {
        log.debug("Request to search UploadActionTypes for query {}", query);
        return StreamSupport
            .stream(uploadActionTypesSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(uploadActionTypesMapper::toDto)
            .collect(Collectors.toList());
    }
}
