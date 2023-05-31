package com.lin.controller.DTO.user;

import com.lin.mapper.UserMapper;
import com.lin.pojo.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author czh
 * @desc 用户信息模型（完全）（自己看自己）用于全部的信息查看，如自己查看自己的信息
 * @date 2023/5/22 11:33
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCompleteDTO {
    private Integer userId;
    private String username;
    private String pictureUrl;
    private String phone;
    protected Integer balance;
    protected String registerAt;

    public UserCompleteDTO(User user, UserMapper userMapper) {
        loadUser(user);
        this.pictureUrl = userMapper.getPictureUrl(user.getUserId());
    }

    private void loadUser(User user) {
        this.userId = user.getUserId();
        this.username = user.getUsername();
        this.phone = user.getPhone();
        this.balance = user.getBalance();
        this.registerAt = user.getRegisterAt();
    }
}
