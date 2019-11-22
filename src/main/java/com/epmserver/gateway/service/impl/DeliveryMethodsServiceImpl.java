package com.epmserver.gateway.service.impl;

import com.epmserver.gateway.service.DeliveryMethodsService;
import com.epmserver.gateway.domain.DeliveryMethods;
import com.epmserver.gateway.repository.DeliveryMethodsRepository;
import com.epmserver.gateway.repository.search.DeliveryMethodsSearchRepository;
import com.epmserver.gateway.service.dto.DeliveryMethodsDTO;
import com.epmserver.gateway.service.mapper.DeliveryMethodsMapper;
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
 * Service Implementation for managing {@link DeliveryMethods}.
 */
@Service
@Transactional
public class DeliveryMethodsServiceImpl implements DeliveryMethodsService {

    private final Logger log = LoggerFactory.getLogger(DeliveryMethodsServiceImpl.class);

    private final DeliveryMethodsRepository deliveryMethodsRepository;

    private final DeliveryMethodsMapper deliveryMethodsMapper;

    private final DeliveryMethodsSearchRepository deliveryMethodsSearchRepository;

    public DeliveryMethodsServiceImpl(DeliveryMethodsRepository deliveryMethodsRepository, DeliveryMethodsMapper deliveryMethodsMapper, DeliveryMethodsSearchRepository deliveryMethodsSearchRepository) {
        this.deliveryMethodsRepository = deliveryMethodsRepository;
        this.deliveryMethodsMapper = deliveryMethodsMapper;
        this.deliveryMethodsSearchRepository = deliveryMethodsSearchRepository;
    }

    /**
     * Save a deliveryMethods.
     *
     * @param deliveryMethodsDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public DeliveryMethodsDTO save(DeliveryMethodsDTO deliveryMethodsDTO) {
        log.debug("Request to save DeliveryMethods : {}", deliveryMethodsDTO);
        DeliveryMethods deliveryMethods = deliveryMethodsMapper.toEntity(deliveryMethodsDTO);
        deliveryMethods = deliveryMethodsRepository.save(deliveryMethods);
        DeliveryMethodsDTO result = deliveryMethodsMapper.toDto(deliveryMethods);
        deliveryMethodsSearchRepository.save(deliveryMethods);
        return result;
    }

    /**
     * Get all the deliveryMethods.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<DeliveryMethodsDTO> findAll() {
        log.debug("Request to get all DeliveryMethods");
        return deliveryMethodsRepository.findAll().stream()
            .map(deliveryMethodsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one deliveryMethods by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<DeliveryMethodsDTO> findOne(Long id) {
        log.debug("Request to get DeliveryMethods : {}", id);
        return deliveryMethodsRepository.findById(id)
            .map(deliveryMethodsMapper::toDto);
    }

    /**
     * Delete the deliveryMethods by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete DeliveryMethods : {}", id);
        deliveryMethodsRepository.deleteById(id);
        deliveryMethodsSearchRepository.deleteById(id);
    }

    /**
     * Search for the deliveryMethods corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<DeliveryMethodsDTO> search(String query) {
        log.debug("Request to search DeliveryMethods for query {}", query);
        return StreamSupport
            .stream(deliveryMethodsSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(deliveryMethodsMapper::toDto)
            .collect(Collectors.toList());
    }
}
