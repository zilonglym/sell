package com.gittoy.repository;

import com.gittoy.dataobject.OrderDetail;

import lombok.val;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

/**
 * OrderDetailRepository
 * Create By GaoYu 2017/11/15 8:53
 */
public interface OrderDetailRepository extends JpaRepository<OrderDetail, String> {

    List<OrderDetail> findByOrderId(String orderId);

	List<OrderDetail> findByOrderIdAndCreateTimeBetween(String orderId, String startDate, String endDate);
	
	Page<OrderDetail> findAll(Pageable pageable);
	
	Long countByOrderId(String orderId);
	
	@Query("select count(1) from OrderDetail c, OrderMaster d where d.orderId=c.orderId and d.payStatus=1")
	Long countByPayStatus();
	
	@Query("select count(1) from OrderDetail c, OrderMaster d where d.orderId=c.orderId and d.payStatus=1 and c.createTime between ? and ?")
	Long countByCreateTimeBetween(Date startDate,Date endDate);
	
	@Query("select count(1) from ProductCategory a , ProductInfo b , OrderDetail c, OrderMaster d where a.categoryId=b.categoryType and b.productId = c.productId and d.orderId=c.orderId and d.payStatus=1 and a.categoryName= ? ")
	Long countByCategoryName(@Param("categoryName") String categoryName);
	
	@Query("select count(1) from ProductCategory a , ProductInfo b , OrderDetail c, OrderMaster d where a.categoryId=b.categoryType and b.productId = c.productId and d.orderId=c.orderId and d.payStatus=1 and a.categoryName= ? and c.createTime between ? and ?")
	Long countByCategoryNameAndCreateTime(@Param("categoryName") String categoryName,Date startDate,Date endDate);
	
	
	// order
	List<OrderDetail> findByOrderId(String orderId, Pageable pageable);
	
	// paid
	@Query("select new OrderDetail(c.detailId,c.orderId,c.productId,c.productName,c.productPrice,c.productQuantity,c.productIcon,c.createTime) from OrderDetail c , OrderMaster d where d.orderId=c.orderId and d.payStatus=1 order by c.createTime desc")
	List<OrderDetail> findByPayStatus(Pageable pageable);
		
		
	// categoryName
	@Query("select new OrderDetail(c.detailId,c.orderId,c.productId,c.productName,c.productPrice,c.productQuantity,c.productIcon,c.createTime) from ProductCategory a , ProductInfo b , OrderDetail c , OrderMaster d where a.categoryId=b.categoryType and b.productId = c.productId and d.orderId=c.orderId and d.payStatus=1 and a.categoryName= ? order by c.createTime desc")
	List<OrderDetail> findByCategoryName(@Param("categoryName") String category_name, Pageable pageable);
	
	// time
	@Query("select new OrderDetail(c.detailId,c.orderId,c.productId,c.productName,c.productPrice,c.productQuantity,c.productIcon,c.createTime) from OrderDetail c , OrderMaster d where d.orderId=c.orderId and d.payStatus=1 and c.createTime between ? and ? order by c.createTime desc")
	List<OrderDetail> findByCreateTimeBetween(Date startDate,Date endDate, Pageable pageable);
	
	// categoryName+time
	@Query("select new OrderDetail(c.detailId,c.orderId,c.productId,c.productName,c.productPrice,c.productQuantity,c.productIcon,c.createTime) from ProductCategory a , ProductInfo b , OrderDetail c , OrderMaster d where a.categoryId=b.categoryType and b.productId = c.productId and d.orderId=c.orderId and d.payStatus=1 and a.categoryName= ? and c.createTime between ? and ? order by c.createTime desc")
	List<OrderDetail> findByCategoryNameAndCreateTime(@Param("categoryName") String category_name, Date startDate,Date endDate, Pageable pageable);
}
