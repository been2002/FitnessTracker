package pl.wsb.fitnesstracker.workoutsession;

import jakarta.persistence.*;
import pl.wsb.fitnesstracker.training.api.Training; // Upewnij się, że ten import pasuje do Twojej paczki Training
import java.time.LocalDateTime;

@Entity
@Table(name = "workout_session")
public class WorkoutSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "training_id", nullable = false)
    private Training training;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    @Column(name = "altitude")
    private Integer altitude;

    // Domyślny konstruktor wymagany przez JPA
    public WorkoutSession() {}

    // Tutaj możesz wygenerować Gettery i Settery (Alt+Insert w IntelliJ)
}