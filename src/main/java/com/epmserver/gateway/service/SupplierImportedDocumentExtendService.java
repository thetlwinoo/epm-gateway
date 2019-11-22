package com.epmserver.gateway.service;

import com.epmserver.gateway.service.dto.SupplierImportedDocumentDTO;

import java.util.Optional;

public interface SupplierImportedDocumentExtendService {
    Optional<SupplierImportedDocumentDTO> getOneByUploadTransactionId(Long transactionId);
}
