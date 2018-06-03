package com.gittoy.service.impl;

import com.gittoy.dataobject.ProductInfo;
import com.gittoy.dto.CartDTO;
import com.gittoy.enums.ProductStatusEnum;
import com.gittoy.enums.ResultEnum;
import com.gittoy.exception.SellException;
import com.gittoy.repository.ProductInfoRepository;
import com.gittoy.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * ProductServiceImpl
 * Create By GaoYu 2017/11/13 10:04
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductInfoRepository repository;

    @Override
    public ProductInfo findOne(String productId) {
        return repository.findOne(productId);
    }

    @Override
    public List<ProductInfo> findUpAll() {
        return repository.findByProductStatus(ProductStatusEnum.UP.getCode());
    }
    
	@Override
	public List<ProductInfo> findUpAllAndNameContaining(String name) {
		return repository.findByProductStatusAndProductNameContaining(ProductStatusEnum.UP.getCode(), name);
	}

    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public ProductInfo save(ProductInfo productInfo) {
        return repository.save(productInfo);
    }

    @Override
    public void increaseStock(List<CartDTO> cartDTOList) {
        for (CartDTO cartDTO : cartDTOList) {
            ProductInfo productInfo = repository.findOne(cartDTO.getProductId());
            if (productInfo == null) {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIT);
            }
            Integer result = productInfo.getProductStock() + cartDTO.getProductQuantity();
            productInfo.setProductStock(result);
            repository.save(productInfo);
        }
    }

    @Override
    @Transactional
    public void decreaseStock(List<CartDTO> cartDTOList) {
        for (CartDTO cartDTO : cartDTOList) {
            ProductInfo productInfo = repository.findOne(cartDTO.getProductId());
            if (productInfo == null) {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIT);
            }
            Integer result = productInfo.getProductStock() - cartDTO.getProductQuantity();
            if (result < 0) {
                throw new SellException(ResultEnum.PRODUCT_STOCK_ERROR);
            }
            productInfo.setProductStock(result);
            repository.save(productInfo);
        }
    }

    @Override
    public ProductInfo onSale(String productId) {
        ProductInfo productInfo = repository.findOne(productId);
        if (productInfo == null) {
            throw new SellException(ResultEnum.PRODUCT_NOT_EXIT);
        }
        if (productInfo.getProductStatusEnum() == ProductStatusEnum.UP) {
            throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR);
        }

        // 更新
        productInfo.setProductStatus(ProductStatusEnum.UP.getCode());
        return repository.save(productInfo);
    }

    @Override
    public ProductInfo offSale(String productId) {
        ProductInfo productInfo = repository.findOne(productId);
        if (productInfo == null) {
            throw new SellException(ResultEnum.PRODUCT_NOT_EXIT);
        }
        if (productInfo.getProductStatusEnum() == ProductStatusEnum.DOWN) {
            throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR);
        }

        // 更新
        productInfo.setProductStatus(ProductStatusEnum.DOWN.getCode());
        return repository.save(productInfo);
    }

	@Override
	public Page<ProductInfo> findByProductName(Pageable pageable, String productName) {
		return repository.findByProductNameContaining(pageable, productName);
	}

	@Override
	public void delete(String productId) {
		repository.delete(productId);
		
	}

	@Override
	public List<ProductInfo> findByCategoryType(Integer categoryType) {
		return repository.findByCategoryType(categoryType);
	}

	@Override
	public Page<ProductInfo> findByCategoryId(Pageable pageable, Integer categoryId) {
		return repository.findByCategoryType(pageable, categoryId);
	}

	@Override
	public Page<ProductInfo> findByCategoryIdAndName(Pageable pageable, Integer categoryId, String productName) {
		return repository.findByCategoryTypeAndProductNameContaining(pageable, categoryId, productName);
	}


}
