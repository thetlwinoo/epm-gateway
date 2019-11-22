package com.epmserver.gateway.service.impl;

import com.epmserver.gateway.service.DangerousGoodsService;
import com.epmserver.gateway.domain.DangerousGoods;
import com.epmserver.gateway.repository.DangerousGoodsRepository;
import com.epmserver.gateway.repository.search.DangerousGoodsSearchRepository;
import com.epmserver.gateway.service.dto.DangerousGoodsDTO;
import com.epmserver.gateway.service.mapper.DangerousGoodsMapper;
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
 * Service Implementation for managing {@link DangerousGoods}.
 */
@Service
@Transactional
public class DangerousGoodsServiceImpl implements DangerousGoodsService {

    private final Logger log = LoggerFactory.getLogger(DangerousGoodsServiceImpl.class);

    private final DangerousGoodsRepository dangerousGoodsRepository;

    private final DangerousGoodsMapper dangerousGoodsMapper;

    private final DangerousGoodsSearchRepository dangerousGoodsSearchRepository;

    public DangerousGoodsServiceImpl(DangerousGoodsRepository dangerousGoodsRepository, DangerousGoodsMapper dangerousGoodsMapper, DangerousGoodsSearchRepository dangerousGoodsSearchRepository) {
        this.dangerousGoodsRepository = dangerousGoodsRepository;
        this.dangerousGoodsMapper = dangerousGoodsMapper;
        this.dangerousGoodsSearchRepository = dangerousGoodsSearchRepository;
    }

    /**
     * Save a dangerousGoods.
     *
     * @param dangerousGoodsDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public DangerousGoodsDTO save(DangerousGoodsDTO dangerousGoodsDTO) {
        log.debug("Request to save DangerousGoods : {}", dangerousGoodsDTO);
        DangerousGoods dangerousGoods = dangerousGoodsMapper.toEntity(dangerousGoodsDTO);
        dangerousGoods = dangerousGoodsRepository.save(dangerousGoods);
        DangerousGoodsDTO result = dangerousGoodsMapper.toDto(dangerousGoods);
        dangerousGoodsSearchRepository.save(dangerousGoods);
        return result;
    }

    /**
     * Get all the dangerousGoods.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<DangerousGoodsDTO> findAll() {
        log.debug("Request to get all DangerousGoods");
        return dangerousGoodsRepository.findAll().stream()
            .map(dangerousGoodsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one dangerousGoods by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<DangerousGoodsDTO> findOne(Long id) {
        log.debug("Request to get DangerousGoods : {}", id);
        return dangerousGoodsRepository.findById(id)
            .map(dangerousGoodsMapper::toDto);
    }

    /**
     * Delete the dangerousGoods by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete DangerousGoods : {}", id);
        dangerousGoodsRepository.deleteById(id);
        dangerousGoodsSearchRepository.deleteById(id);
    }

    /**
     * Search for the dangerousGoods corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<DangerousGoodsDTO> search(String query) {
        log.debug("Request to search DangerousGoods for query {}", query);
        return StreamSupport
            .stream(dangerousGoodsSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(dangerousGoodsMapper::toDto)
            .collect(Collectors.toList());
    }
}
