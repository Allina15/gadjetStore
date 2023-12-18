package peaksooft.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import peaksooft.enums.Role;
import peaksooft.exceptions.NotFoundException;

import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
@Builder
@ToString
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "u_gen" )
    @SequenceGenerator(name = "u_gen",sequenceName = "u_seq",allocationSize = 1)
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private ZonedDateTime createdDate;
    private ZonedDateTime updatedDate;
    @Enumerated(EnumType.STRING)
    private Role role;
    @OneToOne
    @JsonIgnore
    private Basket basket;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Favorite>favorites;
    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Comment>comments;

    public User(String firstName, String lastName, String email, String password, Role role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    @PrePersist
    public void preSave(){
        this.createdDate=ZonedDateTime.now();
        this.updatedDate=ZonedDateTime.now();
    }

    @PreUpdate
    public void preUpdate(){
        this.updatedDate=ZonedDateTime.now();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    public void addComment(Comment comment){
        if (comment != null){
            comments.add(comment);
            comment.setUser(this);
        }else {
            throw new NotFoundException("Comment is null");
        }
    }
}
