package com.gittoy.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gittoy.dataobject.ProductCategory;
import com.gittoy.dataobject.ProductInfo;
import com.gittoy.service.CategoryService;
import com.gittoy.service.ProductService;
import com.gittoy.utils.ResultVOUtil;
import com.gittoy.vo.ProductInfoVO;
import com.gittoy.vo.ProductVO;
import com.gittoy.vo.ResultVO;
import com.gittoy.vo.SubCategoryVO;

/**
 * BuyerProductController
 * Create By GaoYu 2017/11/13 11:19
 */
@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @SuppressWarnings("rawtypes")
	@GetMapping("/list")
    public ResultVO list() {

        // 1，查询所有上架的商品
        List<ProductInfo> productInfoList = productService.findUpAll();

        // 2，查询类目（一次性查询）
//        List<Integer> categoryTypeList = new ArrayList<>();
        // 传统方法
//        for (ProductInfo productInfo : productInfoList) {
//            categoryTypeList.add(productInfo.getCategoryType());
//        }
        // 精简方法（java8, lambda）
        List<Integer> categoryTypeList = productInfoList.stream()
                .map(e -> e.getCategoryType())
                .collect(Collectors.toList());
        List<ProductCategory> productCategoryList = categoryService.findByCategoryIdIn(categoryTypeList);

        List<ProductVO> productVOList = new ArrayList<>();
        // 3，数据拼装
        //A B C
        List<String> CategoryTypeList = productCategoryList.stream().map(ProductCategory::getCategoryName).collect(Collectors.toList());
        CategoryTypeList = CategoryTypeList.stream().distinct().collect(Collectors.toList());
        for(String categoryType:CategoryTypeList){
        	ProductVO productVO = new ProductVO();
            productVO.setCategoryName(categoryType);
            
        	List<SubCategoryVO> subCategoryVOList = new ArrayList<>();
        	List<String> CategorySubTypeList = new ArrayList<>(); //A下的 a1 a2 a3
        	productCategoryList.forEach((k)->{
            	if((k.getCategoryName().equals(categoryType))&&(!CategorySubTypeList.contains(k.getCategorySubName()))){
            		CategorySubTypeList.add(k.getCategorySubName());
            	}
            });
        	
        	for(String categorySubType : CategorySubTypeList){
        		SubCategoryVO subCategoryVO = new SubCategoryVO();
        		subCategoryVO.setCategorySubName(categorySubType);
        		List<ProductInfoVO> productInfoVOList = new ArrayList<>();
        		int categoryid= 0;
        		for(ProductCategory productCategory:productCategoryList){
        			if(StringUtils.equals(productCategory.getCategoryName(), categoryType)
        					&& StringUtils.equals(productCategory.getCategorySubName(), categorySubType)){
        				categoryid = productCategory.getCategoryId();
        				break;
        			}
        		}
        		for (ProductInfo productInfo : productInfoList) {
        			if(productInfo.getCategoryType()==categoryid){
        				ProductInfoVO productInfoVO = new ProductInfoVO();
                        BeanUtils.copyProperties(productInfo, productInfoVO);
                        productInfoVOList.add(productInfoVO);
        			}
        		}
        		subCategoryVO.setProductInfoVOList(productInfoVOList);
        		subCategoryVOList.add(subCategoryVO);
        	}
        	productVO.setSubCategoryList(subCategoryVOList);
        	productVOList.add(productVO);
        }
        
        
//        for (ProductCategory productCategory : productCategoryList) {
//            ProductVO productVO = new ProductVO();
//            productVO.setCategorytype(productCategory.getCategoryType());
//            productVO.setCategoryName(productCategory.getCategoryName());
//
//            SubCategoryVO subCategoryVO = new SubCategoryVO();
//            List<ProductInfoVO> productInfoVOList = new ArrayList<>();
//            for (ProductInfo productInfo : productInfoList) {
//                if (productInfo.getCategoryType().equals(productCategory.getCategoryId())) {
//                    ProductInfoVO productInfoVO = new ProductInfoVO();
//                    BeanUtils.copyProperties(productInfo, productInfoVO);
//                    productInfoVOList.add(productInfoVO);
//                }
//            }
//            subCategoryVO.setCategorySubName(productCategory.getCategorySubName());
//            subCategoryVO.setCategorySubType(productCategory.getCategorySubType());
//            subCategoryVO.setProductInfoVOList(productInfoVOList);
//            subCategoryVOList.add(subCategoryVO);
//            
//            productVO.setSubCategoryList(subCategoryVOList);
//            productVOList.add(productVO);
//        }

        return ResultVOUtil.success(productVOList);
    }
}
