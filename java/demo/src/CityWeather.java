/**
 * Created by IntelliJ IDEA.
 * User: Admin
 * Date: 24.04.12
 * Time: 15:46
 * To change this template use File | Settings | File Templates.
 */
class CityWeather{

    private String city;
    private String weather;

    public CityWeather(String city, String weather){
        this.city = city;
        this.weather = weather;
    }

    public String getCity(){
        return city;
    }

    public String getWeather(){
        return weather;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }
}