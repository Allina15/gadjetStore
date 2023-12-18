package peaksooft.dto;

import lombok.*;
import peaksooft.enums.Category;
import peaksooft.models.Comment;

import java.util.List;
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {
    private String name;
    private double price;
    private List<String> images;
    private String characteristic;
    private Category category;
    private List<Comment>comments;
    private int likes;

    public ProductResponse(String name, double price, List<String> images, String characteristic, Category category, List<Comment> comments) {
        this.name = name;
        this.price = price;
        this.images = images;
        this.characteristic = characteristic;
        this.category = category;
        this.comments = comments;
    }

    public ProductResponse(List<String> images, String name, double price, String characteristic, Category category, List<Comment> comments) {
        this.images = images;
        this.name = name;
        this.price = price;
        this.characteristic = characteristic;
        this.category = category;
        this.comments = comments;
    }
}
