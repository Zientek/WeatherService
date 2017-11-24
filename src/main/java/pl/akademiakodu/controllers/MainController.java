package pl.akademiakodu.controllers;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCharacterCombination;
import javafx.scene.input.MouseEvent;
import pl.akademiakodu.models.WeatherModel;
import pl.akademiakodu.models.database.DatabaseConnector;
import pl.akademiakodu.models.database.dao.WeatherDao;
import pl.akademiakodu.models.database.dao.impl.WeatherDaoImpl;
import pl.akademiakodu.models.services.WeatherData;
import pl.akademiakodu.models.services.WeatherObserver;
import pl.akademiakodu.models.services.WeatherService;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class MainController implements Initializable, WeatherObserver {


    @FXML
    Button buttonShowWeather;

    @FXML
    TextField textFieldCityName;

    @FXML
    Label labelWeatherInfo;

    @FXML
    Button buttonDeleteWeather;

    private WeatherDao weatherDao = new WeatherDaoImpl();
    private WeatherService weatherService = WeatherService.getService();

        @Override
    public void initialize(URL location, ResourceBundle resources) {
        weatherService.registerObserver(this);
        buttonShowWeather.setOnMouseClicked(e -> showWeather());
        buttonDeleteWeather.setOnMouseClicked(e-> weatherDao.deleteWeather(textFieldCityName.getText()));
        }

    private void showWeather() {
        weatherService.makeCall(textFieldCityName.getText());
    }

    private void showWeatherInView(){
    }

    @Override
    public void onWeatherUpdate(WeatherData data) {
        Platform.runLater(()->
        labelWeatherInfo.setText("Temperatura: " + (data.getTemp() - 273) + " C"
                +"\n Zachmurzenie: " + data.getClouds()+ "%"
                +"\n Cisnienie: " + data.getPressure()+ " hPa"
                +"\n Wilgotność: " + data.getHumidity()+ "%"));



        WeatherModel model = new WeatherModel(0, data.getCity(), data.getTemp()- 273);
        weatherDao.saveWeather(model);


    }
}


// WeatherModel weatherModel = new WeatherModel(0,"Kraków",5f);
//  weatherDao.saveWeather(weatherModel);
//        for (WeatherModel weatherModel : weatherDao.loadWeather()) {
//            System.out.println(weatherModel.toString());
//        }
//
//        weatherService.makeCall("Cracow");
