package com.lin.service;

import com.lin.common.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.ExecutionException;

/**
 * @Author czh
 * @desc 基本信息服务接口
 * @date 2023/5/17 10:49
 */
public interface InformationService {
    ResponseResult<Object> usernameModify(String token, String newUsername);

    ResponseResult<Object> passwordModifyByPassword(String token, String oldPassword, String newPassword);

    ResponseResult<Object> passwordModifyByPhone(String token, String code, String newPassword);

    ResponseResult<Object> passwordModifyByPhoneGetCode(String token) throws ExecutionException, InterruptedException;

    ResponseResult<Object> phoneModify(String token, String newPhoneNumber, String newPhoneCode);

    ResponseResult<Object> phoneModifyGetCode(String token, String newPhoneNumber) throws ExecutionException, InterruptedException;

    ResponseResult<Object> modifyPicture(String token, MultipartFile file);

    ResponseResult<Object> viewInformation(String token);
}
