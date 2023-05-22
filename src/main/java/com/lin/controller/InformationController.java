package com.lin.controller;

import com.lin.common.ResponseResult;
import com.lin.controller.DTO.ReleaseDTO;
import com.lin.controller.DTO.SearchCommodityDTO;
import com.lin.service.InformationService;
import com.lin.utils.ParseTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * @Author czh
 * @desc 基本信息模块
 * @date 2023/5/17 10:46
 */
@Slf4j
@RestController
@RequestMapping("/info")
public class InformationController {
    @Autowired
    ParseTokenUtil parseTokenUtil;
    @Autowired
    InformationService informationService;

    /**
     * @Author czh
     * @desc 修改用户名
     * @date 2023/5/17 10:46
     */
    @PutMapping("/username/modify")
    public ResponseResult<Object> usernameModify(@RequestHeader String token, @RequestBody Map<String, String> requestData) {
        String newUsername = requestData.get("newUsername");
        return informationService.usernameModify(token, newUsername);
    }

    /**
     * @Author czh
     * @desc 修改密码(通过旧密码)
     * @date 2023/5/17 10:46
     */
    @PutMapping("/password/modify/byPassword")
    public ResponseResult<Object> passwordModifyByPassword(@RequestHeader String token, @RequestBody Map<String, String> requestData) {
        String oldPassword = requestData.get("oldPassword");
        String newPassword = requestData.get("newPassword");
        return informationService.passwordModifyByPassword(token, oldPassword, newPassword);
    }

    /**
     * @Author czh
     * @desc 修改密码(通过手机验证码)
     * @date 2023/5/17 10:46
     */
    @PutMapping("/password/modify/byPhone")
    public ResponseResult<Object> passwordModifyByPhone(@RequestHeader String token, @RequestBody Map<String, String> requestData) {
        String code = requestData.get("code");
        String newPassword = requestData.get("newPassword");
        return informationService.passwordModifyByPhone(token, code, newPassword);
    }

    /**
     * @Author czh
     * @desc 修改密码(通过手机验证码)(获取验证码)(5分钟内有效)
     * @date 2023/5/17 10:46
     */
    @GetMapping("/password/modify/byPhone/getCode")
    public ResponseResult<Object> passwordModifyByPhoneGetCode(@RequestHeader String token, @RequestBody Map<String, String> requestData) throws ExecutionException, InterruptedException {
        return informationService.passwordModifyByPhoneGetCode(token);
    }

    /**
     * @Author czh
     * @desc 修改手机号
     * @date 2023/5/17 10:46
     */
    @PutMapping("/phone/modify")
    public ResponseResult<Object> phoneModify(@RequestHeader String token, @RequestBody Map<String, String> requestData) {
        String newPhoneNumber = requestData.get("newPhoneNumber");
        String newPhoneCode = requestData.get("newPhoneCode");
        return informationService.phoneModify(token, newPhoneNumber, newPhoneCode);
    }

    /**
     * @Author czh
     * @desc 修改手机号(获取验证码)(5分钟内有效)
     * @date 2023/5/17 10:46
     */
    @PostMapping("/phone/modify/getCode")
    public ResponseResult<Object> phoneModifyGetCode(@RequestHeader String token, @RequestBody Map<String, String> requestData) throws ExecutionException, InterruptedException {
        String newPhoneNumber = requestData.get("newPhoneNumber");
        return informationService.phoneModifyGetCode(token, newPhoneNumber);
    }

    /**
     * @Author czh
     * @desc 修改头像
     * @date 2023/5/17 10:46
     */
    @PostMapping("/picture/modify")
    public ResponseResult<Object> modifyPicture(@RequestHeader String token, @RequestParam("file") MultipartFile file) {
        return informationService.modifyPicture(token, file);
    }

    /**
     * @Author czh
     * @desc TODO 查看个人信息
     * @date 2023/5/17 10:46
     */
    @GetMapping("/view")
    public ResponseResult<Object> viewInformation(@RequestHeader String token) {
        return informationService.viewInformation(token);
    }
}
