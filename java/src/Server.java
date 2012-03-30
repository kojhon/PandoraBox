import java.io.IOException;
import java.security.PublicKey;

import pandora_box.server.*;
/**
 * Created by IntelliJ IDEA.
 * User: Admin
 * Date: 16.02.12
 * Time: 18:50
 * To change this template use File | Settings | File Templates.
 */
public class Server {
     public static void main(String[] args) throws IOException, InterruptedException {
         PandoraBoxServer s = new PandoraBoxServer(13002);
         test_class t = new test_class();
         s.publicateStaticMethod("conc","test_class","concat");
         s.publicateNonstaticMethod("getMax","test_class","getArrayMax",t);
         s.start();
    }
}