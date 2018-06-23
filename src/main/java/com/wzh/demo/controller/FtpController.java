package com.wzh.demo.controller;

import com.wzh.config.utils.FtpManagerUtils;
import com.wzh.config.utils.FtpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * <ftp文件上传控制器>
 * <功能详细描述>
 *
 * @author wzh
 * @version 2018-06-18 17:55
 * @see [相关类/方法] (可选)
 **/
@Controller
@RequestMapping("/ftp")
public class FtpController {

    @Autowired
    @Qualifier("ftpManagerUtils")
    private FtpManagerUtils ftpManagerUtils;

    @RequestMapping(value = "/upload.do",method = RequestMethod.GET)
    public String toFileUpload()
    {
        return "/test/fileUpload";
    }

    @RequestMapping(value = "/upload.do", method = RequestMethod.POST)
    public String FileUpload(@RequestParam("file") MultipartFile file)
    {
        String suffix = FtpUtils.getSuffix(file.getOriginalFilename());

        //根据自身业务做处理重命名
        String newFileName = UUID.randomUUID().toString().replace("-", "");

        //文件上传
        ftpManagerUtils.upLoadFile("FTP_USER_SYSTEM",file,"/file/test/",newFileName);


        return "/test/fileUpload";
    }

    @RequestMapping(value = "/down.do",method = RequestMethod.GET)
    public String toFileDown()
    {

        return "test/fileDown";
    }

    @RequestMapping(value = "/down.do", method = RequestMethod.POST)
    public String fileDown(HttpServletResponse response, String fileName) {
        try {
            // 设置返回编码及浏览器响应类型
            response.setCharacterEncoding("UTF-8");
            response.setContentType("multipart/form-data;charset=UTF-8");

            //获取文件后缀名
            String suffix = FtpUtils.getSuffix(fileName);

            //根据自身业务做处理重命名
            String downName = UUID.randomUUID().toString().replace("-", "") + suffix;

            response.setHeader("Content-Disposition", "attachment;fileName=" + downName);


            //文件下载,大多数业务逻辑都是页面传递文件名或ID，通过数据库或其他文件查询出具体文件路径，这里为了测试，写死
            ftpManagerUtils.readFileWithOutputStream("FTP_USER_SYSTEM", "/file/test/" + fileName,
                    response.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "test/fileDown";
    }
}
