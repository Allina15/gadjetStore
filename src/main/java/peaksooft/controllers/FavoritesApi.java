package peaksooft.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import peaksooft.dto.FavoriteRequest;
import peaksooft.dto.SimpleResponse;
import peaksooft.services.FavoriteService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/favorites")
public class FavoritesApi {

    private final FavoriteService favoriteService;

    @PostMapping
    public SimpleResponse addToFavorites(@RequestBody FavoriteRequest favoriteRequest){
        return favoriteService.addToFavorites(favoriteRequest);
    }
}
