package peaksooft.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import peaksooft.dto.UserResponse;
import peaksooft.enums.Role;
import peaksooft.models.Basket;
import peaksooft.models.User;

import java.util.Optional;

@Repository
@RepositoryRestResource(path="users")
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User>findUserByEmail(String admin);
    @Query("select new peaksooft.dto.UserResponse(" + "t.email, t.firstName, t.lastName) "+"from User t")
    Page<UserResponse> getAll (Pageable pageable);
    boolean existsUserByRole(Role role);
    boolean existsByEmail(String email);
}
