package club.esprit.backend.controllers;

import club.esprit.backend.entities.RequestToJoin;
import club.esprit.backend.services.RequestToJoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/requestToJoin")
public class RequestToJoinController {

    @Autowired
    private RequestToJoinService requestToJoinService;

    @PostMapping
    public RequestToJoin createRequestToJoin(@RequestBody RequestToJoin requestToJoin) {
        return requestToJoinService.saveRequestToJoin(requestToJoin);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RequestToJoin> getRequestToJoinById(@PathVariable Long id) {
        return requestToJoinService.getRequestToJoinById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<RequestToJoin> getAllRequestsToJoin() {
        return requestToJoinService.getAllRequestsToJoin();
    }
    @GetMapping("/getByUserId/{userId}")
    public List<RequestToJoin> getRequestsToJoinByUserId(@PathVariable Long userId) {
        return requestToJoinService.getRequestsToJoinByUserId(userId);
    }
    @GetMapping("/getByClubId/{clubId}")
    public List<RequestToJoin> getRequestsToJoinByClubId(@PathVariable Long clubId) {
        return requestToJoinService.getRequestsToJoinByClubId(clubId);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRequestToJoin(@PathVariable Long id) {
        requestToJoinService.deleteRequestToJoin(id);
        return ResponseEntity.ok().build();
    }
    @PutMapping("/update")
    public RequestToJoin updateRequestToJoin(@RequestBody RequestToJoin requestToJoin) {
        return requestToJoinService.updateRequestToJoin(requestToJoin);
    }
}