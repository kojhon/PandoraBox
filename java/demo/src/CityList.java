import java.util.LinkedList;

/**
 * Created by IntelliJ IDEA.
 * User: Admin
 * Date: 23.04.12
 * Time: 19:11
 * To change this template use File | Settings | File Templates.
 */
public class CityList {
    private static LinkedList cities = new LinkedList();

    private CityList(){

    }

    public static LinkedList getInstance(){
        return  cities;
    }

    public static void add(String city, String weather){
        CityWeather additionalElement = new CityWeather(city,weather);
        cities.add(additionalElement);
    }

    public static void remove(int index){
        cities.remove(index);
    }


}
