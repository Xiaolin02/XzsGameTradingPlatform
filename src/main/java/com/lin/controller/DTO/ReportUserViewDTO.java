package com.lin.controller.DTO;

import com.lin.controller.DTO.user.UserSimpleDTO;
import com.lin.mapper.UserMapper;
import com.lin.pojo.ReportUser;
import lombok.Data;

/**
 * @Author czh
 * @desc 用于管理员查看report
 * @date 2023/5/31 19:59
 */
@Data
public class ReportUserViewDTO {
    private Integer reportId;
    private UserSimpleDTO reporter;
    private UserSimpleDTO reportedUser;
    private String reason;
    private String reportAt;

    public ReportUserViewDTO(ReportUser reportUser, UserMapper userMapper) {
        this(reportUser);
        this.reporter = new UserSimpleDTO(userMapper.selectById(reportUser.getReporterId()), userMapper);
        this.reportedUser = new UserSimpleDTO(userMapper.selectById(reportUser.getUserId()), userMapper);
    }

    private ReportUserViewDTO(ReportUser reportUser) {
        this.reportId = reportUser.getReportId();
        this.reason = reportUser.getReason();
        this.reportAt = reportUser.getReportAt();
    }
}
