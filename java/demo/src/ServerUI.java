import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by IntelliJ IDEA.
 * User: Admin
 * Date: 24.04.12
 * Time: 15:36
 * To change this template use File | Settings | File Templates.
 */
public class ServerUI extends JFrame {
    JTable t;

    public void initialize(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setSize(400,CityList.getInstance().size()*20+20+20+25);
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
        insertButton.addActionListener(new AddActionListener());
        deleteButton.addActionListener(new DeleteActionListener());
        buttonPanel.add(insertButton);
        this.add(buttonPanel);
        CityList.add("Введите город", "Введите погоду");
        t.repaint();
        this.setVisible(true);
    }

    private final class AddActionListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            CityList.add("Введите город", "Введите погоду");
            t.repaint();
        }
    }

    private final class DeleteActionListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            CityList.remove(t.getSelectedRow());
            t.repaint();
        }
    }

}
