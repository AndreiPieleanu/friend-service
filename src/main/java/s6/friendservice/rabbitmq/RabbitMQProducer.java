package s6.friendservice.rabbitmq;

import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import s6.friendservice.dto.FriendRequestCreatedEvent;

@Service
@AllArgsConstructor
public class RabbitMQProducer {
    private final RabbitTemplate rabbitTemplate;

    public void publishFriendEvent(FriendRequestCreatedEvent followCreatedEvent) {
        rabbitTemplate.convertAndSend("follow-exchange", "follow.created", followCreatedEvent);
        System.out.println("Sent event create follow! FollowCreatedEvent: " + followCreatedEvent);
    }
}
