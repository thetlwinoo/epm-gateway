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

import com.epmserver.gateway.domain.ShipMethod;
import com.epmserver.gateway.domain.*; // for static metamodels
import com.epmserver.gateway.repository.ShipMethodRepository;
import com.epmserver.gateway.repository.search.ShipMethodSearchRepository;
import com.epmserver.gateway.service.dto.ShipMethodCriteria;
import com.epmserver.gateway.service.dto.ShipMethodDTO;
import com.epmserver.gateway.service.mapper.ShipMethodMapper;

/**
 * Service for executing complex queries for {@link ShipMethod} entities in the database.
 * The main input is a {@link ShipMethodCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ShipMethodDTO} or a {@link Page} of {@link ShipMethodDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ShipMethodQueryService extends QueryService<ShipMethod> {

    private final Logger log = LoggerFactory.getLogger(ShipMethodQueryService.class);

    private final ShipMethodRepository shipMethodRepository;

    private final ShipMethodMapper shipMethodMapper;

    private final ShipMethodSearchRepository shipMethodSearchRepository;

    public ShipMethodQueryService(ShipMethodRepository shipMethodRepository, ShipMethodMapper shipMethodMapper, ShipMethodSearchRepository shipMethodSearchRepository) {
        this.shipMethodRepository = shipMethodRepository;
        this.shipMethodMapper = shipMethodMapper;
        this.shipMethodSearchRepository = shipMethodSearchRepository;
    }

    /**
     * Return a {@link List} of {@link ShipMethodDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ShipMethodDTO> findByCriteria(ShipMethodCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ShipMethod> specification = createSpecification(criteria);
        return shipMethodMapper.toDto(shipMethodRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ShipMethodDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ShipMethodDTO> findByCriteria(ShipMethodCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ShipMethod> specification = createSpecification(criteria);
        return shipMethodRepository.findAll(specification, page)
            .map(shipMethodMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ShipMethodCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ShipMethod> specification = createSpecification(criteria);
        return shipMethodRepository.count(specification);
    }

    /**
     * Function to convert {@link ShipMethodCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ShipMethod> createSpecification(ShipMethodCriteria criteria) {
        Specification<ShipMethod> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ShipMethod_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), ShipMethod_.name));
            }
        }
        return specification;
    }
}
