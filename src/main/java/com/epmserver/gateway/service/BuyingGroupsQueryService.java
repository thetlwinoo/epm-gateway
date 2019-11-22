package com.epmserver.gateway.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.epmserver.gateway.domain.BuyingGroups;
import com.epmserver.gateway.domain.*; // for static metamodels
import com.epmserver.gateway.repository.BuyingGroupsRepository;
import com.epmserver.gateway.repository.search.BuyingGroupsSearchRepository;
import com.epmserver.gateway.service.dto.BuyingGroupsCriteria;
import com.epmserver.gateway.service.dto.BuyingGroupsDTO;
import com.epmserver.gateway.service.mapper.BuyingGroupsMapper;

/**
 * Service for executing complex queries for {@link BuyingGroups} entities in the database.
 * The main input is a {@link BuyingGroupsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link BuyingGroupsDTO} or a {@link Page} of {@link BuyingGroupsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class BuyingGroupsQueryService extends QueryService<BuyingGroups> {

    private final Logger log = LoggerFactory.getLogger(BuyingGroupsQueryService.class);

    private final BuyingGroupsRepository buyingGroupsRepository;

    private final BuyingGroupsMapper buyingGroupsMapper;

    private final BuyingGroupsSearchRepository buyingGroupsSearchRepository;

    public BuyingGroupsQueryService(BuyingGroupsRepository buyingGroupsRepository, BuyingGroupsMapper buyingGroupsMapper, BuyingGroupsSearchRepository buyingGroupsSearchRepository) {
        this.buyingGroupsRepository = buyingGroupsRepository;
        this.buyingGroupsMapper = buyingGroupsMapper;
        this.buyingGroupsSearchRepository = buyingGroupsSearchRepository;
    }

    /**
     * Return a {@link List} of {@link BuyingGroupsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<BuyingGroupsDTO> findByCriteria(BuyingGroupsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<BuyingGroups> specification = createSpecification(criteria);
        return buyingGroupsMapper.toDto(buyingGroupsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link BuyingGroupsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<BuyingGroupsDTO> findByCriteria(BuyingGroupsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<BuyingGroups> specification = createSpecification(criteria);
        return buyingGroupsRepository.findAll(specification, page)
            .map(buyingGroupsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(BuyingGroupsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<BuyingGroups> specification = createSpecification(criteria);
        return buyingGroupsRepository.count(specification);
    }

    /**
     * Function to convert {@link BuyingGroupsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<BuyingGroups> createSpecification(BuyingGroupsCriteria criteria) {
        Specification<BuyingGroups> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), BuyingGroups_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), BuyingGroups_.name));
            }
            if (criteria.getValidFrom() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValidFrom(), BuyingGroups_.validFrom));
            }
            if (criteria.getValidTo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValidTo(), BuyingGroups_.validTo));
            }
        }
        return specification;
    }
}
