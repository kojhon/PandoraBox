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

    public CityWeather(String _city, String _weather){
        this.city = _city;
        this.weather = _weather;
    }

    public String getCity(){
        return this.city;
    }

    public String getWeather(){
        return  this.weather;
    }
}