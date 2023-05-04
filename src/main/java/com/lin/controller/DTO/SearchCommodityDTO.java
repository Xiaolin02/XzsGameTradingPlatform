package com.lin.controller.DTO;

import com.lin.controller.DTO.General.PageDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import java.util.List;

/**
 * @Author czh
 * @desc 搜索商品DTO
 * @date 2023/4/23 22:12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchCommodityDTO {
    @Nullable
    private Integer priceMax;
    @Nullable
    private Integer priceMin;
    @Nullable
    private String releaseTimeEarliest;
    @Nullable
    private String releaseTimeLatest;
    @Nullable
    private Boolean allowBargaining;
    @Nullable
    private List<String> descriptionDesiredList;
    private PageDTO page;
}

