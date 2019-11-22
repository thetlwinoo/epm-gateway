package com.epmserver.gateway.service;

import com.epmserver.gateway.service.dto.OrdersDTO;
import com.epmserver.gateway.service.dto.ReviewLinesDTO;
import com.epmserver.gateway.service.dto.ReviewsDTO;

import java.security.Principal;
import java.util.List;

public interface ReviewsExtendService {
    ReviewsDTO save(Principal principal, ReviewsDTO reviewsDTO, Long orderId);

    List<OrdersDTO> findAllOrderedProducts(Principal principal);

    List<ReviewLinesDTO> findReviewLinesByStockItemId(Long stockItemId);

    ReviewsDTO completedReview(Long orderId);
}
