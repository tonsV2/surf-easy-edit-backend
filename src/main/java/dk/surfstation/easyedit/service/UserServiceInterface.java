package dk.surfstation.easyedit.service;

import dk.surfstation.easyedit.domain.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface UserServiceInterface extends UserDetailsService {
	User save(String username, String password);
	Optional<User> findByUsername(String username);
	void delete(long id);
}
