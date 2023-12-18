package peaksooft.services;

import org.springframework.boot.autoconfigure.task.TaskSchedulingProperties;
import peaksooft.dto.FavoriteRequest;
import peaksooft.dto.FavoriteResponse;
import peaksooft.dto.SimpleResponse;

public interface FavoriteService {

    SimpleResponse addToFavorites(FavoriteRequest favoriteRequest);
}
