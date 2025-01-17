package club.esprit.backend.controllers;

import club.esprit.backend.entities.Post;
import club.esprit.backend.entities.User;
import club.esprit.backend.services.PostServiceImp;
import club.esprit.backend.services.jwt.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import club.esprit.backend.services.CommentServiceImp;
import club.esprit.backend.entities.Comment;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentServiceImp commentService;
    @Autowired
    private UserServiceImpl userService;
@Autowired
    private PostServiceImp postService;

    public CommentController(CommentServiceImp commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/{postId}")
    public ResponseEntity<Comment> createComment(@RequestBody Comment comment, @PathVariable Long postId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        org.springframework.security.core.userdetails.User principal =
                (org.springframework.security.core.userdetails.User) authentication.getPrincipal();

        User user = userService.findUserByEmail(principal.getUsername());
        Post post = postService.getPostById(postId)
                .orElseThrow(() -> new RuntimeException("Post with id " + postId + " not found")); // Handle Optional<Post>
        comment.setUser(user);
        comment.setPost(post); // Set the Post object in Comment
        return ResponseEntity.ok(commentService.saveComment(comment));
    }
    @GetMapping("/by-post/{postId}")
    public ResponseEntity<List<Comment>> getCommentsByPostId(@PathVariable Long postId) {
        return ResponseEntity.ok(commentService.getCommentsByPostId(postId));
    }
    @GetMapping("/{id}")
    public ResponseEntity<Comment> getCommentById(@PathVariable Long id) {
        return ResponseEntity.of(commentService.getCommentById(id));
    }

    @GetMapping
    public ResponseEntity<List<Comment>> getAllComments() {
        return ResponseEntity.ok(commentService.getAllComments());
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<Comment> updateComment(@PathVariable Long commentId, @RequestBody Map<String, String> body) {
        String updatedText = body.get("text");
        Comment updatedComment = commentService.updateComment(commentId, updatedText);
        return ResponseEntity.ok(updatedComment);
    }
}