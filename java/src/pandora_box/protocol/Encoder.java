package pandora_box.protocol;

import java.io.ByteArrayOutputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;

public class Encoder {

    public ByteArrayOutputStream encodeData(Object... argv) throws IOException {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        DataOutputStream writer = new DataOutputStream(stream);

        writer.writeByte(((String) argv[0]).length() * 2);
        writer.writeChars((String) argv[0]);
        int argc = argv.length;
        writer.writeByte(argc - 1);

        for (int i = 1; i < argc; i++) {
            Object a = argv[i];
            int type = getType(a);
            writer.writeByte((byte) type);

            switch (type) {
                case (1):
                    writer.writeByte(((Byte) argv[i]).byteValue());
                    break;

                case (2):
                    if ((Boolean) argv[i]) {
                        writer.writeByte((byte) 1);
                    } else {
                        writer.writeByte((byte) 0);
                    }
                    break;

                case (3):
                    writer.writeShort(((Short) argv[i]).shortValue());
                    break;

                case (5):
                    writer.writeInt(((Integer) argv[i]).intValue());
                    break;

                case (7):
                    writer.writeLong(((Long) argv[i]).longValue());
                    break;

                case (9):
                    writer.writeFloat(((Float) argv[i]).floatValue());
                    break;

                case (10):
                    writer.writeDouble(((Double) argv[i]).doubleValue());
                    break;

                case (11):
                    writer.writeByte((((String) argv[i]).length() * 2));
                    writer.writeChars(((String) argv[i]));
                    break;

                case (12):
                    writer.writeByte(this.getArrayDimension(a));
                    Object tmp = a;
                    while (tmp.getClass().isArray()) {
                        tmp = Array.get(tmp, 0);
                    }
                    byte element_type = this.getType(tmp);
                    writer.writeByte(element_type);
                    this.arrayEncode(a, element_type, null);
                    break;
            }
        }
        return stream;
    }


    public ByteArrayOutputStream encodeData(String function_name, Object... argv) throws IOException {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        DataOutputStream writer = new DataOutputStream(stream);

        writer.writeByte(function_name.length() * 2);
        writer.writeChars(function_name);
        int argc = argv.length;
        writer.writeByte(argc);

        for (int i = 0; i < argc; i++) {
            Object a = argv[i];
            int type = getType(a);
            writer.writeByte((byte) type);

            switch (type) {
                case (1):
                    writer.writeByte(((Byte) argv[i]).byteValue());
                    break;

                case (2):
                    if ((Boolean) argv[i]) {
                        writer.writeByte((byte) 1);
                    } else {
                        writer.writeByte((byte) 0);
                    }
                    break;

                case (3):
                    writer.writeShort(((Short) argv[i]).shortValue());
                    break;

                case (5):
                    writer.writeInt(((Integer) argv[i]).intValue());
                    break;

                case (7):
                    writer.writeLong(((Long) argv[i]).longValue());
                    break;

                case (9):
                    writer.writeFloat(((Float) argv[i]).floatValue());
                    break;

                case (10):
                    writer.writeDouble(((Double) argv[i]).doubleValue());
                    break;

                case (11):
                    writer.writeByte((((String) argv[i]).length() * 2));
                    writer.writeChars(((String) argv[i]));
                    break;

                case (12):
                    writer.writeByte(this.getArrayDimension(a));
                    Object tmp = a;
                    while (tmp.getClass().isArray()) {
                        tmp = Array.get(tmp, 0);
                    }
                    byte element_type = this.getType(tmp);
                    writer.writeByte(element_type);
                    this.arrayEncode(a, element_type, null);
                    break;
            }
        }
        return stream;
    }

    private byte getArrayDimension(Object a) {
        byte size = 0;

        while (a.getClass().isArray()) {
            size++;
            a = Array.get(a, 0);
        }

        return size;
    }

    private byte getType(Object a) {

        if (a instanceof Byte) {
            return 1;
        }

        if (a instanceof Boolean) {
            return 2;
        }

        if (a instanceof Short) {
            return 3;
        }

        if (a instanceof Integer) {
            return 5;
        }

        if (a instanceof Long) {
            return 7;
        }

        if (a instanceof Float) {
            return 9;
        }

        if (a instanceof Double) {
            return 10;
        }

        if (a instanceof String) {
            return 11;
        }

        if (a.getClass().isArray()) {
            return 12;
        }

        throw new IllegalArgumentException("Unsupported class: " + a.getClass());

    }

    private void arrayEncode(Object a, byte type, DataOutput writer) throws IOException {
        int length = Array.getLength(a);
        writer.writeByte(length);
        if (Array.get(a, 0).getClass().isArray()) {
            for (int i = 0; i < length; i++) {
                arrayEncode(Array.get(a, i), type, null);
            }
        } else {
            switch (type) {
                case (1):
                    for (int i = 0; i < length; i++) {
                        writer.writeByte((Byte) Array.get(a, i));
                    }
                    break;

                case (2):
                    for (int i = 0; i < length; i++) {
                        if ((Boolean) Array.get(a, i)) {
                            writer.writeByte(1);
                        } else {
                            writer.writeByte(0);
                        }
                    }
                    break;

                case (3):
                    for (int i = 0; i < length; i++) {
                        writer.writeShort((Short) Array.get(a, i));
                    }
                    break;

                case (5):
                    for (int i = 0; i < length; i++) {
                        writer.writeInt((Integer) Array.get(a, i));
                    }
                    break;

                case (7):
                    for (int i = 0; i < length; i++) {
                        writer.writeLong((Long) Array.get(a, i));
                    }
                    break;

                case (9):
                    for (int i = 0; i < length; i++) {
                        writer.writeFloat((Float) Array.get(a, i));
                    }
                    break;

                case (10):
                    for (int i = 0; i < length; i++) {
                        writer.writeDouble((Double) Array.get(a, i));
                    }
                    break;

                case (11):
                    for (int i = 0; i < length; i++) {
                        String tmp = (String) Array.get(a, i);
                        writer.writeByte(tmp.length() * 2);
                        writer.writeChars(tmp);
                    }
                    break;
            }
        }
    }
}
