package com.lin.controller;

import com.lin.common.ResponseResult;
import com.lin.controller.DTO.MessageDTO;
import com.lin.controller.DTO.ReportCommodityDTO;
import com.lin.controller.DTO.SearchDTO;
import com.lin.service.impl.CommodityServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author czh
 * @desc 公共模块
 * @date 2023/4/26 21:02
 */
@Slf4j
@RestController
public class PublicController {
    @Autowired
    CommodityServiceImpl commodityService;

    @GetMapping("/searchCommodity")
    public ResponseResult searchCommodity(@RequestHeader String token, @RequestBody SearchDTO searchDTO) {
        return commodityService.searchCommodity(token, searchDTO);
    }

    @PostMapping("/favoriteCommodity/{commodityId}")
    public ResponseResult favoriteCommodity(@RequestHeader String token, @PathVariable Integer commodityId) {
        return commodityService.favoriteCommodity(token, commodityId);
    }

    @PostMapping("/reportCommodity")
    public ResponseResult reportCommodity(@RequestHeader String token, @RequestBody ReportCommodityDTO reportCommodityDTO) {
        return commodityService.reportCommodity(token, reportCommodityDTO.getCommodityId(), reportCommodityDTO.getReason());
    }
}
