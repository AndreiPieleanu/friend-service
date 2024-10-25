package s6.friendservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class FriendServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(FriendServiceApplication.class, args);
    }

}
