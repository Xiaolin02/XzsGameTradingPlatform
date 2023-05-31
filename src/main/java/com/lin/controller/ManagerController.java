package com.lin.controller;

import com.lin.common.ResponseResult;
import com.lin.controller.DTO.MessageDTO;
import com.lin.controller.DTO.general.PageDTO;
import com.lin.service.ManagerService;
import com.lin.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * @Author czh
 * @desc 管理员controller
 * @date 2023/5/31 16:09
 */
@Slf4j
@RestController
@RequestMapping("/manager")
public class ManagerController {
    @Autowired
    ManagerService managerService;
    @Autowired
    MessageService messageService;

    /**
     * @desc 通知用户
     * @date 2023/5/31 17:36
     */
    @PostMapping("/user/inform/{receiverId}")
    public ResponseResult pushSystemMsgToOneUser(@RequestHeader String token, @PathVariable Integer receiverId, @RequestBody MessageDTO messageDTO) {
        return messageService.pushSystemMsgToOneUser(token, receiverId, messageDTO.getContent(), messageDTO.getTitle());
    }

    /**
     * @desc 完成举报处理（举报用户）
     * @date 2023/5/31 17:36
     */
    @DeleteMapping("/user/reported/done/{reportId}")
    public ResponseResult<Object> UserReportDone(@PathVariable Integer reportId) {
        return managerService.UserReportDone(reportId);
    }

    /**
     * @desc 查看未处理的全部举报（举报用户）
     * @date 2023/5/31 17:36
     */
    @PostMapping("/user/reported/view/all")
    public ResponseResult<Object> UserReportViewAll(@RequestBody PageDTO pageDTO) {
        return managerService.UserReportViewAll(pageDTO);
    }

    /**
     * @desc 查看某个用户（全部信息）
     * @date 2023/5/31 17:36
     */
    @GetMapping("/user/view/one/{userId}")
    public ResponseResult<Object> UserViewOne(@PathVariable Integer userId) {
        return managerService.UserViewOne(userId);
    }

    /**
     * @desc 审核通过商品
     * @date 2023/5/31 17:36
     */
    @PutMapping("/commodity/inspect/allow/{commodityId}")
    public ResponseResult<Object> CommodityInspectAllow(@PathVariable Integer commodityId) {
        return managerService.CommodityInspectAllow(commodityId);
    }

    /**
     * @desc 审核下架商品
     * @date 2023/5/31 17:36
     */
    @PutMapping("/commodity/inspect/reject/{commodityId}")
    public ResponseResult<Object> CommodityInspectReject(@PathVariable Integer commodityId) {
        return managerService.CommodityInspectReject(commodityId);
    }

    /**
     * @desc 查看未审核的全部商品
     * @date 2023/5/31 17:36
     */
    @PostMapping("/commodity/view/all")
    public ResponseResult<Object> CommodityReportViewAll(@RequestBody PageDTO pageDTO) {
        return managerService.CommodityInspectViewAll(pageDTO);
    }

    /**
     * @desc 完成举报处理（举报商品）
     * @date 2023/5/31 17:36
     */
    @DeleteMapping("/commodity/reported/done/{reportId}")
    public ResponseResult<Object> CommodityReportDone(@PathVariable Integer reportId) {
        return managerService.CommodityReportDone(reportId);
    }

    /**
     * @desc 查看未处理的全部举报（举报用户）
     * @date 2023/5/31 17:36
     */
    @PostMapping("/commodity/reported/view/all")
    public ResponseResult<Object> CommodityInspectViewAll(@RequestBody PageDTO pageDTO) {
        return managerService.CommodityReportViewAll(pageDTO);
    }

    /**
     * @desc 查看某个商品（全部信息）
     * @date 2023/5/31 17:36
     */
    @GetMapping("/commodity/view/one/{commodityId}")
    public ResponseResult<Object> CommodityViewOne(@PathVariable Integer commodityId) {
        return managerService.CommodityViewOne(commodityId);
    }
}
