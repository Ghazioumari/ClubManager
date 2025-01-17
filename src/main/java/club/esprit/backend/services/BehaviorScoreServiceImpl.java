package club.esprit.backend.services;

import club.esprit.backend.entities.BehaviorScore;
import club.esprit.backend.entities.Membership;
import club.esprit.backend.repository.BehaviorScoreRepository;
import club.esprit.backend.repository.MembershipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Service

public class BehaviorScoreServiceImpl implements IBehaviorScoreService {

    @Autowired
    private BehaviorScoreRepository behaviorScoreRepository;

    @Autowired
    private MembershipRepository membershipRepository;
    private static final Logger logger = LoggerFactory.getLogger(BehaviorScoreServiceImpl.class);
    @Override
    public BehaviorScore addBehaviorScore(BehaviorScore behaviorScore) {
        return behaviorScoreRepository.save(behaviorScore);
    }

    @Override
    public BehaviorScore addBehaviorScorebasedonMembership(Long iduser, Long idClub, BehaviorScore behaviorScore) {
        Membership membership= membershipRepository.findByClub_IdAndAndUserId(idClub,iduser);
        behaviorScore.setMembership(membership);
        return behaviorScoreRepository.save(behaviorScore);
    }



    @Override
    public BehaviorScore updateBehaviorScore(BehaviorScore behaviorScore) {
        try {
            BehaviorScore existingBehaviorScore = behaviorScoreRepository.findById(behaviorScore.getId()).orElse(null);

            if (existingBehaviorScore == null) {
                throw new Exception("BehaviorScore with id " + behaviorScore.getId() + " does not exist");
            }

            if (behaviorScore.getBehaviorType() != null) {
                existingBehaviorScore.setBehaviorType(behaviorScore.getBehaviorType());
            }
            if (behaviorScore.getScore() != 0) {
                existingBehaviorScore.setScore(behaviorScore.getScore());
            }
            if (behaviorScore.getDateRecorded() != null) {
                existingBehaviorScore.setDateRecorded(behaviorScore.getDateRecorded());
            }
            if (behaviorScore.getDescription() != null) {
                existingBehaviorScore.setDescription(behaviorScore.getDescription());
            }
            if (behaviorScore.getEvent() != null) {
                existingBehaviorScore.setEvent(behaviorScore.getEvent());
            }
            if (behaviorScore.getMembership() != null) {
                existingBehaviorScore.setMembership(behaviorScore.getMembership());
            }

            return behaviorScoreRepository.save(existingBehaviorScore);
        } catch (Exception e) {
            logger.error("Error updating BehaviorScore: ", e);
            return null;
        }
    }

    @Override
    public void deleteBehaviorScore(Long id) {
        behaviorScoreRepository.deleteById(id);
    }

    @Override
    public BehaviorScore getBehaviorScore(Long id) {
        return behaviorScoreRepository.findById(id).orElse(null);
    }

    @Override
    public List<BehaviorScore> getBehaviorScoresByMembership(Long id) {
       // logger.info(behaviorScoreRepository.getBehaviorScoreByMembership_Id(id).toString());
        return behaviorScoreRepository.getBehaviorScoreByMembership_Id(id);
    }

    @Override
    public List<BehaviorScore> getAllBehaviorScores() {
        return behaviorScoreRepository.findAll();
    }

    @Override
    public List<BehaviorScore> getBehaviorScoresByDateRange(Date date1, Date date2) {
        return null;
    }


}
