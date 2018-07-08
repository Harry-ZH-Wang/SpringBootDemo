package com.wzh.config.utils;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.core.io.ClassPathResource;

import java.io.*;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * <properties解析工具类>
 * <解析properties文件，支持中文,获取classpath下的文件>
 * @author wzh
 * @version 2018-06-24 16:49
 * @see [相关类/方法] (可选)
 **/
public class PropertiesUtils {

    private static Logger log = Logger.getLogger(PropertiesUtils.class);

    /**
     * Spring boot 项目如果直接读取jar包打包方式中的文件
     * 路径会转换为jar:file:/xxxx/xxx
     * 根据配置判断项目打包方式
     */
    private static String PACKAGE_JAR = "0";

    private static String PACKAGE_WAR = "1";

    /**
     * 需要解析的文件后缀
     */
    private static final String FILE_SUFFIX = ".PROPERTIES";

    /**
     * CLASSES文件夹
     */
    private static final String FILE_LOADER = "CLASSES";

    /**
     * 读取classpath指定配置文件返回properties对象
     * @param filePath 文件路径 xxx/xxx
     * @param fileName 文件名xxx.properties
     * @return properties对象
     */
    public static Properties readProperties(String filePath, String fileName) {

        Properties prop=new Properties();
        try {

            //使用spring中的方法获取classes路径下的文件,这里这么做是因为用的springboot,如果打成jar包常用的方法获取不文件
            ClassPathResource classPathResource = new ClassPathResource(filePath + File.separator + fileName);
            InputStream in = classPathResource.getInputStream();

            // 设置UTF-8 支持中文
            Reader reader = new InputStreamReader(in,"UTF-8");
            prop.load(reader);
            if(null != in)
            {
                in.close();
            }

        }catch (Exception e)
        {
            log.error("获取配置文件" + filePath + "失败 " + e.getMessage() ,e);
        }
        return prop;
    }

    /**
     * 获取classpath指定文件夹下所有配置文件，返回Properties对象
     * 这里把解析到的所有实体文件放到一个一个properties中
     * 如果判断实体文件有重复的key,抛出异常
     * @param filePath xxx/xxx
     * @return properties对象
     */
    public static Properties readProperties(String filePath)
    {
        Properties prop = new Properties();

        try {
            // 获取解析的路径
            ClassPathResource classPathResource = new ClassPathResource(filePath);
            URL url = classPathResource.getURL();
            String packagePath = url.toString();

            Properties configProp = readProperties("config/properties","framework.properties");
            String packageType = configProp.getProperty("config.packge.type");
            // jar 打包方式
            if(PACKAGE_JAR.equals(packageType))
            {
                // 获取文件
                getAllFileByFolder(prop, filePath, FILE_SUFFIX);

            }
            // war 打包方式及本地测试
            if(PACKAGE_WAR.equals(packageType)){
                // 获取文件夹下所有文件对象
                List<File> fileList = new ArrayList<File>();
                getAllFileByFolder(filePath, fileList);

                if (!fileList.isEmpty())
                {
                    //转为file为properties对象
                    boolean flag = false;
                    for(File file : fileList)
                    {
                        // file对象转换为properties对象
                        InputStream in = FileUtils.openInputStream(file);
                        // 设置UTF-8 支持中文
                        Reader reader = new InputStreamReader(in,"UTF-8");
                        Properties tempProp = new Properties();
                        tempProp.load(reader);
                        if (null != in)
                        {
                            in.close();
                        }

                        // 组装对象
                        loadProperties(prop,tempProp,filePath);
                    }

                }

            }

        }catch (Exception e)
        {
            log.error(e.getMessage(),e);
        }

        return prop;
    }

