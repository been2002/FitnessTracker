package pl.wsb.fitnesstracker.healthmetrics.internal;

import jakarta.persistence.Entity;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "Health_Metrics")
public class HealthMetrics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    @Column(nullable = false)
    private LocalDate date;

    @Column(precision = 5, scale = 2)
    private BigDecimal weight;

    private Integer height;

    private Integer heartRate;

}
