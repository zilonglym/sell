<html>
<#include "../common/header.ftl">

<body>
    <div id="wrapper" class="toggled">

        <#--边栏sidebar-->
        <#include "../common/nav.ftl">

        <#--主要内容content-->
        <div id="page-content-wrapper">
            <div class="container">
            <div class="row clearfix">
                

                <#-- 订单详情表数据 -->
                <div class="col-md-4 column">
                    <table class="table table-bordered">
                        <thead>
                        <tr>
                            <th>商品名称&nbsp;</th>
                            <th>价格&nbsp;</th>
                            <th>数量&nbsp;</th>
                            <th>金额&nbsp;</th>
                        </tr>
                        </thead>
                        <tbody>
                        <#list orderDTO.orderDetailList as orderDetail>
                        <tr>
                            <td>${orderDetail.productName}&nbsp;</td>
                            <td>${orderDetail.productPrice}&nbsp;</td>
                            <td>${orderDetail.productQuantity}&nbsp;</td>
                            <td>${orderDetail.productPrice * orderDetail.productQuantity}</td>
                        </tr>
                        </#list>
                        </tbody>
                    </table>
                </div>

                <div class="container">
	                <div class="col-md-2 column">
						<table class="table table-bordered table-condensed">
							<tbody>
								<tr>
									<td class="initialism">商品总金额&nbsp;</td>
									<td class="text-right" class="initialism">${orderDTO.orderAmount}</td>
								</tr>
								<tr>
									<td>配送费&nbsp;</td>
									<td class="text-right">${(orderDTO.orderDeliveryAmount)!}</td>
								</tr>
								<tr>
									<td>总支付金额&nbsp;</td>
									<td class="text-right">${(orderDTO.orderAmount)!+(orderDTO.orderDeliveryAmount)!}</td>
								</tr>
								<tr>
									<td>配送地址&nbsp;</td>
									<td class="text-right">${(orderDTO.buyerAddress)!}</td>
								</tr>
								<tr>
									<td>买家留言&nbsp;</td>
									<td class="text-right">${(orderDTO.buyerComment)!}</td>
								</tr>							
							</tbody>
						</table>
					</div>
				</div>
                
                
                <#-- 操作 -->
                <div class="col-md-12 column">
                    <#if orderDTO.getOrderStatusEnum().message == "新订单">
                        <a href="/sell/seller/order/finish?orderId=${orderDTO.orderId}" type="button" class="btn btn-default btn-primary active">完结订单</a>
                        <a href="/sell/seller/order/cancel?orderId=${orderDTO.orderId}" type="button" class="btn btn-default active btn-danger">取消订单</a>
                    </#if>
                </div>
            	</div>
        	</div>
    	</div>
        
        <div class="container">
			<div class="row clearfix">
				<div class="col-md-12 column">
					 <button type="button" class="btn btn-default" onclick="f_print();">打印订单</button>
				</div>
			</div>
		</div>
	
    </div>
       
    

    <script language="javascript" type="text/javascript" >
	    	var LODOP;  
		function f_print(){
			console.log("1");
			LODOP = getLodop(document.getElementById('LODOP_OB'), document.getElementById('LODOP_EM'));  
	        LODOP.SET_PRINT_PAGESIZE(3, 600, 100, ""); //规定纸张大小；使用A4纸。  
	        LODOP.SET_PRINT_STYLE("FontColor", "#000000");//字体颜色  
	        LODOP.SET_PRINT_STYLE("FontSize", 16); //字体大小  
	        LODOP.ADD_PRINT_TEXT(30, 60, 150, 39, "润予置禾超市"); //// 离纸张顶端、距左边、宽、高 
	        LODOP.SET_PRINT_STYLE("FontSize", 10); //字体大小
	        var a = strs=document.getElementsByClassName("col-md-4 column")[0].innerText.split("\n");
	        LODOP.ADD_PRINT_TEXT(68,10,220,20,a[0]);
	        LODOP.ADD_PRINT_TEXT(88,10,220,20,"==========================");
	        
	        for(i = 1;i<a.length;i++){
	        	LODOP.ADD_PRINT_TEXT(88+i*20,10,220,20,a[i]);
			}
	        
	        
	        var b = strs=document.getElementsByClassName("col-md-2 column")[0].innerText.split("\n");
	        
	        //LODOP.ADD_PRINT_TEXT(268,10,220,200,a.length);
	        //LODOP.ADD_PRINT_HTM(68,10,220,"10%",document.getElementsByClassName("col-md-4 column")[0].innerHTML);
	        LODOP.ADD_PRINT_TEXT(88.0+a.length*20,10,220,20,"==========================");
	        LODOP.ADD_PRINT_TEXT(88.0+a.length*20+20,10,220,b.length*20,document.getElementsByClassName("col-md-2 column")[0].innerText);
	        
	        //LODOP.ADD_PRINT_HTM(68.0+a.length*20,10,220,b.length*20,document.getElementsByClassName("col-md-2 column")[0].innerHTML);
	        /*	        LODOP.SET_PRINT_ST
	        YLE("FontSize", 24); //更换大小  
	        LODOP.ADD_PRINT_TEXT(110, 50, 360, 39, "更换大小");  
	        LODOP.ADD_PRINT_LINE(500, 50, 200, 50, 0, 1); //线，竖杠(上边距1,1左边距,上边距2,2左边距,intLineStyle, intLineWidth)  
	        LODOP.ADD_PRINT_LINE(550, 50, 150, 200, 0, 1); //线，横杠(上边距1,1左边距,上边距2,2左边距,intLineStyle, intLineWidth)   */
	        LODOP.PREVIEW();//打印预览  
		}

    </script>  
</body>

</html>