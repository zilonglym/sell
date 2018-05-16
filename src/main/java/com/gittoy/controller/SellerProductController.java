package com.gittoy.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.management.RuntimeErrorException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.gittoy.dataobject.OrderDetail;
import com.gittoy.dataobject.ProductCategory;
import com.gittoy.dataobject.ProductInfo;
import com.gittoy.exception.SellException;
import com.gittoy.form.ProductForm;
import com.gittoy.service.CategoryService;
import com.gittoy.service.ProductService;
import com.gittoy.service.UploadService;
import com.gittoy.utils.KeyUtil;
import com.gittoy.utils.ResultVOUtil;
import com.gittoy.vo.ProductQueryVo;
import com.gittoy.vo.ResultVO;
import com.gittoy.vo.SalesQueryVo;

/**
 * 卖家端商品
 *
 * Create By GaoYu 2017/11/23 9:39
 */
@RestController
@RequestMapping("/seller/product")
public class SellerProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;
    
    @Autowired
    private UploadService uploadService;

    /**
     * 列表
     *
     * @param page
     * @param size
     * @param map
     * @return
     */
    @GetMapping("list")
    public ModelAndView list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "size", defaultValue = "30") Integer size,
                             Map<String, Object> map) {
        PageRequest request = new PageRequest(page - 1, size);
        Page<ProductInfo> productInfoPage = productService.findAll(request);
        // 查询所有的类目
        List<ProductCategory> categoryList = categoryService.findAll();
        map.put("categoryList", categoryList);
        
        map.put("productInfoPage", productInfoPage);
        map.put("currentPage", page);
        map.put("size", size);
        return new ModelAndView("/product/list", map);
    }
    
    @RequestMapping(value = "list2")
    public ModelAndView index() {
        ModelAndView view = new ModelAndView("/product/list2");
        return view;
    }
    
    /**
     * 列表2
     *
     * @param page
     * @param size
     * @param map
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getlist", method = RequestMethod.POST)
    public Map<String, Object> getList2(@RequestBody ProductQueryVo queryVo){
    
		Map<String, Object> result = new HashMap<>();
		result.put("total", 0);
		result.put("rows", new ArrayList());
		 
		// 参数验证
		if (null == queryVo) {
		    return result;
		}
     	PageRequest request = new PageRequest(queryVo.getPageNumber() - 1, queryVo.getLimit());
         
     	//返回所有符合的商品
        if(!StringUtils.isEmpty(queryVo.getProductName())){
        	Page<ProductInfo> pageAllData = productService.findByProductName(request, queryVo.getProductName());
         	if (pageAllData != null) {
                 result.put("total", pageAllData.getTotalElements());
                 result.put("rows", pageAllData.getContent());
             }
         }else{
        	 Page<ProductInfo> pageAllData = productService.findAll(request);
          	if (pageAllData != null) {
                  result.put("total", pageAllData.getTotalElements());
                  result.put("rows", pageAllData.getContent());
              }
         }
		return result;
    }

    /**
     * 商品上架
     *
     * @param productId
     * @param map
     * @return
     */
    @RequestMapping("on_sale")
    public ModelAndView onSale(@RequestParam("productId") String productId,
    		@RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "30") Integer size,
                               Map<String, Object> map) {
        try {
            productService.onSale(productId);
        } catch (SellException e) {
            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/product/list?page="+page+"&size="+size);
            return new ModelAndView("common/error", map);
        }

        map.put("url", "/sell/seller/product/list?page="+page+"&size="+size);
        return new ModelAndView("common/success", map);
    }

    /**
     * 商品下架
     *
     * @param productId
     * @param map
     * @return
     */
    @RequestMapping("off_sale")
    public ModelAndView offSale(@RequestParam("productId") String productId,
    		@RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "30") Integer size,
                               Map<String, Object> map) {
        try {
            productService.offSale(productId);
        } catch (SellException e) {
            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/product/list?page="+page+"&size="+size);
            return new ModelAndView("common/error", map);
        }

        map.put("url", "/sell/seller/product/list?page="+page+"&size="+size);
        return new ModelAndView("common/success", map);
    }

    @GetMapping("/index")
    public ModelAndView index(@RequestParam(value = "productId", required = false) String productId,
                      Map<String, Object> map) {

        if (!StringUtils.isEmpty(productId)) {
            ProductInfo productInfo = productService.findOne(productId);
            map.put("productInfo", productInfo);
        }

        // 查询所有的类目
        List<ProductCategory> categoryList = categoryService.findAll();
        map.put("categoryList", categoryList);

        return new ModelAndView("product/index", map);
    }
    
    /**
     * 商品图片上传
     * @param request
     * @return
     * @throws Exception
     */
    @SuppressWarnings("rawtypes")
	@RequestMapping(value="imageupdate",method = RequestMethod.POST)  
    @ResponseBody    
    public ResultVO uploadImage(HttpServletRequest request) throws Exception {  
    	Map<String, String> rtn = new HashMap<>();
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;    
        MultipartFile file = null;    
        file = multipartRequest.getFile("file");// 获取上传文件名    
        boolean isSuccessupload = uploadService.uploadFile(file, request);  
        if(isSuccessupload){
            rtn.put("Path", "/opt/data/images/"+file.getOriginalFilename());
            rtn.put("NetPath", "http://111.230.47.102/images/"+file.getOriginalFilename());
        }else{
        	throw new RuntimeErrorException(null, "图片上传失败！");
        }
        return ResultVOUtil.success(rtn);    
    }


    
    @PostMapping("/save")
    public ModelAndView save(@Valid ProductForm form,
                             BindingResult bindingResult,
                             Map<String, Object> map) {

        if (bindingResult.hasErrors()) {
            map.put("msg", bindingResult.getFieldError().getDefaultMessage());
            map.put("url", "/sell/seller/product/index");
            return new ModelAndView("common/error", map);
        }

        ProductInfo productInfo = new ProductInfo();
        try {
            // 如果productId为空，说明是新增
            if (!StringUtils.isEmpty(form.getProductId())) {
                productInfo = productService.findOne(form.getProductId());
            } else {
                form.setProductId(KeyUtil.genUniqueKey());
            }
            BeanUtils.copyProperties(form, productInfo);
            productService.save(productInfo);
        } catch (SellException e) {
            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/product/index");
            return new ModelAndView("common/error", map);
        }

        map.put("url", "/sell/seller/product/list");
        return new ModelAndView("common/success", map);
    }
}
