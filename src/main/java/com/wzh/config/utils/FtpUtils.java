package com.wzh.config.utils;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wzh
 * @create 2018-05-06 23:03
 * @desc ${操作FTP文件工具类}
 **/
public class FtpUtils {

    private static Logger log = Logger.getLogger(FtpUtils.class);

    //本地编码集对象
    private static String encode = Charset.defaultCharset().toString();

    // FTP编码为iso-8859-1
    private static final String SERVER_CHARSET = "ISO-8859-1";

    //FTP下载时读入内存的大小
    private static final int BUFFER_SIZE = 1024000;


    /**
     * 获取FTP连接对象，连接FTP成功返回FTP对象，
     * 连接FTP失败超过最大次数返回null,使用前请判断是否为空
     * @param ftpHost 服务器ip
     * @param ftpPort 服务器端口
     * @param ftpUserName 用户名
     * @param ftpPassword 密码
     * @return FTPClient FTP连接对象
     */
    public static FTPClient getFTPClient(String ftpHost, int ftpPort, String ftpUserName, String ftpPassword) {

        //FTP连接对象
        FTPClient ftpClient = null;

        try
        {
            ftpClient = new FTPClient();
            //设置FTP服务器IP和端口
            ftpClient.connect(ftpHost,ftpPort);
            //设置超时时间,毫秒
            ftpClient.setConnectTimeout(50000);
            //登录FTP
            ftpClient.login(ftpUserName,ftpPassword);

            //设置被动传输模式
            ftpClient.enterLocalPassiveMode();
            //ftpClient.enterRemotePassiveMode();
            //二进制传输
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            //设置读入内存文件大小
            ftpClient.setBufferSize(BUFFER_SIZE);

            //获取FTP连接状态码 ，大于等于200 小于300状态码表示连接正常
            int connectState = ftpClient.getReplyCode();

            //连接失败重试
            int reNum = 0;
            while (!FTPReply.isPositiveCompletion(connectState)
                    && reNum < 3)
            {
                ftpClient.disconnect();
                ++reNum;
                ftpClient.login(ftpUserName,ftpPassword);

            }
            if (reNum < 3) {

                log.info("FTP连接成功");

            } else {
                ftpClient = null;
                log.error("FTP连接失败");
            }

        } catch (Exception e)
        {

            log.error(e.getMessage(), e);
        }

        return ftpClient;
    }

    /**
     *断开FTP
     * @param ftpClient fpt连接对象
     */
    public static void closeFTP(FTPClient ftpClient) {

        if (null != ftpClient) {
            try {
                //登出FTP
                ftpClient.disconnect();
                log.info("登出FTP成功");
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            } finally {
                try {
                    //断开FTP
                    ftpClient.disconnect();
                    log.info("断开FTP成功");
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                }
            }

        }
    }


    /**
     * 根据FTP编码集转换文件路径，防止中文乱码，并设置FTP连接的编码集
     * @param ftpClient FTP连接对象
     * @param path 文件路径
     * @return 转码后的文件路径
     */
    public static String changeEncode(FTPClient ftpClient,String path) throws IOException
    {
        synchronized (encode)
        {
            int status = ftpClient.sendCommand("OPTS UTF8","ON");
            //判断FTP服务器是否支持UTF -8,支持使用UTF-8 否则使用本地编码集
            if(FTPReply.isPositiveCompletion(status))
            {
                encode = "UTF-8";
            }

            log.info("FTP使用编码集：" + encode);

            ftpClient.setControlEncoding(encode);
            path = new String(path.getBytes(encode),SERVER_CHARSET);
        }

        return path;
    }

    /**
     * 获取文件后缀
     * @param fileName 文件名或文件全路径
     * @return 文件后缀
     */
    public static String getSuffix(String fileName)
    {
        String suffix = "";

        int index = fileName.lastIndexOf(".");
        if(index != -1)
        {
            suffix = fileName.substring(index);
            log.info("获取文件后缀名成功，文件名：" + fileName + " 后缀名：" + suffix);
        }else{
            log.warn("获取文件后缀名失败,文件名" + fileName);
        }
        return suffix;
    }

