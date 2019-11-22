package com.epmserver.gateway.service.impl;

import com.epmserver.gateway.repository.SupplierImportedDocumentExtendRepository;
import com.epmserver.gateway.service.SupplierImportedDocumentExtendService;
import com.epmserver.gateway.service.dto.SupplierImportedDocumentDTO;
import com.epmserver.gateway.service.mapper.SupplierImportedDocumentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class SupplierImportedDocumentExtendServiceImpl implements SupplierImportedDocumentExtendService {

    private final Logger log = LoggerFactory.getLogger(SupplierImportedDocumentExtendServiceImpl.class);
    private final SupplierImportedDocumentExtendRepository supplierImportedDocumentExtendRepository;
    private final SupplierImportedDocumentMapper supplierImportedDocumentMapper;

    public SupplierImportedDocumentExtendServiceImpl(SupplierImportedDocumentExtendRepository supplierImportedDocumentExtendRepository, SupplierImportedDocumentMapper supplierImportedDocumentMapper) {
        this.supplierImportedDocumentExtendRepository = supplierImportedDocumentExtendRepository;
        this.supplierImportedDocumentMapper = supplierImportedDocumentMapper;
    }

    @Override
    public Optional<SupplierImportedDocumentDTO> getOneByUploadTransactionId(Long transactionId) {
        return supplierImportedDocumentExtendRepository.findFirstByUploadTransactionId(transactionId)
            .map(supplierImportedDocumentMapper::toDto);
    }
}
