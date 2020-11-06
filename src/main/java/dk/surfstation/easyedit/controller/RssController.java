package dk.surfstation.easyedit.controller;

import com.google.common.collect.Lists;
import dk.surfstation.easyedit.domain.Post;
import dk.surfstation.easyedit.rss.PostRssFeedView;
import dk.surfstation.easyedit.service.PostServiceInterface;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Controller
@RequestMapping("/api")
public class RssController {
	private PostRssFeedView view;
	private PostServiceInterface postService;

	public RssController(PostRssFeedView view, PostServiceInterface postService) {
		this.view = view;
		this.postService = postService;
	}

	@GetMapping(value = "/feed")
	public ModelAndView getFeed(@RequestParam String username) {
		Iterable<Post> posts = postService.findAllByUsername(username);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setView(view);
		modelAndView.addObject("posts", posts);
		return modelAndView;
	}

	@GetMapping(value = "/feed/latest")
	public ModelAndView getLatestFeed(@RequestParam String username) {
		Optional<Post> post = postService.findLatestByUsername(username);

		if (!post.isPresent()) {
			throw new ResponseStatusException(NOT_FOUND);
		}

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setView(view);
		modelAndView.addObject("posts", Lists.newArrayList(post.get()));
		return modelAndView;
	}
}
