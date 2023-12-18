package peaksooft.services.impls;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import peaksooft.dto.FavoriteRequest;
import peaksooft.dto.SimpleResponse;
import peaksooft.exceptions.NotFoundException;
import peaksooft.models.Favorite;
import peaksooft.models.Product;
import peaksooft.models.User;
import peaksooft.repository.FavoriteRepository;
import peaksooft.repository.ProductRepository;
import peaksooft.repository.UserRepository;
import peaksooft.services.FavoriteService;

@Slf4j
@Service
@RequiredArgsConstructor
public class FavoriteServiceImpl implements FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Override
    public SimpleResponse addToFavorites(FavoriteRequest favoriteRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        User user = userRepository.findUserByEmail(userEmail).orElseThrow(() -> new NotFoundException("User with email: " + userEmail + " not found"));
        Product product = productRepository.findProductByName(favoriteRequest.getProductName());

        Favorite existingFavorite = favoriteRepository.findByUserAndProduct(user, product);

        if (existingFavorite == null && favoriteRequest.getWord().equals("save")) {
            Favorite newFavorite = new Favorite();
            newFavorite.setUser(user);
            newFavorite.setProduct(product);
            favoriteRepository.save(newFavorite);
        } else if ( existingFavorite!= null && favoriteRequest.getWord().equals("delete")) {
            favoriteRepository.delete(existingFavorite);
        }
        return new SimpleResponse(HttpStatus.OK, "Okey");
    }
}
