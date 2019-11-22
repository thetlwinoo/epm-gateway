package com.epmserver.gateway.service;

import com.epmserver.gateway.service.dto.UploadTransactionsDTO;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

public interface BatchUploadService {
    UploadTransactionsDTO readDataFromExcel(MultipartFile file, String serverUrl, Principal principal);
    void importToSystem(Long transactionId, Principal principal);
}
