package dk.surfstation.easyedit.controller;

import dk.surfstation.easyedit.domain.Post;
import dk.surfstation.easyedit.domain.User;
import dk.surfstation.easyedit.service.PostServiceInterface;
import dk.surfstation.easyedit.service.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class PostController {
	private final UserServiceInterface userService;
	private final PostServiceInterface postService;

	@Autowired
	public PostController(UserServiceInterface userService, PostServiceInterface postService) {
		this.userService = userService;
		this.postService = postService;
	}

	@PostMapping("/posts")
	@SuppressWarnings("unchecked")
	public ResponseEntity<?> postPost(@RequestBody Post post, Principal principal) {
		Optional<User> user = userService.findByUsername(principal.getName());
		if (!user.isPresent()) {
			// TODO: Is this really what we want to return in case the username of Principal doesn't exists???
			return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		post.setUser(user.get());
		Post save = postService.save(post);
		if (save != null) {
			return new ResponseEntity(save, HttpStatus.CREATED);
		} else {
			return ResponseEntity.noContent().build();
		}
	}

	@GetMapping("/posts")
	public Iterable<Post> getAllPosts(Principal principal) {
		return postService.findAllByUsername(principal.getName());
	}

	@GetMapping("/posts/filter")
	public Iterable<Post> getPosts(@RequestParam String username) {
		return postService.findAllByUsername(username);
	}

	@GetMapping("/posts/latest")
	public ResponseEntity<Post> getLatest(@RequestParam String username) {
		Optional<Post> post = postService.findLatestByUsername(username);
		return post.map(p -> new ResponseEntity<>(p, HttpStatus.OK))
				.orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	@GetMapping("/posts/latest/title")
	public String getLatestTitle(@RequestParam String username) {
		return getLatest(username).getBody().getTitle();
	}

	@GetMapping("/posts/latest/content")
	public String getLatestContent(@RequestParam String username) {
		// TODO: getBody can return null... Delete all tables beside users
		return getLatest(username).getBody().getContent();
	}

	@GetMapping("/posts/{id}")
	public ResponseEntity<Post> getPost(@PathVariable long id) {
		Optional<Post> post = postService.findOne(id);
		return post.map(p -> new ResponseEntity<>(p, HttpStatus.OK))
				.orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	@DeleteMapping("/posts/{id}")
	public ResponseEntity<?> delete(@PathVariable long id) {
		try {
			postService.delete(id);
		} catch (EntityNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().build();
	}
}
