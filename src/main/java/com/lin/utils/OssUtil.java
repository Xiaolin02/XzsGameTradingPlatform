package com.lin.utils;

import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ListObjectsRequest;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;
import com.lin.config.AliyunOssConfig;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author 林炳昌
 * @date 2023年03月26日 10:46
 */
@Component
public class OssUtil {

    @Autowired
    OSS ossClient;

    @Autowired
    AliyunOssConfig aliyunOssConfig;


    /**
     * @desc oss上传文件
     * @date 2023/4/10 10:46
     */
    public String uploadfile(MultipartFile multipartFile,Integer userId,String type) {
        String bucketName = "xiaolin02";
        try {
            InputStream inputStream = multipartFile.getInputStream();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
            String dataPath = dateFormat.format(new Date());
            String originalFilename = multipartFile.getOriginalFilename();
            String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
            String fileUrl = type + "/" + dataPath + "/" + userId + suffix;
            ossClient.putObject(bucketName, fileUrl, inputStream);
            return "https://" + bucketName + "." + aliyunOssConfig.getEndpoint() + "/" + fileUrl;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @desc 获取oss文件列表
     * @date 2023/4/10 10:46
     */
    public List<OSSObjectSummary> getFileList() {
        // 设置最大个数。
        final int maxKeys = 200;
        // 列举文件。
        ObjectListing objectListing = ossClient.listObjects(new ListObjectsRequest(aliyunOssConfig.getBucketName()).withMaxKeys(maxKeys));
        List<OSSObjectSummary> sums = objectListing.getObjectSummaries();
        return sums;
    }

    /**
     * @desc 根据文件名删除oss文件("2023/04/09/test4.txt")
     * @date 2023/4/10 10:49
     */
    public void delete(String objectName) {
        // 根据BucketName,objectName删除文件
        ossClient.deleteObject(aliyunOssConfig.getBucketName(), objectName);
    }

    public void downFile(String oss_url, HttpServletResponse response) {
        // oss文件地址
//        String oss_url = request.getParameter("url");

        // 获取域名后面的内容
        String oss_domain = aliyunOssConfig.getUrl();
        String file_name = oss_url.replace(oss_domain, "");
        // 获取oss文件byte[]
        byte[] oss_byte = getOssFileByteArray(file_name);
        // 后缀名
        String fileExt = oss_url.substring(oss_url.lastIndexOf(".") + 1).toLowerCase();

        try {
            // 清空response
            response.reset();
            // 设置response的Header
            response.setHeader("Content-Disposition",
                    "attachment;filename=" + new SimpleDateFormat("yyyyMMddHHmm").format(new Date()) + "." + fileExt);
            // response.addHeader("Content-Length", "" + file.length());
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            toClient.write(oss_byte);// 以流的形式下载文件。
            toClient.flush();
            toClient.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    public byte[] getOssFileByteArray(String filepath) {
        byte[] result = null;
        OSSClient ossClient = null;
        try {
            if (filepath != null && !"".equals(filepath.trim())) {
                // 创建ClientConfiguration实例，按照您的需要修改默认参数
                ClientConfiguration conf = new ClientConfiguration();
                // 开启支持CNAME选项
                conf.setSupportCname(true);
                ossClient = new OSSClient(aliyunOssConfig.getEndpoint(), aliyunOssConfig.getAccessKeyId(), aliyunOssConfig.getAccessKeySecret(), conf);

                // 上传
                OSSObject ossObj = ossClient.getObject(aliyunOssConfig.getBucketName(), filepath);
                if (ossObj != null) {
                    InputStream is = ossObj.getObjectContent();
                    result = InputStreamToByteArray(is);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("文件下载异常");
        } finally {
            // 关闭client
            ossClient.shutdown();
        }
        return result;
    }

    /**
     * 1.图片转为字节数组
     * 图片到程序FileInputStream
     * 程序到数组 ByteArrayOutputStream
     */
    public static byte[] InputStreamToByteArray(InputStream is) {
        // 1.创建源与目的的
        byte[] dest = null;// 在字节数组输出的时候是不需要源的。
        // 2.选择流，选择文件输入流
        ByteArrayOutputStream os = null;// 新增方法
        try {
            os = new ByteArrayOutputStream();
            // 3.操作,读文件
            byte[] flush = new byte[1024 * 10];// 10k，创建读取数据时的缓冲，每次读取的字节个数。
            int len = -1;// 接受长度；
            while ((len = is.read(flush)) != -1) {
                // 表示当还没有到文件的末尾时
                // 字符数组-->字符串，即是解码。
                os.write(flush, 0, len);// 将文件内容写出字节数组
            }
            os.flush();
            return os.toByteArray();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            // 4.释放资源
            try {
                if (is != null) {// 表示当文打开时，才需要通知操作系统关闭
                    is.close();
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
        return null;

    }


}
