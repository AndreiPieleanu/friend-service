package s6.friendservice.servicelayer;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import s6.friendservice.datalayer.IFriendsRelationshipDal;
import s6.friendservice.datalayer.entities.FriendsRelationship;
import s6.friendservice.datalayer.entities.Status;
import s6.friendservice.dto.CreateFollowRequest;
import s6.friendservice.dto.FriendRequestAcceptedEvent;
import s6.friendservice.rabbitmq.RabbitMQProducer;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class FriendService {

    private final IFriendsRelationshipDal friendsRelationshipDal;
    private final RabbitMQProducer rabbitMQProducer;
    public void sendFriendRequest(CreateFollowRequest request) {
        FriendsRelationship friendsRelationship = FriendsRelationship
                .builder()
                .receiverId(request.getReceiverId())
                .senderId(request.getSenderId())
                .status(Status.PENDING)
                .build();
        friendsRelationshipDal.save(friendsRelationship);
    }

    public List<FriendsRelationship> getAllFriendshipsOfUserWithId(Integer userId){
        return friendsRelationshipDal
                .findAll()
                .stream()
                .filter(f -> Objects.equals(f.getReceiverId(), userId) && f.getStatus() == Status.ACCEPTED)
                .toList();
    }

    public void acceptFriendRequest(Integer id){
        Optional<FriendsRelationship> relationship = friendsRelationshipDal.findById(id);
        if(relationship.isEmpty()){
            return;
        }
        FriendsRelationship updatedRelationship = relationship.get();
        updatedRelationship.setStatus(Status.ACCEPTED);
        friendsRelationshipDal.save(updatedRelationship);
        FriendRequestAcceptedEvent event = FriendRequestAcceptedEvent
                .builder()
                .senderId(updatedRelationship.getSenderId())
                .receiverId(updatedRelationship.getReceiverId())
                .build();
        rabbitMQProducer.publishFriendEvent(event);
    }

    public void deleteFriendRequest(Integer id){
        friendsRelationshipDal.deleteById(id);
    }
}
