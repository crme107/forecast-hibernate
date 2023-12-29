package org.example.hibernate.Model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "forecast")
public class Forecast {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    private LocalDate date;

    public Forecast(String code, LocalDate date) {
        this.code = code;
        this.date = date;
    }

    @Override
    public String toString() {
        return code + ") " + date;
    }
}
