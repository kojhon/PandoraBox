import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

/**
 * Created by IntelliJ IDEA.
 * User: Admin
 * Date: 24.04.12
 * Time: 15:36
 * To change this template use File | Settings | File Templates.
 */
public class JavaUI extends JFrame implements ActionListener{
    JTable t;

    public void initialize(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setSize(400,CityList.getInstance().size()*20+20+25);
        this.setLayout(new BorderLayout());
        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new BorderLayout());
        t = new JTable(new WeatherTableModel());
        tablePanel.add(t,BorderLayout.CENTER);
        this.add(tablePanel, BorderLayout.NORTH);


        JPanel buttonPanel = new JPanel();
        //buttonPanel.setSize(100,10);
        buttonPanel.setLayout(new GridLayout());
        //buttonPanel.setSize(400,10);
        JButton deleteButton = new JButton("Удалить");
        buttonPanel.add(deleteButton);
        JButton insertButton = new JButton("Вставить");
        insertButton.addActionListener(this);
        deleteButton.addActionListener(this);
        buttonPanel.add(insertButton);
        this.add(buttonPanel);

        this.setVisible(true);
    }

    public void actionPerformed(ActionEvent e){
        if (e.getActionCommand() == "Вставить"){
            int rowCount = CityList.getInstance().size();
            CityList.add((String)t.getValueAt(rowCount,0),(String)t.getValueAt(rowCount,1));
            t.setModel(new WeatherTableModel());
            t.repaint();
        }else{

        }
    }
}
