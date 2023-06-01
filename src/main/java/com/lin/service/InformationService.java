package com.lin.service;

import com.lin.common.NullData;
import com.lin.common.ResponseResult;
import com.lin.controller.DTO.user.UserCompleteDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.ExecutionException;

/**
 * @Author czh
 * @desc 基本信息服务接口
 * @date 2023/5/17 10:49
 */
public interface InformationService {
    ResponseResult<NullData> usernameModify(String token, String newUsername);

    ResponseResult<NullData> passwordModifyByPassword(String token, String oldPassword, String newPassword);

    ResponseResult<NullData> passwordModifyByPhone(String token, String code, String newPassword);

    ResponseResult<NullData> passwordModifyByPhoneGetCode(String token) throws ExecutionException, InterruptedException;

    ResponseResult<NullData> phoneModify(String token, String newPhoneNumber, String newPhoneCode);

    ResponseResult<NullData> phoneModifyGetCode(String token, String newPhoneNumber) throws ExecutionException, InterruptedException;

    ResponseResult<NullData> modifyPicture(String token, MultipartFile file);

    ResponseResult<UserCompleteDTO> viewInformation(String token);
}
