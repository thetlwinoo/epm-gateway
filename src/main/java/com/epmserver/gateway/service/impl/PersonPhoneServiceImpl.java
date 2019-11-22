package com.epmserver.gateway.service.impl;

import com.epmserver.gateway.service.PersonPhoneService;
import com.epmserver.gateway.domain.PersonPhone;
import com.epmserver.gateway.repository.PersonPhoneRepository;
import com.epmserver.gateway.repository.search.PersonPhoneSearchRepository;
import com.epmserver.gateway.service.dto.PersonPhoneDTO;
import com.epmserver.gateway.service.mapper.PersonPhoneMapper;
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
 * Service Implementation for managing {@link PersonPhone}.
 */
@Service
@Transactional
public class PersonPhoneServiceImpl implements PersonPhoneService {

    private final Logger log = LoggerFactory.getLogger(PersonPhoneServiceImpl.class);

    private final PersonPhoneRepository personPhoneRepository;

    private final PersonPhoneMapper personPhoneMapper;

    private final PersonPhoneSearchRepository personPhoneSearchRepository;

    public PersonPhoneServiceImpl(PersonPhoneRepository personPhoneRepository, PersonPhoneMapper personPhoneMapper, PersonPhoneSearchRepository personPhoneSearchRepository) {
        this.personPhoneRepository = personPhoneRepository;
        this.personPhoneMapper = personPhoneMapper;
        this.personPhoneSearchRepository = personPhoneSearchRepository;
    }

    /**
     * Save a personPhone.
     *
     * @param personPhoneDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public PersonPhoneDTO save(PersonPhoneDTO personPhoneDTO) {
        log.debug("Request to save PersonPhone : {}", personPhoneDTO);
        PersonPhone personPhone = personPhoneMapper.toEntity(personPhoneDTO);
        personPhone = personPhoneRepository.save(personPhone);
        PersonPhoneDTO result = personPhoneMapper.toDto(personPhone);
        personPhoneSearchRepository.save(personPhone);
        return result;
    }

    /**
     * Get all the personPhones.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<PersonPhoneDTO> findAll() {
        log.debug("Request to get all PersonPhones");
        return personPhoneRepository.findAll().stream()
            .map(personPhoneMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one personPhone by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PersonPhoneDTO> findOne(Long id) {
        log.debug("Request to get PersonPhone : {}", id);
        return personPhoneRepository.findById(id)
            .map(personPhoneMapper::toDto);
    }

    /**
     * Delete the personPhone by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PersonPhone : {}", id);
        personPhoneRepository.deleteById(id);
        personPhoneSearchRepository.deleteById(id);
    }

    /**
     * Search for the personPhone corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<PersonPhoneDTO> search(String query) {
        log.debug("Request to search PersonPhones for query {}", query);
        return StreamSupport
            .stream(personPhoneSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(personPhoneMapper::toDto)
            .collect(Collectors.toList());
    }
}
