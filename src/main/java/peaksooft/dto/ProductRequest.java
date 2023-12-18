package peaksooft.dto;

import jakarta.validation.constraints.NotNull;
import peaksooft.enums.Category;

import java.util.List;

public record ProductRequest(
        @NotNull(message = "product name must not be null")
        String name,
        @NotNull(message = "product price must not be null")
        double price,
        List<String> images,
        String characteristic,
        Category category
) {
}
