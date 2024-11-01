package s6.friendservice.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NotificationMessageRequest {
    private String id;
    private String from;
    private String to;
    private String text;
}
