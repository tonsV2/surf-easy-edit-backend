package dk.surfstation.easyedit.service;

import dk.surfstation.easyedit.domain.Post;
import dk.surfstation.easyedit.domain.User;
import dk.surfstation.easyedit.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class PostService implements PostServiceInterface {
	private final PostRepository postRepository;

	@Autowired
	public PostService(PostRepository postRepository) {
		this.postRepository = postRepository;
	}

	@Override
	public Post save(Post post) {
		return postRepository.save(post);
	}

	@Override
	public Post save(String title, String content, User user) {
		Post post = new Post();
		post.setTitle(title);
		post.setContent(content);
		post.setUser(user);
		return postRepository.save(post);
	}

	@Override
	public Optional<Post> findOne(long id) {
		return Optional.ofNullable(postRepository.findOne(id));
	}

	@Override
	@Transactional
	public Iterable<Post> findAllByUsername(String username) {
		return postRepository.findAllByUsername(username);
	}

	@Override
	@Transactional
	public Optional<Post> findLatestByUsername(String username) {
		Post latestByUsername = postRepository.findLatestByUsername(username);
		return Optional.ofNullable(latestByUsername);
	}

	@Override
	public void delete(long id) {
		try {
			postRepository.delete(id);
		} catch (EmptyResultDataAccessException e) {
			throw new EntityNotFoundException();
		}
	}
}
