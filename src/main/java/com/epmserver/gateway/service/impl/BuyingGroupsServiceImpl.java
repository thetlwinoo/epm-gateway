package com.epmserver.gateway.service.impl;

import com.epmserver.gateway.service.BuyingGroupsService;
import com.epmserver.gateway.domain.BuyingGroups;
import com.epmserver.gateway.repository.BuyingGroupsRepository;
import com.epmserver.gateway.repository.search.BuyingGroupsSearchRepository;
import com.epmserver.gateway.service.dto.BuyingGroupsDTO;
import com.epmserver.gateway.service.mapper.BuyingGroupsMapper;
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
 * Service Implementation for managing {@link BuyingGroups}.
 */
@Service
@Transactional
public class BuyingGroupsServiceImpl implements BuyingGroupsService {

    private final Logger log = LoggerFactory.getLogger(BuyingGroupsServiceImpl.class);

    private final BuyingGroupsRepository buyingGroupsRepository;

    private final BuyingGroupsMapper buyingGroupsMapper;

    private final BuyingGroupsSearchRepository buyingGroupsSearchRepository;

    public BuyingGroupsServiceImpl(BuyingGroupsRepository buyingGroupsRepository, BuyingGroupsMapper buyingGroupsMapper, BuyingGroupsSearchRepository buyingGroupsSearchRepository) {
        this.buyingGroupsRepository = buyingGroupsRepository;
        this.buyingGroupsMapper = buyingGroupsMapper;
        this.buyingGroupsSearchRepository = buyingGroupsSearchRepository;
    }

    /**
     * Save a buyingGroups.
     *
     * @param buyingGroupsDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public BuyingGroupsDTO save(BuyingGroupsDTO buyingGroupsDTO) {
        log.debug("Request to save BuyingGroups : {}", buyingGroupsDTO);
        BuyingGroups buyingGroups = buyingGroupsMapper.toEntity(buyingGroupsDTO);
        buyingGroups = buyingGroupsRepository.save(buyingGroups);
        BuyingGroupsDTO result = buyingGroupsMapper.toDto(buyingGroups);
        buyingGroupsSearchRepository.save(buyingGroups);
        return result;
    }

    /**
     * Get all the buyingGroups.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<BuyingGroupsDTO> findAll() {
        log.debug("Request to get all BuyingGroups");
        return buyingGroupsRepository.findAll().stream()
            .map(buyingGroupsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one buyingGroups by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<BuyingGroupsDTO> findOne(Long id) {
        log.debug("Request to get BuyingGroups : {}", id);
        return buyingGroupsRepository.findById(id)
            .map(buyingGroupsMapper::toDto);
    }

    /**
     * Delete the buyingGroups by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete BuyingGroups : {}", id);
        buyingGroupsRepository.deleteById(id);
        buyingGroupsSearchRepository.deleteById(id);
    }

    /**
     * Search for the buyingGroups corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<BuyingGroupsDTO> search(String query) {
        log.debug("Request to search BuyingGroups for query {}", query);
        return StreamSupport
            .stream(buyingGroupsSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(buyingGroupsMapper::toDto)
            .collect(Collectors.toList());
    }
}
