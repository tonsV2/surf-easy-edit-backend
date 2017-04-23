package dk.surfstation.easyedit.controller;

import dk.surfstation.easyedit.domain.User;
import dk.surfstation.easyedit.service.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {
	private final UserServiceInterface userService;

	@Autowired
	public UserController(UserServiceInterface userService) {
		this.userService = userService;
	}

	@GetMapping("/users")
	public Iterable<User> getAllPosts() {
		return userService.findAll();
	}
}
