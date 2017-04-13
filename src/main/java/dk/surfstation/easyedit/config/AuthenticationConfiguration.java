package dk.surfstation.easyedit.config;

import dk.surfstation.easyedit.service.UserServiceInterface;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@Configuration
@EnableResourceServer
public class AuthenticationConfiguration extends GlobalAuthenticationConfigurerAdapter {
	private final UserServiceInterface userService;
	private final PasswordEncoder passwordEncoder;

	public AuthenticationConfiguration(UserServiceInterface userService, PasswordEncoder passwordEncoder) {
		this.userService = userService;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public void init(AuthenticationManagerBuilder auth) throws Exception {
		auth
				.userDetailsService(userService)
				.passwordEncoder(passwordEncoder);
	}
}
