package com.epmserver.gateway.service.impl;

import com.epmserver.gateway.service.PeopleService;
import com.epmserver.gateway.domain.People;
import com.epmserver.gateway.repository.PeopleRepository;
import com.epmserver.gateway.repository.search.PeopleSearchRepository;
import com.epmserver.gateway.service.dto.PeopleDTO;
import com.epmserver.gateway.service.mapper.PeopleMapper;
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
 * Service Implementation for managing {@link People}.
 */
@Service
@Transactional
public class PeopleServiceImpl implements PeopleService {

    private final Logger log = LoggerFactory.getLogger(PeopleServiceImpl.class);

    private final PeopleRepository peopleRepository;

    private final PeopleMapper peopleMapper;

    private final PeopleSearchRepository peopleSearchRepository;

    public PeopleServiceImpl(PeopleRepository peopleRepository, PeopleMapper peopleMapper, PeopleSearchRepository peopleSearchRepository) {
        this.peopleRepository = peopleRepository;
        this.peopleMapper = peopleMapper;
        this.peopleSearchRepository = peopleSearchRepository;
    }

    /**
     * Save a people.
     *
     * @param peopleDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public PeopleDTO save(PeopleDTO peopleDTO) {
        log.debug("Request to save People : {}", peopleDTO);
        People people = peopleMapper.toEntity(peopleDTO);
        people = peopleRepository.save(people);
        PeopleDTO result = peopleMapper.toDto(people);
        peopleSearchRepository.save(people);
        return result;
    }

    /**
     * Get all the people.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<PeopleDTO> findAll() {
        log.debug("Request to get all People");
        return peopleRepository.findAll().stream()
            .map(peopleMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }



    /**
    *  Get all the people where Cart is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true) 
    public List<PeopleDTO> findAllWhereCartIsNull() {
        log.debug("Request to get all people where Cart is null");
        return StreamSupport
            .stream(peopleRepository.findAll().spliterator(), false)
            .filter(people -> people.getCart() == null)
            .map(peopleMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
    *  Get all the people where Wishlist is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true) 
    public List<PeopleDTO> findAllWhereWishlistIsNull() {
        log.debug("Request to get all people where Wishlist is null");
        return StreamSupport
            .stream(peopleRepository.findAll().spliterator(), false)
            .filter(people -> people.getWishlist() == null)
            .map(peopleMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
    *  Get all the people where Compare is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true) 
    public List<PeopleDTO> findAllWhereCompareIsNull() {
        log.debug("Request to get all people where Compare is null");
        return StreamSupport
            .stream(peopleRepository.findAll().spliterator(), false)
            .filter(people -> people.getCompare() == null)
            .map(peopleMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one people by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PeopleDTO> findOne(Long id) {
        log.debug("Request to get People : {}", id);
        return peopleRepository.findById(id)
            .map(peopleMapper::toDto);
    }

    /**
     * Delete the people by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete People : {}", id);
        peopleRepository.deleteById(id);
        peopleSearchRepository.deleteById(id);
    }

    /**
     * Search for the people corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<PeopleDTO> search(String query) {
        log.debug("Request to search People for query {}", query);
        return StreamSupport
            .stream(peopleSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(peopleMapper::toDto)
            .collect(Collectors.toList());
    }
}
