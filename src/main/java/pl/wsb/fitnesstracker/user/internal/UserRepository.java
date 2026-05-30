package pl.wsb.fitnesstracker.user.internal;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.wsb.fitnesstracker.user.api.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Szuka użytkownika po dokładnym adresie email.
     */
    default Optional<User> findByEmail(String email) {
        return findAll().stream()
                .filter(user -> Objects.equals(user.getEmail(), email))
                .findFirst();
    }

    /**
     * Wyszukiwanie po fragmencie emaila, ignorując wielkość liter.
     * Wykorzystujemy mechanizm Query Methods z Spring Data JPA (najprostsza opcja).
     */
    List<User> findByEmailContainingIgnoreCase(String email);

    /**
     * Wyszukiwanie osób urodzonych przed konkretną datą (czyli starszych niż...).
     */
    List<User> findByBirthdateBefore(LocalDate date);

}