package com.lin.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author czh
 * @desc 举报用户的记录的实体类
 * @date 2023/5/4 7:54
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportUser {
    @TableId(type = IdType.AUTO)
    private Integer reportId;
    private Integer reporterId;
    private Integer userId;
    private String reason;
    private String reportAt;
}
