package com.epmserver.gateway.service.impl;

import com.epmserver.gateway.service.PersonEmailAddressService;
import com.epmserver.gateway.domain.PersonEmailAddress;
import com.epmserver.gateway.repository.PersonEmailAddressRepository;
import com.epmserver.gateway.repository.search.PersonEmailAddressSearchRepository;
import com.epmserver.gateway.service.dto.PersonEmailAddressDTO;
import com.epmserver.gateway.service.mapper.PersonEmailAddressMapper;
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
 * Service Implementation for managing {@link PersonEmailAddress}.
 */
@Service
@Transactional
public class PersonEmailAddressServiceImpl implements PersonEmailAddressService {

    private final Logger log = LoggerFactory.getLogger(PersonEmailAddressServiceImpl.class);

    private final PersonEmailAddressRepository personEmailAddressRepository;

    private final PersonEmailAddressMapper personEmailAddressMapper;

    private final PersonEmailAddressSearchRepository personEmailAddressSearchRepository;

    public PersonEmailAddressServiceImpl(PersonEmailAddressRepository personEmailAddressRepository, PersonEmailAddressMapper personEmailAddressMapper, PersonEmailAddressSearchRepository personEmailAddressSearchRepository) {
        this.personEmailAddressRepository = personEmailAddressRepository;
        this.personEmailAddressMapper = personEmailAddressMapper;
        this.personEmailAddressSearchRepository = personEmailAddressSearchRepository;
    }

    /**
     * Save a personEmailAddress.
     *
     * @param personEmailAddressDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public PersonEmailAddressDTO save(PersonEmailAddressDTO personEmailAddressDTO) {
        log.debug("Request to save PersonEmailAddress : {}", personEmailAddressDTO);
        PersonEmailAddress personEmailAddress = personEmailAddressMapper.toEntity(personEmailAddressDTO);
        personEmailAddress = personEmailAddressRepository.save(personEmailAddress);
        PersonEmailAddressDTO result = personEmailAddressMapper.toDto(personEmailAddress);
        personEmailAddressSearchRepository.save(personEmailAddress);
        return result;
    }

    /**
     * Get all the personEmailAddresses.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<PersonEmailAddressDTO> findAll() {
        log.debug("Request to get all PersonEmailAddresses");
        return personEmailAddressRepository.findAll().stream()
            .map(personEmailAddressMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one personEmailAddress by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PersonEmailAddressDTO> findOne(Long id) {
        log.debug("Request to get PersonEmailAddress : {}", id);
        return personEmailAddressRepository.findById(id)
            .map(personEmailAddressMapper::toDto);
    }

    /**
     * Delete the personEmailAddress by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PersonEmailAddress : {}", id);
        personEmailAddressRepository.deleteById(id);
        personEmailAddressSearchRepository.deleteById(id);
    }

    /**
     * Search for the personEmailAddress corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<PersonEmailAddressDTO> search(String query) {
        log.debug("Request to search PersonEmailAddresses for query {}", query);
        return StreamSupport
            .stream(personEmailAddressSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(personEmailAddressMapper::toDto)
            .collect(Collectors.toList());
    }
}
