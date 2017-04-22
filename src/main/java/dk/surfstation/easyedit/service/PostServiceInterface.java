package dk.surfstation.easyedit.service;

import dk.surfstation.easyedit.domain.Post;
import dk.surfstation.easyedit.domain.User;

import java.util.Optional;

public interface PostServiceInterface {
	Post save(Post post);
	Post save(String title, String content, User user);
	Optional<Post> findOne(long id);
	Iterable<Post> findAllByUsername(String username);
	Optional<Post> findLatestByUsername(String username);
	void delete(long id);
}
