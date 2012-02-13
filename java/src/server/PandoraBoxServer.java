package PandoraBox; /**
 * Created by IntelliJ IDEA.
 * User: Admin
 * Date: 02.12.11
 * Time: 14:12
 * To change this template use File | Settings | File Templates.
 */



import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import PandoraBox.protocolDecoder;
import PandoraBox.protocolEncoder;

public class PandoraBoxServer{
    Integer server_port;
    public PandoraBoxServer(Integer port){
        this.server_port = port;
    }


    public void start() throws IOException, InterruptedException {
        ServerSocket  server;
        Socket client_sock;

        try{
            server = new ServerSocket(this.server_port);
        }catch (IOException e) {
            System.out.print("Couldn't listen to port ");
            System.out.println(server_port);
            return;
        }
        System.out.println("Server is start up at 127.0.0.1:"+((Integer)server_port).toString());
        while (true){
            System.out.println();
            System.out.println("Waiting for client");
            try{
                client_sock = server.accept();
            } catch (IOException e) {
                System.out.println("Accept error");
                return;
            }
            System.out.println("Client connected");
            protocolDecoder pe = new protocolDecoder();
            Object[] data = pe.decodeData(client_sock.getInputStream());
            dubug_Object_printer dop = new dubug_Object_printer();
            System.out.println("RECIVED DATA:");
            dop.print(data);
            OutputStream out = client_sock.getOutputStream();
            Object[] res;
            res = function_calling(data);
            System.out.println("SENDED DATA:");
            dop.print(res);
            protocolEncoder pc = new protocolEncoder();
            ByteArrayOutputStream msg = pc.encodeData(res);
            out.write(msg.toByteArray());
            out.flush();
            out.close();
            client_sock.close();
            /*System.out.println(count);
            for (int i = 0; i < count; i++){
                System.out.println(data[i].toString());
            }
            System.out.println("asdasd");
            */
        }



    }

    private Object[] function_calling(Object[] data){

        return data;

    }
}




