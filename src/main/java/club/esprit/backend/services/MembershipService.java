package club.esprit.backend.services;

import club.esprit.backend.entities.Club;
import club.esprit.backend.entities.Membership;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.util.List;

public interface MembershipService {
    Membership addMember(Membership membership);
    Membership updateMember(Membership membership);
    void deleteMember(Long memberId);

    void deleteMemberByUserId_ClubId(Long userId,Long clubId);
    Membership getMemberById(Long memberId);
    List<Membership> getAllMembers();
     List<Membership> getMembershipByclub(Long id );
    List<Membership> getMembershipByDepartment(Long id );
    Membership getMembershipByUserandClub(Long idUser,Long idClub );
    List<Membership> assignUsersToDepartment(List<Long> userIds, Long departmentId,Long clubId) throws ChangeSetPersister.NotFoundException;

}
