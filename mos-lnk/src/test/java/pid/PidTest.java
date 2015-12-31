package pid;

import java.io.InputStream;

/**
 * @author 刘飞 E-mail:liufei_it@126.com
 * 
 * @version 1.0.0
 * @since 2015年12月31日 下午5:20:30
 */
public class PidTest {

    public static void main(String[] args) throws Exception {
//        Process process = Runtime.getRuntime().exec(new String[] {"/bin/sh", "-c", "echo $PPID"});
        Process process = Runtime.getRuntime().exec(new String[] {"/bin/sh", "-c", "ps aux | grep eclipse"});
        if (process.waitFor() == 0) {
            InputStream in = process.getInputStream();
            int available = in.available();
            byte[] outputBytes = new byte[available];
            in.read(outputBytes);
            String pid = new String(outputBytes);
            System.out.println("pid : " + pid);
        }
    }
}
