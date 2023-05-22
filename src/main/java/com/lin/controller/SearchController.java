package com.lin.controller;

import com.lin.common.ResponseResult;
import com.lin.controller.DTO.commodity.SearchCommodityDTO;
import com.lin.service.impl.SearchServiceImpl;
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
    SearchServiceImpl searchService;

    /**
     * @Author czh
     * @desc 搜索商品列表，支持按时间、价格、议价、描述来搜索商品（仅能搜索售卖中(status=1)的商品）
     * @date 2023/5/4 13:50
     */
    @PostMapping("/commodity/list")
    public ResponseResult<Object> searchCommodityList(@RequestHeader String token, @RequestBody SearchCommodityDTO searchDTO) {
        return searchService.searchCommodity(searchDTO);
    }
    /**
     * @Author czh
     * @desc TODO 搜索单个商品
     * @date 2023/5/22 10:16
     */
    @GetMapping("/commodity/one/{commodityId}")
    public ResponseResult<Object> searchCommodityOne(@RequestHeader String token, @PathVariable String commodityId) {
        return null;
    }
    /**
     * @Author czh
     * @desc TODO 获取推荐商品列表
     * @date 2023/5/22 10:16
     */
    @GetMapping("/commodity/recommend")
    public ResponseResult<Object> searchCommodityRecommend(@RequestHeader String token) {
        return null;
    }
    /**
     * @Author czh
     * @desc TODO 搜索用户列表
     * @date 2023/5/22 10:16
     */
    @GetMapping("/user/list")
    public ResponseResult<Object> searchUserList(@RequestHeader String token) {
        return null;
    }
    /**
     * @Author czh
     * @desc TODO 搜索某个用户
     * @date 2023/5/22 10:16
     */
    @GetMapping("/user/one/{userId}")
    public ResponseResult<Object> searchUserOne(@RequestHeader String token, @PathVariable String userId) {
        return null;
    }
}
