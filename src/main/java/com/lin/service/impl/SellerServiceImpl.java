package com.lin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lin.common.CodeConstants;
import com.lin.common.CommodityStatusConstants;
import com.lin.common.ResponseResult;
import com.lin.controller.DTO.ReleaseDTO;
import com.lin.controller.VO.ViewReleasedVO;
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

import java.util.ArrayList;
import java.util.List;

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
    public ResponseResult release(String token, ReleaseDTO releaseDTO, MultipartFile[] files) {

        Integer userId = parseTokenUtil.parseTokenToUserId(token);
        int count = commodityMapper.selectCount(null).intValue();
        Commodity commodity = new Commodity();
        commodity.setCommodityId(count + 1);
        commodity.setReleaseTime(DateUtil.getDateTime());
        commodity.setTitle(releaseDTO.getTitle());
        commodity.setDescription(releaseDTO.getDescription());
        commodity.setPrice(releaseDTO.getPrice());
        commodity.setSellerId(userId);
        commodity.setGame(releaseDTO.getGame());
        commodity.setAllowBargaining(releaseDTO.getAllowBargaining());
        commodity.setStatus(CommodityStatusConstants.STATUS_INSPECTING);
        commodityMapper.insert(commodity);
        Account account = new Account();
        account.setCommodityId(count + 1);
        account.setAccountNumber(releaseDTO.getAccountNumber());
        account.setAccountPassword(releaseDTO.getAccountPassword());
        accountMapper.insert(account);
        for (MultipartFile file : files) {
            String url = ossUtil.uploadfile(file, userId, "release");
            commodityMapper.addUrl(count + 1,url);
        }
        return new ResponseResult(CodeConstants.CODE_SUCCESS, "发布成功");

    }

    @Override
    public ResponseResult view(String token) {


        QueryWrapper<Commodity> wrapper = new QueryWrapper<>();
        wrapper.eq("seller_id", parseTokenUtil.parseTokenToUserId(token));
        List<Commodity> commodities = commodityMapper.selectList(wrapper);
        ArrayList<ViewReleasedVO> releasedVOS = new ArrayList<>();
        Integer status;
        for (Commodity commodity : commodities) {

            ViewReleasedVO releasedVO = new ViewReleasedVO();
            releasedVO.setReleasedTime(commodity.getReleaseTime());
            releasedVO.setTitle(commodity.getTitle());
            releasedVO.setDescription(commodity.getDescription());
            releasedVO.setGame(commodity.getGame());
            releasedVO.setPrice(commodity.getPrice());
            releasedVO.setAllowBargaining(commodity.getAllowBargaining());
            status = commodity.getStatus();
            if(status == CommodityStatusConstants.STATUS_INSPECTING)
                releasedVO.setStatus("审核中");
            else if (status == CommodityStatusConstants.STATUS_SELLING)
                releasedVO.setStatus("售卖中");
            else if (status == CommodityStatusConstants.STATUS_SOLD)
                releasedVO.setStatus("已售出");
            else if (status == CommodityStatusConstants.STATUS_CANCEL)
                releasedVO.setStatus("已下架");
            else
                releasedVO.setStatus("审核失败");
            releasedVOS.add(releasedVO);

        }

        return new ResponseResult<>(CodeConstants.CODE_SUCCESS, releasedVOS);


    }

}
