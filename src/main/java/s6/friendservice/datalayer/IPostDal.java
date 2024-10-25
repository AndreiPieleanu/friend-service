package s6.friendservice.datalayer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import s6.friendservice.datalayer.entities.Post;

@Repository
public interface IPostDal extends JpaRepository<Post, Integer> {
}