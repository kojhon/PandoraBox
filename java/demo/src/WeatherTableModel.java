import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

/**
 * Created by IntelliJ IDEA.
 * User: Admin
 * Date: 23.04.12
 * Time: 18:41
 * To change this template use File | Settings | File Templates.
 */
public class WeatherTableModel implements TableModel{

    public Object getValueAt(int rowIndex, int columnIndex){
        if (rowIndex < CityList.getInstance().size()){
            CityWeather element = CityList.getInstance().get(rowIndex);
            if (columnIndex == 0){
                return element.getCity();
            }else{
                return element.getWeather();
            }
        }
        return null;
    }

    public Class getColumnClass(int columnIndex){
        return String.class;
    }

    public void setValueAt(Object value, int rowIndex, int columnIndex){
        final CityWeather cityWeather = CityList.getInstance().get(rowIndex);
        if (columnIndex == 0){
            cityWeather.setCity((String) value);
        }else{
            cityWeather.setWeather((String)value);
        }
    }

    public String getColumnName(int i){
        switch (i){
            case(0):
                return "Город";
            case(1):
                return "Погода";
            default:
                throw new IllegalArgumentException("Wrong count of columns:"+i);
        }
    }

    public int getColumnCount(){
        return 2;
    }

    public int getRowCount(){
        return CityList.getInstance().size();
    }

    public boolean isCellEditable(int rowIndex,int columnIndex){
        return true;
    }

    public void addTableModelListener(TableModelListener l){
    }

    public void removeTableModelListener(TableModelListener l){
    }

}
