package pandora_box.protocol;

import com.sun.deploy.util.ArrayUtil;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;

public class Decoder {
    public static Object[] decodeResult(InputStream stream) throws IOException, ClassNotFoundException {
        DataInputStream reader = new DataInputStream(stream);
        byte tmp = reader.readByte();
        Object result[] = new Object[2];
        result[0]= false;

        if (tmp == 1){
            result[0] = true;
            int length = reader.readByte();
            byte ntmp[] = new byte[length];
            int len = reader.read(ntmp, 0, length);
            result[1] = new String(ntmp, 0, len, "UTF-16BE");
        }else{
            result[1] = getElement(reader);
        }

        return result;
    }

    public static Object[] decodeRequest(InputStream stream) throws IOException, ClassNotFoundException {
        DataInputStream reader = new DataInputStream(stream);

        byte name_length = reader.readByte();
        byte tmp[] = new byte[name_length];
        int len = reader.read(tmp, 0, name_length);

        byte count = reader.readByte();
        Object data[] = new Object[count + 1];
        data[0] = new String(tmp, 0, len, "UTF-16BE");
        for (int i = 1; i < count + 1; i++) {

            data[i] = getElement(reader);

        }

        return data;
    }

    private static Object getElement(DataInputStream reader) throws IOException, ClassNotFoundException {
            byte flag = reader.readByte();
            Object element;
            if (flag == -1) {
                return null;
            }

            switch (flag) {

                case (1)://byte block
                    element = reader.readByte();
                    break;

                case (2)://boolean block
                    byte bool_value = reader.readByte();
                    element = bool_value == 1;
                    break;

                case (3)://short block
                    element = reader.readShort();
                    break;

                case (5)://int block
                    element = reader.readInt();
                    break;

                case (7)://Long block
                    element = reader.readLong();
                    break;

                case (9)://float block
                    element = reader.readFloat();
                    break;

                case (10)://double block
                    element = reader.readDouble();
                    break;

                case (11):
                    int length = reader.readByte();
                    byte ntmp[] = new byte[length];
                    int len = reader.read(ntmp, 0, length);
                    element = new String(ntmp, 0, len, "UTF-16BE");
                    break;

                case (12):
                    int dimension = (int) reader.readByte();
                    byte type = reader.readByte();
                    int dimentionsSize[] = new int[dimension];
                    for (int i = 0; i < dimension; i++){
                        dimentionsSize[i] = reader.readByte();
                    }
                    Class arrayElementClass = getArrayElementClass(type);
                    Object arr = Array.newInstance(arrayElementClass,dimentionsSize);
                    length = dimentionsSize.length;
                    for (int i = 0; i < length/2; i++){
                        int tmp = dimentionsSize[i];
                        dimentionsSize[i] = dimentionsSize[length-i-1];
                        dimentionsSize[length-i-1] = tmp;
                    }
                    element = decodeArray(dimension, type, reader, arr, dimentionsSize);
                    break;

                default:
                    return null;

            }
            return element;
    }

    private static Class getArrayElementClass(byte type) throws ClassNotFoundException {
        switch (type){
            case(1):
                return Class.forName("java.lang.Byte");

            case(2):
                return Class.forName("java.lang.Boolean");

            case(3):
                return Class.forName("java.lang.Short");

            case(5):
                return Class.forName("java.lang.Integer");

            case(7):
                return Class.forName("java.lang.Long");

            case(9):
                return Class.forName("java.lang.Float");

            case(10):
                return Class.forName("java.lang.Double");

            case(11):
                return Class.forName("java.lang.String");

        }
        return null;
    }

    private static Object decodeArray(int dimension, byte type, DataInputStream reader, Object arr, int[] dimentionsSizes) throws IOException {
        //Byte length = reader.readByte();

        //Object[] data = new Object[length];
        if (dimension != 1) {
            for (int i = 0; i < dimentionsSizes[dimension-1]; i++) {
                Array.set(arr,i,decodeArray(dimension - 1, type, reader,Array.get(arr,i),dimentionsSizes));
            }

            return arr;
        }


        switch (type) {
            case (1):
                for (int i = 0; i < dimentionsSizes[dimension-1]; i++) {
                    byte tmp = reader.readByte();
                    Array.set(arr,i,tmp);
                }
                break;

            case (2):
                for (int i = 0; i < dimentionsSizes[dimension-1]; i++) {
                    byte tmp = reader.readByte();
                    boolean tmp2 = tmp == 1;
                    Array.set(arr,i,tmp2);
                }
                break;

            case (3):
                for (int i = 0; i < dimentionsSizes[dimension-1]; i++) {
                    short tmp= reader.readShort();
                    Array.set(arr,i,tmp);
                }
                break;

            case (5):
                for (int i = 0; i < dimentionsSizes[dimension-1]; i++) {
                    int tmp = reader.readInt();
                    Array.set(arr,i,tmp);
                }
                break;

            case (7):
                for (int i = 0; i < dimentionsSizes[dimension-1]; i++) {
                    long tmp = reader.readLong();
                    Array.set(arr,i,tmp);
                }
                break;

            case (9):
                for (int i = 0; i < dimentionsSizes[dimension-1]; i++) {
                    float tmp = reader.readFloat();
                    Array.set(arr,i,tmp);
                }
                break;

            case (10):
                for (int i = 0; i < dimentionsSizes[dimension-1]; i++) {
                    double tmp = reader.readDouble();
                    Array.set(arr,i,tmp);
                }
                break;

            case (11):
                for (int i = 0; i < dimentionsSizes[dimension-1]; i++) {
                    int strlen = reader.readByte();
                    byte ntmp[] = new byte[strlen];
                    final int len = reader.read(ntmp, 0, strlen);
                    String tmp = new String(ntmp, 0, len, "UTF-16BE");
                    Array.set(arr,i,tmp);
                }
                break;
        }
        return arr;
    }
}
