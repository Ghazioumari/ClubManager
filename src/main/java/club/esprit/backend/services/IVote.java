package club.esprit.backend.services;

import club.esprit.backend.entities.Post;
import club.esprit.backend.entities.User;
import club.esprit.backend.entities.Vote;

import java.util.List;
import java.util.Optional;

public interface IVote {
    Vote saveVote(Vote vote);
    Optional<Vote> getVoteById(Long id);
    List<Vote> getAllVotes();
    void deleteVote(Long id);
    Optional<Vote> getTopVoteByPostAndUser(Post post, User user);
    Vote updateVote(Long id, Vote.VoteType newVoteType);
    void cancelVote(Long postId, User user);
}