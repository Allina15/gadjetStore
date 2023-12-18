package peaksooft.services.impls;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import peaksooft.dto.*;
import peaksooft.exceptions.BadCredentialsException;
import peaksooft.exceptions.NotFoundException;
import peaksooft.models.Basket;
import peaksooft.models.Product;
import peaksooft.models.User;
import peaksooft.repository.BasketRepository;
import peaksooft.repository.FavoriteRepository;
import peaksooft.repository.ProductRepository;
import peaksooft.repository.UserRepository;
import peaksooft.services.ProductService;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final FavoriteRepository favoriteRepository;
    private final BasketRepository basketRepository;
    @Override
    public SimpleResponse save(ProductRequest productRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findUserByEmail(email).orElseThrow(() -> new NotFoundException("not found"));
        if (user.getRole().name().equals("ADMIN")) {
            Product product = new Product();
            product.setName(productRequest.name());
            product.setPrice(productRequest.price());
            product.setImages(productRequest.images());
            product.setCharacteristic(productRequest.characteristic());
            product.setCategory(productRequest.category());
            productRepository.save(product);
            return new SimpleResponse(HttpStatus.OK, "Product saved");
        } else {
            throw new BadCredentialsException("Access denied");
        }
    }

    @Override
    public SimpleResponse delete(long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findUserByEmail(email).orElseThrow(() -> new NotFoundException("not found"));
        if (user.getRole().name().equals("ADMIN")) {
            Product product = productRepository.findById(id).orElseThrow(()->new NotFoundException("product with id "+id+" not found"));
            Basket basket = basketRepository.findBasketByProductsIn(Collections.singletonList(product));
            productRepository.delete(product);
            basketRepository.delete(basket);
            return new SimpleResponse(HttpStatus.OK, "product deleted");
        }else{
         throw new BadCredentialsException("Access denied");
        }
    }

    @Override
    public ProductResponse getProfile(long id) {
        Product product = productRepository.findById(id).orElseThrow(()->new NotFoundException("product with id "+id+" not found"));
        int sumLikes = favoriteRepository.countByProduct(product);
        return new ProductResponse(product.getName(), product.getPrice(), product.getImages(), product.getCharacteristic(), product.getCategory(), product.getComments(), sumLikes);
    }

    @Override
    public ProductResponse2 getAllProducts(int currentPage, int size, ProductRequest productRequest) {
        Pageable pageable = PageRequest.of(currentPage-1,size);
        Page<Product> products = productRepository.findProductsByCategoryAndMaxPrice(productRequest.category(),productRequest.price(),pageable);
        return ProductResponse2.builder()
                .products(products.getContent())
                .currentPage(products.getNumber()+1)
                .size(products.getTotalPages())
                .build();
    }

    @Override
    public FavoriteResponse getFavoriteProducts() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findUserByEmail(email).orElseThrow(() -> new NotFoundException("User with email: " + email + " not found"));
            return new FavoriteResponse(user.getFavorites());
    }
}
