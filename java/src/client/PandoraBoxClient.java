package PandoraBox; /**
 * Created by IntelliJ IDEA.
 * User: Admin
 * Date: 09.02.12
 * Time: 19:33
 * To change this template use File | Settings | File Templates.
 */

import java.io.*;
import java.net.Socket;
//import PandoraBox.Protocol.*;

/**
 * Created by IntelliJ IDEA.
 * User: Admin
 * Date: 02.12.11
 * Time: 14:13
 * To change this template use File | Settings | File Templates.
 */
public class PandoraBoxClient{
    private String server_adress = null;
    private int server_port = 0;


    public PandoraBoxClient(String adress,int port){
       this.server_adress = adress;
       this.server_port = port;
    }

    public void get_result(String function_name,Object ... argv) throws IOException {
        try{
            ByteArrayOutputStream msg;
            protocolEncoder pc = new protocolEncoder();
            msg = pc.encodeData(function_name,argv);
            dubug_Object_printer dop = new dubug_Object_printer();
            System.out.println("Connection to server at "+server_adress+":"+((Integer)server_port).toString());
            System.out.println("SENDED DATA:");
            dop.print(function_name,argv);
            Socket client_sock = new Socket(this.server_adress,this.server_port);
            OutputStream out = client_sock.getOutputStream();

            out.write(msg.toByteArray());
            out.flush();

            protocolDecoder pe = new protocolDecoder();
            Object[] result = pe.decodeData(client_sock.getInputStream());
            System.out.println("RECIVED DATA:");
            dop.print(result);
            client_sock.close();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }

    }
}




