package s6.friendservice.controller;

import jakarta.annotation.security.RolesAllowed;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import s6.friendservice.configuration.security.isauthenticated.IsAuthenticated;
import s6.friendservice.datalayer.entities.FriendsRelationship;
import s6.friendservice.datalayer.entities.Status;
import s6.friendservice.dto.CreateFollowRequest;
import s6.friendservice.dto.NotificationMessageRequest;
import s6.friendservice.servicelayer.FriendService;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@AllArgsConstructor
public class FriendController {
    private final FriendService friendService;
    @PostMapping
    @IsAuthenticated
    @RolesAllowed({"ROLE_USER", "ROLE_ADMIN", "ROLE_MODERATOR"})
    public ResponseEntity<Void> sendFriendRequest(@RequestBody NotificationMessageRequest message){
        CreateFollowRequest request = CreateFollowRequest
                .builder()
                .senderId(Integer.parseInt(message.getFrom()))
                .receiverId(Integer.parseInt(message.getTo()))
                .build();
        friendService.sendFriendRequest(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/update/{requestId}")
    @IsAuthenticated
    @RolesAllowed({"ROLE_USER", "ROLE_ADMIN", "ROLE_MODERATOR"})
    public ResponseEntity<Void> acceptFriendRequest(
            @PathVariable(name = "requestId")Integer requestId){
        friendService.acceptFriendRequest(requestId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/friendships/{userId}")
    @IsAuthenticated
    @RolesAllowed({"ROLE_USER", "ROLE_ADMIN", "ROLE_MODERATOR"})
    public ResponseEntity<List<FriendsRelationship>> getAllFriendshipsOfUserWithId(@PathVariable(name =
            "userId")Integer userId){
        return ResponseEntity.ok(friendService.getAllFriendshipsOfUserWithId(userId));
    }

    @DeleteMapping("{requestId}")
    @IsAuthenticated
    @RolesAllowed({"ROLE_USER", "ROLE_ADMIN", "ROLE_MODERATOR"})
    public ResponseEntity<Void> deleteFriendRequest(@PathVariable(name = "requestId")Integer requestId){
        friendService.deleteFriendRequest(requestId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
