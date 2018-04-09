package com.gittoy.dataobject;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gittoy.enums.ScoreStatusEnum;
import com.gittoy.utils.EnumUtil;

import lombok.Data;

/**
 * ScoreFunctionInfo
 * Create By lzhao 2018/04/09
 */
@Data
@Entity
public class ScoreFunction {

	@Id
    private String sellerName;

    private BigDecimal scoreRatio;

    private Integer threshold;
    
    private Integer scoreStatus;

    private String describeInfo;
    
    /** 商品在架状态 */
    @JsonIgnore
    public ScoreStatusEnum getScoreStatusEnum() {
        return EnumUtil.getByCode(scoreStatus, ScoreStatusEnum.class);
    }
}
