package peaksooft.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import peaksooft.enums.Category;
import peaksooft.exceptions.NotFoundException;

import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "products")
@Builder
@ToString
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "p_gen" )
    @SequenceGenerator(name = "p_gen",sequenceName = "p_seq",allocationSize = 1)
    private long id;
    private String name;
    private double price;
    @ElementCollection
    private List<String> images;
    private String characteristic;
    private boolean isFavorite;
    @Enumerated(EnumType.STRING)
    private Category category;
    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Favorite>favorites;
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Comment>comments;
    @ManyToMany(mappedBy = "products")
    @JsonIgnore
    private List<Basket>baskets;

    public Product(String name, double price, List<String> images, String characteristic, boolean isFavorite, Category category) {
        this.name = name;
        this.price = price;
        this.images = images;
        this.characteristic = characteristic;
        this.isFavorite = isFavorite;
        this.category = category;
    }
}
