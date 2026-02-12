package gym.repository;

import gym.security.User;

public interface UserRepository {
    User findByUsername(String username);
}
