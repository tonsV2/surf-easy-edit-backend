package dk.surfstation.easyedit.config;

import dk.surfstation.easyedit.service.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {
	private final TokenStore tokenStore;
	private final UserServiceInterface userService;

	@Autowired
	public AuthorizationServerConfiguration(TokenStore tokenStore, UserServiceInterface userService) {
		this.tokenStore = tokenStore;
		this.userService = userService;
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.tokenStore(tokenStore).approvalStoreDisabled();
//		endpoints.authenticationManager(authenticationManager);
		endpoints.userDetailsService(userService);
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients
				.inMemory()
				.withClient("html5")
				.secret("password")
				.authorizedGrantTypes("password")
				.scopes("openid");
	}
}
