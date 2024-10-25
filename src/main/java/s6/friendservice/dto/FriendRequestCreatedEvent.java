package s6.friendservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FriendRequestCreatedEvent {
    private Integer senderId;
    private Integer receiverId;
    private Boolean isAccepted = false;
    // Any other relevant fields.
}