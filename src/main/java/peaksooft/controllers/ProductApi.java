package peaksooft.controllers;

import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksooft.dto.*;
import peaksooft.services.ProductService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductApi {

    private final ProductService productService;

    @PermitAll
    @GetMapping("/allProducts")
    public ProductResponse2 getAll(@RequestParam int currentPage, @RequestParam int size, @RequestBody ProductRequest productRequest){
        return productService.getAllProducts(currentPage, size, productRequest);
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public SimpleResponse save(@RequestBody ProductRequest productRequest){
        return productService.save(productRequest);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public SimpleResponse delete(@PathVariable long id){
        return productService.delete(id);
    }

    @PermitAll
    @GetMapping("/{id}")
    public ProductResponse getProfile(@PathVariable long id){
        return productService.getProfile(id);
    }

    @PermitAll
    @GetMapping
    public FavoriteResponse getFavoriteProducts(){
        return productService.getFavoriteProducts();
    }
}
