package com.lin.controller.DTO.user;

import com.lin.controller.DTO.general.PageDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author czh
 * @desc 搜索用户DTO
 * @date 2023/5/31 12:17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchUserDTO {
    private String keyword;
    private PageDTO page;
}

