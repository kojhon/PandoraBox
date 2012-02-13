package pandora_box.server;

import pandora_box.utils.DebugObjectPrinter;
import pandora_box.protocol.Decoder;
import pandora_box.protocol.Encoder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class PandoraBoxServer {

    private final Decoder decoder = new Decoder();
    private final Encoder encoder = new Encoder();

    private final Integer serverPort;

    public PandoraBoxServer(Integer port) {
        this.serverPort = port;
    }


    public void start() throws IOException, InterruptedException {
        ServerSocket server;
        Socket client_sock;

        server = new ServerSocket(this.serverPort);
        System.out.printf("Server is start up at 127.0.0.1: %d\n" + serverPort);
        while (true) {
            System.out.println();
            System.out.println("Waiting for client");
            try {
                client_sock = server.accept();
            } catch (IOException e) {
                System.out.println("Accept error");
                continue;
            }
            System.out.println("Client connected");
            Object[] data = decoder.decodeData(client_sock.getInputStream());
            DebugObjectPrinter dop = new DebugObjectPrinter();
            System.out.println("RECIVED DATA:");
            dop.print(data);
            OutputStream out = client_sock.getOutputStream();
            Object[] res;
            res = function_calling(data);
            System.out.println("SENDED DATA:");
            dop.print(res);
            ByteArrayOutputStream msg = encoder.encodeData(res);
            out.write(msg.toByteArray());
            out.flush();
            out.close();
            client_sock.close();
        }


    }

    private Object[] function_calling(Object[] data) {
        return data;
    }
}




