package com.wzh.config.utils;

import com.wzh.config.framework.domain.FtpBean;
import com.wzh.config.framework.frameworkInit.InitConstant;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * <FTP文件管理器>
 * <此工具类交由容器管理，在com.wzh.utils.FtpUtils的基础上做了进一步封装>
 * @author wzh
 * @version 2018-06-11 00:09
 * @see [相关类/方法] (可选)
 **/
@Component("ftpManagerUtils")
public class FtpManagerUtils {

    private static Logger log = Logger.getLogger(FtpManagerUtils.class);
    /**
     * ftp 账号集合
     */
    private static Map<String, Object> ftpInfoMap;

    public FtpManagerUtils() {

        ftpInfoMap = InitConstant.getFtpInfoMap();

    }

    /**
     * 连接ftp服务器
     * @param ftpName ftp账号
     * @return ftp连接对象
     */
    private FTPClient linkFtp(String ftpName) {
        //获取FTP账户信息
        FtpBean ftp = (FtpBean) ftpInfoMap.get(ftpName);
        return FtpUtils.getFTPClient(ftp.getFtpHost(), NumberUtils.toInt(ftp.getFtpPort()),
                ftp.getFtpUserName(), ftp.getFtpPassword());
    }

    /**
     * 获取FTP文件服务器上文件大小（byte）
     * @param ftpName ftp账号别名
     * @param path    文件路径 /public/file/xxx.txt
     * @return 文件大小（byte）获取失败返回-1
     */
    public Long getFileSize(String ftpName, String path) {

        FTPClient ftpClient = linkFtp(ftpName);

        Long size = -1L;

        if (null != ftpClient) {
            // 获取文件大小
            size = FtpUtils.getFtpFileSize(ftpClient, path);
            // 关闭连接
            FtpUtils.closeFTP(ftpClient);

        }

        return size;
    }

    /**
     * 从ftp文件服务器下载文件到工程指定目录
     * @param ftpName     ftp别名
     * @param ftpPath     ftp服务器文件路径 /public/file/xxx.txt
     * @param downPath    保存的目录 绝对路径
     * @param newFileName 重命名的文件
     * @return 文件是否下载成功过
     */
    public boolean downLoadFile(String ftpName, String ftpPath, String downPath, String newFileName) {
        // 默认失败
        boolean flag = false;

        FTPClient ftpClient = linkFtp(ftpName);
        if (null != ftpClient) {
            // 获取文件大小
            flag = FtpUtils.downLoadFtpFile(ftpClient, ftpPath, downPath, newFileName);
            // 关闭连接
            FtpUtils.closeFTP(ftpClient);

        }

        return flag;
    }

    /**
     * 上传文件到ftp服务器
     * @param ftpName     ftp别名
     * @param filePath    文件绝对路径  /xxxx/xx.txt
     * @param ftpPath     ftp 服务器保存路径
     * @param newFileName 保存的文件名
     * @return 上传是否成功过
     */
    public boolean upLoadFile(String ftpName, String filePath, String ftpPath, String newFileName) {
        //默认失败
        boolean flag = false;

        FTPClient ftpClient = linkFtp(ftpName);

        if (null != ftpClient) {
            // 文件上传
            flag = FtpUtils.uploadFile(ftpClient, filePath, ftpPath, newFileName);

            // 关闭连接
            FtpUtils.closeFTP(ftpClient);
        }

        return flag;
    }

