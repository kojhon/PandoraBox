/**
 * Created by IntelliJ IDEA.
 * User: Admin
 * Date: 02.12.11
 * Time: 14:12
 * To change this template use File | Settings | File Templates.
 */



import java.io.*;
import java.lang.reflect.Array;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Objects;
import java.util.Random;

public class server_RRPC{
    Integer server_port;
    public server_RRPC(Integer port){
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
            protocol_encoder pe = new protocol_encoder();
            Object[] data = pe.encode(client_sock.getInputStream());
            dubug_Object_printer dop = new dubug_Object_printer();
            System.out.println("RECIVED DATA:");
            dop.print(data);
            OutputStream out = client_sock.getOutputStream();
            Object[] res;
            res = function_calling(data);
            System.out.println("SENDED DATA:");
            dop.print(res);
            protocol_coder pc = new protocol_coder();
            ByteArrayOutputStream msg = pc.code(res);
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


class protocol_coder{
    ByteArrayOutputStream stream;
    DataOutputStream writer;
    public ByteArrayOutputStream code(Object ... argv) throws IOException {
        stream = new ByteArrayOutputStream();
        writer = new DataOutputStream(stream);
        writer.writeByte(((String)argv[0]).length()*2);
        writer.writeChars((String)argv[0]);
        int argc = argv.length;
        writer.writeByte(argc-1);

        for(int i = 1; i < argc; i++){
            Object a = argv[i];
            if (a instanceof Byte){
                //System.out.println("Byte");
                writer.writeByte((byte) 1);
                writer.writeByte(((Byte)argv[i]).byteValue());
            }

            if (a instanceof Boolean){
                //System.out.println("Boolean");
                writer.writeByte((byte) 2);
                if ((Boolean) argv[i]){
                    writer.writeByte((byte) 1);
                }else{
                    writer.writeByte((byte)0);
                }
            }

            if (a instanceof Short){
                //System.out.println("Short");
                writer.writeByte((byte) 3);
                writer.writeShort(((Short) argv[i]).shortValue());
            }

            if (a instanceof Integer){
                //System.out.println("Integer");
                writer.writeByte((byte) 5);
                writer.writeInt(((Integer) argv[i]).intValue());
            }
            //Long la = (long)10;

            if (a instanceof Long ){
                //System.out.println("Long");
                writer.writeByte((byte) 7);
                writer.writeLong(((Long)argv[i]).longValue());
            }

            if (a instanceof Float){
                //System.out.println("Float");
                writer.writeByte((byte) 9);
                writer.writeFloat( ((Float)argv[i]).floatValue());
            }

            if (a instanceof Double){
                //System.out.println("Double");
                writer.writeByte((byte) 10);
                writer.writeDouble( ((Double)argv[i]).doubleValue());
            }

            if (a instanceof String){
                //System.out.println("String");
                writer.writeByte((byte) 11);
                writer.writeByte((((String)argv[i]).length()*2));
                writer.writeChars(((String)argv[i]));
            }

            if (a.getClass().isArray()){
                //System.out.println("array");
                writer.writeByte((byte) 12);
                writer.writeByte(this.GetArrayDimension(a));
                byte type = this.GetType(a);
                writer.writeByte(type);
                this.arrayParse(a,type);
            }
        }
        return stream;
    }


    public  ByteArrayOutputStream code(String function_name,Object ... argv) throws IOException {
        stream = new ByteArrayOutputStream();
        writer = new DataOutputStream(stream);
        writer.writeByte(function_name.length()*2);
        writer.writeChars(function_name);
        int argc = argv.length;
        writer.writeByte(argc);

        for(int i = 0; i < argc; i++){
            Object a = argv[i];
            if (a instanceof Byte){
                //System.out.println("Byte");
                writer.writeByte((byte) 1);
                writer.writeByte(((Byte)argv[i]).byteValue());
            }

            if (a instanceof Boolean){
                //System.out.println("Boolean");
                writer.writeByte((byte) 2);
                if ((Boolean) argv[i]){
                    writer.writeByte((byte) 1);
                }else{
                    writer.writeByte((byte)0);
                }
            }

            if (a instanceof Short){
                //System.out.println("Short");
                writer.writeByte((byte) 3);
                writer.writeShort(((Short) argv[i]).shortValue());
            }

            if (a instanceof Integer){
                //System.out.println("Integer");
                writer.writeByte((byte) 5);
                writer.writeInt(((Integer) argv[i]).intValue());
            }
            //Long la = (long)10;

            if (a instanceof Long ){
                //System.out.println("Long");
                writer.writeByte((byte) 7);
                writer.writeLong(((Long)argv[i]).longValue());
            }

            if (a instanceof Float){
                //System.out.println("Float");
                writer.writeByte((byte) 9);
                writer.writeFloat( ((Float)argv[i]).floatValue());
            }

            if (a instanceof Double){
                //System.out.println("Double");
                writer.writeByte((byte) 10);
                writer.writeDouble( ((Double)argv[i]).doubleValue());
            }

            if (a instanceof String){
                //System.out.println("String");
                writer.writeByte((byte) 11);
                writer.writeByte((((String)argv[i]).length()*2));
                writer.writeChars(((String)argv[i]));
            }

            if (a.getClass().isArray()){
                //System.out.println("array");
                writer.writeByte((byte) 12);
                writer.writeByte(this.GetArrayDimension(a));
                byte type = this.GetType(a);
                writer.writeByte(type);
                this.arrayParse(a,type);
            }
        }
        return stream;
    }

    private byte GetArrayDimension(Object a){
        byte size = 0;

        while (a.getClass().isArray()){
            size++;
            a = Array.get(a, 0);
        }

        return size;
    }

    private byte GetType(Object a){


        while (a.getClass().isArray()){
            a = Array.get(a,0);
        }

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
        //Long la = (long)10;

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

        return -1;
    }

    private void arrayParse(Object a, byte type) throws  IOException{
        switch (type){
            case (1):
                this.arrayParseByte(a);
                break;

            case (2):
                this.arrayParseBool(a);
                break;

            case (3):
                this.arrayParseShort(a);
                break;

            case (5):
                this.arrayParseInt(a);
                break;

            case (7):
                this.arrayParseLong(a);
                break;

            case (9):
                this.arrayParseFloat(a);
                break;

            case (10):
                this.arrayParseDouble(a);
                break;
            case (11):
                this.arrayParseString(a);
                break;
        }

    }

    private void arrayParseByte(Object a) throws IOException {
        int length = Array.getLength(a);
        writer.writeByte(length);
        if (Array.get(a,0).getClass().isArray()){
            for (int i = 0; i < length; i++){
                arrayParseByte(Array.get(a,i));
            }
        }else{
            for (int i = 0; i < length; i++){
                writer.writeByte((Byte)Array.get(a,i));
            }
        }
    }

    private void arrayParseBool(Object a) throws IOException {
        int length = Array.getLength(a);
        writer.writeByte(length);
        if (Array.get(a,0).getClass().isArray()){
            for (int i = 0; i < length; i++){
                arrayParseBool(Array.get(a,i));
            }
        }else{
            for (int i = 0; i < length; i++){
                if ((Boolean)Array.get(a,i)){
                    writer.writeByte(1);
                }else{
                    writer.writeByte(0);
                }
            }
        }
    }

    private void arrayParseShort(Object a) throws IOException {
        int length = Array.getLength(a);
        writer.writeByte(length);
        if (Array.get(a,0).getClass().isArray()){
            for (int i = 0; i < length; i++){
                arrayParseShort(Array.get(a,i));
            }
        }else{
            for (int i = 0; i < length; i++){
                writer.writeShort((Short)Array.get(a,i));
            }
        }
    }

    private void arrayParseInt(Object a) throws IOException {
        int length = Array.getLength(a);
        writer.writeByte(length);
        if (Array.get(a,0).getClass().isArray()){
            for (int i = 0; i < length; i++){
                arrayParseInt(Array.get(a,i));
            }
        }else{
            for (int i = 0; i < length; i++){
                if (a.getClass().isArray()){
                    writer.writeInt((Integer)Array.get(a,i));
                }
            }
        }
    }

    private void arrayParseLong(Object a) throws IOException {
        int length = Array.getLength(a);
        writer.writeByte(length);
        if (Array.get(a,0).getClass().isArray()){
            for (int i = 0; i < length; i++){
                arrayParseLong(Array.get(a,i));
            }
        }else{
            for (int i = 0; i < length; i++){
                writer.writeLong((Long)Array.get(a,i));
            }
        }
    }

    private void arrayParseFloat(Object a) throws IOException {
        int length = Array.getLength(a);
        writer.writeByte(length);
        if (Array.get(a,0).getClass().isArray()){
            for (int i = 0; i < length; i++){
                arrayParseFloat(Array.get(a,i));
            }
        }else{
            for (int i = 0; i < length; i++){
                writer.writeFloat((Float)Array.get(a,i));
            }
        }
    }

    private void arrayParseDouble(Object a) throws IOException {
        int length = Array.getLength(a);
        writer.writeByte(length);
        if (Array.get(a,0).getClass().isArray()){
            for (int i = 0; i < length; i++){
                arrayParseDouble(Array.get(a,i));
            }
        }else{
            for (int i = 0; i < length; i++){
                writer.writeDouble((Double)Array.get(a,i));
            }
        }
    }

    private void arrayParseString(Object a) throws IOException {
        int length = Array.getLength(a);
        writer.writeByte(length);
        if (Array.get(a,0).getClass().isArray()){
            for (int i = 0; i < length; i++){
                arrayParseString(Array.get(a,i));
            }
        }else{
            for (int i = 0; i < length; i++){
                String tmp = (String)Array.get(a,i);
                writer.writeByte(tmp.length()*2);
                writer.writeChars(tmp);
            }
        }
    }
}




class protocol_encoder{
    DataInputStream reader;
    public Object[] encode(InputStream stream) throws IOException{
        reader = new DataInputStream(stream);
        //byte[] msg = new byte[1024];
        //reader.readFully(msg);
        //System.out.println("bla");
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
                    data[i]=perse_to_array();
                break;

                default:
                    return null;

            }

        }

        return data;
    }

