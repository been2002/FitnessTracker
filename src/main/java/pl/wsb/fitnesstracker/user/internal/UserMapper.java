package pl.wsb.fitnesstracker.user.internal;

import org.springframework.stereotype.Component;
import pl.wsb.fitnesstracker.user.api.User;
import pl.wsb.fitnesstracker.user.api.UserDto;
import pl.wsb.fitnesstracker.user.api.UserEmailDto;
import pl.wsb.fitnesstracker.user.api.UserSummaryDto;

@Component
class UserMapper {

    /**
     * Mapuje encję User na pełny obiekt UserDto.
     */
    UserDto toDto(User user) {
        return new UserDto(user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getBirthdate(),
                user.getEmail());
    }

    /**
     * Mapuje encję User na uproszczony UserSummaryDto (tylko ID i Imię + Nazwisko).
     */
    UserSummaryDto toSummaryDto(User user) {
        return new UserSummaryDto(user.getId(), user.getFirstName(), user.getLastName());
    }

    /**
     * Mapuje encję User na UserEmailDto (tylko ID i Email).
     */
    UserEmailDto toEmailDto(User user) {
        return new UserEmailDto(user.getId(),
                user.getEmail());
    }

    /**
     * Mapuje obiekt UserDto na encję User (potrzebne przy zapisie do bazy).
     */
    User toEntity(UserDto userDto) {
        return new User(userDto.firstName(),
                userDto.lastName(),
                userDto.birthdate(),
                userDto.email());
    }
}