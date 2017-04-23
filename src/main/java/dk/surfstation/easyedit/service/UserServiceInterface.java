package dk.surfstation.easyedit.service;

import dk.surfstation.easyedit.domain.User;

import java.util.Optional;

public interface UserServiceInterface {
	User save(String username, String password);
	Optional<User> findByUsername(String username);
	void delete(long id);
	Optional<User> findByEditId(String editId);
}
