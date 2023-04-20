package com.lin.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 林炳昌
 * @desc 权限菜单
 * @date 2023年03月31日 22:40
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Menu {

    @TableId
    private Long id;
    private String menuName;
    private String permKey;
    private Boolean status;

}
