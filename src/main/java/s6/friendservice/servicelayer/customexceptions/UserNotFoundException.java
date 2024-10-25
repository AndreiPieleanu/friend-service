package s6.friendservice.servicelayer.customexceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UserNotFoundException extends ResponseStatusException {
    public UserNotFoundException() {
        super(HttpStatus.NOT_FOUND, "USER COULD NOT BE FOUND");
    }
}
