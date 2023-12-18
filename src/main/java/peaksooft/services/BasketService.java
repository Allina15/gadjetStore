package peaksooft.services;

import peaksooft.dto.BasketRequest;
import peaksooft.dto.BasketResponse;
import peaksooft.dto.SimpleResponse;

public interface BasketService {
    BasketResponse addToBasket(BasketRequest basketRequest);

    BasketResponse allProductsInBasket(int currentPage, int size);

    SimpleResponse clearBasket();
}
