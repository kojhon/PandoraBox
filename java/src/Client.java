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
        Object[] ttv = new Object[7];
        int z = 10;
        ttv[0] = 10;
        ttv[1] = (Double) 10.25;
        Double ft = 10.85;
        ft.byteValue();
        ttv[2] = "blablabla";
        //ttv[3] = new
        int[] val = new int[5];
        for (int i = 0; i < 5; i++){
            val[i] = i;
        }
        ttv[3] = val;
        int[][] val2 = new int[5][5];
        for (int i = 0; i < 5; i++){
            for (int j = 0; j < 5; j++){
                val2[i][j] = i*10+j;
            }
        }
        ttv[4] = val2;

        Object[] tmp = new Object[5];
        tmp[0] = "byakbyak";
        tmp[1] = "zhdgfrlk";
        tmp[2] = "habrhabr";
        tmp[3] = "pokarqsad";
        tmp[4] = "asdzgwet";

        ttv[5] = tmp;

        boolean[] arr = new boolean[10];
        for(int i = 0; i < 10; i++){
            if (i%2 == 0){
                arr[i] = false;
            }else {
                arr[i] = true;
            }
        }

        ttv[6] = arr;

        /*float[][][] arr2 = new float[6][7][8];
        for (int i = 0; i < 6; i++){
            for (int j = 0; j < 7; j++){
                for (int k = 0; k < 8; k++){
                    arr2[i][j][k] = k * 100+j*10+i;
                }
            }
        }

        ttv[7] = arr2;*/
        /*PandoraBox.dubug_Object_printer dp = new PandoraBox.dubug_Object_printer();
        dp.print("my_function",ttv);*/
       /* String to_send = new String();
        Scanner in = new Scanner(System.in);
        System.out.println("Enter data which will be send.");
        to_send = in.nextLine();

        PandoraBox.PandoraBoxClient c = new PandoraBox.PandoraBoxClient("localhost",13002);
        String new_str;
        new_str = new String(to_send.getBytes("UTF-16"),"UTF-16");
        c.get_result(new_str);*/
        PandoraBoxClient client = new PandoraBoxClient("localhost",13002);
        try {
            client.getResult("my_function",ttv);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}


