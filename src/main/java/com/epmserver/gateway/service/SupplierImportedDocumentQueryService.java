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

import com.epmserver.gateway.domain.SupplierImportedDocument;
import com.epmserver.gateway.domain.*; // for static metamodels
import com.epmserver.gateway.repository.SupplierImportedDocumentRepository;
import com.epmserver.gateway.repository.search.SupplierImportedDocumentSearchRepository;
import com.epmserver.gateway.service.dto.SupplierImportedDocumentCriteria;
import com.epmserver.gateway.service.dto.SupplierImportedDocumentDTO;
import com.epmserver.gateway.service.mapper.SupplierImportedDocumentMapper;

/**
 * Service for executing complex queries for {@link SupplierImportedDocument} entities in the database.
 * The main input is a {@link SupplierImportedDocumentCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SupplierImportedDocumentDTO} or a {@link Page} of {@link SupplierImportedDocumentDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SupplierImportedDocumentQueryService extends QueryService<SupplierImportedDocument> {

    private final Logger log = LoggerFactory.getLogger(SupplierImportedDocumentQueryService.class);

    private final SupplierImportedDocumentRepository supplierImportedDocumentRepository;

    private final SupplierImportedDocumentMapper supplierImportedDocumentMapper;

    private final SupplierImportedDocumentSearchRepository supplierImportedDocumentSearchRepository;

    public SupplierImportedDocumentQueryService(SupplierImportedDocumentRepository supplierImportedDocumentRepository, SupplierImportedDocumentMapper supplierImportedDocumentMapper, SupplierImportedDocumentSearchRepository supplierImportedDocumentSearchRepository) {
        this.supplierImportedDocumentRepository = supplierImportedDocumentRepository;
        this.supplierImportedDocumentMapper = supplierImportedDocumentMapper;
        this.supplierImportedDocumentSearchRepository = supplierImportedDocumentSearchRepository;
    }

    /**
     * Return a {@link List} of {@link SupplierImportedDocumentDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SupplierImportedDocumentDTO> findByCriteria(SupplierImportedDocumentCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<SupplierImportedDocument> specification = createSpecification(criteria);
        return supplierImportedDocumentMapper.toDto(supplierImportedDocumentRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SupplierImportedDocumentDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SupplierImportedDocumentDTO> findByCriteria(SupplierImportedDocumentCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SupplierImportedDocument> specification = createSpecification(criteria);
        return supplierImportedDocumentRepository.findAll(specification, page)
            .map(supplierImportedDocumentMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SupplierImportedDocumentCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<SupplierImportedDocument> specification = createSpecification(criteria);
        return supplierImportedDocumentRepository.count(specification);
    }

    /**
     * Function to convert {@link SupplierImportedDocumentCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<SupplierImportedDocument> createSpecification(SupplierImportedDocumentCriteria criteria) {
        Specification<SupplierImportedDocument> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), SupplierImportedDocument_.id));
            }
            if (criteria.getDocumentUrl() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDocumentUrl(), SupplierImportedDocument_.documentUrl));
            }
            if (criteria.getDocumentType() != null) {
                specification = specification.and(buildSpecification(criteria.getDocumentType(), SupplierImportedDocument_.documentType));
            }
            if (criteria.getLastEditedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastEditedBy(), SupplierImportedDocument_.lastEditedBy));
            }
            if (criteria.getLastEditedWhen() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastEditedWhen(), SupplierImportedDocument_.lastEditedWhen));
            }
            if (criteria.getUploadTransactionId() != null) {
                specification = specification.and(buildSpecification(criteria.getUploadTransactionId(),
                    root -> root.join(SupplierImportedDocument_.uploadTransaction, JoinType.LEFT).get(UploadTransactions_.id)));
            }
        }
        return specification;
    }
}
