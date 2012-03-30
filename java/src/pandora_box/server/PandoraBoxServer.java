package pandora_box.server;

import pandora_box.utils.DebugObjectPrinter;
import pandora_box.protocol.Decoder;
import pandora_box.protocol.Encoder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class PandoraBoxServer {

    //private final Decoder decoder = new Decoder();
    //private final Encoder encoder = new Encoder();

    private final Integer serverPort;

    HashMap methodsData = new HashMap();//Contain information about methods. Method shortname - key, and array os data - value. Array contain
                                        //namespace and class (first element), full method name (second element) and object for nonstatic methods
                                        //(third element)
    static final boolean ERROR = true;
    static final boolean NOERROR = false;

    public PandoraBoxServer(Integer port) {
        this.serverPort = port;
    }


    public void start() throws IOException, InterruptedException {
        ServerSocket server;
        Socket client_sock;
        server = new ServerSocket(this.serverPort);
        System.out.printf("Server is start up at 127.0.0.1: %d\n",serverPort);
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
            Object[] data = Decoder.decodeRequest(client_sock.getInputStream());
            DebugObjectPrinter dop = new DebugObjectPrinter();
            System.out.println("RECIVED DATA:");
            dop.print(data);
            OutputStream out = client_sock.getOutputStream();
            Object[] res;
            res = invoke(data);
            System.out.println("SENDED DATA:");
            dop.print(res);
            ByteArrayOutputStream msg = Encoder.encodeResult(res);
            out.write(msg.toByteArray());
            out.flush();
            out.close();
            client_sock.close();
        }


    }


    public void publicateStaticMethod(String shortName, String className, String fullName){
        Object[] methodData = new Object[2];
        methodData[0] = className;
        methodData[1] = fullName;
        methodsData.put(shortName,methodData);
    }

    public void publicateNonstaticMethod(String shortName, String className, String fullName, Object object){
        Object[] methodData = new Object[3];
        methodData[0] = className;
        methodData[1] = fullName;
        methodData[2] = object;
        methodsData.put(shortName,methodData);
    }

    private Object[] invoke(Object[] argv){

        String shortName = (String)argv[0];
        Object[] result = new Object[2];
        int argc = argv.length-1;
        Object params[] = new Object[argc];
        Class paramTypes[] = new Class[argc];
        for (int i = 0; i < argc; i++){
            params[i] = argv[i+1];
            paramTypes[i] = argv[i+1].getClass();
        }
        if (methodsData.containsKey(shortName)){
            Object methodData[] = (Object[])methodsData.get(shortName);
            int dataCount = methodData.length;
            try{
                Method invokingMethod = Class.forName((String)methodData[0]).getMethod((String)methodData[1],paramTypes);
                if (dataCount == 2){
                    result[0] = NOERROR;
                    result[1] = invokingMethod.invoke(null,params);
                }else{
                    result[0] = NOERROR;
                    result[1] = invokingMethod.invoke(methodData[2],params);
                }
            }catch (ClassNotFoundException e){
                result[0] = ERROR;
                result[1] = "Error of publication. Please refer to the publisher."+e.getMessage();
            }
            catch (IllegalAccessException e){
                result[0] = ERROR;
                result[1] = "Error of publication. Please refer to the publisher."+e.getMessage();
            }
            catch (NoSuchMethodException e){
                result[0] = ERROR;
                result[1] = "Wrong parameters number or type."+e.getMessage();
            }
            catch (InvocationTargetException e){
                result[0] = ERROR;
                result[1] = "Method error:"+e.getMessage()+".Please refer to the publisher."+e.getMessage();
            }
        }
        else{
            result[0] = ERROR;
            result[1] = "No such publicated function.";
        }
        return result;
    }
}




