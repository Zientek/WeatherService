package pl.akademiakodu.models.database.dao;

import pl.akademiakodu.models.WeatherModel;

import java.util.List;

public interface WeatherDao {
    void saveWeather(WeatherModel model);
    List<WeatherModel> loadWeather(String city);
    List<WeatherModel> loadWeather();
    List<WeatherModel> loadWeather(float temp);
    void deleteWeather(String city);




}
