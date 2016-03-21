package net.krishlogic.wakemeup.service;

import net.krishlogic.wakemeup.model.Weather;

import java.util.List;

/**
 * Created by kvenkat on 3/16/16.
 */
public class WeatherService {

    private static volatile WeatherService mInstance;

    private WeatherService() {

    }

    public static synchronized WeatherService getInstance() {

        if (mInstance == null) {
            mInstance = new WeatherService();
        }

        return mInstance;
    }

    public List<Weather> getWeather() {



        return null;
    }
}
