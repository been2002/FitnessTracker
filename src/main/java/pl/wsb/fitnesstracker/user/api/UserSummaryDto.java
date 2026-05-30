package pl.wsb.fitnesstracker.user.api;

/**
 * Record dla listy "simple" - testy wymagają oddzielnych pól firstName i lastName.
 */
public record UserSummaryDto(Long id, String firstName, String lastName) {
}