    private Object perse_to_array() throws  IOException{
        int dimention = (int)reader.readByte();
        byte type = reader.readByte();
        switch (type){
            case (1):
                return parse_to_array_byte(dimention);
            case (2):
                return parse_to_array_bool(dimention);
            case (3):
                return parse_to_array_short(dimention);
            case (5):
                return parse_to_array_int(dimention);
            case (7):
                return parse_to_array_long(dimention);
            case (9):
                return parse_to_array_float(dimention);
            case (10):
                return parse_to_array_double(dimention);
            case (11):
                return parse_to_array_string(dimention);
        }
        return null;
    }

    private Object parse_to_array_byte(int my_dimention) throws  IOException{
        Byte length = reader.readByte();
        Object[] data = new Object[length];

        if (my_dimention == 1){
            for (int i = 0; i < length; i++){
                data[i] = reader.readByte();
            }
        }else{
            for (int i = 0; i < length; i++){
                data[i] = parse_to_array_byte(my_dimention-1);
            }
        }

        return data;
    }

    private Object parse_to_array_bool(int my_dimention)throws  IOException{
        Byte length = reader.readByte();
        Object[] data = new Object[length];

        if (my_dimention == 1){
            for (int i = 0; i < length; i++){
                byte tmp = reader.readByte();
                if (tmp == 1){
                    data[i] = true;
                }else{
                    data[i]=false;
                }
            }
        }else{
            for (int i = 0; i < length; i++){
                data[i] = parse_to_array_bool(my_dimention-1);
            }
        }

        return data;
    }

