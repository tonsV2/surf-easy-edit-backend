package dk.surfstation.easyedit.service;

import dk.surfstation.easyedit.domain.Role;
import dk.surfstation.easyedit.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService implements UserServiceInterface {
	private final UserRepository userRepository;

	@Autowired
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public Optional<User> findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return findByUsername(username)
				.map(user -> {
					Collection<Role> roles = user.getRoles();
					Set<GrantedAuthority> authorities = roles
							.stream()
							.map(role -> (GrantedAuthority) role::getName)
							.collect(Collectors.toSet());

					return new org.springframework.security.core.userdetails.User(user.getUsername(),
							user.getPassword(),
							user.getEnabled(),
							user.getEnabled(),
							user.getEnabled(),
							user.getEnabled(),
							authorities);
				})
				.orElseThrow(() -> new UsernameNotFoundException("Couldn't find user: " + username));
	}
}
