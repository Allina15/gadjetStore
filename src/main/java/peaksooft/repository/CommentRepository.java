package peaksooft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import peaksooft.models.Comment;
import peaksooft.models.Product;
import peaksooft.models.User;

@Repository
@RepositoryRestResource(path="comments")
public interface CommentRepository extends JpaRepository<Comment,Long> {
    Comment findByUserAndProduct(User user, Product product);

}
