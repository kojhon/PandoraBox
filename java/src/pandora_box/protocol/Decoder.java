package pandora_box.protocol;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Decoder {
    public static Object[] decodeResult(InputStream stream) throws IOException {
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

    public static Object[] decodeRequest(InputStream stream) throws IOException {
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

    private static Object getElement(DataInputStream reader) throws IOException{
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
                    element = decodeArray(dimension, type, reader);
                    break;

                default:
                    return null;

            }
            return element;
    }

    private static Object decodeArray(int dimension, byte type, DataInputStream reader) throws IOException {
        Byte length = reader.readByte();
        Object[] data = new Object[length];
        if (dimension != 1) {
            for (int i = 0; i < length; i++) {
                data[i] = decodeArray(dimension - 1, type, reader);
            }

            return data;
        }


        switch (type) {
            case (1):
                for (int i = 0; i < length; i++) {
                    data[i] = reader.readByte();
                }
                break;

            case (2):
                for (int i = 0; i < length; i++) {
                    byte tmp = reader.readByte();
                    data[i] = tmp == 1;
                }
                break;

            case (3):
                for (int i = 0; i < length; i++) {
                    data[i] = reader.readShort();
                }
                break;

            case (5):
                for (int i = 0; i < length; i++) {
                    data[i] = reader.readInt();
                }
                break;

            case (7):
                for (int i = 0; i < length; i++) {
                    data[i] = reader.readLong();
                }
                break;

            case (9):
                for (int i = 0; i < length; i++) {
                    data[i] = reader.readFloat();
                }
                break;

            case (10):
                for (int i = 0; i < length; i++) {
                    data[i] = reader.readDouble();
                }
                break;

            case (11):
                for (int i = 0; i < length; i++) {
                    int strlen = reader.readByte();
                    byte ntmp[] = new byte[strlen];
                    final int len = reader.read(ntmp, 0, strlen);
                    data[i] = new String(ntmp, 0, len, "UTF-16BE");
                }
                break;
        }
        return data;
    }
}
