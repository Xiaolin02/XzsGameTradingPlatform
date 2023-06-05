package com.lin.controller;

import com.lin.common.NullData;
import com.lin.common.ResponseResult;
import com.lin.controller.DTO.MessageDTO;
import com.lin.controller.DTO.ReportCommodityViewDTO;
import com.lin.controller.DTO.ReportUserViewDTO;
import com.lin.controller.DTO.commodity.CommodityCompleteDTO;
import com.lin.controller.DTO.commodity.CommoditySimpleDTO;
import com.lin.controller.DTO.general.PageDTO;
import com.lin.controller.DTO.user.UserCompleteDTO;
import com.lin.service.ManagerService;
import com.lin.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


/**
 * @Author czh
 * @desc 管理员controller
 * @date 2023/5/31 16:09
 */
@Slf4j
@RestController
@RequestMapping("/manager")
@PreAuthorize("hasRole('ROLE_MANAGER')")
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
    public ResponseResult<NullData> pushSystemMsgToOneUser(@RequestHeader String token, @PathVariable Integer receiverId, @RequestBody MessageDTO messageDTO) {
        return messageService.pushSystemMsgToOneUser(token, receiverId, messageDTO.getContent(), messageDTO.getTitle());
    }

    /**
     * @desc 完成举报处理（举报用户）
     * @date 2023/5/31 17:36
     */
    @DeleteMapping("/user/reported/done/{reportId}")
    public ResponseResult<NullData> userReportDone(@PathVariable Integer reportId) {
        return managerService.userReportDone(reportId);
    }

    /**
     * @desc 查看未处理的全部举报（举报用户）
     * @date 2023/5/31 17:36
     */
    @PostMapping("/user/reported/view/all")
    public ResponseResult<Map<String, List<ReportUserViewDTO>>> userReportViewAll(@RequestBody PageDTO pageDTO) {
        return managerService.userReportViewAll(pageDTO);
    }

    /**
     * @desc 查看某个用户（全部信息）
     * @date 2023/5/31 17:36
     */
    @GetMapping("/user/view/one/{userId}")
    public ResponseResult<UserCompleteDTO> userViewOne(@PathVariable Integer userId) {
        return managerService.userViewOne(userId);
    }

    /**
     * @desc 审核通过商品
     * @date 2023/5/31 17:36
     */
    @PutMapping("/commodity/inspect/allow/{commodityId}")
    public ResponseResult<NullData> commodityInspectAllow(@PathVariable Integer commodityId) {
        return managerService.commodityInspectAllow(commodityId);
    }

    /**
     * @desc 审核下架商品
     * @date 2023/5/31 17:36
     */
    @PutMapping("/commodity/inspect/reject/{commodityId}")
    public ResponseResult<NullData> commodityInspectReject(@PathVariable Integer commodityId) {
        return managerService.commodityInspectReject(commodityId);
    }

    /**
     * @desc 查看未审核的全部商品
     * @date 2023/5/31 17:36
     */
    @PostMapping("/commodity/view/all")
    public ResponseResult<Map<String, List<CommoditySimpleDTO>>> commodityReportViewAll(@RequestBody PageDTO pageDTO) {
        return managerService.commodityInspectViewAll(pageDTO);
    }

    /**
     * @desc 完成举报处理（举报商品）
     * @date 2023/5/31 17:36
     */
    @DeleteMapping("/commodity/reported/done/{reportId}")
    public ResponseResult<NullData> commodityReportDone(@PathVariable Integer reportId) {
        return managerService.commodityReportDone(reportId);
    }

    /**
     * @desc 查看未处理的全部举报（举报用户）
     * @date 2023/5/31 17:36
     */
    @PostMapping("/commodity/reported/view/all")
    public ResponseResult<Map<String, List<ReportCommodityViewDTO>>> commodityInspectViewAll(@RequestBody PageDTO pageDTO) {
        return managerService.commodityReportViewAll(pageDTO);
    }

    /**
     * @desc 查看某个商品（全部信息）
     * @date 2023/5/31 17:36
     */
    @GetMapping("/commodity/view/one/{commodityId}")
    public ResponseResult<CommodityCompleteDTO> commodityViewOne(@PathVariable Integer commodityId) {
        return managerService.commodityViewOne(commodityId);
    }
}
