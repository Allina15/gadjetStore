package peaksooft.controllers;

import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import peaksooft.dto.CommentRequest;
import peaksooft.dto.SimpleResponse;
import peaksooft.services.CommentService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
public class CommentApi {

    private final CommentService commentService;

    @PermitAll
    @PostMapping
    public SimpleResponse addComment(@RequestBody @Valid CommentRequest commentRequest){
        return commentService.addComment(commentRequest);
    }

    @PermitAll
    @PutMapping
    public SimpleResponse update(@RequestBody @Valid CommentRequest commentRequest){
        return commentService.update(commentRequest);
    }

    @PermitAll
    @DeleteMapping
    public SimpleResponse delete(@RequestBody CommentRequest commentRequest){
        return commentService.delete(commentRequest);
    }
}
