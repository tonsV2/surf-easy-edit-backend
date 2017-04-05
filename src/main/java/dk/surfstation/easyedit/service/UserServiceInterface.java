package dk.surfstation.easyedit.service;

import dk.surfstation.easyedit.domain.User;

import java.util.Optional;

public interface UserServiceInterface {
	Optional<User> findByUsername(String username);
}
