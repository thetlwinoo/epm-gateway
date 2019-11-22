package com.epmserver.gateway.service.impl;

import com.epmserver.gateway.service.AddressesService;
import com.epmserver.gateway.domain.Addresses;
import com.epmserver.gateway.repository.AddressesRepository;
import com.epmserver.gateway.repository.search.AddressesSearchRepository;
import com.epmserver.gateway.service.dto.AddressesDTO;
import com.epmserver.gateway.service.mapper.AddressesMapper;
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
 * Service Implementation for managing {@link Addresses}.
 */
@Service
@Transactional
public class AddressesServiceImpl implements AddressesService {

    private final Logger log = LoggerFactory.getLogger(AddressesServiceImpl.class);

    private final AddressesRepository addressesRepository;

    private final AddressesMapper addressesMapper;

    private final AddressesSearchRepository addressesSearchRepository;

    public AddressesServiceImpl(AddressesRepository addressesRepository, AddressesMapper addressesMapper, AddressesSearchRepository addressesSearchRepository) {
        this.addressesRepository = addressesRepository;
        this.addressesMapper = addressesMapper;
        this.addressesSearchRepository = addressesSearchRepository;
    }

    /**
     * Save a addresses.
     *
     * @param addressesDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public AddressesDTO save(AddressesDTO addressesDTO) {
        log.debug("Request to save Addresses : {}", addressesDTO);
        Addresses addresses = addressesMapper.toEntity(addressesDTO);
        addresses = addressesRepository.save(addresses);
        AddressesDTO result = addressesMapper.toDto(addresses);
        addressesSearchRepository.save(addresses);
        return result;
    }

    /**
     * Get all the addresses.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<AddressesDTO> findAll() {
        log.debug("Request to get all Addresses");
        return addressesRepository.findAll().stream()
            .map(addressesMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one addresses by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AddressesDTO> findOne(Long id) {
        log.debug("Request to get Addresses : {}", id);
        return addressesRepository.findById(id)
            .map(addressesMapper::toDto);
    }

    /**
     * Delete the addresses by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Addresses : {}", id);
        addressesRepository.deleteById(id);
        addressesSearchRepository.deleteById(id);
    }

    /**
     * Search for the addresses corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<AddressesDTO> search(String query) {
        log.debug("Request to search Addresses for query {}", query);
        return StreamSupport
            .stream(addressesSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(addressesMapper::toDto)
            .collect(Collectors.toList());
    }
}
