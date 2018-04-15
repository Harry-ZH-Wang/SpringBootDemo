import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 远程下载文件测试
 */
public class FileTest {

    public static void downloadFile(String remoteFilePath, String localFilePath){
        URL urlfile = null;
        HttpURLConnection httpUrl = null;
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        File f = new File(localFilePath);
        try
        {
            urlfile = new URL(remoteFilePath);
            httpUrl = (HttpURLConnection)urlfile.openConnection();
            httpUrl.connect();
            System.out.println(httpUrl.getContentLength());
            System.out.println(httpUrl.getResponseCode());
            httpUrl.disconnect();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void main(String[] args)
    {
        String a = "http://localhost:8080/SpringBootDemo/imge/test.png";
        downloadFile(a,"");
    }
}
