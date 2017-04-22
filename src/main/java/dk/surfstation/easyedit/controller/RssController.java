package dk.surfstation.easyedit.controller;

import dk.surfstation.easyedit.domain.Post;
import dk.surfstation.easyedit.rss.PostRssFeedView;
import dk.surfstation.easyedit.service.PostServiceInterface;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;
import java.util.Optional;

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
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setView(view);

		Optional<Iterable<Post>> optional = postService.findAllByUsername(username);
		Iterable<Post> posts = optional.orElse(Collections.EMPTY_LIST);
		modelAndView.addObject("posts", posts);

		return modelAndView;
	}
}
