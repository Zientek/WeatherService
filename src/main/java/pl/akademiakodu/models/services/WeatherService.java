package pl.akademiakodu.models.services;

import org.json.JSONObject;
import pl.akademiakodu.models.utils.Config;
import pl.akademiakodu.models.utils.HttpUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WeatherService {
    private static WeatherService ourInstance = new WeatherService();
    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    public static WeatherService getService() {
        return ourInstance;
    }

    private List<WeatherObserver> observers;

    private WeatherService() {
        observers = new ArrayList<>();
    }

    public void registerObserver(WeatherObserver observer){
        observers.add(observer);
    }

    private  void notifyObservers(WeatherData data){
        for (WeatherObserver observer : observers) {
            observer.onWeatherUpdate(data);
        }
    }

    public void makeCall(String city) {
        executorService.execute(() -> {
            parseJsonData(HttpUtils.makeHttpRequest(Config.APP_URL + city + "&appid=" + Config.APP_ID));
        });
    }

    private void parseJsonData(String text){
        JSONObject root = new JSONObject(text);
        JSONObject main = root.getJSONObject("main");
        JSONObject clouds = root.getJSONObject("clouds");

        int temp = main.getInt("temp");
        int cloudsAll = clouds.getInt("all");
        int pressure = main.getInt("pressure");
        int humidity = main.getInt("humidity");
        String name = root.getString("name");

        WeatherData data = new WeatherData();
        data.setTemp(temp);
        data.setClouds(cloudsAll);
        data.setHumidity(humidity);
        data.setPressure(pressure);
        data.setCity(name);
        notifyObservers(data);
    }
}
