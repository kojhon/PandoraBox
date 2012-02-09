/**
 * Created by IntelliJ IDEA.
 * User: Admin
 * Date: 09.02.12
 * Time: 19:33
 * To change this template use File | Settings | File Templates.
 */
import com.sun.corba.se.spi.orbutil.fsm.Input;
import sun.io.ByteToCharUTF16;

import java.io.*;
import java.lang.reflect.Array;
import java.net.Socket;

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

class protocolEncoder{
    ByteArrayOutputStream stream;
    DataOutputStream writer;

    public  ByteArrayOutputStream encodeData(String function_name,Object ... argv) throws IOException {
        stream = new ByteArrayOutputStream();
        writer = new DataOutputStream(stream);
        writer.writeByte(function_name.length()*2);
        writer.writeChars(function_name);
        int argc = argv.length;
        writer.writeByte(argc);

        for(int i = 0; i < argc; i++){
            Object a = argv[i];
            int type = getType(a);
            writer.writeByte((byte) type);

            switch (type){
                case (1):
                    writer.writeByte(((Byte)argv[i]).byteValue());
                break;

                case (2):
                    if ((Boolean) argv[i]){
                        writer.writeByte((byte) 1);
                    }else{
                        writer.writeByte((byte)0);
                    }
                break;

                case(3):
                    writer.writeShort(((Short) argv[i]).shortValue());
                break;

                case(5):
                    writer.writeInt(((Integer) argv[i]).intValue());
                break;

                case(7):
                    writer.writeLong(((Long)argv[i]).longValue());
                break;

                case(9):
                    writer.writeFloat( ((Float)argv[i]).floatValue());
                break;

                case(10):
                    writer.writeDouble( ((Double)argv[i]).doubleValue());
                break;

                case(11):
                    writer.writeByte((((String)argv[i]).length()*2));
                    writer.writeChars(((String)argv[i]));
                break;

                case(12):
                    writer.writeByte(this.getArrayDimension(a));
                    Object tmp = a;
                    while (tmp.getClass().isArray()){
                        tmp = Array.get(tmp,0);
                    }
                    byte element_type = this.getType(tmp);
                    writer.writeByte(element_type);
                    this.arrayEncode(a,element_type);
                break;
            }
        }
        return stream;
    }

    private byte getArrayDimension(Object a){
        byte size = 0;

        while (a.getClass().isArray()){
            size++;
            a = Array.get(a,0);
        }

        return size;
    }

    private byte getType(Object a){

        if (a instanceof Byte){
            return 1;
        }

        if (a instanceof Boolean){
            return 2;
        }

        if (a instanceof Short){
            return 3;
        }

        if (a instanceof Integer){
            return 5;
        }

        if (a instanceof Long ){
            return 7;
        }

        if (a instanceof Float){
            return 9;
        }

        if (a instanceof Double){
            return 10;
        }

        if (a instanceof String){
            return 11;
        }

        if (a.getClass().isArray()){
            return 12;
        }

        throw new IllegalArgumentException("Unsupported class: " + a.getClass());

    }

    private void arrayEncode(Object a, byte type) throws  IOException{
        int length = Array.getLength(a);
        writer.writeByte(length);
        if (Array.get(a,0).getClass().isArray()){
            for (int i = 0; i < length; i++){
                arrayEncode(Array.get(a, i), type);
            }
        }else{
            switch (type){
                case (1):
                    for (int i = 0; i < length; i++){
                        writer.writeByte((Byte)Array.get(a,i));
                    }
                break;

                case (2):
                    for (int i = 0; i < length; i++){
                        if ((Boolean)Array.get(a,i)){
                            writer.writeByte(1);
                        }else{
                            writer.writeByte(0);
                        }
                    }
                    break;

                case (3):
                    for (int i = 0; i < length; i++){
                        writer.writeShort((Short)Array.get(a,i));
                    }
                    break;

                case (5):
                    for (int i = 0; i < length; i++){
                        writer.writeInt((Integer)Array.get(a,i));
                    }
                    break;

                case (7):
                    for (int i = 0; i < length; i++){
                        writer.writeLong((Long)Array.get(a,i));
                    }
                    break;

                case (9):
                    for (int i = 0; i < length; i++){
                        writer.writeFloat((Float)Array.get(a,i));
                    }
                    break;

                case (10):
                    for (int i = 0; i < length; i++){
                        writer.writeDouble((Double)Array.get(a,i));
                    }
                    break;

                case (11):
                    for (int i = 0; i < length; i++){
                        String tmp = (String)Array.get(a,i);
                        writer.writeByte(tmp.length()*2);
                        writer.writeChars(tmp);
                    }
                    break;
            }
        }
    }
}




