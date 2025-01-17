package club.esprit.backend.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import club.esprit.backend.entities.Comment;
import club.esprit.backend.entities.Post;
import club.esprit.backend.entities.User;
import club.esprit.backend.repository.CommentRepository;

import java.util.List;
import java.util.Optional;
@Service
@AllArgsConstructor
public class CommentServiceImp implements IComment{
    private CommentRepository commentRepository;
    @Override
    public Comment saveComment(Comment comment) {
        return commentRepository.save(comment) ;
    }
    public List<Comment> getCommentsByPostId(Long postId) {
        return commentRepository.findByPostPostId(postId);
    }
    @Override
    public Optional<Comment> getCommentById(Long id) {
            return commentRepository.findById(id);
    }

    @Override
    public List<Comment> getAllComments() {
        return  commentRepository.findAll();
    }

    @Override
    public void deleteComment(Long id) {
 commentRepository.deleteById(id);
    }

    @Override
    public List<Comment> getCommentsByPost(Post post) {
        return  commentRepository.findByPost(post);
    }

    @Override
    public List<Comment> getCommentsByUser(User user) {
        return commentRepository.findAllByUser(user);
    }
    @Override
    public Comment updateComment(Long commentId, String updatedText) {
        Comment existingComment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment with id " + commentId + " not found"));
        existingComment.setText(updatedText);
        return commentRepository.save(existingComment);
    }
}
