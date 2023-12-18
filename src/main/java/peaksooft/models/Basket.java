package peaksooft.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "baskets")
@Builder
@ToString
public class Basket {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "b_gen" )
    @SequenceGenerator(name = "b_gen", sequenceName = "b_seq", allocationSize = 1)
    private long id;
    @ManyToMany
    @JsonIgnore
    private List<Product>products;
    @OneToOne(cascade = CascadeType.REMOVE)
    @JsonIgnore
    private User user;

    public Basket(List<Product> products, User user) {
        this.products = products;
        this.user = user;
    }
}
