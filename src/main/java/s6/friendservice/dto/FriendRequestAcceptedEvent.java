package s6.friendservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FriendRequestAcceptedEvent {
    private Integer senderId;
    private Integer receiverId;
    // Any other relevant fields.
}