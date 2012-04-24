import com.sun.xml.internal.bind.v2.runtime.reflect.Lister;

import javax.swing.table.TableModel;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelListener;
import javax.xml.transform.sax.SAXTransformerFactory;

/**
 * Created by IntelliJ IDEA.
 * User: Admin
 * Date: 23.04.12
 * Time: 18:41
 * To change this template use File | Settings | File Templates.
 */
public class WeatherTableModel implements TableModel{
    public void addTableModelListener(TableModelListener l){

    }

    public void removeTableModelListener(TableModelListener l){

    }

    public Object getValueAt(int rowIndex, int columnIndex){
        if (rowIndex < CityList.getInstance().size()){
            CityWeather element = (CityWeather)CityList.getInstance().get(rowIndex);
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

    }

    public String getColumnName(int i){
        switch (i){
            case(0):
                return "Город";
            case(1):
                return "Погода";
            default:
                return "ololo";
        }
    }

    public int getColumnCount(){
        return 2;
    }

    public int getRowCount(){
        return CityList.getInstance().size()+1;
    }

    public boolean isCellEditable(int rowIndex,int columnIndex){
        if (rowIndex == CityList.getInstance().size()){
            return true;
        }
        return false;
    }

}
