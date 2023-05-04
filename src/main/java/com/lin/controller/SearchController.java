package com.lin.controller;

import com.lin.common.ResponseResult;
import com.lin.controller.DTO.SearchCommodityDTO;
import com.lin.service.impl.CommodityServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author czh
 * @desc 搜索：支持 查 商品
 * @date 2023/5/3 18:49
 */
@Slf4j
@RestController
@RequestMapping("/search")
public class SearchController {
    @Autowired
    CommodityServiceImpl commodityService;

    /**
     * @Author czh
     * @desc 搜索商品，支持按时间、价格、议价、描述来搜索商品（仅能搜索售卖中(status=1)的商品）
     * @date 2023/5/4 13:50
     */
    @GetMapping("/commodity")
    public ResponseResult<Object> searchCommodity(@RequestHeader String token, @RequestBody SearchCommodityDTO searchDTO) {
        return commodityService.searchCommodity(searchDTO);
    }
}
