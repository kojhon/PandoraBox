package pandora_box.client;

import pandora_box.utils.DebugObjectPrinter;
import pandora_box.protocol.Decoder;
import pandora_box.protocol.Encoder;

import java.io.*;
import java.net.Socket;

public class PandoraBoxClient {

    private final Encoder encoder = new Encoder();
    private final Decoder decoder = new Decoder();

    private final String serverIp;
    private final int serverPort;

    public PandoraBoxClient(String adress, int port) {
        this.serverIp = adress;
        this.serverPort = port;
    }

    public void getResult(String function_name, Object... argv) throws IOException {
        Socket socket = null;
        try {
            ByteArrayOutputStream msg;
            msg = encoder.encodeData(function_name, argv);
            DebugObjectPrinter dop = new DebugObjectPrinter();
            System.out.printf("Connection to server at %s:%d\n", serverIp, serverPort);
            System.out.println("SENDED DATA:");
            dop.print(function_name, argv);
            socket = new Socket(this.serverIp, this.serverPort);
            OutputStream out = socket.getOutputStream();

            out.write(msg.toByteArray());
            out.flush();

            Object[] result = decoder.decodeData(socket.getInputStream());
            System.out.println("RECIVED DATA:");
            dop.print(result);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (socket != null) {
                socket.close();
            }
        }
    }
}




