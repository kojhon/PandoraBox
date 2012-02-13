package PandoraBox;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by IntelliJ IDEA.
 * User: Admin
 * Date: 11.02.12
 * Time: 15:36
 * To change this template use File | Settings | File Templates.
 */
public class protocolDecoder {
        DataInputStream reader;
        public Object[] decodeData(InputStream stream) throws IOException {
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
