package club.esprit.backend.services;

import club.esprit.backend.entities.BehaviorScore;

import java.util.Date;
import java.util.List;
public interface IBehaviorScoreService {
    BehaviorScore addBehaviorScore(BehaviorScore behaviorScore);
    BehaviorScore addBehaviorScorebasedonMembership(Long iduser, Long idClub,BehaviorScore behaviorScore);

    BehaviorScore updateBehaviorScore(BehaviorScore behaviorScore);
    void deleteBehaviorScore(Long id);
    BehaviorScore getBehaviorScore(Long id);
    List<BehaviorScore> getBehaviorScoresByMembership(Long id);
    List<BehaviorScore> getAllBehaviorScores();
    List<BehaviorScore> getBehaviorScoresByDateRange(Date date1, Date date2);

}
