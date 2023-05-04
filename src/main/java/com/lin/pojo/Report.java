package com.lin.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author czh
 * @desc 举报记录实体类
 * @date 2023/5/4 7:54
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Report {
    @TableId(type = IdType.AUTO)
    private Integer reportId;
    private String reason;
    private Integer reporterId;
    private Integer reportedUserId;
    private Integer reportedCommodityId;
    private String reportTime;
}
