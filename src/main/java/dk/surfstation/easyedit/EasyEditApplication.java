package dk.surfstation.easyedit;

import dk.surfstation.easyedit.domain.User;
import dk.surfstation.easyedit.service.PostServiceInterface;
import dk.surfstation.easyedit.service.UserServiceInterface;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.stream.Stream;

@SpringBootApplication
public class EasyEditApplication {
	@Bean
	public CommandLineRunner init(UserServiceInterface userService, PostServiceInterface postService) {
		return args -> {
			if (!userService.findByUsername("tons").isPresent()) {

				Stream.of("tons,skummet", "snot,skummet")
						.map(s -> s.split(","))
						.forEach(s -> userService.save(s[0], s[1]));

				initPosts(userService, postService, "tons", new String[]{"tons title,content", "tons other title,other content", "tons yet another title,yet some more content"});
				initPosts(userService, postService, "snot", new String[]{"title,content", "other title,other content", "yet another title,yet some more content"});
				}
		};
	}

	private void initPosts(UserServiceInterface userService, PostServiceInterface postService, String username, String[] strings) {
		Stream.of(strings)
				.map(s -> s.split(","))
				.forEach(s -> {
					User user = userService.findByUsername(username).get();
					postService.save(s[0], s[1], user);
				});
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	public static void main(String[] args) {
		SpringApplication.run(EasyEditApplication.class, args);
	}
}