    /**
     * 获取FTP指定文件大小
     * @param ftpClient ftp连接对象
     * @param fileName ftp文件服务器路径 如：/public/file/xxx.text
     * @return 文件大小，获取失败返回-1
     */
    public static Long getFtpFileSize(FTPClient ftpClient, String fileName)
    {
        FTPFile[] files = null;
        Long fileSize = -1L;
        try {

            files = ftpClient.listFiles(changeEncode(ftpClient,fileName));

            //因为指定了具体的文件名，这里只取数组0位
            if (null != files && files.length > 0)
            {
                log.info("文件个数：" + files.length + " 文件名：" +
                        files[0].getName() + " 文件大小：" + files[0].getSize());

                fileSize = files[0].getSize();
            }
        } catch (IOException e) {

            fileSize = 1L;
            log.error(e.getMessage(),e);
        }

        return fileSize;
    }

    /**
     *下载FTP上指定文件路径文件
     * @param ftpClient FTP连接对象
     * @param filePath 文件路径+文件名 例如:/public/file/a.txt
     * @param downPath 下载文件保存的路径
     * @param newFileName 新的文件名 例如:newFileName
     * @return
     */
    public static boolean downLoadFtpFile(FTPClient ftpClient, String filePath,String downPath, String newFileName)
    {
        //默认失败
        boolean flag = false;

        //获取文件后缀
        String suffix = getSuffix(filePath);

        //下载的文件对象
        File dwonFile = new File(downPath + File.separator + newFileName + suffix);
        try
        {
            OutputStream out = new FileOutputStream(dwonFile);
            flag = ftpClient.retrieveFile(changeEncode(ftpClient,filePath),out);
            out.flush();
            out.close();

            if(flag)
            {
                log.info("下载文件成功，文件路径：" + filePath);
            }else{
                log.error("下载文件失败，文件路径：" + filePath);
            }
        }
        catch (Exception e)
        {
            log.error(e.getMessage(),e);
        }

        return flag;
    }

    /**
     * FTP文件上传工具类
     * @param ftpClient 连接对象
     * @param filePath 本地文件路径 /xxxx/xx.txt
     * @param ftpPath ftp储存路径
     * @param newFileName ftp保存的文件名
     * @return true 下载成功，false 下载失败
     */
    public static boolean uploadFile(FTPClient ftpClient,String filePath,String ftpPath, String newFileName)
    {
        boolean flag = false;

        InputStream in = null;

        try {
            // 获取文件后缀
            String suffix = getSuffix(filePath);

            // 路径转码，处理中文
            ftpPath = changeEncode(ftpClient,ftpPath);
            newFileName = changeEncode(ftpClient,newFileName + suffix);

            // 判断目标文件夹是否存在,不存在就创建
            if(!ftpClient.changeWorkingDirectory(ftpPath))
            {
                ftpClient.makeDirectory(ftpPath);
                ftpClient.changeWorkingDirectory(ftpPath);
            }

            // 上传文件
            File file = new File(filePath);
            in = new FileInputStream(file);
            flag = ftpClient.storeFile(newFileName,in);
            if(flag)
            {
                log.info("文件上传成功：" + filePath);
            }
        }
        catch (Exception e)
        {
            log.error(e.getMessage(),e);
        }
        finally
        {
            try {
                if(in != null)
                {
                    in.close();
                }
            } catch (IOException e) {
                log.error(e.getMessage(),e);
            }

        }

        return flag;
    }

    /**
     * FTP上文复制文件到另外一个路径
     * @param ftpClient ftp连接对象
     * @param oldFtpPath 源文件储存路径 xxx/xxx.txt
     * @param newFtpPath 新路径 /public/file/
     * @param newFileName 新文件名
     * @return true 下载成功，false 下载失败
     */
    public static boolean copyFile(FTPClient ftpClient,String oldFtpPath,String newFtpPath, String newFileName)
    {
        boolean flag = false;

        ByteArrayInputStream in = null;
        ByteArrayOutputStream out = null;

        try {
            out = new ByteArrayOutputStream();

            //获取文件后缀
            String suffix = getSuffix(oldFtpPath);

            //先读入内存，绑定out输出流，然后再转换为输入流
            String encodeOldPath = changeEncode(ftpClient,oldFtpPath);
            ftpClient.retrieveFile(encodeOldPath,out);
            in = new ByteArrayInputStream(out.toByteArray());

            //切换工作目录,没有就创建
            String encodeNewPath = changeEncode(ftpClient,newFtpPath);
            if(!ftpClient.changeWorkingDirectory(encodeNewPath))
            {
                ftpClient.makeDirectory(encodeNewPath);
                ftpClient.changeWorkingDirectory(encodeNewPath);
            }

            //复制文件
            flag = ftpClient.storeFile(changeEncode(ftpClient,newFileName + suffix),in);
            out.flush();
            out.close();
            in.close();
            if (flag) {
                log.info("文件复制成功，源文件：" + oldFtpPath + " 新路径：" + newFtpPath + newFileName);
            } else {
                throw new BusinessException("文件复制失败，源文件：" + oldFtpPath);
            }

        }
        catch (Exception e)
        {
            log.error(e.getMessage(),e);
        }

        return flag;
    }

