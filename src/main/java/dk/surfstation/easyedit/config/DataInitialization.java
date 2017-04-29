package dk.surfstation.easyedit.config;

import dk.surfstation.easyedit.domain.User;
import dk.surfstation.easyedit.service.PostServiceInterface;
import dk.surfstation.easyedit.service.UserServiceInterface;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.UUID;
import java.util.stream.Stream;

@Component
public class DataInitialization {
	private final UserServiceInterface userService;
	private final PostServiceInterface postService;
	private final String[] users;

	DataInitialization(UserServiceInterface userService, PostServiceInterface postService, @Value("${data.users}") String[] users) {
		this.userService = userService;
		this.postService = postService;
		this.users = users;

		createData();
	}

	private void createData() {
		if (!userService.findByUsername("surfstation").isPresent()) {
			Arrays.stream(users)
					.map(s -> s.split(","))
//					.forEach(s -> userService.save(s[0], s[1]));
					.forEach(s -> {
						String username = s[0];
						String password = UUID.randomUUID().toString();
						System.out.println(String.format("User: %s:%s", username, password));
						userService.save(username, password);
					});
			initPosts(userService, postService, "surfstation", new String[]{"title,content", "other title,other content", "yet another title,yet some more content"});
		}
	}

	private void initPosts(UserServiceInterface userService, PostServiceInterface postService, String username, String[] strings) {
		Stream.of(strings)
				.map(s -> s.split(","))
				.forEach(s -> {
					User user = userService.findByUsername(username).get();
					postService.save(s[0], s[1], user);
				});
	}
}
