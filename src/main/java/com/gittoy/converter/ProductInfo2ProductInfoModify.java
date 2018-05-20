package com.gittoy.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.gittoy.dataobject.ProductCategory;
import com.gittoy.dataobject.ProductInfo;
import com.gittoy.dataobject.ProductInfoModify;
import com.gittoy.service.CategoryService;

public class ProductInfo2ProductInfoModify {

	public ProductInfoModify convert(ProductInfo productInfo, List<ProductCategory> productCategoryList){
		ProductInfoModify productInfoModify = new ProductInfoModify();
		productInfoModify.setProductId(productInfo.getProductId());
		productInfoModify.setProductName(productInfo.getProductName());
		productInfoModify.setProductPrice(productInfo.getProductPrice());
		productInfoModify.setProductDescription(productInfo.getProductDescription());
		productInfoModify.setProductIcon(productInfo.getProductIcon());
		productInfoModify.setProductStatus(productInfo.getProductStatus());
		productInfoModify.setProductStock(productInfo.getProductStock());
		productInfoModify.setCreateTime(productInfo.getCreateTime());
		productInfoModify.setUpdateTime(productInfo.getUpdateTime());
		
		ProductCategory categoryFinded = productCategoryList.stream().filter(a-> productInfo.getCategoryType().equals(a.getCategoryId()))
		.collect(Collectors.toList()).get(0);
		
		productInfoModify.setCategoryType(categoryFinded.getCategoryName()+"-"+categoryFinded.getCategorySubName());
		return productInfoModify;
	} 
	
	public List<ProductInfoModify> convert(List<ProductInfo> productInfoList, List<ProductCategory> productCategoryList) {
        return productInfoList.stream().map(e ->
                convert(e,productCategoryList)
        ).collect(Collectors.toList());
    }
}
