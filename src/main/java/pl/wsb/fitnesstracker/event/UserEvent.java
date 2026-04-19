package pl.wsb.fitnesstracker.event;

import jakarta.persistence.*;

@Entity
@Table(name = "user_event")
public class UserEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Dodajemy brakujące kolumny, o które pluje się test
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "event_id")
    private Long eventId;
}