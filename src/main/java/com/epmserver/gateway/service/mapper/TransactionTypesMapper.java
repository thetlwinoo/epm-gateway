package com.epmserver.gateway.service.mapper;

import com.epmserver.gateway.domain.*;
import com.epmserver.gateway.service.dto.TransactionTypesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link TransactionTypes} and its DTO {@link TransactionTypesDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TransactionTypesMapper extends EntityMapper<TransactionTypesDTO, TransactionTypes> {



    default TransactionTypes fromId(Long id) {
        if (id == null) {
            return null;
        }
        TransactionTypes transactionTypes = new TransactionTypes();
        transactionTypes.setId(id);
        return transactionTypes;
    }
}
