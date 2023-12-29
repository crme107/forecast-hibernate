package org.example.hibernate;

import org.example.hibernate.Dao.GenericDaoImpl;
import org.example.hibernate.Utility.SessionFactoryManager;
import org.example.hibernate.Utility.SessionManager;
import org.example.hibernate.Model.Forecast;
import org.example.hibernate.Model.Weather;

import java.time.LocalTime;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        SessionManager.openSession();

        GenericDaoImpl<Weather> weatherGenericDao = new GenericDaoImpl<>(Weather.class);
        GenericDaoImpl<Forecast> forecastGenericDao = new GenericDaoImpl<>(Forecast.class);

        System.out.println("\nWeather by ID:");
        Optional<Weather> weatherById = weatherGenericDao.getById(3);
        weatherById.ifPresent(System.out::println);

        System.out.println("\nWeather by Code:");
        Optional<Weather> weatherByCode = weatherGenericDao.getByCode("w1");
        weatherByCode.ifPresent(System.out::println);

        System.out.println("\nAll weathers:");
        weatherGenericDao.getAll().forEach(System.out::println);

        System.out.println("\nSave weather:");
        Optional<Forecast> forecast = forecastGenericDao.getById(1);
        forecast.ifPresent(value -> weatherGenericDao.save(new Weather("w0", LocalTime.now(), false, 3, 20, 50, 1000, 1010, value)));
        Optional<Weather> savedWeather = weatherGenericDao.getByCode("w0");
        savedWeather.ifPresent(System.out::println);

        System.out.println("\nUpdate weather:");
        Optional<Weather> oldWeather = weatherGenericDao.getByCode("w0");
        oldWeather.ifPresent(value -> {
            value.setRaining(true);
            weatherGenericDao.update(value);
        });
        Optional<Weather> updatedWeather = weatherGenericDao.getByCode("w0");
        updatedWeather.ifPresent(System.out::println);

        System.out.println("\nDelete weather:");
        Optional<Weather> deleteWeather = weatherGenericDao.getByCode("w0");
        deleteWeather.ifPresent(weatherGenericDao::delete);
        System.out.println("Weather was deleted successfully!");

        System.out.println("\nDelete weather:");
        Optional<Forecast> deleteForecast = forecastGenericDao.getByCode("a1");
        deleteForecast.ifPresent(forecastGenericDao::delete);
        System.out.println("Forecast was deleted successfully!");

        SessionManager.closeSession();
        SessionFactoryManager.shutdownFactory();
    }
}
