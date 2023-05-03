package com.lin.controller.DTO.General;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author czh
 * @desc 通用分页查询
 * @date 2023/5/3 20:07
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageDTO {
    private Integer pageNum;
    private Integer pageSize;

    public <T> Page<T> toPage() {
        return new Page<>(pageNum, pageSize);
    }
}
