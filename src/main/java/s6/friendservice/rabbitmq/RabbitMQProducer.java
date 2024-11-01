package s6.friendservice.rabbitmq;

import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import s6.friendservice.dto.FriendRequestAcceptedEvent;

@Service
@AllArgsConstructor
public class RabbitMQProducer {
    private final RabbitTemplate rabbitTemplate;

    public void publishFriendEvent(FriendRequestAcceptedEvent friendRequestAcceptedEvent) {
        rabbitTemplate.convertAndSend("friend-exchange", "friend.created", friendRequestAcceptedEvent);
        System.out.println("Sent event accept friend request! FriendRequestAcceptedEvent: " + friendRequestAcceptedEvent);
    }
    public void removeFriendship(Integer friendshipId){
        rabbitTemplate.convertAndSend("friend-exchange", "friend.deleted", friendshipId);
        System.out.println("Sent event delete friendship! friendshipId: " + friendshipId);
    }
}