    /**
     *获取文件夹下所有文件对象，并返回File对象集合
     * 只能读取文件夹下的文件，不支持jar中读取
     * @param filePath 文件夹路径
     * @param fileList 返回的文件集合
     * @return 文件集合
     * @throws Exception 如果文件夹不存在，抛出业务异常
     */
    public static List<File> getAllFileByFolder(String filePath,List<File> fileList) throws Exception
    {
        //获取路径
        ClassPathResource classPathResource = new ClassPathResource(filePath);
        URL url = classPathResource.getURL();
        log.info(url.toString());
        File file = classPathResource.getFile();
        if(file.exists())
        {
            File[] files = file.listFiles();
            if(files == null || files.length == 0)
            {
                log.warn("文件夹为空：" + filePath);
                return fileList;

            }else{

                for (File temp : files)
                {
                    if(temp.isFile())
                    {
                        fileList.add(temp);
                        log.info("获取文件：" + temp.getName());
                    }else {

                        String newPath = filePath + File.separator + temp.getName();
                        //递归调用
                        getAllFileByFolder(newPath, fileList);
                    }
                }
            }

        }else {
            throw new BusinessException("文件夹不存在：" + filePath);
        }


        return fileList;
    }

    /**
     * 读取jar中的资源文件，classes文件夹下
     * @param filePath 文件路径
     * @param prop 配置文件对象
     * @param fileType 文件类型
     * @return 配置文件对象
     * @throws Exception
     */
    public static Properties getAllFileByFolder(Properties prop,String filePath, String fileType) throws Exception
    {
        // 获取路径
        ClassPathResource classPathResource = new ClassPathResource(filePath);
        URL dirPath = classPathResource.getURL();
        /*
        ----------------------------
         如果工程打为jar 获取的路径大概为：
         jar:file:/Volumes/TOSHIBA/temp/SpringBootDemo.jar!/BOOT-INF/classes!/static/file
         所以根据!/进行拆分
        ----------------------------
         */
        String jarPath = dirPath.toString().substring(0, dirPath.toString().indexOf("!/") + 2);
        // 项目jar url对象
        URL jarURL = new URL(jarPath);
        JarURLConnection jarURLConnection = (JarURLConnection) jarURL.openConnection();

        // 获取jar包中文件目录文件
        JarFile jarFile = jarURLConnection.getJarFile();
        Enumeration<JarEntry> jarEntrys = jarFile.entries();

        // 迭代
        while (jarEntrys.hasMoreElements()){

            // 文件索引对象
            JarEntry jarEntry = jarEntrys.nextElement();
            String fileName = jarEntry.getName();
            log.info(fileName);

            String checkPatch = FILE_LOADER + File.separator +filePath;
            // 只获取指定的文件夹下的指定类型的文件
            if(fileName.toUpperCase().indexOf(checkPatch.toUpperCase()) != -1
                     && fileName.toUpperCase().endsWith(fileType))
            {
                //使用spring中的方法获取classes路径下的文件,这里这么做是因为用的springboot,如果打成jar包常用的方法获取不文件
                ClassPathResource resource = new ClassPathResource(fileName);
                InputStream in = resource.getInputStream();

                // 设置UTF-8 支持中文
                Reader reader = new InputStreamReader(in,"UTF-8");
                Properties tempProp = new Properties();
                tempProp.load(reader);

                if(null != in)
                {
                    in.close();
                }

                // 组装对象
                loadProperties(prop,tempProp,fileName);

            }
        }

        return prop;
    }

    /**
     * 判断重复并合并properties对象
     * @param prop 返回properties对象
     * @param tempProp 需要被合并的临时对象
     * @param filePath 文件路径
     */
    private static void loadProperties(Properties prop,Properties tempProp, String filePath) throws Exception {

        //遍历properties
        Set<Map.Entry<Object, Object>> entrys = tempProp.entrySet();
        for(Map.Entry<Object, Object> entry : entrys)
        {
            String key = (String) entry.getKey();
            // 取不到值加入对象，取得到则properties中key重复，抛出异常
            if(StringUtils.isBlank(prop.getProperty(key)))
            {
                prop.put(entry.getKey(),entry.getValue());
                log.info("成功加载配置文件：" + filePath);

            } else {
                throw new BusinessException("文件夹" + filePath + "下properties key：" + key + " 重复，请核对");
            }
        }
    }
}
