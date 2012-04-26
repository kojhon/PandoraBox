/**
 * Created by IntelliJ IDEA.
 * User: Admin
 * Date: 24.04.12
 * Time: 15:41
 * To change this template use File | Settings | File Templates.
 */
public class ServerDemo {
    public static void main(String[] argv) {
        CityList.add("Новосибирск", "+15");
        CityList.add("Кемерово", "+20");
        CityList.add("Омск", "+1");
        JavaUI ui = new JavaUI();
        ui.initialize();
    }
}
