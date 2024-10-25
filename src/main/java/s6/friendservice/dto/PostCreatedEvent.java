package s6.friendservice.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Builder
@Data
public class PostCreatedEvent {
    private Integer id;
    private String text;
    private Date createdAt;
    private Integer userId;
    private Boolean isBlocked;
}