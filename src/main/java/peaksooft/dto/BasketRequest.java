package peaksooft.dto;

import jakarta.validation.constraints.NotNull;

public record BasketRequest(
        @NotNull(message = "product name must not be null")
        String productName,
        @NotNull(message = "save or delete?")
        String saveOrDelete
) {
}
