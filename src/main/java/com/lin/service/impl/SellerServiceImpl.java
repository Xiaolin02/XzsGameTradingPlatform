package com.lin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lin.common.CodeConstants;
import com.lin.common.CommodityStatusConstants;
import com.lin.common.ResponseResult;
import com.lin.controller.DTO.ReleaseDTO;
import com.lin.mapper.AccountMapper;
import com.lin.mapper.CommodityMapper;
import com.lin.pojo.Account;
import com.lin.pojo.Commodity;
import com.lin.pojo.User;
import com.lin.service.SellerService;
import com.lin.utils.DateUtil;
import com.lin.utils.OssUtil;
import com.lin.utils.ParseTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author 林炳昌
 * @desc SellerService实现类
 * @date 2023年05月07日 16:36
 */
@Service
public class SellerServiceImpl implements SellerService {

    @Autowired
    CommodityMapper commodityMapper;

    @Autowired
    ParseTokenUtil parseTokenUtil;

    @Autowired
    OssUtil ossUtil;

    @Autowired
    AccountMapper accountMapper;

    @Override
    public ResponseResult release(String token, ReleaseDTO releaseDTO) {

        User user = parseTokenUtil.parseTokenToGetUser(token);
        int count = commodityMapper.selectCount(null).intValue();
        Commodity commodity = new Commodity();
        commodity.setCommodityId(count + 1);
        commodity.setReleaseTime(DateUtil.getDateTime());
        commodity.setTitle(releaseDTO.getTitle());
        commodity.setDescription(releaseDTO.getDescription());
        commodity.setPrice(releaseDTO.getPrice());
        commodity.setSellerId(user.getUserId());
        commodity.setGame(releaseDTO.getGame());
        commodity.setAllowBargaining(releaseDTO.getAllowBargaining());
        commodity.setStatus(CommodityStatusConstants.STATUS_INSPECTING);
        commodityMapper.insert(commodity);
        Account account = new Account();
        account.setCommodityId(count + 1);
        account.setAccountNumber(releaseDTO.getAccountNumber());
        account.setAccountPassword(releaseDTO.getAccountPassword());
        accountMapper.insert(account);
        return new ResponseResult(CodeConstants.CODE_SUCCESS, "发布成功");

    }

    @Override
    public ResponseResult upload(String token, MultipartFile[] files) {

        User user = parseTokenUtil.parseTokenToGetUser(token);
        for (MultipartFile file : files) {
            ossUtil.uploadfile(file, user.getUserId(), "release");
        }

        return new ResponseResult<>(CodeConstants.CODE_SUCCESS, "上传成功");

    }

}
