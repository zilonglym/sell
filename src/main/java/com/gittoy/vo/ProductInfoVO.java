package com.gittoy.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 商品详情
 * Create By GaoYu 2017/11/14 8:45
 */
@Data
public class ProductInfoVO implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -2358478133508878427L;

	@JsonProperty("id")
    private String productId;

    @JsonProperty("name")
    private String productName;

    @JsonProperty("price")
    private BigDecimal productPrice;

    @JsonProperty("description")
    private String productDescription;

    @JsonProperty("icon")
    private String productIcon;
}
