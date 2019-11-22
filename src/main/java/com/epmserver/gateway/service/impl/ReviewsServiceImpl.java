package com.epmserver.gateway.service.impl;

import com.epmserver.gateway.service.ReviewsService;
import com.epmserver.gateway.domain.Reviews;
import com.epmserver.gateway.repository.ReviewsRepository;
import com.epmserver.gateway.repository.search.ReviewsSearchRepository;
import com.epmserver.gateway.service.dto.ReviewsDTO;
import com.epmserver.gateway.service.mapper.ReviewsMapper;
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
 * Service Implementation for managing {@link Reviews}.
 */
@Service
@Transactional
public class ReviewsServiceImpl implements ReviewsService {

    private final Logger log = LoggerFactory.getLogger(ReviewsServiceImpl.class);

    private final ReviewsRepository reviewsRepository;

    private final ReviewsMapper reviewsMapper;

    private final ReviewsSearchRepository reviewsSearchRepository;

    public ReviewsServiceImpl(ReviewsRepository reviewsRepository, ReviewsMapper reviewsMapper, ReviewsSearchRepository reviewsSearchRepository) {
        this.reviewsRepository = reviewsRepository;
        this.reviewsMapper = reviewsMapper;
        this.reviewsSearchRepository = reviewsSearchRepository;
    }

    /**
     * Save a reviews.
     *
     * @param reviewsDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ReviewsDTO save(ReviewsDTO reviewsDTO) {
        log.debug("Request to save Reviews : {}", reviewsDTO);
        Reviews reviews = reviewsMapper.toEntity(reviewsDTO);
        reviews = reviewsRepository.save(reviews);
        ReviewsDTO result = reviewsMapper.toDto(reviews);
        reviewsSearchRepository.save(reviews);
        return result;
    }

    /**
     * Get all the reviews.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ReviewsDTO> findAll() {
        log.debug("Request to get all Reviews");
        return reviewsRepository.findAll().stream()
            .map(reviewsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }



    /**
    *  Get all the reviews where Order is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true) 
    public List<ReviewsDTO> findAllWhereOrderIsNull() {
        log.debug("Request to get all reviews where Order is null");
        return StreamSupport
            .stream(reviewsRepository.findAll().spliterator(), false)
            .filter(reviews -> reviews.getOrder() == null)
            .map(reviewsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one reviews by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ReviewsDTO> findOne(Long id) {
        log.debug("Request to get Reviews : {}", id);
        return reviewsRepository.findById(id)
            .map(reviewsMapper::toDto);
    }

    /**
     * Delete the reviews by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Reviews : {}", id);
        reviewsRepository.deleteById(id);
        reviewsSearchRepository.deleteById(id);
    }

    /**
     * Search for the reviews corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ReviewsDTO> search(String query) {
        log.debug("Request to search Reviews for query {}", query);
        return StreamSupport
            .stream(reviewsSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(reviewsMapper::toDto)
            .collect(Collectors.toList());
    }
}
