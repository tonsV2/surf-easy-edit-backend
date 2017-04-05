package dk.surfstation.easyedit.controller;

import dk.surfstation.easyedit.domain.Post;
import dk.surfstation.easyedit.service.PostServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.persistence.EntityNotFoundException;
import java.net.URI;

@RestController
public class PostController {
	private final PostServiceInterface postService;

	@Autowired
	public PostController(PostServiceInterface postService) {
		this.postService = postService;
	}

	@PostMapping("/posts")
	public ResponseEntity<?> postPost(@RequestBody Post post) {
		Post save = postService.save(post);
		if (save != null) {
			URI location = ServletUriComponentsBuilder
					.fromCurrentRequest().path("/{id}")
					.buildAndExpand(save.getId()).toUri();
			return ResponseEntity.created(location).build();
		} else {
			return ResponseEntity.noContent().build();
		}
	}

	@GetMapping("/posts")
	public Iterable<Post> getPosts() {
		return postService.findAll();
	}

	@GetMapping("/posts/{id}")
	public Post getPosts(@PathVariable Long id) {
		return postService.findOne(id).orElseThrow(EntityNotFoundException::new);
	}
}
