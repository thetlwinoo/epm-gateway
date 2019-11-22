package com.epmserver.gateway.repository;

import com.epmserver.gateway.domain.ReviewLines;

import java.util.List;

public interface ReviewLinesExtendRepository extends ReviewLinesRepository {
    List<ReviewLines> findAllByStockItemId(Long stockItemId);
}
