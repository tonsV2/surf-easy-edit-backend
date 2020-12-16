package dk.surfstation.easyedit.service;

import dk.surfstation.easyedit.domain.Post;
import dk.surfstation.easyedit.domain.User;
import dk.surfstation.easyedit.repository.PostRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class PostService implements PostServiceInterface {
	final static String POST_FIND_LATESTS_BY_USERNAME_CACHE_KEY = "post.findLatestByUsername";
	final static String POST_FIND_LATESTS_BY_USERNAME_TEST_USERNAME_VALUE = "CACHE-TEST";

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	private final PostRepository postRepository;

	@Autowired
	public PostService(PostRepository postRepository) {
		this.postRepository = postRepository;
	}

	@Override
	@Caching(evict = {@CacheEvict(value = POST_FIND_LATESTS_BY_USERNAME_CACHE_KEY, key = "#post.user.username")})
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
		return postRepository.findById(id);
	}

	@Override
	@Transactional
	public Iterable<Post> findAllByUsername(String username) {
		return postRepository.findAllByUsername(username);
	}

	@Override
	@Transactional
	@Cacheable(value = POST_FIND_LATESTS_BY_USERNAME_CACHE_KEY, key = "#username", unless = "#a0 == '" + POST_FIND_LATESTS_BY_USERNAME_TEST_USERNAME_VALUE + "'")
	public Optional<Post> findLatestByUsername(String username) {
		Post latestByUsername = postRepository.findLatestByUsername(username);
		return Optional.ofNullable(latestByUsername);
	}

	@Override
	public void delete(long id) {
		try {
			postRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new EntityNotFoundException();
		}
	}
}
