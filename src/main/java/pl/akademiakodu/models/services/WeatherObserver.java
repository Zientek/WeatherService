package pl.akademiakodu.models.services;

public interface WeatherObserver {
    void onWeatherUpdate(WeatherData data);
}
