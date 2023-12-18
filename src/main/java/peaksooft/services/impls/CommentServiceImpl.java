package peaksooft.services.impls;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import peaksooft.dto.CommentRequest;
import peaksooft.dto.SimpleResponse;
import peaksooft.exceptions.NotFoundException;
import peaksooft.models.Comment;
import peaksooft.models.Product;
import peaksooft.models.User;
import peaksooft.repository.CommentRepository;
import peaksooft.repository.ProductRepository;
import peaksooft.repository.UserRepository;
import peaksooft.services.CommentService;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Override
    public SimpleResponse addComment(CommentRequest commentRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findUserByEmail(email).orElseThrow(() -> new NotFoundException("not found"));
        log.info("ok");
        Product product = productRepository.findProductByName(commentRequest.getProductName());
        Comment comment = new Comment(user, product, commentRequest.getComment());
        log.info("ok");
        comment.setProduct(product);
        comment.setComment(commentRequest.getComment());
        log.info("ok");
        comment.setUser(user);
        commentRepository.save(comment);
        return new SimpleResponse(HttpStatus.OK, "comment saved");
    }

    @Override
    public SimpleResponse update(CommentRequest commentRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findUserByEmail(email).orElseThrow(() -> new NotFoundException("User not found"));

        Product product = productRepository.findProductByName(commentRequest.getProductName());
        Comment existingComment = commentRepository.findByUserAndProduct(user, product);
        if (existingComment != null) {
            existingComment.setComment(commentRequest.getComment());
            commentRepository.save(existingComment);
            return new SimpleResponse(HttpStatus.OK, "Comment updated");
        } else {
            return new SimpleResponse(HttpStatus.NOT_FOUND, "Comment not found");
        }
    }

    @Override
    public SimpleResponse delete(CommentRequest commentRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findUserByEmail(email).orElseThrow(() -> new NotFoundException("User not found"));

        Product product = productRepository.findProductByName(commentRequest.getProductName());

        Comment existingComment = commentRepository.findByUserAndProduct(user,product);

        if (existingComment != null) {
            commentRepository.delete(existingComment);
            return new SimpleResponse(HttpStatus.OK, "Comment deleted");
        } else {
            return new SimpleResponse(HttpStatus.NOT_FOUND, "Comment not found");
        }
    }
}
