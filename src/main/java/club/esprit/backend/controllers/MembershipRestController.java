package club.esprit.backend.controllers;

import club.esprit.backend.entities.Club;
import club.esprit.backend.entities.Membership;
import club.esprit.backend.services.MembershipService;
import jdk.jfr.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/members")
public class MembershipRestController {

    private MembershipService membershipService;

    @Autowired
    public MembershipRestController(MembershipService membershipService) {
        this.membershipService = membershipService;
    }

    @GetMapping
    public ResponseEntity<List<Membership>> getAllMembers() {
        List<Membership> memberships = membershipService.getAllMembers();
        return ResponseEntity.ok(memberships);
    }

    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Membership> addMember(@RequestBody Membership membership) {
        Membership newMembership = membershipService.addMember(membership);
        return ResponseEntity.status(HttpStatus.CREATED).body(newMembership);
    }

    @PutMapping("/update")
    public ResponseEntity<Membership> updateMember(@RequestBody Membership membership) {

        Membership updatedMembership = membershipService.updateMember(membership);
        return ResponseEntity.ok(updatedMembership);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteMember(@PathVariable Long id) {
        membershipService.deleteMember(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Membership> getMemberById(@PathVariable Long id) {
        Membership membership = membershipService.getMemberById(id);
        if (membership != null) {
            return ResponseEntity.ok(membership);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/memberbyclub/{id}")
    public ResponseEntity<List<Membership>> getMemberByclub(@PathVariable Long id) {
         List<Membership> membership = membershipService.getMembershipByclub(id);
        if (membership != null) {
            return ResponseEntity.ok(membership);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/memberbydepartment/{id}")
    public ResponseEntity<List<Membership>> getMemberBydepartment(@PathVariable Long id) {
        List<Membership> membership = membershipService.getMembershipByDepartment(id);
        if (membership != null) {
            return ResponseEntity.ok(membership);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PutMapping("/assignUsersToDepartment/{did}/{cid}")
    public List<Membership> assignUsersToDepartment(@RequestBody List<Long> userIds, @PathVariable("did") Long departmentId,@PathVariable("cid")Long clubId){
        try {
            return  membershipService.assignUsersToDepartment(userIds,departmentId,clubId);
        } catch (ChangeSetPersister.NotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    @DeleteMapping("/deletem/{userId}/{clubId}")
    public ResponseEntity<Void> deleteMemberByUserId_ClubId(@PathVariable("userId") Long userId, @PathVariable("clubId")Long clubId){
        membershipService.deleteMemberByUserId_ClubId(userId,clubId);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/getMembershipbyCandU/{idUser}/{idClub}")
    public ResponseEntity<Membership> getMembershipByUserandClub(@PathVariable("idUser") Long idUser, @PathVariable("idClub")Long idClub){
        Membership membership = membershipService.getMembershipByUserandClub(idUser,idClub);
        if (membership != null) {
            return ResponseEntity.ok(membership);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
