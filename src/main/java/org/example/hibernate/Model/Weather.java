package org.example.hibernate.Model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "weather")
public class Weather {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true, nullable = false)
    private String code;
    private LocalTime time;
    private boolean isRaining;
    private int windSpeed;
    private int temperature;
    private int cloudsPercentage;
    private long visibility;
    private int pressure;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "forecast_fk")
    private Forecast forecast;

    public Weather(String code, LocalTime time, boolean isRaining, int windSpeed, int temperature, int cloudsPercentage, long visibility, int pressure) {
        this.code = code;
        this.time = time;
        this.isRaining = isRaining;
        this.windSpeed = windSpeed;
        this.temperature = temperature;
        this.cloudsPercentage = cloudsPercentage;
        this.visibility = visibility;
        this.pressure = pressure;
    }

    public Weather(String code, LocalTime time, boolean isRaining, int windSpeed, int temperature, int cloudsPercentage, long visibility, int pressure, Forecast forecast) {
        this.code = code;
        this.time = time;
        this.isRaining = isRaining;
        this.windSpeed = windSpeed;
        this.temperature = temperature;
        this.cloudsPercentage = cloudsPercentage;
        this.visibility = visibility;
        this.pressure = pressure;
        this.forecast = forecast;
    }

    private String timeTo24HourFormat() {
        return time.format(DateTimeFormatter.ofPattern("HH:mm"));
    }

    @Override
    public String toString() {
        return "[ " + timeTo24HourFormat() + " ]"
                + "\nCode: [" + code + "]"
                + "\nTemperature: " + temperature + " Celsius"
                + "\nRaining: " + (isRaining ? "yes" : "no")
                + "\nClouds: " + cloudsPercentage + " %"
                + "\nWind: " + windSpeed + " m/s"
                + "\nVisibility: " + visibility + " m"
                + "\nPressure: " + pressure + " mBar"
                + "\nOwned by: [" + forecast.getCode() + "]\n";
    }
}
