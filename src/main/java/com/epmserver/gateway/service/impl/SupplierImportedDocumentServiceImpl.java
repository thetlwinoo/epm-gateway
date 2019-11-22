package com.epmserver.gateway.service.impl;

import com.epmserver.gateway.service.SupplierImportedDocumentService;
import com.epmserver.gateway.domain.SupplierImportedDocument;
import com.epmserver.gateway.repository.SupplierImportedDocumentRepository;
import com.epmserver.gateway.repository.search.SupplierImportedDocumentSearchRepository;
import com.epmserver.gateway.service.dto.SupplierImportedDocumentDTO;
import com.epmserver.gateway.service.mapper.SupplierImportedDocumentMapper;
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
 * Service Implementation for managing {@link SupplierImportedDocument}.
 */
@Service
@Transactional
public class SupplierImportedDocumentServiceImpl implements SupplierImportedDocumentService {

    private final Logger log = LoggerFactory.getLogger(SupplierImportedDocumentServiceImpl.class);

    private final SupplierImportedDocumentRepository supplierImportedDocumentRepository;

    private final SupplierImportedDocumentMapper supplierImportedDocumentMapper;

    private final SupplierImportedDocumentSearchRepository supplierImportedDocumentSearchRepository;

    public SupplierImportedDocumentServiceImpl(SupplierImportedDocumentRepository supplierImportedDocumentRepository, SupplierImportedDocumentMapper supplierImportedDocumentMapper, SupplierImportedDocumentSearchRepository supplierImportedDocumentSearchRepository) {
        this.supplierImportedDocumentRepository = supplierImportedDocumentRepository;
        this.supplierImportedDocumentMapper = supplierImportedDocumentMapper;
        this.supplierImportedDocumentSearchRepository = supplierImportedDocumentSearchRepository;
    }

    /**
     * Save a supplierImportedDocument.
     *
     * @param supplierImportedDocumentDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public SupplierImportedDocumentDTO save(SupplierImportedDocumentDTO supplierImportedDocumentDTO) {
        log.debug("Request to save SupplierImportedDocument : {}", supplierImportedDocumentDTO);
        SupplierImportedDocument supplierImportedDocument = supplierImportedDocumentMapper.toEntity(supplierImportedDocumentDTO);
        supplierImportedDocument = supplierImportedDocumentRepository.save(supplierImportedDocument);
        SupplierImportedDocumentDTO result = supplierImportedDocumentMapper.toDto(supplierImportedDocument);
        supplierImportedDocumentSearchRepository.save(supplierImportedDocument);
        return result;
    }

    /**
     * Get all the supplierImportedDocuments.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<SupplierImportedDocumentDTO> findAll() {
        log.debug("Request to get all SupplierImportedDocuments");
        return supplierImportedDocumentRepository.findAll().stream()
            .map(supplierImportedDocumentMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one supplierImportedDocument by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SupplierImportedDocumentDTO> findOne(Long id) {
        log.debug("Request to get SupplierImportedDocument : {}", id);
        return supplierImportedDocumentRepository.findById(id)
            .map(supplierImportedDocumentMapper::toDto);
    }

    /**
     * Delete the supplierImportedDocument by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SupplierImportedDocument : {}", id);
        supplierImportedDocumentRepository.deleteById(id);
        supplierImportedDocumentSearchRepository.deleteById(id);
    }

    /**
     * Search for the supplierImportedDocument corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<SupplierImportedDocumentDTO> search(String query) {
        log.debug("Request to search SupplierImportedDocuments for query {}", query);
        return StreamSupport
            .stream(supplierImportedDocumentSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(supplierImportedDocumentMapper::toDto)
            .collect(Collectors.toList());
    }
}
