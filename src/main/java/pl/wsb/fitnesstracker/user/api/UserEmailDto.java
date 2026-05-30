package pl.wsb.fitnesstracker.user.api;

/**
 * Record do wyszukiwania po mailu (tylko ID i email).
 */
public record UserEmailDto(Long id, String email) {
}