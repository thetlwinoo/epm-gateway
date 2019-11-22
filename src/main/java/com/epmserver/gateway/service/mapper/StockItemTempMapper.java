package com.epmserver.gateway.service.mapper;

import com.epmserver.gateway.domain.*;
import com.epmserver.gateway.service.dto.StockItemTempDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link StockItemTemp} and its DTO {@link StockItemTempDTO}.
 */
@Mapper(componentModel = "spring", uses = {UploadTransactionsMapper.class})
public interface StockItemTempMapper extends EntityMapper<StockItemTempDTO, StockItemTemp> {

    @Mapping(source = "uploadTransaction.id", target = "uploadTransactionId")
    StockItemTempDTO toDto(StockItemTemp stockItemTemp);

    @Mapping(source = "uploadTransactionId", target = "uploadTransaction")
    StockItemTemp toEntity(StockItemTempDTO stockItemTempDTO);

    default StockItemTemp fromId(Long id) {
        if (id == null) {
            return null;
        }
        StockItemTemp stockItemTemp = new StockItemTemp();
        stockItemTemp.setId(id);
        return stockItemTemp;
    }
}
