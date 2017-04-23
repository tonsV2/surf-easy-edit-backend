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
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PostServiceTest {
	@Autowired
	private UserServiceInterface userService;

	@Autowired
	private PostServiceInterface postService;

	private String username;
	private String password;
	private User user;
	private List<Long> postIds = new ArrayList<>();
	private long postId;


	@Before
	public void setup() {
		username = "test" + UUID.randomUUID().toString();
		password = "test";

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
	public void findOneNonExistingPost() throws Exception {
		// Given
		int id = 22;

		// When
		Optional<Post> post = postService.findOne(id);

		// Then
		assertFalse(post.isPresent());
	}

	@Test
	public void findOnePost() throws Exception {
		// Given
		long id = postId;

		// When
		Optional<Post> post = postService.findOne(id);

		// Then
		assertTrue(post.isPresent());
	}

	@Test
	public void findAllByUsername() throws Exception {
		// Given
		String username = this.username;

		// When
		Iterable<Post> posts = postService.findAllByUsername(username);

		// Then
		assertTrue(Lists.newArrayList(posts).size() == 3);
	}

	@Test
	public void findLatestByUsername() throws Exception {
		// Given
		String username = this.username;

		// When
		Optional<Post> post = postService.findLatestByUsername(username);

		// Then
		assertTrue(post.isPresent());
	}

	@Test(expected = EntityNotFoundException.class)
	public void deleteNonExisting() throws Exception {
		// Given
		int id = 666;

		// When
		postService.delete(id);

		// Then
	}

	@Test
	public void deleteExisting() throws Exception {
		// Given
		long id = postId;

		// When
		postService.delete(id);

		// Then
		Optional<Post> post = postService.findOne(postId);
		assertFalse(post.isPresent());
	}

}
