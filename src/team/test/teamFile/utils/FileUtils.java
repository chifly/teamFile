package team.test.teamFile.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 *  文件io
 * @author chh
 */
public class FileUtils {
    /**
     * 文件IO
     * @param in
     * @param out
     * @throws IOException
     */
    public static void io(InputStream in, OutputStream out) throws IOException {
        byte[] buff = new byte[1024];
        int len = 0;
        //读取输入流
        while ((len = in.read(buff)) != -1) {
            //输出流写入
            out.write(buff, 0, len);
        }
        in.close();
        out.close();
    }
}
