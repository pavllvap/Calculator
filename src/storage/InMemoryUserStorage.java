package storage;

import entity.Operation;
import entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class InMemoryUserStorage {

	private long incId = 1;

	private final List<User> users = new ArrayList<>();

	public void save(User user) {
		user.setId(incId++);
		users.add(user);
	}

	public Optional<User> getByUsername(String username) {
		for (User user : users) {
			if (user.getUsername().equals(username)) {
				return Optional.of(user);
			}
		}
		return Optional.empty();
	}
}
