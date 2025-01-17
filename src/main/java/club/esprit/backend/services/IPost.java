package club.esprit.backend.services;

import club.esprit.backend.entities.Category;
import club.esprit.backend.entities.Post;
import club.esprit.backend.entities.User;

import java.util.List;
import java.util.Optional;

public interface IPost {
    Post savePost(Post post, Long categoryId);
    Optional<Post> getPostById(Long id);
    List<Post> getAllPosts();
    void deletePost(Long id);
    List<Post> getPostsByCategory(Category category);
    List<Post> getPostsByUser(User user);
    Post udpatePost(Post post);
    List<Post> getPostsByCategory(Long categoryId);
    Post updateVoteCount(Long id, int voteCount);
    Post updatePostName(Long id, String newName);
    Post updatePostDescription(Long id, String newDescription);
    Post updatePostUrl(Long id, String newUrl);
}