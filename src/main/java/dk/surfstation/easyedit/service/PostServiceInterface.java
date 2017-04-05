package dk.surfstation.easyedit.service;

import dk.surfstation.easyedit.domain.Post;

import java.util.Optional;

public interface PostServiceInterface {
	Post save(Post post);
	Optional<Post> findOne(long id);
	Iterable<Post> findAll();
	Iterable<Post> findAllByUsername(String username);
}
