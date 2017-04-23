package dk.surfstation.easyedit.controller;

import dk.surfstation.easyedit.domain.Post;
import dk.surfstation.easyedit.domain.User;
import dk.surfstation.easyedit.service.PostServiceInterface;
import dk.surfstation.easyedit.service.UserServiceInterface;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class EditController {
	private PostServiceInterface postService;
	private UserServiceInterface userService;

	public EditController(UserServiceInterface userService, PostServiceInterface postService) {
		this.userService = userService;
		this.postService = postService;
	}

	@GetMapping(value = "/edit/{id}")
	public ResponseEntity<String> getEditForm(@PathVariable String id) {
		Optional<User> user = userService.findByEditId(id);
		if (user.isPresent()) {
			String username = user.get().getUsername();
			Optional<Post> post = postService.findLatestByUsername(username);
			if (post.isPresent()) {
				return ResponseEntity.ok(post.get().getContent());
			} else {
				Post save = postService.save("", "", user.get());
				return ResponseEntity.ok(save.getContent());
			}
		}
		return ResponseEntity.notFound().build();
	}

	@PutMapping(value = "/edit/{id}")
	public ResponseEntity<Post> putEditForm(@PathVariable String id, @RequestBody String content) {
		Optional<User> user = userService.findByEditId(id);
		if (user.isPresent()) {
			String username = user.get().getUsername();
			Optional<Post> post = postService.findLatestByUsername(username);
			if (post.isPresent()) {
				post.get().setContent(content);
				Post save = postService.save(post.get());
				return ResponseEntity.ok(save);
			}
		}
		return ResponseEntity.notFound().build();
	}
}
