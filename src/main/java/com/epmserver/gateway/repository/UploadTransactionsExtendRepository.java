package com.epmserver.gateway.repository;

import com.epmserver.gateway.domain.UploadTransactions;

import java.util.List;

public interface UploadTransactionsExtendRepository extends UploadTransactionsRepository {
    List<UploadTransactions> findAllBySupplierId(Long supplierId);
}
