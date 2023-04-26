package com.lin.controller.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import java.util.List;

/**
 * @Author czh
 * @desc 搜索商品模型
 * @date 2023/4/23 22:12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchDTO {
    @Nullable
    private String releaseTimeEarliest;
    @Nullable
    private String releaseTimeLatest;
    @Nullable
    private Integer priceMax;
    @Nullable
    private Integer priceMin;
    @Nullable
    private Boolean allowBargaining;
    @Nullable
    private List<String> descriptionDesired;
    private Integer pageSize;
    private Integer pageNum;

    public Integer getOffset() {
        return (pageNum - 1) * pageSize;
    }
}

