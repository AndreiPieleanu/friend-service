package s6.friendservice.rabbitmq;

import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import s6.friendservice.datalayer.IFriendsRelationshipDal;
import s6.friendservice.datalayer.IPostDal;
import s6.friendservice.datalayer.IUserDal;
import s6.friendservice.datalayer.entities.Post;
import s6.friendservice.datalayer.entities.User;
import s6.friendservice.dto.PostCreatedEvent;
import s6.friendservice.dto.UserCreatedEvent;
import s6.friendservice.dto.UserDeletedEvent;

@Service
@AllArgsConstructor
public class RabbitMQConsumer {
    private IUserDal userDal;
    private IPostDal postDal;
    private final IFriendsRelationshipDal friendsRelationshipDal;
    @RabbitListener(queues = "user-create-queue")
    public void handleUserCreatedEvent(UserCreatedEvent userCreatedEvent){
        System.out.println("Received User Created Event: " + userCreatedEvent);
        userDal.save(
                User
                        .builder()
                        .id(userCreatedEvent.getId())
                        .firstName(userCreatedEvent.getFirstName())
                        .lastName(userCreatedEvent.getLastName())
                        .build()
        );
    }

    @RabbitListener(queues = "post-create-queue")
    public void handlePostCreatedEvent(PostCreatedEvent event) {
        System.out.println("Received Post Created Event: " + event);
        postDal.save(
                Post
                        .builder()
                        .id(event.getId())
                        .text(event.getText())
                        .isBlocked(event.getIsBlocked())
                        .createdAt(event.getCreatedAt())
                        .userId(event.getUserId())
                        .build()
        );
    }

    @RabbitListener(queues = "user-delete-queue")
    public void consumeUserDeletedEvent(UserDeletedEvent userDeletedEvent){
        System.out.println("Received User Deleted Event: " + userDeletedEvent);
        friendsRelationshipDal.deleteAllFriendRelationshipsOfUserWithId(userDeletedEvent.getId());
    }

//    // Listen to user update events
//    @RabbitListener(queues = "post-update-queue")
//    public void handleUserUpdatedEvent(UserUpdatedEvent event) {
//        System.out.println("Received User Updated Event: " + event);
//        // Handle user update logic
//    }
//
//    // Listen to user deletion events
//    @RabbitListener(queues = "post-delete-queue")
//    public void handleUserDeletedEvent(UserDeletedEvent event) {
//        System.out.println("Received User Deleted Event: " + event);
//        // Handle user deletion logic
//    }
}
