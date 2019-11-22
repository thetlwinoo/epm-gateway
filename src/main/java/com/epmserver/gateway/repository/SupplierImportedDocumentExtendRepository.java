package com.epmserver.gateway.repository;

import com.epmserver.gateway.domain.SupplierImportedDocument;

import java.util.Optional;

public interface SupplierImportedDocumentExtendRepository extends SupplierImportedDocumentRepository {
    Optional<SupplierImportedDocument> findFirstByUploadTransactionId(Long id);
}