    /**
     * 上传文件到ftp服务器
     * @param ftpName     ftp别名
     * @param file        文件对象
     * @param ftpPath     ftp 服务器保存路径
     * @param newFileName 保存的文件名
     * @return 上传是否成功过
     */
    public boolean upLoadFile(String ftpName, MultipartFile file, String ftpPath, String newFileName){
        //默认失败
        boolean flag = false;

        FTPClient ftpClient = linkFtp(ftpName);
        InputStream in = null;

        if (null != ftpClient) {
            try {
                // 获取文件名
                String fileName = file.getOriginalFilename();

                // 获取文件后缀名
                String suffix = FtpUtils.getSuffix(fileName);

                // 路径转码，处理中文
                ftpPath = FtpUtils.changeEncode(ftpClient,ftpPath);
                newFileName = FtpUtils.changeEncode(ftpClient,newFileName + suffix);

                // 判断目标文件夹是否存在,不存在就创建
                if(!ftpClient.changeWorkingDirectory(ftpPath))
                {
                    ftpClient.makeDirectory(ftpPath);
                    ftpClient.changeWorkingDirectory(ftpPath);
                }
                in = file.getInputStream();
                flag = ftpClient.storeFile(newFileName,in);
                if(flag)
                {
                    log.info("文件上传成功：" + fileName);
                }

            }catch (Exception e)
            {
                log.error("文件上传失败：" + e.getMessage(),e);

            }finally
            {
                try {
                    if(in != null)
                    {
                        in.close();
                    }
                } catch (IOException e) {
                    log.error(e.getMessage(),e);
                }

                // 关闭连接
                FtpUtils.closeFTP(ftpClient);
            }

        }

        return flag;
    }

    /**
     * ftp服务器上文件转存
     * @param ftpName     ftp别名
     * @param oldFtpPath  ftp服务器上文件旧路径
     * @param newFtpPath  ftp服务器上文件新路径
     * @param newFileName 转存后的文件名
     * @return 是否成功
     */
    public boolean copyFile(String ftpName, String oldFtpPath, String newFtpPath, String newFileName) {

        boolean flag = false;

        FTPClient ftpClient = linkFtp(ftpName);

        if(null != ftpClient)
        {
            // 文件转存
            flag = FtpUtils.copyFile(ftpClient, oldFtpPath, newFtpPath, newFileName);

            // 关闭连接
            FtpUtils.closeFTP(ftpClient);
        }

        return flag;
    }

    /**
     * 删除FTP服务器上的文件
     * @param ftpName  ftp别名
     * @param filePath 文件路径
     * @return 是否删除成功过
     */
    public boolean delectFile(String ftpName, String filePath) {

        boolean flag = false;

        FTPClient ftpClient = linkFtp(ftpName);

        if(null != ftpClient)
        {
            // 删除文件
            flag = FtpUtils.delectFile(ftpClient, filePath);

            // 关闭连接
            FtpUtils.closeFTP(ftpClient);
        }

        return flag;
    }

    /**
     * 文件移动
     * @param ftpName     ftp别名
     * @param oldFtpPath  ftp服务器文件原路径
     * @param newFtpPath  ftp服务器文件保存新路径
     * @param newFileName 移动后的文件名
     * @return 移动是否成功
     */
    public boolean moveFile(String ftpName ,String oldFtpPath,String newFtpPath, String newFileName)
    {
        boolean flag = false;

        FTPClient ftpClient = linkFtp(ftpName);

        if(null != ftpClient)
        {
            // 文件移动，不保留原始文件
            flag = FtpUtils.moveFile(ftpClient, oldFtpPath, newFtpPath, newFileName);

            // 关闭连接
            FtpUtils.closeFTP(ftpClient);
        }

        return flag;
    }

