package peaksooft.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import peaksooft.models.Favorite;
import peaksooft.models.Product;

import java.util.List;

@Builder
@Getter
@Setter
public class FavoriteResponse{

    private List<Favorite>favorites;
    public FavoriteResponse(List<Favorite>favorites) {
        this.favorites = favorites;
    }
}
