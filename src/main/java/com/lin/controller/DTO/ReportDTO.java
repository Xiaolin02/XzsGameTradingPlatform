package com.lin.controller.DTO;

import lombok.Data;
import org.springframework.lang.Nullable;

/**
 * @Author czh
 * @desc TODO
 * @date 2023/5/4 8:58
 */
@Data
public class ReportDTO {
    private String reason;
    @Nullable
    private Integer reportedCommodityId;
    @Nullable
    private Integer reportedUserId;
}
