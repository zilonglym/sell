package com.gittoy.form;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class ScoreFunctionForm {
    private String sellerName;

    private BigDecimal scoreRatio;

    private Integer threshold;
    
    private Integer scoreStatus;

    private String describeInfo;
}
