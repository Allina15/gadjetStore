package peaksooft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import peaksooft.models.Favorite;
import peaksooft.models.Product;
import peaksooft.models.User;

import java.util.List;

@Repository
@RepositoryRestResource(path="favorites")
public interface FavoriteRepository extends JpaRepository<Favorite,Long> {
    int countByProduct(Product product);

    Favorite findByUserAndProduct(User user, Product product);

    List<Favorite> findAllByUserAndProduct(User user, Product product);
}
