package com.epmserver.gateway.service.impl;

import com.epmserver.gateway.service.PhoneNumberTypeService;
import com.epmserver.gateway.domain.PhoneNumberType;
import com.epmserver.gateway.repository.PhoneNumberTypeRepository;
import com.epmserver.gateway.repository.search.PhoneNumberTypeSearchRepository;
import com.epmserver.gateway.service.dto.PhoneNumberTypeDTO;
import com.epmserver.gateway.service.mapper.PhoneNumberTypeMapper;
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
 * Service Implementation for managing {@link PhoneNumberType}.
 */
@Service
@Transactional
public class PhoneNumberTypeServiceImpl implements PhoneNumberTypeService {

    private final Logger log = LoggerFactory.getLogger(PhoneNumberTypeServiceImpl.class);

    private final PhoneNumberTypeRepository phoneNumberTypeRepository;

    private final PhoneNumberTypeMapper phoneNumberTypeMapper;

    private final PhoneNumberTypeSearchRepository phoneNumberTypeSearchRepository;

    public PhoneNumberTypeServiceImpl(PhoneNumberTypeRepository phoneNumberTypeRepository, PhoneNumberTypeMapper phoneNumberTypeMapper, PhoneNumberTypeSearchRepository phoneNumberTypeSearchRepository) {
        this.phoneNumberTypeRepository = phoneNumberTypeRepository;
        this.phoneNumberTypeMapper = phoneNumberTypeMapper;
        this.phoneNumberTypeSearchRepository = phoneNumberTypeSearchRepository;
    }

    /**
     * Save a phoneNumberType.
     *
     * @param phoneNumberTypeDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public PhoneNumberTypeDTO save(PhoneNumberTypeDTO phoneNumberTypeDTO) {
        log.debug("Request to save PhoneNumberType : {}", phoneNumberTypeDTO);
        PhoneNumberType phoneNumberType = phoneNumberTypeMapper.toEntity(phoneNumberTypeDTO);
        phoneNumberType = phoneNumberTypeRepository.save(phoneNumberType);
        PhoneNumberTypeDTO result = phoneNumberTypeMapper.toDto(phoneNumberType);
        phoneNumberTypeSearchRepository.save(phoneNumberType);
        return result;
    }

    /**
     * Get all the phoneNumberTypes.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<PhoneNumberTypeDTO> findAll() {
        log.debug("Request to get all PhoneNumberTypes");
        return phoneNumberTypeRepository.findAll().stream()
            .map(phoneNumberTypeMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one phoneNumberType by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PhoneNumberTypeDTO> findOne(Long id) {
        log.debug("Request to get PhoneNumberType : {}", id);
        return phoneNumberTypeRepository.findById(id)
            .map(phoneNumberTypeMapper::toDto);
    }

    /**
     * Delete the phoneNumberType by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PhoneNumberType : {}", id);
        phoneNumberTypeRepository.deleteById(id);
        phoneNumberTypeSearchRepository.deleteById(id);
    }

    /**
     * Search for the phoneNumberType corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<PhoneNumberTypeDTO> search(String query) {
        log.debug("Request to search PhoneNumberTypes for query {}", query);
        return StreamSupport
            .stream(phoneNumberTypeSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(phoneNumberTypeMapper::toDto)
            .collect(Collectors.toList());
    }
}
