package dk.surfstation.easyedit.repository;

import dk.surfstation.easyedit.domain.Post;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends CrudRepository<Post, Long> {
	@Query("SELECT p FROM Post p INNER JOIN p.user WHERE p.user.username = :username")
	Iterable<Post> findAllByUsername(@Param("username") String username);
}
