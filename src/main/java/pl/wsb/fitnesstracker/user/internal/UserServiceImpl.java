package pl.wsb.fitnesstracker.user.internal;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.wsb.fitnesstracker.user.api.User;
import pl.wsb.fitnesstracker.user.api.UserNotFoundException;
import pl.wsb.fitnesstracker.user.api.UserProvider;
import pl.wsb.fitnesstracker.user.api.UserService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Implementacja serwisów {@link UserService} oraz {@link UserProvider}.
 * Klasa realizuje logikę biznesową dotyczącą zarządzania użytkownikami,
 * w tym ich tworzenie, wyszukiwanie, aktualizację oraz usuwanie z bazy danych.
 */
@Service
@RequiredArgsConstructor
@Slf4j
class UserServiceImpl implements UserService, UserProvider {

    private final UserRepository userRepository;

    /**
     * Tworzy i zapisuje nowego użytkownika w systemie.
     * * @param user Obiekt encji użytkownika do zapisania.
     * @return Zapisany użytkownik.
     * @throws IllegalArgumentException Jeśli użytkownik posiada już przypisane ID (update nie jest dozwolony tą metodą).
     */
    @Override
    public User createUser(final User user) {
        log.info("Creating User {}", user);
        if (user.getId() != null) {
            throw new IllegalArgumentException("User has already DB ID, update is not permitted!");
        }
        return userRepository.save(user);
    }

    /**
     * Pobiera użytkownika na podstawie jego identyfikatora ID.
     * * @param userId Identyfikator użytkownika.
     * @return {@link Optional} zawierający użytkownika lub pusty, jeśli nie znaleziono.
     */
    @Override
    public Optional<User> getUser(final Long userId) {
        return userRepository.findById(userId);
    }

    /**
     * Pobiera użytkownika na podstawie dokładnego adresu e-mail.
     * * @param email Adres e-mail użytkownika.
     * @return {@link Optional} zawierający użytkownika lub pusty, jeśli nie znaleziono.
     */
    @Override
    public Optional<User> getUserByEmail(final String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * Pobiera listę wszystkich użytkowników znajdujących się w systemie.
     * * @return Lista wszystkich encji {@link User}.
     */
    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Usuwa użytkownika z bazy danych na podstawie jego ID.
     * * @param id Identyfikator użytkownika do usunięcia.
     */
    @Override
    public void deleteUser(Long id) {
        log.info("Deleting User with ID {}", id);
        userRepository.deleteById(id);
    }

    /**
     * Wyszukuje użytkowników, których adres e-mail zawiera podany ciąg znaków (wielkość liter nie ma znaczenia).
     * * @param email Fragment adresu e-mail.
     * @return Lista znalezionych użytkowników.
     */
    @Override
    public List<User> findUsersByEmail(String email) {
        log.info("Searching Users by email fragment: {}", email);
        return userRepository.findByEmailContainingIgnoreCase(email);
    }

    /**
     * Wyszukuje użytkowników urodzonych przed określoną datą.
     * * @param date Data graniczna.
     * @return Lista użytkowników starszych niż wynika to ze wskazanej daty.
     */
    @Override
    public List<User> findUsersOlderThan(LocalDate date) {
        log.info("Searching Users born before: {}", date);
        return userRepository.findByBirthdateBefore(date);
    }

    /**
     * Aktualizuje dane istniejącego użytkownika.
     * * @param id Identyfikator użytkownika, który ma zostać zaktualizowany.
     * @param userDetails Nowe dane użytkownika do nadpisania.
     * @return Zaktualizowany i zapisany obiekt {@link User}.
     * @throws UserNotFoundException Jeśli użytkownik o podanym ID nie istnieje w bazie.
     */
    @Override
    public User updateUser(Long id, User userDetails) {
        log.info("Updating User with ID {}", id);
        return userRepository.findById(id)
                .map(user -> {
                    user.setFirstName(userDetails.getFirstName());
                    user.setLastName(userDetails.getLastName());
                    user.setBirthdate(userDetails.getBirthdate());
                    user.setEmail(userDetails.getEmail());
                    return userRepository.save(user);
                })
                .orElseThrow(() -> new UserNotFoundException(id));
    }

}