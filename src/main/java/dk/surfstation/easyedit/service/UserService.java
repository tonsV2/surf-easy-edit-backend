package dk.surfstation.easyedit.service;

import dk.surfstation.easyedit.domain.Role;
import dk.surfstation.easyedit.domain.User;
import dk.surfstation.easyedit.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService implements UserServiceInterface {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	@Autowired
	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public User save(String username, String password) {
		User user = new User();
		user.setUsername(username);
		user.setPassword(passwordEncoder.encode(password));
		return userRepository.save(user);
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
							true,
							true,
							true,
							true,
							authorities);
				})
				.orElseThrow(() -> new UsernameNotFoundException("Couldn't find user: " + username));
	}
}
