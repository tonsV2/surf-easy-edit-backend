package dk.surfstation.easyedit.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	// Inspiration: http://disq.us/p/13yhboy
	@Override
	public void configure(WebSecurity web) throws Exception {
		web
				.ignoring()
				.antMatchers(HttpMethod.OPTIONS, "/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.csrf().disable()
				.authorizeRequests()

				.antMatchers(HttpMethod.GET, "/api/posts/filter").permitAll()
				.antMatchers(HttpMethod.GET, "/api/posts/latest").permitAll()
				.antMatchers(HttpMethod.GET, "/api/posts/latest/title").permitAll()
				.antMatchers(HttpMethod.GET, "/api/posts/latest/content").permitAll()

				.antMatchers(HttpMethod.GET, "/api/feed").permitAll()
				.antMatchers(HttpMethod.GET, "/api/feed/latest").permitAll()

				.antMatchers(HttpMethod.GET, "/api/edit/*").permitAll()
				.antMatchers(HttpMethod.PUT, "/api/edit/*").permitAll()

				.antMatchers(HttpMethod.GET, "/api/users").permitAll()

				.antMatchers(HttpMethod.GET, "/v2/api-docs", "/swagger-resources/**", "/swagger-ui.html").permitAll()

				.antMatchers(HttpMethod.GET, "/", "/index.html", "/static/**").permitAll()

				.anyRequest()
				.authenticated();
	}
}
