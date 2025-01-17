package club.esprit.backend.services;

import club.esprit.backend.entities.RequestToJoin;

import java.util.List;
import java.util.Optional;

public interface RequestToJoinService  {
    RequestToJoin saveRequestToJoin(RequestToJoin requestToJoin);
    Optional<RequestToJoin> getRequestToJoinById(Long id);
    List<RequestToJoin> getAllRequestsToJoin();
    List<RequestToJoin> getRequestsToJoinByUserId(Long userId);
    List<RequestToJoin> getRequestsToJoinByClubId(Long clubId);
    void deleteRequestToJoin(Long id);
    RequestToJoin updateRequestToJoin(RequestToJoin requestToJoin);

}
