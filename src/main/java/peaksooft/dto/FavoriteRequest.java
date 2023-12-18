package peaksooft.dto;

import lombok.Data;

@Data
public class FavoriteRequest {
    private String email;
    private String productName;
    private String word;
}
