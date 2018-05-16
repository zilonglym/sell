<!DOCTYPE html>
<html>
<#include "../common/header.ftl">


<body class="fixed-sidebar full-height-layout gray-bg" style="overflow:hidden">
	<div id="wrapper" class="toggled" >
	
		<#--边栏sidebar-->
		<#include "../common/nav.ftl">
		<div id="page-content-wrapper2" >
			<p >&nbsp;&nbsp;&nbsp;商品名(模糊搜索):<br>
	        <input style="width:250px;" name="productName" id="productName" class="input-sm form-control"></p>
	
			<div class="btn-group hidden-xs" id="toolbar" role="group">
				<div class="container">
					<div class="row clearfix">
						<div class="col-md-12 column">
							 <button id="query" type="button" class="btn btn-sm btn-primary">搜索</button>
							 <span width:60px;></span>
							 <button id="clear" type="button" class="btn btn-sm btn-primary">重置</button>
							 <span width:60px;></span>
							 <button id="up" type="button" class="btn btn-sm btn-primary">批量上架</button>
							 <span width:60px;></span>
							 <button id="down" type="button" class="btn btn-sm btn-primary">批量下架</button>							 
						</div>
					</div>
				</div>
		    </div>
		    <div style="height: 700px;overlow: auto;" class="table-responsive">
		    	<table class="table table-bordered table-condensed" id="table" data-mobile-responsive="true"></table>
		    	<thread></thread>
		    	<tbody></tbody>
		    </div>
		</div>
	</div>
	
	<script type="text/javascript">
	    (function () {
	
	         //初始化事件
	        function initEvent() {
	            //1.查询按钮事件
	            $('#query').click(function () {
	                $('#table').bootstrapTable('refresh', {
	                    pageNumber: 1
	                });
	            })
	
	            //2.重置按钮事件
	            $('#clear').click(function () {
	                document.getElementById("productName").value="";
	            });
	        }; 
	
	        //得到查询的参数
	        function queryParams(params) {
	
	            var temp = {
	                limit: params.limit,    //页面大小
	                offset: params.offset,   //页码
	                productName: $('#productName').val(),
	            };
	            return temp;
	        };
	        
	        $("#up").click(function () {
	            if (confirm("确认批量上架？")) {
	                var idlist = "";
	                $("input[name='btSelectItem']:checked").each(function () {
	                    idlist += $(this).parents("tr").attr("data-uniqueid") + ",";
	                })
	                alert("删除的列表为" + idlist);

	            }
	        });
	        function initTable() {
	            $('#table').bootstrapTable({
	                url: '${request.contextPath}/seller/product/getlist',    //请求后台的URL（*）
	                method: 'post',                     //请求方式（*）
	                contentType: "application/json",
	                toolbarAlign: 'right',               //工具栏对齐方式
	                buttonsAlign: 'right',               //按钮对齐方式
	                striped: true,                      //是否显示行间隔色
	                cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
	                pagination: true,                   //是否显示分页（*）
	                sortable: true,                    //是否启用排序
	                sortOrder: "asc",                   //排序方式
	                sortName: "productName",                    // 排序字段
	                queryParams: queryParams,//传递参数（*）
	                sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
	                pageNumber: 1,                       //初始化加载第一页，默认第一页
	                pageSize: 10,                       //每页的记录行数（*）
	                pageList: [15, 25, 50, 100],        //可供选择的每页的行数（*）
	                strictSearch: true,
	                clickToSelect: true,                //是否启用点击选中行
	                height: 700,                        //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
	                uniqueId: "productName",                     //每一行的唯一标识，一般为主键列
	                cardView: false,                    //是否显示详细视图
	                detailView: false,                  //是否显示父子表
	                showRefresh: true,                   //刷新按钮
	                showExport: false,                     //是否显示导出
	                columns: [{
	                    checkbox: true
	                },{
	                    field: 'productId',
	                    sortable: true,
	                    sortOrder: "asc",
	                    title: '商品Id' 
	                },{
	                    field: 'productName',
	                    sortable: true,
	                    sortOrder: "asc",
	                    title: '名字'
	                },  {
	                    field: 'productPrice',
	                    sortable: true,
	                    sortOrder: "asc",
	                    title: '单价'
	                },{
	                    field: 'productStock',
	                    sortable: true,
	                    sortOrder: "asc",
	                    title: '库存'
	                }, {
	                    field: 'productDescription',
	                    sortable: true,
	                    sortOrder: "asc",
	                    title: '描述'
	                },{
	                    field: 'productIcon',
	                    sortable: true,
	                    sortOrder: "asc",
	                    title: '小图',
	                    formatter:function(value,row,index){
	   					 var s;
	   					 if(row.productIcon!=null){
	   						 var url = row.productIcon;
	   					 	s = '<a class = "view"  href="javascript:void(0)"><img style="width:300;height:40px;"  src="'+url+'" /></a>';
	   					 }
	   	            	 return s;
	   				 }
	                },{
	                    field: 'categoryType',
	                    sortable: true,
	                    sortOrder: "asc",
	                    title: '类目'
	                },{
	                    field: 'productStatus',
	                    sortable: true,
	                    sortOrder: "asc",
	                    title: '上架状态'
	                },{
	                    field: 'updateTime',
	                    sortable: true,
	                    sortOrder: "asc",
	                    title: '修改时间',
	                  	//——修改——获取日期列的值进行转换  
	                    formatter: function (value, row, index) {  
	                        return changeDateFormat(value)  
	                    } 
	                },{
	                	title: '修改时间',
	                  	//——修改——获取日期列的值进行转换  
	                    formatter: function (value, row, index) {  
	                    	var s;
	                    	s = '<a href="${request.contextPath}/seller/product/index?productId='+row.productId+'">修改</a>';
	                        return s; 
	                    }
	                }]
	            });
	        }
	
	        // 页面刷新
	        var pageReload = function (millisec) {
	            var s = 1000;
	            if (!millisec) {
	                s = millisec;
	            }
	            setTimeout(function () {
	                $('#table').bootstrapTable('refresh', {
	                    pageNumber: 1
	                });
	            }, s);
/* 	            $("#categoryName").empty().append('<option value="3">所有类别</option>'); 
 */	        }; 
	        
	      	//修改——转换日期格式(时间戳转换为datetime格式)  
	        function changeDateFormat(cellval) {  
	            if (cellval != null) {  
	                var date = new Date(parseInt(cellval.toString().replace("/Date(", "").replace(")/", ""), 10));  
	                var month = date.getMonth() + 1 < 10 ? "0" + (date.getMonth() + 1) : date.getMonth() + 1;  
	                var currentDate = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();  
	                return date.getFullYear() + "-" + month + "-" + currentDate;  
	            }  
	        }  
	      	
	      	
	        /* $('#categoryName').change(function() {refreshCategoryName();}); */   //下拉框的点击改变事件，但是会使得选择一次就刷新掉所选择的
	        function refreshCategoryName(){
				$.ajax({ 
					type:"get", 
					url:"/sell/seller/category/getall", 
					data: {}, 
					dataType: "json",
					success:function(select){
				  		//console.log(select);
			    		var categoryName_id = $("#categoryName"); 
			    		if (select) {
			    			//console.log(select.data[0]);
			    			var arrayCategory = select.data[0].category;
							$("option",categoryName_id).remove();
							//console.log(arrayCategory);
							//console.log(arrayCategory.length);
	 						var temp = "<option value=''>未选择</option>";
							categoryName_id.append(temp);  
							for(var i=0;i<arrayCategory.length;i++){
								var option = "<option value='"+arrayCategory[i]+"'>"+arrayCategory[i]+"</option>";
								//console.log(option);
								categoryName_id.append(option); 
							}
			    		} 
					} 
				});
	        } 
	
	        $(function () {
/* 	        	refreshCategoryName();
 */	            initTable();
	            initEvent();
	        });
	        
	    })();
	</script>
</body>

</html>