    private Object parse_to_array_short(int my_dimention) throws  IOException{
        Byte length = reader.readByte();
        Object[] data = new Object[length];

        if (my_dimention == 1){
            for (int i = 0; i < length; i++){
                data[i]=reader.readShort();
            }
        }else{
            for (int i = 0; i < length; i++){
                data[i] = parse_to_array_short(my_dimention-1);
            }
        }

        return data;
    }

    private Object parse_to_array_int(int my_dimention) throws  IOException{
        Byte length = reader.readByte();
        Object[] data = new Object[length];

        if (my_dimention == 1){
            for (int i = 0; i < length; i++){
                data[i]=reader.readInt();
            }
        }else{
            for (int i = 0; i < length; i++){
                data[i] = parse_to_array_int(my_dimention-1);
            }
        }

        return data;
    }

    private Object parse_to_array_long(int my_dimention) throws  IOException{
        Byte length = reader.readByte();
        Object[] data = new Object[length];

        if (my_dimention == 1){
            for (int i = 0; i < length; i++){
                data[i]=reader.readLong();
            }
        }else{
            for (int i = 0; i < length; i++){
                data[i] = parse_to_array_long(my_dimention-1);
            }
        }

        return data;
    }

    private Object parse_to_array_float(int my_dimention) throws  IOException{
        Byte length = reader.readByte();
        Object[] data = new Object[length];

        if (my_dimention == 1){
            for (int i = 0; i < length; i++){
                data[i]=reader.readFloat();
            }
        }else{
            for (int i = 0; i < length; i++){
                data[i] = parse_to_array_float(my_dimention-1);
            }
        }

        return data;
    }

    private Object parse_to_array_double(int my_dimention) throws  IOException{
        Byte length = reader.readByte();
        Object[] data = new Object[length];

        if (my_dimention == 1){
            for (int i = 0; i < length; i++){
                data[i]=reader.readDouble();
            }
        }else{
            for (int i = 0; i < length; i++){
                data[i] = parse_to_array_double(my_dimention-1);
            }
        }

        return data;

    }

    private Object parse_to_array_string(int my_dimention) throws  IOException{
        Byte length = reader.readByte();
        Object[] data = new Object[length];

        if (my_dimention == 1){
            for (int i = 0; i < length; i++){
                int strlen = reader.readByte();
                byte ntmp[] = new byte[strlen];
                reader.read(ntmp,0,strlen);
                data[i]=new String(ntmp,0,strlen,"UTF-16BE");
            }
        }else{
            for (int i = 0; i < length; i++){
                data[i] = parse_to_array_string(my_dimention-1);
            }
        }

        return data;
    }
}