package club.esprit.backend.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import club.esprit.backend.entities.Category;
import club.esprit.backend.entities.Post;
import club.esprit.backend.entities.User;
import club.esprit.backend.exceptions.PostNotFoundException;
import club.esprit.backend.repository.CategoryRepository;
import club.esprit.backend.repository.PostRepository;

import java.util.List;
import java.util.Optional;
@Service
@AllArgsConstructor

public class PostServiceImp implements IPost{
    private PostRepository postRepository;
    private CategoryRepository categoryRepository;
    @Override
    public Post savePost(Post post, Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("CategoryLibrary with id " + categoryId + " not found"));
        post.setCategory(category);
        return postRepository.save(post);
    }
    @Override
    public Post updateVoteCount(Long id, int voteCount) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException("Post with id " + id + " not found"));
        post.setVoteCount(voteCount);
        return postRepository.save(post);
    }
    @Override
    public List<Post> getPostsByCategory(Long categoryId) {
        return postRepository.findByCategory_IdCategory(categoryId);
    }
    @Override
    public Optional<Post> getPostById(Long id) {
        return postRepository.findById(id);
    }

    @Override
    public List<Post> getAllPosts() {
        return  postRepository.findAll();
    }

    @Override
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }

    @Override
    public List<Post> getPostsByCategory(Category category) {
       return postRepository.findAllByCategory(category);
    }

    @Override
    public List<Post> getPostsByUser(User user) {
  return postRepository.findByUser(user);
    }

    @Override
    public Post udpatePost(Post post) {
   return postRepository.save(post);
    }



    @Override
    public Post updatePostName(Long id, String newName) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException("Post with id " + id + " not found"));

        post.setPostName(newName);
        return postRepository.save(post);
    }

    @Override
    public Post updatePostDescription(Long id, String newDescription) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException("Post with id " + id + " not found"));

        post.setDescription(newDescription);
        return postRepository.save(post);
    }

    @Override
    public Post updatePostUrl(Long id, String newUrl) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException("Post with id " + id + " not found"));

        post.setUrl(newUrl);
        return postRepository.save(post);
    }
}