class protocolDecoder{
    DataInputStream reader;
    public Object[] decodeData(InputStream stream) throws IOException{
        reader = new DataInputStream(stream);

        byte name_length = reader.readByte();
        byte tmp[] = new byte[name_length];
        reader.read(tmp,0,name_length);
        //reader.

        byte count = reader.readByte();
        Object data[] = new Object[count+1];
        data[0] = new String(tmp,0,name_length,"UTF-16BE");
        //System.out.print(data[0]);
        boolean errors = false;
        for (int i = 1; i < count+1 ;i++){

            byte flag = reader.readByte();

            if (flag == -1){
                return null;
            }

            switch (flag){

                case (1)://byte block
                    data[i] = reader.readByte();
                break;

                case (2)://boolean block
                    byte bool_value = reader.readByte();
                    if (bool_value == 1){
                        data[i] = true;
                    }else{
                        data[i] = false;
                    }
                break;

                case (3)://short block
                    data[i] = reader.readShort();
                break;

                case (5)://int block
                    data[i] = reader.readInt();
                break;

                case (7)://Long block
                    data[i] = reader.readLong();
                break;

                case (9)://float block
                    data[i] = reader.readFloat();
                break;

                case (10)://double block
                    data[i] = reader.readDouble();
                break;

                case (11):
                    int length = reader.readByte();
                    byte ntmp[] = new byte[length];
                    reader.read(ntmp,0,length);
                    data[i]=new String(ntmp,0,length,"UTF-16BE");
                break;

                case (12):
                    int dimention = (int)reader.readByte();
                    byte type = reader.readByte();
                    data[i]=decodeArray(dimention,type);
                break;

                default:
                    return null;

            }

        }

        return data;
    }

    private Object decodeArray(int dimention, byte type) throws  IOException{
        Byte length = reader.readByte();
        Object[] data = new Object[length];
        if (dimention != 1){
            for (int i = 0; i < length; i++){
                data[i] = decodeArray(dimention-1,type);
            }
        }else{
            switch (type){
                case (1):
                    for (int i = 0; i < length; i++){
                        data[i] = reader.readByte();
                    }
                break;

                case (2):
                    for (int i = 0; i < length; i++){
                        byte tmp = reader.readByte();
                        if (tmp == 1){
                            data[i] = true;
                        }else{
                            data[i]=false;
                        }
                    }
                break;

                case (3):
                    for (int i = 0; i < length; i++){
                        data[i]=reader.readShort();
                    }
                break;

                case (5):
                    for (int i = 0; i < length; i++){
                        data[i]=reader.readInt();
                    }
                break;

                case (7):
                    for (int i = 0; i < length; i++){
                        data[i]=reader.readLong();
                    }
                break;

                case (9):
                    for (int i = 0; i < length; i++){
                        data[i]=reader.readFloat();
                    }
                break;

                case (10):
                    for (int i = 0; i < length; i++){
                        data[i]=reader.readDouble();
                    }
                break;

                case (11):
                    for (int i = 0; i < length; i++){
                        int strlen = reader.readByte();
                        byte ntmp[] = new byte[strlen];
                        reader.read(ntmp,0,strlen);
                        data[i]=new String(ntmp,0,strlen,"UTF-16BE");
                    }
                break;
            }
        }
        return data;
    }
}
