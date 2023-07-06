package com.lin.controller.DTO.commodity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author czh
 * @desc 用于商品列表的返回，内含商品简单信息列表，以及总记录数
 * @date 2023/7/6 10:33
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommodityListDTO<T> {
    private List<T> commodityList;
    private Long total;
}
