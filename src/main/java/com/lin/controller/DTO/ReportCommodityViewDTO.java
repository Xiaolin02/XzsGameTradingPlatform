package com.lin.controller.DTO;

import com.lin.controller.DTO.commodity.CommoditySimpleDTO;
import com.lin.controller.DTO.user.UserSimpleDTO;
import com.lin.mapper.CommodityMapper;
import com.lin.mapper.UserMapper;
import com.lin.pojo.ReportCommodity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author czh
 * @desc TODO 用于管理员查看report
 * @date 2023/5/31 19:59
 */
@Data
public class ReportCommodityViewDTO {
    private Integer reportId;
    private UserSimpleDTO reporter;
    private CommoditySimpleDTO reportedCommodity;
    private String reason;
    private String reportAt;

    public ReportCommodityViewDTO(ReportCommodity reportCommodity, UserMapper userMapper, CommodityMapper commodityMapper) {
        this(reportCommodity);
        this.reporter = new UserSimpleDTO(userMapper.selectById(reportCommodity.getReporterId()), userMapper);
        this.reportedCommodity = new CommoditySimpleDTO(commodityMapper.selectById(reportCommodity.getCommodityId()),userMapper);
    }

    private ReportCommodityViewDTO(ReportCommodity reportCommodity) {
        this.reportId = reportCommodity.getReportId();
        this.reason = reportCommodity.getReason();
        this.reportAt = reportCommodity.getReportAt();
    }
}
