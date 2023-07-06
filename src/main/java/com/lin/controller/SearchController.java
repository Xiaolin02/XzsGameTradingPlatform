package com.lin.controller;

import com.lin.common.ResponseResult;
import com.lin.controller.DTO.commodity.CommodityDetailedDTO;
import com.lin.controller.DTO.commodity.CommodityListDTO;
import com.lin.controller.DTO.commodity.CommoditySimpleDTO;
import com.lin.controller.DTO.commodity.SearchCommodityDTO;
import com.lin.controller.DTO.general.PageDTO;
import com.lin.controller.DTO.user.SearchUserDTO;
import com.lin.controller.DTO.user.UserDetailedDTO;
import com.lin.controller.DTO.user.UserListDTO;
import com.lin.controller.DTO.user.UserSimpleDTO;
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
     * @desc 获取推荐商品列表
     * @date 2023/5/22 10:16
     */
    @PostMapping("/commodity/recommend")
    public ResponseResult<CommodityListDTO<CommoditySimpleDTO>> searchCommodityRecommend(@RequestBody PageDTO pageDTO) {
        return searchService.searchCommodityRecommend(pageDTO);
    }

    /**
     * @Author czh
     * @desc 搜索商品列表，支持按时间、价格、议价、描述来搜索商品（仅能搜索售卖中(status=1)的商品）
     * @date 2023/5/4 13:50
     */
    @PostMapping("/commodity/list")
    public ResponseResult<CommodityListDTO<CommoditySimpleDTO>> searchCommodityList(@RequestBody SearchCommodityDTO searchDTO) {
        return searchService.searchCommodityList(searchDTO);
    }

    /**
     * @Author czh
     * @desc 搜索单个商品
     * @date 2023/5/22 10:16
     */
    @GetMapping("/commodity/one/{commodityId}")
    public ResponseResult<CommodityDetailedDTO> searchCommodityOne(@PathVariable String commodityId) {
        return searchService.searchCommodityOne(commodityId);
    }

    /**
     * @Author czh
     * @desc 搜索用户列表
     * @date 2023/5/22 10:16
     */
    @PostMapping("/user/list")
    public ResponseResult<UserListDTO<UserSimpleDTO>> searchUserList(@RequestBody SearchUserDTO searchUserDTO) {
        return searchService.searchUserList(searchUserDTO);
    }

    /**
     * @Author czh
     * @desc 搜索某个用户
     * @date 2023/5/22 10:16
     */
    @GetMapping("/user/one/{userId}")
    public ResponseResult<UserDetailedDTO> searchUserOne(@PathVariable String userId) {
        return searchService.searchUserOne(userId);
    }
}
