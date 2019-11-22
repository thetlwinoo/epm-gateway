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

import com.epmserver.gateway.domain.PurchaseOrderLines;
import com.epmserver.gateway.domain.*; // for static metamodels
import com.epmserver.gateway.repository.PurchaseOrderLinesRepository;
import com.epmserver.gateway.repository.search.PurchaseOrderLinesSearchRepository;
import com.epmserver.gateway.service.dto.PurchaseOrderLinesCriteria;
import com.epmserver.gateway.service.dto.PurchaseOrderLinesDTO;
import com.epmserver.gateway.service.mapper.PurchaseOrderLinesMapper;

/**
 * Service for executing complex queries for {@link PurchaseOrderLines} entities in the database.
 * The main input is a {@link PurchaseOrderLinesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PurchaseOrderLinesDTO} or a {@link Page} of {@link PurchaseOrderLinesDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PurchaseOrderLinesQueryService extends QueryService<PurchaseOrderLines> {

    private final Logger log = LoggerFactory.getLogger(PurchaseOrderLinesQueryService.class);

    private final PurchaseOrderLinesRepository purchaseOrderLinesRepository;

    private final PurchaseOrderLinesMapper purchaseOrderLinesMapper;

    private final PurchaseOrderLinesSearchRepository purchaseOrderLinesSearchRepository;

    public PurchaseOrderLinesQueryService(PurchaseOrderLinesRepository purchaseOrderLinesRepository, PurchaseOrderLinesMapper purchaseOrderLinesMapper, PurchaseOrderLinesSearchRepository purchaseOrderLinesSearchRepository) {
        this.purchaseOrderLinesRepository = purchaseOrderLinesRepository;
        this.purchaseOrderLinesMapper = purchaseOrderLinesMapper;
        this.purchaseOrderLinesSearchRepository = purchaseOrderLinesSearchRepository;
    }

    /**
     * Return a {@link List} of {@link PurchaseOrderLinesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PurchaseOrderLinesDTO> findByCriteria(PurchaseOrderLinesCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<PurchaseOrderLines> specification = createSpecification(criteria);
        return purchaseOrderLinesMapper.toDto(purchaseOrderLinesRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PurchaseOrderLinesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PurchaseOrderLinesDTO> findByCriteria(PurchaseOrderLinesCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PurchaseOrderLines> specification = createSpecification(criteria);
        return purchaseOrderLinesRepository.findAll(specification, page)
            .map(purchaseOrderLinesMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PurchaseOrderLinesCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PurchaseOrderLines> specification = createSpecification(criteria);
        return purchaseOrderLinesRepository.count(specification);
    }

    /**
     * Function to convert {@link PurchaseOrderLinesCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<PurchaseOrderLines> createSpecification(PurchaseOrderLinesCriteria criteria) {
        Specification<PurchaseOrderLines> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), PurchaseOrderLines_.id));
            }
            if (criteria.getOrderedOuters() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOrderedOuters(), PurchaseOrderLines_.orderedOuters));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), PurchaseOrderLines_.description));
            }
            if (criteria.getReceivedOuters() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getReceivedOuters(), PurchaseOrderLines_.receivedOuters));
            }
            if (criteria.getExpectedUnitPricePerOuter() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getExpectedUnitPricePerOuter(), PurchaseOrderLines_.expectedUnitPricePerOuter));
            }
            if (criteria.getLastReceiptDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastReceiptDate(), PurchaseOrderLines_.lastReceiptDate));
            }
            if (criteria.getIsOrderLineFinalized() != null) {
                specification = specification.and(buildSpecification(criteria.getIsOrderLineFinalized(), PurchaseOrderLines_.isOrderLineFinalized));
            }
            if (criteria.getLastEditedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastEditedBy(), PurchaseOrderLines_.lastEditedBy));
            }
            if (criteria.getLastEditedWhen() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastEditedWhen(), PurchaseOrderLines_.lastEditedWhen));
            }
            if (criteria.getPackageTypeId() != null) {
                specification = specification.and(buildSpecification(criteria.getPackageTypeId(),
                    root -> root.join(PurchaseOrderLines_.packageType, JoinType.LEFT).get(PackageTypes_.id)));
            }
            if (criteria.getStockItemId() != null) {
                specification = specification.and(buildSpecification(criteria.getStockItemId(),
                    root -> root.join(PurchaseOrderLines_.stockItem, JoinType.LEFT).get(StockItems_.id)));
            }
            if (criteria.getPurchaseOrderId() != null) {
                specification = specification.and(buildSpecification(criteria.getPurchaseOrderId(),
                    root -> root.join(PurchaseOrderLines_.purchaseOrder, JoinType.LEFT).get(PurchaseOrders_.id)));
            }
        }
        return specification;
    }
}
