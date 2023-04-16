package com.lin.config;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 林炳昌
 * @desc
 * @date 2023年04月09日 21:45
 */
@Data
@Configuration
public class AliyunOssConfig {

    @Value("${aliyun.endpoint}")
    private String endpoint;
    @Value("${aliyun.accessKeyId}")
    private String accessKeyId;
    @Value("${aliyun.accessKeySecret}")
    private String accessKeySecret;
    @Value("${aliyun.bucketName}")
    private String bucketName;
    @Value("${aliyun.url}")
    private String url;

    @Bean
    public OSS oSSClient() {
        return new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
    }

}
