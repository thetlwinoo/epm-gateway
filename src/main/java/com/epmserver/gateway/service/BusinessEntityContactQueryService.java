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

import com.epmserver.gateway.domain.BusinessEntityContact;
import com.epmserver.gateway.domain.*; // for static metamodels
import com.epmserver.gateway.repository.BusinessEntityContactRepository;
import com.epmserver.gateway.repository.search.BusinessEntityContactSearchRepository;
import com.epmserver.gateway.service.dto.BusinessEntityContactCriteria;
import com.epmserver.gateway.service.dto.BusinessEntityContactDTO;
import com.epmserver.gateway.service.mapper.BusinessEntityContactMapper;

/**
 * Service for executing complex queries for {@link BusinessEntityContact} entities in the database.
 * The main input is a {@link BusinessEntityContactCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link BusinessEntityContactDTO} or a {@link Page} of {@link BusinessEntityContactDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class BusinessEntityContactQueryService extends QueryService<BusinessEntityContact> {

    private final Logger log = LoggerFactory.getLogger(BusinessEntityContactQueryService.class);

    private final BusinessEntityContactRepository businessEntityContactRepository;

    private final BusinessEntityContactMapper businessEntityContactMapper;

    private final BusinessEntityContactSearchRepository businessEntityContactSearchRepository;

    public BusinessEntityContactQueryService(BusinessEntityContactRepository businessEntityContactRepository, BusinessEntityContactMapper businessEntityContactMapper, BusinessEntityContactSearchRepository businessEntityContactSearchRepository) {
        this.businessEntityContactRepository = businessEntityContactRepository;
        this.businessEntityContactMapper = businessEntityContactMapper;
        this.businessEntityContactSearchRepository = businessEntityContactSearchRepository;
    }

    /**
     * Return a {@link List} of {@link BusinessEntityContactDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<BusinessEntityContactDTO> findByCriteria(BusinessEntityContactCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<BusinessEntityContact> specification = createSpecification(criteria);
        return businessEntityContactMapper.toDto(businessEntityContactRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link BusinessEntityContactDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<BusinessEntityContactDTO> findByCriteria(BusinessEntityContactCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<BusinessEntityContact> specification = createSpecification(criteria);
        return businessEntityContactRepository.findAll(specification, page)
            .map(businessEntityContactMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(BusinessEntityContactCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<BusinessEntityContact> specification = createSpecification(criteria);
        return businessEntityContactRepository.count(specification);
    }

    /**
     * Function to convert {@link BusinessEntityContactCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<BusinessEntityContact> createSpecification(BusinessEntityContactCriteria criteria) {
        Specification<BusinessEntityContact> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), BusinessEntityContact_.id));
            }
            if (criteria.getPersonId() != null) {
                specification = specification.and(buildSpecification(criteria.getPersonId(),
                    root -> root.join(BusinessEntityContact_.person, JoinType.LEFT).get(People_.id)));
            }
            if (criteria.getContactTypeId() != null) {
                specification = specification.and(buildSpecification(criteria.getContactTypeId(),
                    root -> root.join(BusinessEntityContact_.contactType, JoinType.LEFT).get(ContactType_.id)));
            }
        }
        return specification;
    }
}
