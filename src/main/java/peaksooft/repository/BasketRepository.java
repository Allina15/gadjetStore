package peaksooft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import peaksooft.models.Basket;
import peaksooft.models.Product;

import java.util.List;

@Repository
@RepositoryRestResource(path="baskets")
public interface BasketRepository extends JpaRepository<Basket,Long> {

    Basket findBasketByProductsIn(List<Product> products);
}
