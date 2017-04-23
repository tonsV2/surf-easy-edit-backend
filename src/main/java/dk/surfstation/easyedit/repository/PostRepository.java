package dk.surfstation.easyedit.repository;

import dk.surfstation.easyedit.domain.Post;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends CrudRepository<Post, Long> {
	@Query("SELECT p FROM Post p WHERE p.user.username = :username ORDER BY p.updated DESC")
	Iterable<Post> findAllByUsername(@Param("username") String username);

// HQL
//	@Query("SELECT p FROM Post p WHERE p.user.username = :username AND p.updated = (SELECT MAX(p.updated) FROM Post p WHERE p.user.username = :username)")
// Native SQL
	@Query(value = "SELECT p.id, p.title, p.content, p.created, p.updated, p.user_id\n" +
			"FROM post p\n" +
			"  INNER JOIN users u\n" +
			"    ON p.user_id = u.id\n" +
			"WHERE u.username = :username\n" +
			"ORDER BY p.updated DESC\n" +
			"LIMIT 1", nativeQuery = true)
	Post findLatestByUsername(@Param("username") String username);
}
