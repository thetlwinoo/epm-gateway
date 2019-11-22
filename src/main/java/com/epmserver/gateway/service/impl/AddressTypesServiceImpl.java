package com.epmserver.gateway.service.impl;

import com.epmserver.gateway.service.AddressTypesService;
import com.epmserver.gateway.domain.AddressTypes;
import com.epmserver.gateway.repository.AddressTypesRepository;
import com.epmserver.gateway.repository.search.AddressTypesSearchRepository;
import com.epmserver.gateway.service.dto.AddressTypesDTO;
import com.epmserver.gateway.service.mapper.AddressTypesMapper;
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
 * Service Implementation for managing {@link AddressTypes}.
 */
@Service
@Transactional
public class AddressTypesServiceImpl implements AddressTypesService {

    private final Logger log = LoggerFactory.getLogger(AddressTypesServiceImpl.class);

    private final AddressTypesRepository addressTypesRepository;

    private final AddressTypesMapper addressTypesMapper;

    private final AddressTypesSearchRepository addressTypesSearchRepository;

    public AddressTypesServiceImpl(AddressTypesRepository addressTypesRepository, AddressTypesMapper addressTypesMapper, AddressTypesSearchRepository addressTypesSearchRepository) {
        this.addressTypesRepository = addressTypesRepository;
        this.addressTypesMapper = addressTypesMapper;
        this.addressTypesSearchRepository = addressTypesSearchRepository;
    }

    /**
     * Save a addressTypes.
     *
     * @param addressTypesDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public AddressTypesDTO save(AddressTypesDTO addressTypesDTO) {
        log.debug("Request to save AddressTypes : {}", addressTypesDTO);
        AddressTypes addressTypes = addressTypesMapper.toEntity(addressTypesDTO);
        addressTypes = addressTypesRepository.save(addressTypes);
        AddressTypesDTO result = addressTypesMapper.toDto(addressTypes);
        addressTypesSearchRepository.save(addressTypes);
        return result;
    }

    /**
     * Get all the addressTypes.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<AddressTypesDTO> findAll() {
        log.debug("Request to get all AddressTypes");
        return addressTypesRepository.findAll().stream()
            .map(addressTypesMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one addressTypes by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AddressTypesDTO> findOne(Long id) {
        log.debug("Request to get AddressTypes : {}", id);
        return addressTypesRepository.findById(id)
            .map(addressTypesMapper::toDto);
    }

    /**
     * Delete the addressTypes by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete AddressTypes : {}", id);
        addressTypesRepository.deleteById(id);
        addressTypesSearchRepository.deleteById(id);
    }

    /**
     * Search for the addressTypes corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<AddressTypesDTO> search(String query) {
        log.debug("Request to search AddressTypes for query {}", query);
        return StreamSupport
            .stream(addressTypesSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(addressTypesMapper::toDto)
            .collect(Collectors.toList());
    }
}
