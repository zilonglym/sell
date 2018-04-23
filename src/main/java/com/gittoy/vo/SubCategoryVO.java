package com.gittoy.vo;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
@Data
public class SubCategoryVO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@JsonProperty("subname")
    private String categorySubName;

    @JsonProperty("subtype")
    private Integer categorySubType;

    @JsonProperty("subfoods")
    private List<ProductInfoVO> productInfoVOList;
}
