package peaksooft.services;

import peaksooft.dto.CommentRequest;
import peaksooft.dto.SimpleResponse;

public interface CommentService {

    SimpleResponse addComment(CommentRequest commentRequest);
    SimpleResponse update(CommentRequest commentRequest);

    SimpleResponse delete(CommentRequest commentRequest);
}
