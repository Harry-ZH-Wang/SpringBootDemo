package com.wzh.config.utils;

import org.apache.log4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * <文件工具类> <功能详细描述>
 * 
 * @author wzh
 * @version 2018-12-09 17:01
 * @see [相关类/方法] (可选)
 **/
public class FileUtils
{
    
    private static Logger log = Logger.getLogger(FileUtils.class);
    
    /**
     * 文件后缀分隔符：.
     */
    private static final String SEPARATOR_POINT = ".";
    
    /**
     * 根据指定的编码集，末尾追加方式写入文本文件
     * @param result 需要写入文件的消息
     * @param fileName 文件名
     * @param suffix 文件名后缀
     * @param filePath 文件路径
     * @param encode 保存文件的编码集
     * @param append 是否为追加写入，true 为追加在文本末尾，false 为覆盖写入
     */
    public static boolean writeByLine(List<String> result, String fileName,
        String suffix, String filePath, String encode, boolean append)
    {
        long startTime = System.currentTimeMillis();
        boolean flag = false;
        BufferedWriter bufferedWriter = null;

        try
        {
            // 判断路径是否存在
            File tempDirPath = new File(filePath);
            if (!tempDirPath.exists())
            {
                tempDirPath.mkdir();
            }
            
            // 判断是否包含后缀分隔符
            String tempSuffix = "";
            if (suffix != null)
            {
                if (suffix.lastIndexOf(SEPARATOR_POINT) == -1)
                {
                    tempSuffix = SEPARATOR_POINT + suffix;
                }
                else
                {
                    tempSuffix = suffix;
                }
            }
            
            // 判断文件是否存在
            String tempFilePath =
                filePath + File.separator + fileName + tempSuffix;
            File tempFile = new File(tempFilePath);
            if (!tempFile.exists())
            {
                tempFile.createNewFile();
            }
            
            // 获得文件流，追加模式写入，设置编码集
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(tempFile, append), encode));
            
            // 循环追加
            for (String info : result)
            {
                bufferedWriter.write(info);
                bufferedWriter.newLine();
            }
            
            log.info(
                "写入文件耗时：" + (System.currentTimeMillis() - startTime) + "毫秒");
            
        }
        catch (IOException e)
        {
            log.error(e.getMessage(), e);
        }
        finally
        {
            try
            {
                if (bufferedWriter != null)
                {
                    bufferedWriter.flush();
                    bufferedWriter.close();
                }
            }
            catch (IOException e)
            {
                log.error(e.getMessage(), e);
            }

        }
        return flag;
    }
    
    public static void main(String[] args)
    {
        
        List<String> list = new ArrayList<String>();
        
        for (int i = 0; i < 10; i++)
        {
            list.add("我是第" + i + "行随机数：" + Math.random());
        }
        
        FileUtils.writeByLine(list,
            "测试",
            "txt",
            "/Users/wzh/Downloads",
            "UTF-8",
            true);
        
    }
}
