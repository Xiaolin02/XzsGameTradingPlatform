package com.lin.controller;

import com.lin.common.ResponseResult;
import com.lin.controller.DTO.SearchCommodityDTO;
import com.lin.service.impl.CommodityServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author czh
 * @desc 搜索，目前只有搜索商品
 * @date 2023/5/3 18:49
 */
@Slf4j
@RestController
@RequestMapping("/search")
public class SearchController {
    @Autowired
    CommodityServiceImpl commodityService;

    @GetMapping("/commodity")
    public ResponseResult<Object> searchCommodity(@RequestHeader String token, @RequestBody SearchCommodityDTO searchDTO) {
        return commodityService.searchCommodity(searchDTO);
    }
}
