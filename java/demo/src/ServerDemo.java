/**
 * Created by IntelliJ IDEA.
 * User: Admin
 * Date: 24.04.12
 * Time: 15:41
 * To change this template use File | Settings | File Templates.
 */
import pandora_box.server.*;

public class ServerDemo {
    public static void main(String[] argv) {
        CityList.add("Новосибирск", "+15");
        CityList.add("Кемерово", "+20");
        CityList.add("Омск", "+1");
        ServerUI ui = new ServerUI();
        ui.initialize();
        PandoraBoxServer server = new PandoraBoxServer(10001);
        server.publicateStaticMethod("getCities","WeatherService","getCities");
        server.publicateStaticMethod("getWeather","WeatherService","getWeather");
        try{
            server.start();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
