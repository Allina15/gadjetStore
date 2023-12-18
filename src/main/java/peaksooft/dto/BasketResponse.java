package peaksooft.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import peaksooft.models.Product;

import java.util.List;
@Builder
@Getter
@NoArgsConstructor
public class BasketResponse{
        private List<Product>products;
        private int currentPage;
        private int size;
        private long count;
        private long totalPrice;
    public BasketResponse(List<Product> products) {
        this.products = products;
    }

    public BasketResponse(List<Product> products, int currentPage, int size, long count, long totalPrice) {
        this.products = products;
        this.currentPage = currentPage;
        this.size = size;
        this.count = count;
        this.totalPrice = totalPrice;
    }
}
