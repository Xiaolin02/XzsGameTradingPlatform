package com.lin.controller;

import com.lin.common.ResponseResult;
import com.lin.controller.DTO.ReportDTO;
import com.lin.service.ReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author czh
 * @desc 举报：支持 增删 举报记录
 * @date 2023/5/4 9:14
 */
@Slf4j
@RestController
@RequestMapping("/report")
public class ReportController {

    @Autowired
    ReportService reportService;

    /**
     * @Author czh
     * @desc 新增举报（目前可重复举报）
     * @date 2023/5/4 13:50
     */
    @PostMapping("")
    public ResponseResult<Object> insertReport(@RequestHeader String token, @RequestBody ReportDTO reportDTO) {
        return reportService.insertReport(token, reportDTO);
    }

    /**
     * @Author czh
     * @desc 删除举报（只能删除自己的举报记录）
     * @date 2023/5/4 13:50
     */
    @DeleteMapping("/{reportId}")
    public ResponseResult<Object> deleteReport(@RequestHeader String token, @PathVariable Integer reportId) {
        return reportService.deleteReport(token, reportId);
    }

}
