package com.lin.controller.DTO.user;

import com.lin.pojo.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author czh
 * @desc 用户信息模型（迷你）（只有名字）用于最少的信息查看，如商品只显示发布者名/关注的人
 * @date 2023/5/22 11:47
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserMiniDTO {
    private Integer userId;
    private String username;

    public UserMiniDTO(User user) {
        this.userId = user.getUserId();
        this.username = user.getUsername();
    }

}
