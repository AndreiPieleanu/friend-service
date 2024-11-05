package s6.friendservice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import s6.friendservice.datalayer.IFriendsRelationshipDal;
import s6.friendservice.datalayer.entities.FriendsRelationship;
import s6.friendservice.datalayer.entities.Status;
import s6.friendservice.dto.CreateFollowRequest;
import s6.friendservice.dto.FriendRequestAcceptedEvent;
import s6.friendservice.rabbitmq.RabbitMQProducer;
import s6.friendservice.servicelayer.FriendService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FriendServiceTests {
    private FriendService friendService;
    private IFriendsRelationshipDal friendsRelationshipDal;
    private RabbitMQProducer rabbitMQProducer;
    private FriendsRelationship friendsRelationship;
    @BeforeEach
    void setUp() {
        friendsRelationshipDal = mock(IFriendsRelationshipDal.class);
        rabbitMQProducer = mock(RabbitMQProducer.class);
        friendService = new FriendService(friendsRelationshipDal, rabbitMQProducer);
        friendsRelationship = FriendsRelationship.builder()
                .id(1)
                .senderId(100)
                .receiverId(200)
                .status(Status.PENDING)
                .build();
    }
    @Test
    void testSendFriendRequest_Success() {
        CreateFollowRequest request = new CreateFollowRequest(100, 200);

        // Act
        friendService.sendFriendRequest(request);

        // Assert
        verify(friendsRelationshipDal, times(1)).save(any(FriendsRelationship.class));
    }

    @Test
    void testGetAllFriendshipsOfUserWithId_Success() {
        FriendsRelationship acceptedRelationship = FriendsRelationship.builder()
                .id(2)
                .senderId(100)
                .receiverId(200)
                .status(Status.ACCEPTED)
                .build();

        when(friendsRelationshipDal.findAll()).thenReturn(List.of(acceptedRelationship, friendsRelationship));

        // Act
        List<FriendsRelationship> result = friendService.getAllFriendshipsOfUserWithId(200);

        // Assert
        assertEquals(1, result.size());
        assertEquals(Status.ACCEPTED, result.get(0).getStatus());
    }

    @Test
    void testAcceptFriendRequest_Success() {
        when(friendsRelationshipDal.findById(1)).thenReturn(Optional.of(friendsRelationship));

        // Act
        friendService.acceptFriendRequest(1);

        // Assert
        assertEquals(Status.ACCEPTED, friendsRelationship.getStatus());
        verify(friendsRelationshipDal, times(1)).save(friendsRelationship);
        verify(rabbitMQProducer, times(1)).publishFriendEvent(any(FriendRequestAcceptedEvent.class));
    }

    @Test
    void testAcceptFriendRequest_NotFound() {
        when(friendsRelationshipDal.findById(1)).thenReturn(Optional.empty());

        // Act
        friendService.acceptFriendRequest(1);

        // Assert
        verify(friendsRelationshipDal, never()).save(any(FriendsRelationship.class));
        verify(rabbitMQProducer, never()).publishFriendEvent(any(FriendRequestAcceptedEvent.class));
    }

    @Test
    void testDeleteFriendRequest_Success() {
        // Act
        friendService.deleteFriendRequest(1);

        // Assert
        verify(friendsRelationshipDal, times(1)).deleteById(1);
    }
}
