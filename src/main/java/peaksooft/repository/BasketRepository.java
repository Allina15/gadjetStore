package peaksooft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import peaksooft.models.Basket;
import peaksooft.models.Product;

import java.util.Collection;
import java.util.List;

@Repository
@RepositoryRestResource(path="baskets")
public interface BasketRepository extends JpaRepository<Basket,Long> {


    @Query("SELECT b FROM Basket b JOIN b.products p WHERE p = :product")
    Basket findBasketByProduct(@Param("product") Product product);
}
