package com.lin.controller.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author 林炳昌
 * @desc 出价模型
 * @date 2023年04月20日 16:52
 */
@Data
@AllArgsConstructor
public class OfferDTO {

    private Integer commodityId;
    private Integer money;

}
