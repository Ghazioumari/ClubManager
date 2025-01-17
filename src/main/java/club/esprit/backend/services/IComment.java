package club.esprit.backend.services;

import club.esprit.backend.entities.Comment;
import club.esprit.backend.entities.Post;
import club.esprit.backend.entities.User;

import java.util.List;
import java.util.Optional;

public interface IComment {
    Comment saveComment(Comment comment);
    Optional<Comment> getCommentById(Long id);
    List<Comment> getAllComments();
    void deleteComment(Long id);
   // Comment updateComment(Comment comment);
    List<Comment> getCommentsByPost(Post post);
    List<Comment> getCommentsByUser(User user);
    Comment updateComment(Long commentId, String updatedText);
}