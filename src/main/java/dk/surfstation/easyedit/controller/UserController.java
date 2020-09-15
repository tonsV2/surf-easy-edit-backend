package dk.surfstation.easyedit.controller;

import dk.surfstation.easyedit.domain.User;
import dk.surfstation.easyedit.service.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api")
public class UserController {
	private final UserServiceInterface userService;

	@Autowired
	public UserController(UserServiceInterface userService) {
		this.userService = userService;
	}

	@GetMapping("/users")
	public Iterable<User> getAllUsers() {
		return userService.findAll();
	}

	@PostMapping("/users")
	public User postUsers(@RequestBody User user) {
		return userService.save(user.getUsername(), UUID.randomUUID().toString());
	}
}
