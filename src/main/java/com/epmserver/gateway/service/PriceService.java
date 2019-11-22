package com.epmserver.gateway.service;

import com.epmserver.gateway.domain.ShoppingCarts;

public interface PriceService {
    ShoppingCarts calculatePrice(ShoppingCarts cart);
}
