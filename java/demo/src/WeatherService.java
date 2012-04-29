import java.util.LinkedList;

/**
 * Created by IntelliJ IDEA.
 * User: Admin
 * Date: 27.04.12
 * Time: 13:26
 * To change this template use File | Settings | File Templates.
 */
public class WeatherService {
    public static String[] getCities(){
        LinkedList<CityWeather> cityWeatherList = CityList.getInstance();
        int count = cityWeatherList.size();
        String[] cities = new String[count-1];

        for (int i = 0; i < count-1; i++){
            cities[i] = cityWeatherList.get(i).getCity();
        }

        return cities;
    }

    public static String getWeather(String city){
        LinkedList<CityWeather> cityWeatherList = CityList.getInstance();
        int count = cityWeatherList.size();

        for (int i = 0; i < count; i++){
            if (cityWeatherList.get(i).getCity().equals(city)){
                return cityWeatherList.get(i).getWeather();
            }
        }
        throw new IllegalArgumentException("No such city on table: "+city);
    }
}
