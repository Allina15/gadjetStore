package peaksooft.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "favorites")
@Builder
@ToString
public class Favorite {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "f_gen" )
    @SequenceGenerator(name = "f_gen",sequenceName = "f_seq",allocationSize = 1)
    private long id;
    @ManyToOne
    @JsonIgnore
    private User user;
    @JsonIgnore
    @ManyToOne
    private Product product;

    public Favorite(User user, Product product) {
        this.user = user;
        this.product = product;
    }
}
