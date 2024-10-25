package s6.friendservice.datalayer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import s6.friendservice.datalayer.entities.User;

@Repository
public interface IUserDal extends JpaRepository<User, Integer> {
}
