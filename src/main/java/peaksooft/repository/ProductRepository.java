package peaksooft.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import peaksooft.dto.ProductResponse;
import peaksooft.dto.UserResponse;
import peaksooft.enums.Category;
import peaksooft.models.Product;

import java.util.List;

@Repository
@RepositoryRestResource(path="products")
public interface ProductRepository extends JpaRepository<Product,Long> {

    @Query("SELECT p.images, p.name, p.price, p.characteristic, p.category, c.comment, c.createdDate " +
            "FROM Product p " +
            "LEFT JOIN p.comments c")
    Page<Product> getAll (Pageable pageable);

    Product findProductByName(String name);

    @Query("SELECT p FROM Product p WHERE (:category IS NULL OR p.category = :category) AND (:maxPrice IS NULL OR p.price <= :maxPrice) ORDER BY p.price")
    Page<Product> findProductsByCategoryAndMaxPrice(@Param("category") Category category, @Param("maxPrice") Double maxPrice, Pageable pageable);

}
