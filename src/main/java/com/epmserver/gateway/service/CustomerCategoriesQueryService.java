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

import com.epmserver.gateway.domain.CustomerCategories;
import com.epmserver.gateway.domain.*; // for static metamodels
import com.epmserver.gateway.repository.CustomerCategoriesRepository;
import com.epmserver.gateway.repository.search.CustomerCategoriesSearchRepository;
import com.epmserver.gateway.service.dto.CustomerCategoriesCriteria;
import com.epmserver.gateway.service.dto.CustomerCategoriesDTO;
import com.epmserver.gateway.service.mapper.CustomerCategoriesMapper;

/**
 * Service for executing complex queries for {@link CustomerCategories} entities in the database.
 * The main input is a {@link CustomerCategoriesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CustomerCategoriesDTO} or a {@link Page} of {@link CustomerCategoriesDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CustomerCategoriesQueryService extends QueryService<CustomerCategories> {

    private final Logger log = LoggerFactory.getLogger(CustomerCategoriesQueryService.class);

    private final CustomerCategoriesRepository customerCategoriesRepository;

    private final CustomerCategoriesMapper customerCategoriesMapper;

    private final CustomerCategoriesSearchRepository customerCategoriesSearchRepository;

    public CustomerCategoriesQueryService(CustomerCategoriesRepository customerCategoriesRepository, CustomerCategoriesMapper customerCategoriesMapper, CustomerCategoriesSearchRepository customerCategoriesSearchRepository) {
        this.customerCategoriesRepository = customerCategoriesRepository;
        this.customerCategoriesMapper = customerCategoriesMapper;
        this.customerCategoriesSearchRepository = customerCategoriesSearchRepository;
    }

    /**
     * Return a {@link List} of {@link CustomerCategoriesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CustomerCategoriesDTO> findByCriteria(CustomerCategoriesCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CustomerCategories> specification = createSpecification(criteria);
        return customerCategoriesMapper.toDto(customerCategoriesRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CustomerCategoriesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CustomerCategoriesDTO> findByCriteria(CustomerCategoriesCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CustomerCategories> specification = createSpecification(criteria);
        return customerCategoriesRepository.findAll(specification, page)
            .map(customerCategoriesMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CustomerCategoriesCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CustomerCategories> specification = createSpecification(criteria);
        return customerCategoriesRepository.count(specification);
    }

    /**
     * Function to convert {@link CustomerCategoriesCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CustomerCategories> createSpecification(CustomerCategoriesCriteria criteria) {
        Specification<CustomerCategories> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), CustomerCategories_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), CustomerCategories_.name));
            }
            if (criteria.getValidFrom() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValidFrom(), CustomerCategories_.validFrom));
            }
            if (criteria.getValidTo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValidTo(), CustomerCategories_.validTo));
            }
        }
        return specification;
    }
}
