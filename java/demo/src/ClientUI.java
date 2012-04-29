import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.Book;
import java.lang.reflect.Array;
import java.util.LinkedList;

import com.sun.security.ntlm.Client;
import pandora_box.client.*;
import pandora_box.protocol.Decoder;
import pandora_box.protocol.Encoder;

/**
 * Created by IntelliJ IDEA.
 * User: Admin
 * Date: 27.04.12
 * Time: 13:38
 * To change this template use File | Settings | File Templates.
 */
public class ClientUI extends JFrame implements ActionListener{
    JTextField portField;
    JTextField adressField;
    PandoraBoxClient client;
    String[] cities;
    JLabel weatherLabel;
    JComboBox<String> cityList;
    JPanel libPanel;
    JPanel weatherPanel;


    public void initialize(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(200,120);
        this.setResizable(false);
        libPanel = new JPanel(new GridLayout(3,2));
        libPanel.add(new JLabel("Введите порт"));
        portField = new JTextField();
        portField.setSize(100,20);
        libPanel.add(portField);
        libPanel.add(new JLabel("Введите адресс"));
        adressField = new JTextField();
        adressField.setSize(100,20);
        libPanel.add(adressField);
        JButton OKButton = new JButton("OK");
        OKButton.addActionListener(this);
        libPanel.add(OKButton);
        this.add(libPanel);

        this.setVisible(true);
    }

    public void form2Start(){
        this.setVisible(false);
        this.remove(libPanel);
        //libPanel.setVisible(false);
        cityList = new JComboBox(cities);
        weatherLabel = new JLabel("");
        JPanel weatherPanel = new JPanel(new GridLayout(3,1));
        weatherPanel.add(cityList);
        JButton getWeatherButton = new JButton("Узнать");
        getWeatherButton.addActionListener(new WeatherFormActionListener());
        weatherPanel.add(getWeatherButton);
        weatherPanel.add(weatherLabel);
        this.add(weatherPanel);
        this.setVisible(true);
        this.repaint();
    }

    public void actionPerformed(ActionEvent e) {
        Object[] res;
        try{
            client = new PandoraBoxClient(adressField.getText(),Integer.parseInt(portField.getText()));
            res = client.getResult("getCities");

        }catch (Exception ex){
            res = new Object[2];
            res[0] = true;
            res[1] = "Uncorect input data: port should be a number";
        }
        if ((Boolean)res[0]){
            JOptionPane.showMessageDialog(null,(String)res[1]);
        }else{
            Object tmp = res[1];
            int length = Array.getLength(tmp);
            cities = new String[length];
            cities = (String[])tmp;
            this.form2Start();
        }
    }


     class WeatherFormActionListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            try{
                Object[] res = client.getResult("getWeather",cityList.getSelectedItem());
                if ((Boolean)res[0]){
                    JOptionPane.showMessageDialog(null,(String)res[1]);
                }else{
                    weatherLabel.setText((String)res[1]);
                    weatherLabel.repaint();
                }
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }
}


class LibForm extends  JFrame{
    JTextField portField;
    JTextField adressField;
    PandoraBoxClient client;
    String[] cities;

    public void initializeLibForm(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(200,120);
        this.setResizable(false);
        JPanel fieldsPanel = new JPanel(new GridLayout(3,2));
        fieldsPanel.add(new JLabel("Введите порт"));
        portField = new JTextField();
        portField.setSize(100,20);
        fieldsPanel.add(portField);
        fieldsPanel.add(new JLabel("Введите адресс"));
        adressField = new JTextField();
        adressField.setSize(100,20);
        fieldsPanel.add(adressField);
        JButton OKButton = new JButton("OK");
        OKButton.addActionListener(new libInitialiseActionListener());
        //JPanel buttonPanel = new JPanel(new BorderLayout());
        //buttonPanel.add(OKButton,BorderLayout.CENTER);
        fieldsPanel.add(OKButton);
        this.add(fieldsPanel);
        //this.add(buttonPanel);
        this.setVisible(true);



    }

    class libInitialiseActionListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            Object[] res;
            try{
                client = new PandoraBoxClient(adressField.getText(),Integer.parseInt(portField.getText()));
                res = client.getResult("getCities");

            }catch (Exception ex){
                res = new Object[2];
                res[0] = true;
                res[1] = "Uncorect input data: port should be a number";
            }
            if ((Boolean)res[0]){
                JOptionPane.showMessageDialog(null,(String)res[1]);
            }else{
                Object tmp = res[1];
                int length = Array.getLength(tmp);
                cities = new String[length];
                cities = (String[])tmp;
            }
        }
    }

    public PandoraBoxClient getClient(){
        return this.client;
    }

    public String[] getCities(){
        return this.cities;
    }
}

class WeatherForm extends JFrame{
    PandoraBoxClient client;
    String[] cities;
    JLabel weatherLabel;
    JComboBox<String> cityList;

    public WeatherForm(PandoraBoxClient _client, String[] _cities){
        client = _client;
        cities = _cities;
    }

    public void initializeWeatherForm(){
        this.setVisible(false);
        this.removeAll();
        cityList = new JComboBox(cities);
        weatherLabel = new JLabel("");
        JPanel weatherPanel = new JPanel(new GridLayout(3,1));
        weatherPanel.add(cityList);
        JButton getWeatherButton = new JButton("Узнать");
        getWeatherButton.addActionListener(new WeatherFormActionListener());
        weatherPanel.add(getWeatherButton);
        weatherPanel.add(weatherLabel);
        this.setVisible(true);
    }

    class WeatherFormActionListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            try{
                Object[] res = client.getResult("getWeather",cityList.getSelectedItem());
                if ((Boolean)res[0]){
                    JOptionPane.showMessageDialog(null,(String)res[1]);
                }else{
                    weatherLabel.setText((String)res[1]);
                    weatherLabel.repaint();
                }
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }
}
