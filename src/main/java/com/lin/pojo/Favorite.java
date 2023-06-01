package com.lin.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author czh
 * @desc 收藏实体类
 * @date 2023/5/3 19:15
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Favorite {
    private Integer userId;
    private Integer commodityId;
}
