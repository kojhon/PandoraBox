/**
 * Created by IntelliJ IDEA.
 * User: Admin
 * Date: 29.03.12
 * Time: 23:43
 * To change this template use File | Settings | File Templates.
 */
public class test_class{
    public static String concat(String a, String b){
        return a+b;
    }
    public int getArrayMax(Integer[][] arr){
        int iLength = arr.length;
        int jLength = arr[0].length;
        int max = 0;
        boolean isFounded = false;
        for (int i = 0; i < iLength; i++){
            for (int j = 0; j < jLength; j++){
                if ((!(isFounded))||(arr[i][j] > max)){
                    max = arr[i][j];
                    isFounded = true;
                }
            }
        }
        return max;
    }
}
