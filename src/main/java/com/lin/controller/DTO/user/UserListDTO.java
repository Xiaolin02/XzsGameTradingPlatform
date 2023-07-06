package com.lin.controller.DTO.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author czh
 * @desc 用于用户列表的返回，内含用户简单信息列表，以及总记录数
 * @date 2023/7/6 10:42
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserListDTO<T> {
    private List<T> userList;
    private Long total;
}
