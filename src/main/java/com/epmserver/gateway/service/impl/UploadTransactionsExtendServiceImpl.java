package com.epmserver.gateway.service.impl;

import com.epmserver.gateway.domain.UploadTransactions;
import com.epmserver.gateway.repository.UploadTransactionsExtendRepository;
import com.epmserver.gateway.service.SuppliersExtendService;
import com.epmserver.gateway.service.UploadTransactionsExtendService;
import com.epmserver.gateway.service.dto.SuppliersDTO;
import com.epmserver.gateway.service.dto.UploadTransactionsDTO;
import com.epmserver.gateway.service.mapper.UploadTransactionsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class UploadTransactionsExtendServiceImpl implements UploadTransactionsExtendService {

    private final Logger log = LoggerFactory.getLogger(UploadTransactionsExtendServiceImpl.class);
    private final UploadTransactionsExtendRepository uploadTransactionsExtendRepository;
    private final SuppliersExtendService suppliersExtendService;
    private final UploadTransactionsMapper uploadTransactionsMapper;

    public UploadTransactionsExtendServiceImpl(UploadTransactionsExtendRepository uploadTransactionsExtendRepository, SuppliersExtendService suppliersExtendService, UploadTransactionsMapper uploadTransactionsMapper) {
        this.uploadTransactionsExtendRepository = uploadTransactionsExtendRepository;
        this.suppliersExtendService = suppliersExtendService;
        this.uploadTransactionsMapper = uploadTransactionsMapper;
    }

    @Override
    public void clearStockItemTemp(Long transactionId) {
        UploadTransactions uploadTransactions = uploadTransactionsExtendRepository.getOne(transactionId);
        uploadTransactions.setStockItemTempLists(null);
        uploadTransactionsExtendRepository.save(uploadTransactions);
    }

    @Override
    public List<UploadTransactionsDTO> findAll(Principal principal) {
        Optional<SuppliersDTO> suppliersDTOOptional = suppliersExtendService.getSupplierByPrincipal(principal);
        return uploadTransactionsExtendRepository.findAllBySupplierId(suppliersDTOOptional.get().getId()).stream()
            .map(uploadTransactionsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }
}
