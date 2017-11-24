package pl.akademiakodu.models.database.dao.impl;

import pl.akademiakodu.models.WeatherModel;
import pl.akademiakodu.models.database.DatabaseConnector;
import pl.akademiakodu.models.database.dao.WeatherDao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class WeatherDaoImpl implements WeatherDao {

    private DatabaseConnector databaseConnector = DatabaseConnector.getInstance();

    @Override
    public void saveWeather(WeatherModel model) {
        PreparedStatement statement = databaseConnector.createStatment
                ("INSERT INTO weather(cityname, temp) VALUES(?, ?);");
        try {
            statement.setString(1, model.getCity());
            statement.setFloat(2, model.getTemp());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<WeatherModel> loadWeather(String city) {
        List<WeatherModel> weatherModels = new ArrayList<WeatherModel>();
        PreparedStatement preparedStatement = databaseConnector.createStatment("SELECT * FROM weather WHERE cityname = ?");

        try {
            preparedStatement.setString(1, city);
            ResultSet set = preparedStatement.executeQuery();
            createModels(weatherModels, set);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return weatherModels;
    }
    @Override
    public List<WeatherModel> loadWeather(){
        List<WeatherModel> weatherModels = new ArrayList<WeatherModel>();
        PreparedStatement preparedStatement = databaseConnector.createStatment("SELECT * FROM weather");

        try {
            ResultSet set = preparedStatement.executeQuery();
            createModels(weatherModels, set);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //List<Float> tempList = weatherModels.stream().map(s-> (s.getTemp())).collect(Collectors.toList());
        return weatherModels;
    }

    @Override
    public List<WeatherModel> loadWeather(float temp) {
        List<WeatherModel> weatherModels = new ArrayList<WeatherModel>();
        PreparedStatement preparedStatement = databaseConnector.createStatment("SELECT * FROM weather WHERE temp = ?");


        try {
            preparedStatement.setFloat(1, temp);
            ResultSet set = preparedStatement.executeQuery();
            createModels(weatherModels, set);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return weatherModels;
    }

    @Override
    public void deleteWeather(String city) {
        PreparedStatement statement = databaseConnector.createStatment("DELETE FROM weather WHERE cityname = ?");
        try {
            statement.setString(1,city);
            statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createModels(List<WeatherModel> weatherModels, ResultSet set) throws SQLException {
        WeatherModel model;
        while (set.next()){
            model = new WeatherModel(set.getInt("id"),
                    set.getString("cityname"),
                    set.getFloat("temp"));
            weatherModels.add(model);
        }
    }
}
