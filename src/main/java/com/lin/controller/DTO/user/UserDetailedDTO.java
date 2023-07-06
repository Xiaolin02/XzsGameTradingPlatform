package com.lin.controller.DTO.user;

import com.lin.mapper.UserMapper;
import com.lin.pojo.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.DecimalFormat;

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
    private Integer transactionsNumber;
    private Integer successNumber;
    private double successRate;

    public UserDetailedDTO(User user, UserMapper userMapper) {
        loadUser(user);
        this.pictureUrl = userMapper.getPictureUrl(user.getUserId());
        this.transactionsNumber = user.getTransactionsNumber();
        this.successNumber = user.getSuccessNumber();
        if(transactionsNumber == 0 || successNumber == 0) {
            this.successRate = Double.parseDouble(new DecimalFormat("0.00").format(0));
        } else {
            this.successRate = Double.parseDouble(new DecimalFormat("0.00").format((double)successNumber / transactionsNumber));
        }
    }

    private void loadUser(User user) {
        this.userId = user.getUserId();
        this.username = user.getUsername();
        this.phone = user.getPhone();
    }

}
