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
		User user = userService
				.findByUsername(principal.getName())
				.orElseThrow(EntityNotFoundException::new);
		post.setUser(user);
		Post save = postService.save(post);
		if (save != null) {
			return new ResponseEntity(save, HttpStatus.CREATED);
		} else {
			return ResponseEntity.noContent().build();
		}
	}

	@GetMapping("/posts/filter")
	public Iterable<Post> getPosts(@RequestParam String username) {
		return postService
				.findAllByUsername(username)
				.orElseThrow(EntityNotFoundException::new);
	}

	@GetMapping("/posts/latest")
	public Post getLatest(@RequestParam String username) {
		return postService
				.findLatestByUsername(username)
				.orElseThrow(EntityNotFoundException::new);
	}

	@GetMapping("/posts/latest/title")
	public String getLatestTitle(@RequestParam String username) {
		return postService
				.findLatestByUsername(username)
				.orElseThrow(EntityNotFoundException::new)
				.getTitle();
	}

	@GetMapping("/posts/latest/content")
	public String getLatestContent(@RequestParam String username) {
		return postService
				.findLatestByUsername(username)
				.orElseThrow(EntityNotFoundException::new)
				.getContent();
	}

	@GetMapping("/posts/{id}")
	public Post getPost(@PathVariable long id) {
		return postService
				.findOne(id)
				.orElseThrow(EntityNotFoundException::new);
	}

	@DeleteMapping("/posts/{id}")
	public void deletePost(@PathVariable long id) {
		postService.delete(id);
	}
}
