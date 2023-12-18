package peaksooft.dto;

import lombok.*;
import peaksooft.models.Basket;
import peaksooft.models.Favorite;

import java.util.List;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse{
        private String firstName;
        private String lastName;
        private String username;
        private String token;
        private List<Favorite>favorites;
        private Basket basket;

    public UserResponse(String firstName, String lastName, String username, List<Favorite> favorites, Basket basket) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.favorites = favorites;
        this.basket = basket;
    }

    public UserResponse(String firstName, String lastName, String username) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
    }
}
