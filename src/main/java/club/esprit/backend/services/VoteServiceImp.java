package club.esprit.backend.services;

import club.esprit.backend.repository.PostRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import club.esprit.backend.entities.Post;
import club.esprit.backend.entities.User;
import club.esprit.backend.entities.Vote;
import club.esprit.backend.repository.VoteRepository;

import java.util.List;
import java.util.Optional;
@Service
@AllArgsConstructor
public class VoteServiceImp implements IVote{
private VoteRepository voteRepository;
private PostRepository postRepository;

    @Override
    public Vote saveVote(Vote vote) {
        // Check if a vote by the same user on the same post already exists
        Optional<Vote> existingVote = voteRepository.findVoteByPostAndUser(vote.getPost(), vote.getUser());
        if (existingVote.isPresent()) {
            // If it does, check if the new vote is the same as the existing vote
            Vote existingVoteEntity = existingVote.get();
            if (existingVoteEntity.getVoteType() == vote.getVoteType()) {
                // If it is, delete the existing vote
                voteRepository.delete(existingVoteEntity);
                return null;
            } else {
                // If it's not, update the existing vote
                existingVoteEntity.setVoteType(vote.getVoteType());
                voteRepository.save(existingVoteEntity);
                return existingVoteEntity;
            }
        } else {
            // If it doesn't, create a new vote
            voteRepository.save(vote);
        }

        // Recalculate the vote count for the post
        Post post = vote.getPost();
        int voteCount = post.getVoteCount();
        if (vote.getVoteType() == Vote.VoteType.UPVOTE) {
            voteCount++;
        } else if (vote.getVoteType() == Vote.VoteType.DOWNVOTE) {
            voteCount--;
        }
        post.setVoteCount(voteCount);

        // Save the post with the updated vote count
        postRepository.save(post);

        return vote;
    }
    @Override
    public Optional<Vote> getVoteById(Long id) {
        return  voteRepository.findById(id);
    }

    @Override
    @Transactional
    public List<Vote> getAllVotes() {
        return voteRepository.findAll();
    }
    @Override
    public void cancelVote(Long postId, User user) {
        // Find the existing vote by post and user
        Optional<Vote> existingVote = voteRepository.findVoteByPostAndUser(postRepository.getById(postId), user);
        existingVote.ifPresent(vote -> {
            // Delete the existing vote
            voteRepository.delete(vote);
            // Refresh the post from the repository
            Post post = postRepository.findById(postId)
                    .orElseThrow(() -> new RuntimeException("Post with id " + postId + " not found"));
            // Update the vote count for the post
            int voteCount = post.getVoteCount();
            if (vote.getVoteType() == Vote.VoteType.UPVOTE) {
                voteCount--;
            } else if (vote.getVoteType() == Vote.VoteType.DOWNVOTE) {
                voteCount++;
            }
            post.setVoteCount(voteCount);
            // Save the post with the updated vote count
            postRepository.save(post);
        });
    }




    @Override
    public void deleteVote(Long id) {
        voteRepository.deleteById(id);
    }

    @Override
    public Optional<Vote> getTopVoteByPostAndUser(Post post, User user) {
        return  voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post, user);
    }

    @Override
    public Vote updateVote(Long id, Vote.VoteType newVoteType) {
        Optional<Vote> voteOptional = voteRepository.findById(id);
        if (voteOptional.isPresent()) {
            Vote vote = voteOptional.get();
            vote.setVoteType(newVoteType);
            return voteRepository.save(vote);
        } else {
            throw new RuntimeException("Vote not found for id: " + id);
        }
    }
}
