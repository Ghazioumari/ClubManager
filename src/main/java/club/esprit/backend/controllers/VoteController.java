package club.esprit.backend.controllers;

import club.esprit.backend.entities.Post;
import club.esprit.backend.entities.User;
import club.esprit.backend.services.PostServiceImp;
import club.esprit.backend.services.jwt.UserServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import club.esprit.backend.services.VoteServiceImp;
import club.esprit.backend.entities.Vote;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/votes")
public class VoteController {

    private final VoteServiceImp voteService;
    private final PostServiceImp postService;
    private final UserServiceImpl userService;


    @PostMapping("/{postId}")
    public ResponseEntity<Vote> createVote(@RequestBody Vote vote, @PathVariable Long postId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        org.springframework.security.core.userdetails.User principal =
                (org.springframework.security.core.userdetails.User) authentication.getPrincipal();

        User user = userService.findUserByEmail(principal.getUsername());
        vote.setUser(user);
        Post post = postService.getPostById(postId)
                .orElseThrow(() -> new RuntimeException("Post with id " + postId + " not found"));
        vote.setPost(post);
        return ResponseEntity.ok(voteService.saveVote(vote));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vote> getVoteById(@PathVariable Long id) {
        return ResponseEntity.of(voteService.getVoteById(id));
    }

    @GetMapping
    public ResponseEntity<List<Vote>> getAllVotes() {
        return ResponseEntity.ok(voteService.getAllVotes());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVote(@PathVariable Long id) {
        voteService.deleteVote(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Vote> updateVote(@PathVariable Long id, @RequestBody Vote.VoteType newVoteType) {
        Vote existingVote = voteService.getVoteById(id)
                .orElseThrow(() -> new RuntimeException("Vote with id " + id + " not found"));
        existingVote.setVoteType(newVoteType);
        return ResponseEntity.ok(voteService.updateVote(id, newVoteType));
    }
    @DeleteMapping("/{postId}/cancel")
    public ResponseEntity<Void> cancelVote(@PathVariable Long postId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        org.springframework.security.core.userdetails.User principal =
                (org.springframework.security.core.userdetails.User) authentication.getPrincipal();

        User user = userService.findUserByEmail(principal.getUsername());
        voteService.cancelVote(postId, user);
        return ResponseEntity.ok().build();
    }

}