package com.lin.controller;

import com.lin.common.ResponseResult;
import com.lin.controller.DTO.General.PageDTO;
import com.lin.service.FavoriteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author czh
 * @desc 收藏，目前只有对商品的 增删查 收藏
 * @date 2023/5/3 18:49
 */
@Slf4j
@RestController
@RequestMapping("/favorite")
public class FavoriteController {
    @Autowired
    FavoriteService favoriteService;

    @PostMapping("/commodity/{commodityId}")
    public ResponseResult insertFavorite(@RequestHeader String token, @PathVariable Integer commodityId) {
        return favoriteService.insertFavorite(token, commodityId);
    }

    @DeleteMapping("/commodity/{commodityId}")
    public ResponseResult deleteFavorite(@RequestHeader String token, @PathVariable Integer commodityId) {
        return favoriteService.deleteFavorite(token, commodityId);
    }

    @GetMapping("/commodity")
    public ResponseResult selectFavoriteCommodity(@RequestHeader String token, @RequestBody PageDTO pageDTO) {
        return favoriteService.selectFavoriteCommodity(token, pageDTO);
    }

}