    /**
     * 解析txt实体文件并转换为对应的list集合,如果没有分隔符，用String接收
     * 需注意实体类与字符串拆分后的顺序需相同，排除final 属性不进行设置值
     * 其实还有一种方案就是可以用xml等配置文件进行配置文件映射，属性类型，这里为了简单就直接要求顺序相同
     * @param ftpName          ftp别名
     * @param filePath         服务器文件路径
     * @param encode           文件编码集
     * @param regex            文件数据分隔符
     * @param obj              解析映射的对象
     * @param simpleDateFormat 解析映射的对象
     * @param <T>              对象泛型
     * @return 返回解析后的集合
     */
    public <T> List<T> readFileWithLine(String ftpName, String filePath, String encode, String regex,
                                        String simpleDateFormat,T obj) throws Exception{

        //解析后返回的数据集合
        List<T> clazzes = new ArrayList<T>();

        FTPClient ftpClient = linkFtp(ftpName);

        if(null != ftpClient)
        {
            List<String> info = FtpUtils.redFtpFileWithLine(ftpClient, filePath, encode);
            if(null != info && !info.isEmpty())
            {
                if(StringUtils.isBlank(regex))
                {
                    // 无分隔符，判断为String 集合
                    clazzes.addAll((Collection<? extends T>) info);

                }else {

                    for(String str : info)
                    {
                        // 拆分行数据
                        String [] line = str.split(regex);

                        //因为JDK用的1.9 所以没有直接newInstance，如果是低版本的jdk 可以直接getClass().newInstance
                        T t = (T) obj.getClass().getDeclaredConstructor().newInstance();
                        // 获取文件属性数组
                        Field[] fielders = t.getClass().getDeclaredFields();

                        // 循环排除final属性
                        List<Field> fieldList = new ArrayList<Field>();
                        for(Field cell : fielders)
                        {
                            if(!Modifier.isFinal(cell.getModifiers()))
                            {
                                // 非final的属性才进行处理
                                fieldList.add(cell);
                            }
                        }

                        // 数据和对象映射要求完全对应，所以这里取对象下标
                        for(int i = 0; i < fieldList.size(); i++)
                        {
                            Field field = fieldList.get(i);
                            // 设置权限
                            field.setAccessible(true);

                            // 判断数据类型进行转换,这里只做了几种常见类型的判断，如果有需要可以继续添加
                            String type = field.getType().getName();
                            try {

                                if("java.lang.Integer".equals(type) || "int".equals(type))
                                {
                                    field.set(t,NumberUtils.toInt(line[i]));
                                }
                                else if("java.lang.Double".equals(type) || "double".equals(type))
                                {
                                    field.set(t,NumberUtils.toDouble(line[i]));
                                }
                                else if("java.lang.Float".equals(type) || "float".equals(type))
                                {
                                    field.set(t,NumberUtils.toFloat(line[i]));
                                }
                                else if("java.lang.Long".equals(type) || "long".equals(type))
                                {
                                    field.set(t,NumberUtils.toLong(line[i]));
                                }
                                else if("java.lang.Short".equals(type) || "short".equals(type))
                                {
                                    field.set(t,NumberUtils.toShort(line[i]));
                                }
                                else if("java.lang.Boolean".equals(type) || "boolean".equals(type))
                                {
                                    field.set(t, BooleanUtils.toBoolean(line[i]));
                                }
                                else if("java.util.Date".equals(type) || "Date".equals(type))
                                {
                                    SimpleDateFormat sdf=new SimpleDateFormat(simpleDateFormat);
                                    if(StringUtils.isBlank(line[i]))
                                    {
                                        field.set(t, null);
                                    }else{
                                        field.set(t, sdf.parse(line[i]));
                                    }
                                }
                                else {
                                    field.set(t, line[i]);
                                }

                            }catch (Exception e){
                                log.error(e.getMessage(),e);
                            }

                        }

                        // 添加数据
                        clazzes.add(t);
                    }
                }

            }

        }
        return clazzes;
    }

    /**
     * 获取文件流
     * @param ftpName  ftp别名
     * @param filePath ftp服务器上文件路径
     * @param out      输出流
     */
    public void readFileWithOutputStream(String ftpName, String filePath, OutputStream out)
    {
        FTPClient ftpClient = linkFtp(ftpName);

        if(null != ftpClient)
        {

            // 绑定输出流
            FtpUtils.readFileWithOutputStream(ftpClient, filePath, out);

            // 关闭连接
            FtpUtils.closeFTP(ftpClient);
        }
    }
}
