package com.gittoy.vo;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class CategoryList implements Serializable{
	
	private static final long serialVersionUID = -8662972778381226179L;

	@JsonProperty("category")
    private List<String> category;
}
