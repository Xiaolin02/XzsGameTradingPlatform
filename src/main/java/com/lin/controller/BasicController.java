package com.lin.controller;

import com.lin.common.ResponseResult;
import com.lin.controller.DTO.UserDTO;
import com.lin.service.impl.BasicServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 林炳昌
 * @desc 基础功能
 * @date 2023年04月16日 15:48
 */
@RestController
public class BasicController {

    @Autowired
    BasicServiceImpl basicService;

    @PostMapping("/login")
    public ResponseResult login(@RequestBody UserDTO userDTO) {
        return basicService.login(userDTO);
    }

}
