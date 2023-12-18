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
import peaksooft.exceptions.NotFoundException;
import peaksooft.models.Basket;
import peaksooft.models.Product;
import peaksooft.models.User;
import peaksooft.repository.BasketRepository;
import peaksooft.repository.ProductRepository;
import peaksooft.repository.UserRepository;
import peaksooft.services.BasketService;

import java.util.ArrayList;
import java.util.Collections;

@Service
@RequiredArgsConstructor
@Slf4j
public class BasketServiceImpl implements BasketService {

     private final BasketRepository basketRepository;
     private final UserRepository userRepository;
     private final ProductRepository productRepository;

    @Override
    public BasketResponse addToBasket(BasketRequest basketRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        User user = userRepository.findUserByEmail(userEmail).orElseThrow(() -> new NotFoundException("User with email: " + userEmail + " not found"));
        Product product = productRepository.findProductByName(basketRequest.productName());
        Basket basket = new Basket();
            log.info("user ok");
            if (user.getBasket() == null) {
                basket.setUser(user);
                user.setBasket(basket);
                log.info("setUserAndBasket ok");
            }
            if (user.getBasket().getProducts() == null) {
                user.getBasket().setProducts(new ArrayList<>());
                log.info("product set ok");
            }
        saveOrDelete(basketRequest.saveOrDelete(),user.getBasket(),product);
        basketRepository.save(user.getBasket());
        userRepository.save(user);
        return new BasketResponse(user.getBasket().getProducts());
    }

    @Override
    public BasketResponse allProductsInBasket(int currentPage, int size) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userEmail = authentication.getName();
            User user = userRepository.findUserByEmail(userEmail).orElseThrow(() -> new NotFoundException("User with email: " + userEmail + " not found"));
                Pageable pageable = PageRequest.of(currentPage-1,size);
                Page<Product> products = productRepository.getAll(pageable);

        long count = 0L;
        long totalPrice = 0L;

        for (Product product : user.getBasket().getProducts()) {
            count++;
            totalPrice += (long) product.getPrice();
        }
                return BasketResponse.builder()
                        .products(products.getContent())
                        .currentPage(products.getNumber()+1)
                        .size(products.getTotalPages())
                        .count(count)
                        .totalPrice(totalPrice)
                        .build();
    }

    @Override
    public SimpleResponse clearBasket() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        User user = userRepository.findUserByEmail(userEmail).orElseThrow(() -> new NotFoundException("User with email: " + userEmail + " not found"));
        user.getBasket().getProducts().clear();
        return new SimpleResponse(HttpStatus.OK, "ok");
    }

    private void saveOrDelete(String word, Basket basket, Product product){
        if (word.equals("delete")){
            basket.getProducts().remove(product);
            product.getBaskets().remove(basket);
        }else if(word.equals("save")){
            basket.getProducts().add(product);
            product.setBaskets(Collections.singletonList(basket));
        }
    }

}
