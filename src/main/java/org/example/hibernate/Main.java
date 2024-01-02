package org.example.hibernate;

import org.example.hibernate.Dao.GenericDaoImpl;
import org.example.hibernate.Utility.SessionFactoryManager;
import org.example.hibernate.Utility.SessionManager;
import org.example.hibernate.Model.Forecast;
import org.example.hibernate.Model.Weather;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        SessionManager.openSession();

        GenericDaoImpl<Weather> weatherGenericDao = new GenericDaoImpl<>(Weather.class);
        GenericDaoImpl<Forecast> forecastGenericDao = new GenericDaoImpl<>(Forecast.class);

        System.out.println("Weather by ID (3):");
        Optional<Weather> weatherById = weatherGenericDao.getById(3);
        weatherById.ifPresent(System.out::println);

        System.out.println("Add forecast (f0):");
        forecastGenericDao.save(new Forecast("f0", LocalDate.now()));
        System.out.println("Forecast f0 added successfully!");

        System.out.println("\nSave weather (w0 and w01) for forecast (f0):");
        Optional<Forecast> forecast = forecastGenericDao.getByCode("f0");
        forecast.ifPresent(value -> weatherGenericDao.save(new Weather("w0", LocalTime.now(), false, 3, 20, 50, 1000, 1010, value)));
        forecast.ifPresent(value -> weatherGenericDao.save(new Weather("w01", LocalTime.now(), false, 4, 22, 52, 1002, 1011, value)));
        System.out.println("Weather w0 and w01 were saved successfully!");

        System.out.println("\nWeather by Code (w0):");
        Optional<Weather> savedWeather = weatherGenericDao.getByCode("w0");
        savedWeather.ifPresent(System.out::println);

        System.out.println("Update rain for weather (w0):");
        Optional<Weather> oldWeather = weatherGenericDao.getByCode("w0");
        oldWeather.ifPresent(value -> {
            value.setRaining(true);
            weatherGenericDao.update(value);
        });
        Optional<Weather> updatedWeather = weatherGenericDao.getByCode("w0");
        updatedWeather.ifPresent(System.out::println);

        System.out.println("Delete weather (w0):");
        Optional<Weather> deleteWeather = weatherGenericDao.getByCode("w0");
        deleteWeather.ifPresent(weatherGenericDao::delete);
        System.out.println("Weather w0 was deleted successfully!");

        System.out.println("\nDelete forecast (f0):");
        Optional<Forecast> deleteForecast = forecastGenericDao.getByCode("f0");
        deleteForecast.ifPresent(forecastGenericDao::delete);
        System.out.println("Forecast f0 and its children were deleted successfully!");

        System.out.println("\nAll forecasts:");
        forecastGenericDao.getAll().forEach(System.out::println);

        System.out.println("\nAll weathers:");
        weatherGenericDao.getAll().forEach(System.out::println);

        SessionManager.closeSession();
        SessionFactoryManager.shutdownFactory();
    }
}
