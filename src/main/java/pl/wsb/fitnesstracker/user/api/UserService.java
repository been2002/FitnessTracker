package pl.wsb.fitnesstracker.user.api;

import java.time.LocalDate;
import java.util.List;

public interface UserService {

    User createUser(User user);

    void deleteUser(Long id);

    User updateUser(Long id, User userDetails);

    List<User> findUsersByEmail(String email);

    List<User> findUsersOlderThan(LocalDate date);
}