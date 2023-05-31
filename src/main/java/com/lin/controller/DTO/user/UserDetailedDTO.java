package com.lin.controller.DTO.user;

import com.lin.mapper.UserMapper;
import com.lin.pojo.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author czh
 * @desc 用户信息模型（详情）（访客查看）用于基本的信息查看，如访客查看其他人的信息
 * @date 2023/5/22 11:26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailedDTO {
    private Integer userId;
    private String username;
    private String pictureUrl;
    private String phone;

    public UserDetailedDTO(User user, UserMapper userMapper) {
        this(user);
        this.pictureUrl = userMapper.getPictureUrl(user.getUserId());
    }

    public UserDetailedDTO(User user) {
        this.userId = user.getUserId();
        this.username = user.getUsername();
        this.phone = user.getPhone();
    }
}
