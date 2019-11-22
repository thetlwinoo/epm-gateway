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

import com.epmserver.gateway.domain.SupplierTransactions;
import com.epmserver.gateway.domain.*; // for static metamodels
import com.epmserver.gateway.repository.SupplierTransactionsRepository;
import com.epmserver.gateway.repository.search.SupplierTransactionsSearchRepository;
import com.epmserver.gateway.service.dto.SupplierTransactionsCriteria;
import com.epmserver.gateway.service.dto.SupplierTransactionsDTO;
import com.epmserver.gateway.service.mapper.SupplierTransactionsMapper;

/**
 * Service for executing complex queries for {@link SupplierTransactions} entities in the database.
 * The main input is a {@link SupplierTransactionsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SupplierTransactionsDTO} or a {@link Page} of {@link SupplierTransactionsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SupplierTransactionsQueryService extends QueryService<SupplierTransactions> {

    private final Logger log = LoggerFactory.getLogger(SupplierTransactionsQueryService.class);

    private final SupplierTransactionsRepository supplierTransactionsRepository;

    private final SupplierTransactionsMapper supplierTransactionsMapper;

    private final SupplierTransactionsSearchRepository supplierTransactionsSearchRepository;

    public SupplierTransactionsQueryService(SupplierTransactionsRepository supplierTransactionsRepository, SupplierTransactionsMapper supplierTransactionsMapper, SupplierTransactionsSearchRepository supplierTransactionsSearchRepository) {
        this.supplierTransactionsRepository = supplierTransactionsRepository;
        this.supplierTransactionsMapper = supplierTransactionsMapper;
        this.supplierTransactionsSearchRepository = supplierTransactionsSearchRepository;
    }

    /**
     * Return a {@link List} of {@link SupplierTransactionsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SupplierTransactionsDTO> findByCriteria(SupplierTransactionsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<SupplierTransactions> specification = createSpecification(criteria);
        return supplierTransactionsMapper.toDto(supplierTransactionsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SupplierTransactionsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SupplierTransactionsDTO> findByCriteria(SupplierTransactionsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SupplierTransactions> specification = createSpecification(criteria);
        return supplierTransactionsRepository.findAll(specification, page)
            .map(supplierTransactionsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SupplierTransactionsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<SupplierTransactions> specification = createSpecification(criteria);
        return supplierTransactionsRepository.count(specification);
    }

    /**
     * Function to convert {@link SupplierTransactionsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<SupplierTransactions> createSpecification(SupplierTransactionsCriteria criteria) {
        Specification<SupplierTransactions> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), SupplierTransactions_.id));
            }
            if (criteria.getSupplierInvoiceNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSupplierInvoiceNumber(), SupplierTransactions_.supplierInvoiceNumber));
            }
            if (criteria.getTransactionDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTransactionDate(), SupplierTransactions_.transactionDate));
            }
            if (criteria.getAmountExcludingTax() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAmountExcludingTax(), SupplierTransactions_.amountExcludingTax));
            }
            if (criteria.getTaxAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTaxAmount(), SupplierTransactions_.taxAmount));
            }
            if (criteria.getTransactionAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTransactionAmount(), SupplierTransactions_.transactionAmount));
            }
            if (criteria.getOutstandingBalance() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOutstandingBalance(), SupplierTransactions_.outstandingBalance));
            }
            if (criteria.getFinalizationDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFinalizationDate(), SupplierTransactions_.finalizationDate));
            }
            if (criteria.getIsFinalized() != null) {
                specification = specification.and(buildSpecification(criteria.getIsFinalized(), SupplierTransactions_.isFinalized));
            }
            if (criteria.getLastEditedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastEditedBy(), SupplierTransactions_.lastEditedBy));
            }
            if (criteria.getLastEditedWhen() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastEditedWhen(), SupplierTransactions_.lastEditedWhen));
            }
            if (criteria.getSupplierId() != null) {
                specification = specification.and(buildSpecification(criteria.getSupplierId(),
                    root -> root.join(SupplierTransactions_.supplier, JoinType.LEFT).get(Suppliers_.id)));
            }
            if (criteria.getTransactionTypeId() != null) {
                specification = specification.and(buildSpecification(criteria.getTransactionTypeId(),
                    root -> root.join(SupplierTransactions_.transactionType, JoinType.LEFT).get(TransactionTypes_.id)));
            }
        }
        return specification;
    }
}
