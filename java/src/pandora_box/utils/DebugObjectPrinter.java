package pandora_box.utils;

import java.lang.reflect.Array;

public class DebugObjectPrinter {

    public void print(Object... argv) {
        int count = argv.length;
        for (int i = 0; i < count; i++) {
            if (argv[i].getClass().isArray()) {
                this.printArray(argv[i]);
            } else {
                System.out.println(argv[i].toString());

            }
            System.out.println();
        }
    }

    public void print(String function_name, Object... argv) {
        System.out.println(function_name);
        System.out.println();
        int count = argv.length;
        for (int i = 0; i < count; i++) {
            if (argv[i].getClass().isArray()) {
                this.printArray(argv[i]);
            } else {
                System.out.println(argv[i].toString());

            }
            System.out.println();
        }

    }

    private void printArray(Object data) {
        int type = this.getType(data);
        System.out.print("Array of ");
        switch (type) {
            case (1):
                System.out.println("bytes:");
                this.printByteArray(data, 1);
                break;

            case (2):
                System.out.println("booleans:");
                this.printBooleanArray(data, 1);

                break;

            case (3):
                System.out.println("shortes:");
                this.printShortArray(data, 1);

                break;

            case (5):
                System.out.println("ints:");
                this.printIntArray(data, 1);

                break;

            case (7):
                System.out.println("longs:");
                this.printLongArray(data, 1);

                break;

            case (9):
                System.out.println("floats:");
                this.printFloatArray(data, 1);

                break;

            case (10):
                System.out.println("doubles:");
                this.printDoubleArray(data, 1);

                break;
            case (11):
                System.out.println("strings:");
                this.printStringArray(data, 1);
                break;
        }
        System.out.println("End of array");
    }

    private int getType(Object a) {

        while (a.getClass().isArray()) {
            a = Array.get(a, 0);
        }

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

        return -1;
    }

    private void printByteArray(Object data, int dimension) {
        String tabulation = "";
        for (int i = 0; i < dimension; i++) {
            tabulation += "\t";
        }
        int length = Array.getLength(data);
        if (Array.get(data, 0).getClass().isArray()) {
            for (int i = 0; i < length; i++) {
                System.out.print(tabulation);
                System.out.print(i);
                System.out.println("=>");
                printByteArray(Array.get(data, i), dimension + 1);
            }
        } else {
            for (int i = 0; i < length; i++) {
                System.out.print(tabulation);
                System.out.println(Array.get(data, i));
            }
        }
    }

    private void printBooleanArray(Object data, int dimension) {
        String tabulation = "";
        for (int i = 0; i < dimension; i++) {
            tabulation += "\t";
        }
        int length = Array.getLength(data);
        if (Array.get(data, 0).getClass().isArray()) {
            for (int i = 0; i < length; i++) {
                System.out.print(tabulation);
                System.out.print(i);
                System.out.println("=>");
                printBooleanArray(Array.get(data, i), dimension + 1);
            }
        } else {
            for (int i = 0; i < length; i++) {
                System.out.print(tabulation);
                if ((Boolean) Array.get(data, i)) {
                    System.out.println("true");
                } else {
                    System.out.println("false");
                }
            }
        }
    }

    private void printShortArray(Object data, int dimension) {
        String tabulation = "";
        for (int i = 0; i < dimension; i++) {
            tabulation += "\t";
        }
        int length = Array.getLength(data);
        if (Array.get(data, 0).getClass().isArray()) {
            for (int i = 0; i < length; i++) {
                System.out.print(tabulation);
                System.out.print(i);
                System.out.println("=>");
                printShortArray(Array.get(data, i), dimension + 1);
                System.out.print(tabulation);
                System.out.println("}");
            }
        } else {
            for (int i = 0; i < length; i++) {
                System.out.print(tabulation);
                System.out.println(Array.get(data, i));
            }
        }
    }

    private void printIntArray(Object data, int dimension) {
        String tabulation = "";
        for (int i = 0; i < dimension; i++) {
            tabulation += "\t";
        }
        int length = Array.getLength(data);
        if (Array.get(data, 0).getClass().isArray()) {
            for (int i = 0; i < length; i++) {
                System.out.print(tabulation);
                System.out.print(i);
                System.out.println("=>");
                printIntArray(Array.get(data, i), dimension + 1);
            }
        } else {
            for (int i = 0; i < length; i++) {
                System.out.print(tabulation);
                System.out.println(Array.get(data, i));
            }
        }
    }

    private void printLongArray(Object data, int dimension) {
        String tabulation = "";
        for (int i = 0; i < dimension; i++) {
            tabulation += "\t";
        }
        int length = Array.getLength(data);
        if (Array.get(data, 0).getClass().isArray()) {
            for (int i = 0; i < length; i++) {
                System.out.print(tabulation);
                System.out.print(i);
                System.out.println("=>");
                printLongArray(Array.get(data, i), dimension + 1);
            }
        } else {
            for (int i = 0; i < length; i++) {
                System.out.print(tabulation);
                System.out.println(Array.get(data, i));
            }
        }
    }

    private void printFloatArray(Object data, int dimension) {
        String tabulation = "";
        for (int i = 0; i < dimension; i++) {
            tabulation += "\t";
        }
        int length = Array.getLength(data);
        if (Array.get(data, 0).getClass().isArray()) {
            for (int i = 0; i < length; i++) {
                System.out.print(tabulation);
                System.out.print(i);
                System.out.println("=>");
                printFloatArray(Array.get(data, i), dimension + 1);
            }
        } else {
            for (int i = 0; i < length; i++) {
                System.out.print(tabulation);
                System.out.println(Array.get(data, i));
            }
        }
    }

    private void printDoubleArray(Object data, int dimension) {
        String tabulation = "";
        for (int i = 0; i < dimension; i++) {
            tabulation += "\t";
        }
        int length = Array.getLength(data);
        if (Array.get(data, 0).getClass().isArray()) {
            for (int i = 0; i < length; i++) {
                System.out.print(tabulation);
                System.out.print(i);
                System.out.println("=>");
                printDoubleArray(Array.get(data, i), dimension + 1);
            }
        } else {
            for (int i = 0; i < length; i++) {
                System.out.print(tabulation);
                System.out.println(Array.get(data, i));
            }
        }
    }

    private void printStringArray(Object data, int dimension) {
        String tabulation = "";
        for (int i = 0; i < dimension; i++) {
            tabulation += "\t";
        }
        int length = Array.getLength(data);
        if (Array.get(data, 0).getClass().isArray()) {
            for (int i = 0; i < length; i++) {
                System.out.print(tabulation);
                System.out.print(i);
                System.out.println("=>");
                printStringArray(Array.get(data, i), dimension + 1);
            }
        } else {
            for (int i = 0; i < length; i++) {
                System.out.print(tabulation);
                System.out.println((String) Array.get(data, i));
            }
        }
    }
}