    /**
     * 删除Ftp上的文件
     * @param ftpClient 连接对象
     * @param filePath 服务器文件路径 /public/file/xxx.txt
     * @return true 成功，false 失败
     */
    public static boolean delectFile(FTPClient ftpClient,String filePath)
    {
        boolean flag = false;

        try {
            flag = ftpClient.deleteFile(changeEncode(ftpClient,filePath));
            if(flag)
            {
                log.info("删除文件成功：" + filePath);
            }else{
                log.error("删除文件失败：" + filePath);
            }
        } catch (IOException e) {
            log.error(e.getMessage(),e);
        }

        return flag;
    }

    /**
     * 文件移动
     * @param ftpClient fpt连接对象
     * @param oldFtpPath 文件原路径 /public/old/xxx.txt
     * @param newFtpPath 文件新路径 /public/new/
     * @param newFileName 文件名
     * @return true 成功，false 失败
     */
    public static boolean moveFile(FTPClient ftpClient,String oldFtpPath,String newFtpPath, String newFileName)
    {
        boolean flag = false;

        try {
            //文件后缀
            String suffix = getSuffix(oldFtpPath);
            //路径编码
            String encodeOldPath = changeEncode(ftpClient,oldFtpPath);
            String encodeNewPath = changeEncode(ftpClient,newFtpPath);
            String encodeNewFileName = changeEncode(ftpClient,newFileName + suffix);

            //切换工作目录
            if(!ftpClient.changeWorkingDirectory(encodeNewPath))
            {
                ftpClient.makeDirectory(encodeNewPath);
                ftpClient.changeWorkingDirectory(encodeNewPath);
            }

            //转存
            flag = ftpClient.rename(encodeOldPath, encodeNewFileName);
            if(flag)
            {
                log.info("文件转存成功：" + oldFtpPath);
            }else {
                log.error("文件转存失败：" + oldFtpPath);
            }

        } catch (IOException e) {
            log.error(e.getMessage(),e);
        }
        return flag;
    }

    /**
     * 读取Ftp文本文件，返回行数据集合
     * @param ftpClient ftp连接对象
     * @param filePath 文件路径 /public/file/xxx.txt
     * @param encode 解析文件编码集
     * @return 行数据集合
     */
    public static List<String> redFtpFileWithLine(FTPClient ftpClient, String filePath, String encode)
    {
        List<String> lineList = new ArrayList<String>();
        InputStream in = null;
        BufferedReader reader = null;

        try {
            //获取文件流数据
            in = ftpClient.retrieveFileStream(changeEncode(ftpClient,filePath));
            if(in == null)
            {
                throw new BusinessException("获取文件流失败：" + filePath);
            }
            reader = new BufferedReader(new InputStreamReader(in,encode));
            String inLine;
            while ((inLine = reader.readLine()) != null)
            {
                lineList.add(inLine);
            }

            //关闭流
            if(reader != null)
            {
                reader.close();
            }
            in.close();
            /*
            retrieveFileStream使用了流，需要释放一下，不然会返回null
            方法一：主动调用一次getReply()把接下来的226消费掉
            方法二：主动调用一次completePendingCommand()，把流释放掉
             */
            ftpClient.getReply();

        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }


        return lineList;
    }

    /**
     * 获取文件输出流
     * @param ftpClient ftp连接对象
     * @param filePath 文件路径
     * @param out 文件输出流
     */
    public static void readFileWithOutputStream(FTPClient ftpClient, String filePath, OutputStream out)
    {
        try {
            if(out == null)
            {
                throw new BusinessException("输出流为null");
            }

            ftpClient.retrieveFile(filePath,out);

            out.flush();
            out.close();

        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }
    }

}
