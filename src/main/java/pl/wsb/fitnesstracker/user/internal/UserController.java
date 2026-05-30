package pl.wsb.fitnesstracker.user.internal;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.wsb.fitnesstracker.user.api.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Kontroler REST odpowiedzialny za operacje na użytkownikach (CRUD).
 * Zapewnia endpointy do wyszukiwania, tworzenia, aktualizowania oraz usuwania użytkowników.
 */
@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
class UserController {

    private final UserServiceImpl userService;
    private final UserMapper userMapper;

    /**
     * Pobiera pełną listę wszystkich użytkowników zarejestrowanych w systemie.
     * * @return Lista obiektów {@link UserDto} zawierających szczegółowe dane użytkowników.
     */
    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.findAllUsers()
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    /**
     * Pobiera uproszczoną listę wszystkich użytkowników (tylko ID, imię i nazwisko).
     * * @return Lista obiektów {@link UserSummaryDto}.
     */
    @GetMapping("/simple")
    public List<UserSummaryDto> getAllSimpleUsers() {
        return userService.findAllUsers()
                .stream()
                .map(userMapper::toSummaryDto)
                .toList();
    }

    /**
     * Tworzy nowego użytkownika w systemie.
     * * @param userDto Obiekt transferu danych zawierający informacje o nowym użytkowniku.
     * @return {@link UserDto} stworzonego użytkownika wraz z nadanym identyfikatorem ID.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto createUser(@RequestBody UserDto userDto) {
        User user = userService.createUser(userMapper.toEntity(userDto));
        return userMapper.toDto(user);
    }

    /**
     * Pobiera szczegółowe informacje o konkretnym użytkowniku na podstawie jego identyfikatora ID.
     * * @param id Unikalny identyfikator użytkownika.
     * @return {@link UserDto} znalezionego użytkownika.
     * @throws UserNotFoundException Jeśli użytkownik o podanym ID nie istnieje.
     */
    @GetMapping("/{id}")
    public UserDto getUser(@PathVariable Long id) {
        return userService.getUser(id)
                .map(userMapper::toDto)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    /**
     * Usuwa użytkownika z systemu na podstawie jego identyfikatora ID.
     * * @param id Unikalny identyfikator użytkownika do usunięcia.
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    /**
     * Wyszukuje użytkowników, których adres e-mail zawiera podaną frazę.
     * Wyszukiwanie nie rozróżnia wielkości liter.
     * * @param email Fragment adresu e-mail do wyszukania.
     * @return Lista obiektów {@link UserEmailDto} (tylko ID i e-mail).
     */
    @GetMapping("/email")
    public List<UserEmailDto> findUsersByEmail(@RequestParam String email) {
        return userService.findUsersByEmail(email)
                .stream()
                .map(userMapper::toEmailDto)
                .toList();
    }

    /**
     * Wyszukuje użytkowników starszych niż zdefiniowana data urodzenia.
     * * @param date Graniczna data urodzenia (użytkownicy urodzeni przed tą datą).
     * @return Lista obiektów {@link UserDto} spełniających kryterium wieku.
     */
    @GetMapping("/older/{date}")
    public List<UserDto> findUsersOlderThan(@PathVariable LocalDate date) {
        return userService.findUsersOlderThan(date)
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    /**
     * Aktualizuje dane istniejącego użytkownika.
     * * @param id Unikalny identyfikator użytkownika do aktualizacji.
     * @param userDto Nowe dane użytkownika.
     * @return {@link UserDto} po dokonanych zmianach.
     */
    @PutMapping("/{id}")
    public UserDto updateUser(@PathVariable Long id, @RequestBody UserDto userDto) {
        User updatedUser = userService.updateUser(id, userMapper.toEntity(userDto));
        return userMapper.toDto(updatedUser);
    }
}