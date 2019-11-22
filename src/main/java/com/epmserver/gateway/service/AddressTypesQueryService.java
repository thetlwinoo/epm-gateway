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

import com.epmserver.gateway.domain.AddressTypes;
import com.epmserver.gateway.domain.*; // for static metamodels
import com.epmserver.gateway.repository.AddressTypesRepository;
import com.epmserver.gateway.repository.search.AddressTypesSearchRepository;
import com.epmserver.gateway.service.dto.AddressTypesCriteria;
import com.epmserver.gateway.service.dto.AddressTypesDTO;
import com.epmserver.gateway.service.mapper.AddressTypesMapper;

/**
 * Service for executing complex queries for {@link AddressTypes} entities in the database.
 * The main input is a {@link AddressTypesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AddressTypesDTO} or a {@link Page} of {@link AddressTypesDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AddressTypesQueryService extends QueryService<AddressTypes> {

    private final Logger log = LoggerFactory.getLogger(AddressTypesQueryService.class);

    private final AddressTypesRepository addressTypesRepository;

    private final AddressTypesMapper addressTypesMapper;

    private final AddressTypesSearchRepository addressTypesSearchRepository;

    public AddressTypesQueryService(AddressTypesRepository addressTypesRepository, AddressTypesMapper addressTypesMapper, AddressTypesSearchRepository addressTypesSearchRepository) {
        this.addressTypesRepository = addressTypesRepository;
        this.addressTypesMapper = addressTypesMapper;
        this.addressTypesSearchRepository = addressTypesSearchRepository;
    }

    /**
     * Return a {@link List} of {@link AddressTypesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AddressTypesDTO> findByCriteria(AddressTypesCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<AddressTypes> specification = createSpecification(criteria);
        return addressTypesMapper.toDto(addressTypesRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AddressTypesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AddressTypesDTO> findByCriteria(AddressTypesCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<AddressTypes> specification = createSpecification(criteria);
        return addressTypesRepository.findAll(specification, page)
            .map(addressTypesMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AddressTypesCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<AddressTypes> specification = createSpecification(criteria);
        return addressTypesRepository.count(specification);
    }

    /**
     * Function to convert {@link AddressTypesCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<AddressTypes> createSpecification(AddressTypesCriteria criteria) {
        Specification<AddressTypes> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), AddressTypes_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), AddressTypes_.name));
            }
            if (criteria.getRefer() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRefer(), AddressTypes_.refer));
            }
        }
        return specification;
    }
}
