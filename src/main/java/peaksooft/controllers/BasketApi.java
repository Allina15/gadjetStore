package peaksooft.controllers;

import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksooft.dto.BasketRequest;
import peaksooft.dto.BasketResponse;
import peaksooft.dto.SimpleResponse;
import peaksooft.services.BasketService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/basket")
public class BasketApi {

    private final BasketService basketService;

    @PermitAll
    @PostMapping
    public BasketResponse addToBasket(@RequestBody BasketRequest basketRequest){
        return basketService.addToBasket(basketRequest);
    }

    @GetMapping
    public BasketResponse productsInBasket(@RequestParam  int currentPage, @RequestParam int size){
        return basketService.allProductsInBasket(currentPage,size);
    }

    @DeleteMapping
    public SimpleResponse clearBasket(){
        return basketService.clearBasket();
    }
}
