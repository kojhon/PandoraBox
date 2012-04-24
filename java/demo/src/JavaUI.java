import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: Admin
 * Date: 24.04.12
 * Time: 15:36
 * To change this template use File | Settings | File Templates.
 */
public class JavaUI extends JFrame{
    public void initialize(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setSize(400,CityList.getInstance().size()*20+20+25);
        this.setLayout(new BorderLayout());
        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new BorderLayout());
        tablePanel.add(new JTable(new WeatherTableModel()),BorderLayout.CENTER);
        this.add(tablePanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        //buttonPanel.setSize(100,10);
        buttonPanel.setLayout(new GridLayout());
        //buttonPanel.setSize(400,10);
        buttonPanel.add(new JButton("Удалить"));
        buttonPanel.add(new JButton("Вставить"));
        this.add(buttonPanel);

        this.setVisible(true);
    }
}
