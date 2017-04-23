package dk.surfstation.easyedit.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

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
		// TODO: WTF... Learn and fix this!
		http
				.csrf().disable()
				.requestMatchers()
				.antMatchers(HttpMethod.GET, "/api/posts/filter")
				.antMatchers(HttpMethod.GET, "/my-project/**")
				.antMatchers(HttpMethod.GET, "/api/posts/latest")
				.antMatchers(HttpMethod.GET, "/api/posts/latest/title")
				.antMatchers(HttpMethod.GET, "/api/posts/latest/content")
				.antMatchers(HttpMethod.GET, "/api/feed")
				.antMatchers(HttpMethod.GET, "/api/edit/*")
				.antMatchers(HttpMethod.PUT, "/api/edit/*")
				.and()
				.authorizeRequests()
				.antMatchers(HttpMethod.GET, "/api/posts/filter").permitAll()
				.antMatchers(HttpMethod.GET, "/my-project/**").permitAll()
				.antMatchers(HttpMethod.GET, "/api/posts/latest", "/api/posts/latest/title", "/api/posts/latest/content").permitAll()
				.antMatchers(HttpMethod.GET, "/api/feed").permitAll()
				.antMatchers(HttpMethod.GET, "/api/edit/*").permitAll()
				.antMatchers(HttpMethod.PUT, "/api/edit/*").permitAll()
				.anyRequest()
				.authenticated();
	}
}
