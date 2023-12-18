package peaksooft.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import peaksooft.models.Product;

import java.util.List;
@Builder
@Setter
@Getter
public class ProductResponse2 {
    private List<Product> products;
    private int currentPage;
    private int size;
}
