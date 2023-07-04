package com.lin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lin.common.DefaultValue;
import com.lin.pojo.Commodity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author 林炳昌
 * @desc
 * @date 2023年04月20日 21:52
 */
@Mapper
public interface CommodityMapper extends BaseMapper<Commodity> {
    void offer(Integer commodityId, Integer buyerId, Integer offer);

    void insertPictureUrl(Integer commodityId, String url);

    List<String> selectPictureUrl(Integer commodityId);

    /**
     * @desc 通过商品id获得商品的封面，若为null，则返回一个默认url
     * @return 商品封面url
     */
    default String selectCoverUrl(Integer commodityId) {
        List<String> urlList = selectPictureUrl(commodityId);
        if (urlList.size() == 0) {
            return DefaultValue.DEFAULT_COVER_URL;
        }
        return urlList.get(0);
    }
}
