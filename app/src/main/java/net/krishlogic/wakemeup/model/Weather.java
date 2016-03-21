package net.krishlogic.wakemeup.model;

import java.util.Date;

/**
 * Created by kvenkat on 3/16/16.
 */
public class Weather {

    private String city;
    private String tempCelcius;
    private String tempFarenheit;
    private Date date;
    private String wind;
    private String pressure;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTempCelcius() {
        return tempCelcius;
    }

    public void setTempCelcius(String tempCelcius) {
        this.tempCelcius = tempCelcius;
    }

    public String getTempFarenheit() {
        return tempFarenheit;
    }

    public void setTempFarenheit(String tempFarenheit) {
        this.tempFarenheit = tempFarenheit;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getWind() {
        return wind;
    }

    public void setWind(String wind) {
        this.wind = wind;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }
}
