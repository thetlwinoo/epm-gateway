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

import com.epmserver.gateway.domain.ProductOptionSet;
import com.epmserver.gateway.domain.*; // for static metamodels
import com.epmserver.gateway.repository.ProductOptionSetRepository;
import com.epmserver.gateway.repository.search.ProductOptionSetSearchRepository;
import com.epmserver.gateway.service.dto.ProductOptionSetCriteria;
import com.epmserver.gateway.service.dto.ProductOptionSetDTO;
import com.epmserver.gateway.service.mapper.ProductOptionSetMapper;

/**
 * Service for executing complex queries for {@link ProductOptionSet} entities in the database.
 * The main input is a {@link ProductOptionSetCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProductOptionSetDTO} or a {@link Page} of {@link ProductOptionSetDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProductOptionSetQueryService extends QueryService<ProductOptionSet> {

    private final Logger log = LoggerFactory.getLogger(ProductOptionSetQueryService.class);

    private final ProductOptionSetRepository productOptionSetRepository;

    private final ProductOptionSetMapper productOptionSetMapper;

    private final ProductOptionSetSearchRepository productOptionSetSearchRepository;

    public ProductOptionSetQueryService(ProductOptionSetRepository productOptionSetRepository, ProductOptionSetMapper productOptionSetMapper, ProductOptionSetSearchRepository productOptionSetSearchRepository) {
        this.productOptionSetRepository = productOptionSetRepository;
        this.productOptionSetMapper = productOptionSetMapper;
        this.productOptionSetSearchRepository = productOptionSetSearchRepository;
    }

    /**
     * Return a {@link List} of {@link ProductOptionSetDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProductOptionSetDTO> findByCriteria(ProductOptionSetCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ProductOptionSet> specification = createSpecification(criteria);
        return productOptionSetMapper.toDto(productOptionSetRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ProductOptionSetDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProductOptionSetDTO> findByCriteria(ProductOptionSetCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ProductOptionSet> specification = createSpecification(criteria);
        return productOptionSetRepository.findAll(specification, page)
            .map(productOptionSetMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProductOptionSetCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ProductOptionSet> specification = createSpecification(criteria);
        return productOptionSetRepository.count(specification);
    }

    /**
     * Function to convert {@link ProductOptionSetCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ProductOptionSet> createSpecification(ProductOptionSetCriteria criteria) {
        Specification<ProductOptionSet> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ProductOptionSet_.id));
            }
            if (criteria.getValue() != null) {
                specification = specification.and(buildStringSpecification(criteria.getValue(), ProductOptionSet_.value));
            }
        }
        return specification;
    }
}
