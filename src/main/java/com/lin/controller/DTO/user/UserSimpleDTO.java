package com.lin.controller.DTO.user;

import com.lin.mapper.UserMapper;
import com.lin.pojo.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author czh
 * @desc 用户信息模型（简单）（名称头像）用于简单的信息查看，如搜索列表展示/页面右上角头像
 * @date 2023/5/22 11:24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSimpleDTO {
    private Integer userId;
    private String username;
    private String pictureUrl;

    public UserSimpleDTO(User user, UserMapper userMapper) {
        this(user);
        this.pictureUrl = userMapper.getPictureUrl(user.getUserId());
    }

    private UserSimpleDTO(User user) {
        this.userId = user.getUserId();
        this.username = user.getUsername();
    }
}
