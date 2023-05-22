package com.lin.utils;

import com.aliyun.auth.credentials.Credential;
import com.aliyun.auth.credentials.provider.StaticCredentialProvider;
import com.aliyun.sdk.service.dysmsapi20170525.AsyncClient;
import com.aliyun.sdk.service.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.sdk.service.dysmsapi20170525.models.SendSmsResponse;
import com.google.gson.Gson;
import com.lin.config.AliyunConfig;
import darabonba.core.client.ClientOverrideConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author 林炳昌
 * @desc
 * @date 2023年04月01日 19:02
 */
@Component
public class SendMsgUtil {

    @Autowired
    AliyunConfig aliyunConfig;

    public String sendMsg(String phone) throws ExecutionException, InterruptedException {

        String code = RandomUtil.getFourBitRandom();
        StaticCredentialProvider provider = StaticCredentialProvider.create(Credential.builder()
                .accessKeyId(aliyunConfig.getAccessKeyId())
                .accessKeySecret(aliyunConfig.getAccessKeySecret())
                .build());

        AsyncClient client = AsyncClient.builder()
                .region("cn-shenzhen")
                .credentialsProvider(provider)
                .overrideConfiguration(
                        ClientOverrideConfiguration.create()
                                .setEndpointOverride("dysmsapi.aliyuncs.com")
                )
                .build();

        SendSmsRequest sendSmsRequest = SendSmsRequest.builder()
                .signName("林陈一点")
                .templateCode("SMS_275320148")
                .phoneNumbers(phone)
                .templateParam("{\"code\":\"" + code + "\"}")
                .build();

        CompletableFuture<SendSmsResponse> response = client.sendSms(sendSmsRequest);
        SendSmsResponse resp = response.get();
        System.out.println(new Gson().toJson(resp));
        client.close();
        return code;
    }

}
