package peaksooft.services;

import peaksooft.dto.*;

public interface ProductService {
    SimpleResponse save(ProductRequest productRequest);
    SimpleResponse delete(long id);
    ProductResponse getProfile(long id);
    ProductResponse2 getAllProducts(int currentPage, int size, ProductRequest productRequest);
    FavoriteResponse getFavoriteProducts();
}
