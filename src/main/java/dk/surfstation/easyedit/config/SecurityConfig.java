package dk.surfstation.easyedit.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	private final DataSource dataSource;
	// TODO: Change realm
	private final static String REALM = "MY_TEST_REALM";

	@Autowired
	public SecurityConfig(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Autowired
	public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
		String usersByUsernameQuery = "SELECT username, password, enabled FROM users WHERE username=?";
		String authoritiesByUsernameQuery = "SELECT users.username, role.name\n" +
				"FROM users\n" +
				"LEFT OUTER JOIN users_roles\n" +
				"  ON users.id = users_roles.user_id\n" +
				"LEFT OUTER JOIN role\n" +
				"  ON users_roles.role_id = role.id\n" +
				"WHERE username = ?";
		auth
				.jdbcAuthentication()
				.dataSource(dataSource)
				.usersByUsernameQuery(usersByUsernameQuery)
				.authoritiesByUsernameQuery(authoritiesByUsernameQuery);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
// TODO: https://github.com/tonsV2/Lift-Log-Backend/blob/master/src/main/java/dk/fitfit/liftlog/config/SecurityConfiguration.java#L44
//		http.csrf().disable();

		http.csrf().disable()
				.authorizeRequests()
				.antMatchers(HttpMethod.GET, "/posts").permitAll()
				.antMatchers("/**").authenticated()
				.and().httpBasic().realmName(REALM)
				.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);//We don't need sessions to be created.
	}
}
