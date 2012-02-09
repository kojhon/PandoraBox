import java.lang.reflect.Array;

/**
 * Created by IntelliJ IDEA.
 * User: Admin
 * Date: 10.01.12
 * Time: 21:52
 * To change this template use File | Settings | File Templates.
 */
public class dubug_Object_printer {
    public void print(Object ... argv){
        int count = argv.length;
        for (int i = 0; i < count; i++){
            if (argv[i].getClass().isArray()){
                this.array_print(argv[i]);
            }else{
                System.out.println(argv[i].toString());

            }
            System.out.println();
        }
    }

    public  void print(String function_name, Object ... argv){
        System.out.println(function_name);
        System.out.println();
        int count = argv.length;
        for (int i = 0; i < count; i++){
            if (argv[i].getClass().isArray()){
                this.array_print(argv[i]);
            }else{
                System.out.println(argv[i].toString());

            }
            System.out.println();
        }

    }

    private void array_print(Object data){
        int type = this.get_type(data);
        System.out.print("Array of ");
        switch (type){
            case (1):
                System.out.println("bytes:");
                this.byte_array_print(data,1);
                break;

            case (2):
                System.out.println("booleans:");
                this.bool_array_print(data,1);

                break;

            case (3):
                System.out.println("shortes:");
                this.short_array_print(data,1);

                break;

            case (5):
                System.out.println("ints:");
                this.int_array_print(data,1);

                break;

            case (7):
                System.out.println("longs:");
                this.long_array_print(data,1);

                break;

            case (9):
                System.out.println("floats:");
                this.float_array_print(data,1);

                break;

            case (10):
                System.out.println("doubles:");
                this.double_array_print(data,1);

                break;
            case (11):
                System.out.println("strings:");
                this.string_array_print(data,1);
                break;
        }
        System.out.println("End of array");
    }

    private int get_type(Object a){

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

    private void byte_array_print(Object data,int dimention){
        String tabulation = new String();
        for (int i = 0; i < dimention; i++){
            tabulation += "\t";
        }
        int length = Array.getLength(data);
        if (Array.get(data,0).getClass().isArray()){
            for (int i = 0; i < length; i++){
                System.out.print(tabulation);
                System.out.print(i);
                System.out.println("=>");
                byte_array_print(Array.get(data,i),dimention+1);
            }
        }else{
            for (int i = 0; i < length; i++){
                System.out.print(tabulation);
                System.out.println((Byte)Array.get(data,i));
            }
        }
    }

    private void bool_array_print(Object data,int dimention){
        String tabulation = new String();
        for (int i = 0; i < dimention; i++){
            tabulation += "\t";
        }
        int length = Array.getLength(data);
        if (Array.get(data,0).getClass().isArray()){
            for (int i = 0; i < length; i++){
                System.out.print(tabulation);
                System.out.print(i);
                System.out.println("=>");
                bool_array_print(Array.get(data,i),dimention+1);
            }
        }else{
            for (int i = 0; i < length; i++){
                System.out.print(tabulation);
                if ((Boolean)Array.get(data,i)){
                    System.out.println("true");
                }else{
                    System.out.println("false");
                }
            }
        }
    }

    private void short_array_print(Object data,int dimention){
        String tabulation = new String();
        for (int i = 0; i < dimention; i++){
            tabulation += "\t";
        }
        int length = Array.getLength(data);
        if (Array.get(data,0).getClass().isArray()){
            for (int i = 0; i < length; i++){
                System.out.print(tabulation);
                System.out.print(i);
                System.out.println("=>");
                short_array_print(Array.get(data,i),dimention+1);
                System.out.print(tabulation);
                System.out.println("}");
            }
        }else{
            for (int i = 0; i < length; i++){
                System.out.print(tabulation);
                System.out.println((Short)Array.get(data,i));
            }
        }
    }

    private void int_array_print(Object data,int dimention){
        String tabulation = new String();
        for (int i = 0; i < dimention; i++){
            tabulation += "\t";
        }
        int length = Array.getLength(data);
        if (Array.get(data,0).getClass().isArray()){
            for (int i = 0; i < length; i++){
                System.out.print(tabulation);
                System.out.print(i);
                System.out.println("=>");
                int_array_print(Array.get(data,i),dimention+1);
            }
        }else{
            for (int i = 0; i < length; i++){
                System.out.print(tabulation);
                System.out.println((Integer)Array.get(data,i));
            }
        }
    }

    private void long_array_print(Object data, int dimention){
        String tabulation = new String();
        for (int i = 0; i < dimention; i++){
            tabulation += "\t";
        }
        int length = Array.getLength(data);
        if (Array.get(data,0).getClass().isArray()){
            for (int i = 0; i < length; i++){
                System.out.print(tabulation);
                System.out.print(i);
                System.out.println("=>");
                long_array_print(Array.get(data,i),dimention+1);
            }
        }else{
            for (int i = 0; i < length; i++){
                System.out.print(tabulation);
                System.out.println((Long)Array.get(data,i));
            }
        }
    }

    private void float_array_print(Object data,int dimention){
        String tabulation = new String();
        for (int i = 0; i < dimention; i++){
            tabulation += "\t";
        }
        int length = Array.getLength(data);
        if (Array.get(data,0).getClass().isArray()){
            for (int i = 0; i < length; i++){
                System.out.print(tabulation);
                System.out.print(i);
                System.out.println("=>");;
                float_array_print(Array.get(data,i),dimention+1);
            }
        }else{
            for (int i = 0; i < length; i++){
                System.out.print(tabulation);
                System.out.println((Float)Array.get(data,i));
            }
        }
    }

    private void double_array_print(Object data,int dimention){
        String tabulation = new String();
        for (int i = 0; i < dimention; i++){
            tabulation += "\t";
        }
        int length = Array.getLength(data);
        if (Array.get(data,0).getClass().isArray()){
            for (int i = 0; i < length; i++){
                System.out.print(tabulation);
                System.out.print(i);
                System.out.println("=>");
                double_array_print(Array.get(data,i),dimention+1);
            }
        }else{
            for (int i = 0; i < length; i++){
                System.out.print(tabulation);
                System.out.println((Double)Array.get(data,i));
            }
        }
    }

    private void string_array_print(Object data,int dimention){
        String tabulation = new String();
        for (int i = 0; i < dimention; i++){
            tabulation += "\t";
        }
        int length = Array.getLength(data);
        if (Array.get(data,0).getClass().isArray()){
            for (int i = 0; i < length; i++){
                System.out.print(tabulation);
                System.out.print(i);
                System.out.println("=>");
                string_array_print(Array.get(data,i),dimention+1);
            }
        }else{
            for (int i = 0; i < length; i++){
                System.out.print(tabulation);
                System.out.println((String)Array.get(data,i));
            }
        }
    }
}
