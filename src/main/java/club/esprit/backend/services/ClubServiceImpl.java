package club.esprit.backend.services;

import club.esprit.backend.entities.Club;
import club.esprit.backend.entities.quiz.Test;
import club.esprit.backend.repository.ClubRepository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClubServiceImpl implements IClubService {

    @Autowired
    private ClubRepository clubRepository;

    @Override
    public Club addClub(Club club) {


        return clubRepository.save(club);
    }

    @Override
    public Club updateClub(Club club) {
        Club existingClub = clubRepository.findById(club.getId()).orElse(null);
        if (existingClub != null) {
            if (club.getName() != null) {
                existingClub.setName(club.getName());
            }
            if (club.getDescription() != null) {
                existingClub.setDescription(club.getDescription());
            }
            if (club.getContactInfo() != null) {
                existingClub.setContactInfo(club.getContactInfo());
            }
            if (club.getLogo() != null) {
                existingClub.setLogo(club.getLogo());
            }
            if(club.getTests()!=null)
            {
                existingClub.setTests(club.getTests());
            }
            if(club.getDepartments()!=null)
            {
                existingClub.setDepartments(club.getDepartments());
            }
            if(club.getMemberships()!=null)
            {
                System.out.println("this the log" +club.getMemberships());
                existingClub.setMemberships(club.getMemberships());
            }
            /*if(club.getEvents()!=null)
            {
                existingClub.setEvents(club.getEvents());
            }*/

            return clubRepository.save(existingClub);
        } else {
            return null;
        }
    }
    @Override
    public Club updateClubTest(Long clubId, Test newTest) {
        Club existingClub = clubRepository.findById(clubId).orElse(null);
        if (existingClub != null) {
            List<Test> tests = existingClub.getTests();
            tests.add(newTest);
            existingClub.setTests(tests);
            clubRepository.save(existingClub);
        }
        return existingClub;
    }
    @Override

    public void deleteClub(Long id) {
        clubRepository.deleteById(id);
    }

    @Override
    public Club getClub(Long id) {
        return clubRepository.findById(id).orElse(null);
    }

    @Override
    public List<Club> getAllClubs() {
        return clubRepository.findAll();
    }

    @Override
    public List<Club> getClubByUser(Long id) {return clubRepository.findClubsByUserId(id);}

    @Override
    public Club findClubsByUserIdAndPresident(Long id) {
        List<Club> clubs = clubRepository.findClubsByUserIdAndPresident(id);
        if (!clubs.isEmpty()) {
            return clubs.get(0);
        } else {

            return null;
        }
    }
}
