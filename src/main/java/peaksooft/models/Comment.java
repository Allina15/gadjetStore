package peaksooft.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.ZonedDateTime;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "comments")
@Builder
@ToString
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "c_gen" )
    @SequenceGenerator(name = "c_gen",sequenceName = "c_seq",allocationSize = 1)
    private long id;
    private String comment;
    private ZonedDateTime createdDate;
    @ManyToOne
    @JsonIgnore
    private User user;
    @JsonIgnore
    @ManyToOne
    private Product product;

    public Comment(User user, Product product,  String comment) {
        this.comment = comment;
        this.user = user;
        this.product = product;
    }

    @PrePersist
    public void preSave() {
        this.createdDate = ZonedDateTime.now();
    }

}
