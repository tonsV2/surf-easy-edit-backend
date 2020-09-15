package dk.surfstation.easyedit.controller;

import dk.surfstation.easyedit.domain.Post;
import dk.surfstation.easyedit.domain.User;
import dk.surfstation.easyedit.service.PostServiceInterface;
import dk.surfstation.easyedit.service.UserServiceInterface;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
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

	@PostMapping(value = "/edit/{id}")
	public ResponseEntity<Post> postEditForm(@PathVariable String id, @RequestBody String content) throws UnsupportedEncodingException {
		// For some reason axios submits a string where spaces are replaced by + and with a trailing =
		// This is probably due to the content being sent as application/x-www-form-urlencoded
		// This should be investigated further and fix differently than what I'm doing here... But time and money :-/
		String decodedContent = URLDecoder.decode(content, StandardCharsets.UTF_8.toString());

		String trimmedContent;
		if (decodedContent.endsWith("=")) {
			trimmedContent = decodedContent.substring(0, decodedContent.length() - 1);
		} else {
			trimmedContent = decodedContent;
		}

		Optional<User> user = userService.findByEditId(id);
		if (user.isPresent()) {
			Post post = new Post();
			post.setContent(trimmedContent);
			post.setUser(user.get());
			Post save = postService.save(post);
			return ResponseEntity.ok(save);
		}
		return ResponseEntity.notFound().build();
	}
}
