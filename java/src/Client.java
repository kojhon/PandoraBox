/**
 * Created by IntelliJ IDEA.
 * User: Admin
 * Date: 20.11.11
 * Time: 17:51
 * To change this template use File | Settings | File Templates.
 */

import pandora_box.client.*;

import java.io.*;

public class Client {
    public static void main(String[] args) throws IOException {
        int array[][] = new int[5][5];
        for (int i = 0; i < 5; i++){
            for (int j = 0; j < 5; j++){
                array[i][j] = i*10+j;
            }
        }
        PandoraBoxClient client = new PandoraBoxClient("localhost",13002);
        try {
            client.getResult("conc","blablabla","yourbeyourbe");
            client.getResult("getMax",array);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}


