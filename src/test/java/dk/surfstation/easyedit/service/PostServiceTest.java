package dk.surfstation.easyedit.service;

import dk.surfstation.easyedit.domain.Post;
import dk.surfstation.easyedit.domain.User;
import org.assertj.core.util.Lists;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static dk.surfstation.easyedit.service.PostService.POST_FIND_LATESTS_BY_USERNAME_CACHE_KEY;
import static dk.surfstation.easyedit.service.PostService.POST_FIND_LATESTS_BY_USERNAME_TEST_USERNAME_VALUE;
import static java.util.Optional.empty;
import static java.util.Optional.ofNullable;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PostServiceTest {
	@Autowired
	private UserServiceInterface userService;

	@Autowired
	private PostServiceInterface postService;

	@Autowired
	private CacheManager cacheManager;

	private String username;
	private User user;
	private final List<Long> postIds = new ArrayList<>();
	private long postId;

	@Before
	public void setup() {
		username = "test" + UUID.randomUUID().toString();
		String password = "test";

		user = userService.save(username, password);

		Post post0 = postService.save("title0", "content0", user);
		postId = post0.getId();
		postIds.add(postId);

		Post post1 = postService.save("title1", "content1", user);
		postIds.add(post1.getId());

		Post post2 = postService.save("title2", "content2", user);
		postIds.add(post2.getId());
	}

	@After
	public void cleanup() {
		for (Long postId : postIds) {
			try {
				postService.delete(postId);
			} catch (EntityNotFoundException ignored) {

			}
		}
		userService.delete(user.getId());
	}

	@Test
	public void findLatestByUsername_shouldPopulateCache() {
		Optional<Post> post = postService.findLatestByUsername(username);

		assertEquals(post, getCachedPost(username));
	}

	@Test
	public void findLatestByUsername_shouldNotPopulateCacheWithSpecialUsername() {
		String username = POST_FIND_LATESTS_BY_USERNAME_TEST_USERNAME_VALUE;

		postService.findLatestByUsername(username);

		assertEquals(empty(), getCachedPost(username));
	}

	@Test
	public void findOneNonExistingPost() {
		// Given
		int id = 22;

		// When
		Optional<Post> post = postService.findOne(id);

		// Then
		assertFalse(post.isPresent());
	}

	@Test
	public void findOnePost() {
		// Given
		long id = postId;

		// When
		Optional<Post> post = postService.findOne(id);

		// Then
		assertTrue(post.isPresent());
		assertEquals(id, post.get().getId().longValue());
		assertEquals("title0", post.get().getTitle());
		assertEquals("content0", post.get().getContent());
	}

	@Test
	public void findAllByUsername() {
		// Given
		String username = this.username;

		// When
		Iterable<Post> posts = postService.findAllByUsername(username);

		// Then
		assertEquals(3, Lists.newArrayList(posts).size());
	}

	@Test
	public void findLatestByUsername() {
		// Given
		String username = this.username;

		// When
		Optional<Post> post = postService.findLatestByUsername(username);

		// Then
		assertTrue(post.isPresent());
		assertEquals("title2", post.get().getTitle());
		assertEquals("content2", post.get().getContent());
	}

	@Test(expected = EntityNotFoundException.class)
	public void deleteNonExisting() {
		// Given
		int id = 666;

		// When
		postService.delete(id);

		// Then
	}

	@Test
	public void deleteExisting() {
		// Given
		long id = postId;

		// When
		postService.delete(id);

		// Then
		Optional<Post> post = postService.findOne(postId);
		assertFalse(post.isPresent());
	}

	private Optional<Post> getCachedPost(String username) {
		return ofNullable(cacheManager.getCache(POST_FIND_LATESTS_BY_USERNAME_CACHE_KEY)).map(c -> c.get(username, Post.class));
	}
}
