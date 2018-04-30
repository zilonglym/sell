<!DOCTYPE html>
<html>
<#include "../common/header.ftl">


<body class="fixed-sidebar full-height-layout gray-bg" style="overflow:hidden">
	<div id="wrapper" class="toggled" >
	
		<#--边栏sidebar-->
		<#include "../common/nav.ftl">
		<div id="page-content-wrapper2" >
			<div class="container">
				<div class="row clearfix">
					
					<div class="col-md-6 column">
						<div class="column">
							<p>&nbsp;&nbsp;&nbsp;订单日期范围:<br>
							<input style="width:250px;" name="startDate" id="startDate" placeholder="起始日期" class="input-sm form-control">
						</div>
						<div class="column">
							<p>&nbsp;&nbsp;&nbsp;~<br>
							<input style="width:250px;" name="endDate" id="endDate" placeholder="结束日期" class="input-sm form-control">
						</div>
					</div>
					
					<div class="col-md-6 column">
						<div>
							<p>&nbsp;&nbsp;&nbsp;产品大类:<br>
				        	<!-- <input style="width:250px;" name="categoryName" id="categoryName" placeholder="产品大类" class="input-sm form-control"></p> -->
				        	<select style="width:250px;height: 30px" id="categoryName" name="categoryName" class="input-sm form-control" >
					            <option value="">---请选择---</option>
								{foreach $reward as $value}
								<option value="{$value['material_id']}">{$value['material_name']}</option>
								{/foreach}
					        </select></p>
				        </div>
						<div>
							<p hidden>&nbsp;&nbsp;&nbsp;买家订单号:<br>
				        	<input hidden style="width:250px;" name="orderId" id="orderId" placeholder="订单id" class="input-sm form-control"></p>
						</div>
					</div>
					
				</div>
			</div>
	
			<div class="btn-group hidden-xs" id="toolbar" role="group">
				<div class="container">
					<div class="row clearfix">
						<div class="col-md-12 column">
							 <button id="query" type="button" class="btn btn-sm btn-primary">搜索</button>
							 <span width:60px;></span>
							 <button id="clear" type="button" class="btn btn-sm btn-primary">重置</button>
							 <span width:60px;></span>
							 <button id="exportExcel" type="button" onClick ="$('#table').tableExport({ type: 'excel', escape: 'false',fileName: '流水账单' })" class="btn btn-sm btn-primary">导出Excel</button>
						</div>
					</div>
				</div>
		    </div>
		    <div style="height: 450px;overlow: auto;" class="table-responsive">
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
	                document.getElementById("startDate").value="";
	                document.getElementById("endDate").value="";
	                document.getElementById("categoryName").value="";
	            });
	        }; 
	
	        //得到查询的参数
	        function queryParams(params) {
	
	            var temp = {
	                limit: params.limit,    //页面大小
	                offset: params.offset,   //页码
	                categoryName: $('#categoryName').val(),
	                orderId: $('#orderId').val(),
	                startDate: $('#startDate').val(),
	                endDate: $('#endDate').val(),
	            };
	            return temp;
	        };
	        
	        //日期控件选择
	        $("#startDate").datetimepicker({//选择年月日
	      　　　　　　format: 'yyyy-mm-dd',
	      　　　　　　language: 'zh-CN',
	      　　　　　　weekStart: 1,
	      　　　　　　todayBtn: 1,//显示‘今日’按钮
	      　　　　　　autoclose: 1,
	      　　　　　　todayHighlight: 1,
	      　　　　　　startView: 2,
	      　　　　　　minView: 2,  //Number, String. 默认值：0, 'hour'，日期时间选择器所能够提供的最精确的时间选择视图。
	      　　　　　　clearBtn:true,//清除按钮
	      　　　　　　forceParse: 0
	      　　　　});
	        $("#endDate").datetimepicker({//选择年月日
	      　　　　　　format: 'yyyy-mm-dd',
	      　　　　　　language: 'zh-CN',
	      　　　　　　weekStart: 1,
	      　　　　　　todayBtn: 1,//显示‘今日’按钮
	      　　　　　　autoclose: 1,
	      　　　　　　todayHighlight: 1,
	      　　　　　　startView: 2,
	      　　　　　　minView: 2,  //Number, String. 默认值：0, 'hour'，日期时间选择器所能够提供的最精确的时间选择视图。
	      　　　　　　clearBtn:true,//清除按钮
	      　　　　　　forceParse: 0
	      　　　　});
	        
	
	        function initTable() {
	            $('#table').bootstrapTable({
	                url: '${request.contextPath}/seller/order/flow/getSalesFlowList',    //请求后台的URL（*）
	                method: 'post',                     //请求方式（*）
	                contentType: "application/json",
	                toolbarAlign: 'right',               //工具栏对齐方式
	                buttonsAlign: 'right',               //按钮对齐方式
	                striped: true,                      //是否显示行间隔色
	                cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
	                pagination: true,                   //是否显示分页（*）
	                sortable: true,                    //是否启用排序
	                sortOrder: "asc",                   //排序方式
	                sortName: "detailId",                    // 排序字段
	                queryParams: queryParams,//传递参数（*）
	                sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
	                pageNumber: 1,                       //初始化加载第一页，默认第一页
	                pageSize: 10,                       //每页的记录行数（*）
	                pageList: [15, 25, 50, 100],        //可供选择的每页的行数（*）
	                strictSearch: true,
	                clickToSelect: true,                //是否启用点击选中行
	                height: 450,                        //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
	                uniqueId: "detailId",                     //每一行的唯一标识，一般为主键列
	                cardView: false,                    //是否显示详细视图
	                detailView: false,                  //是否显示父子表
	                showRefresh: false,                   //刷新按钮
	                showExport: false,                     //是否显示导出
	                exportDataType: "all",              //basic', 'all', 'selected'.
	                exportTypes:['excel'], 				 //导出文件类型
	                exportOptions:{  
	                    fileName: '流水',  //文件名称设置  
	                    worksheetName: 'sheet1',  //表格工作区名称  
	                    tableName: '商品流水详情',  
	                    excelstyles: ['background-color', 'color', 'font-size', 'font-weight'],
	                    numbers: {
	                    	output: false  //禁用输出excel文件时对数字的格式化处理
	                    }
	                },
	                columns: [{
	                    field: 'orderId',
	                    sortable: true,
	                    sortOrder: "asc",
	                    title: '订单Id',
	                    cellStyle:{  
	                        css:{"mso-number-format":"\\@"}  
	                    } 
	                },{
	                    field: 'detailId',
	                    sortable: true,
	                    sortOrder: "asc",
	                    title: '订单详情Id'
	                },  {
	                    field: 'productId',
	                    sortable: true,
	                    sortOrder: "asc",
	                    title: '商品编号'
	                },{
	                    field: 'productName',
	                    sortable: true,
	                    sortOrder: "asc",
	                    title: '商品名'
	                }, {
	                    field: 'productPrice',
	                    sortable: true,
	                    sortOrder: "asc",
	                    title: '商品成交单价'
	                },{
	                    field: 'productQuantity',
	                    sortable: true,
	                    sortOrder: "asc",
	                    title: '商品数量',
	                }, {
	                    field: 'createTime',
	                    sortable: true,
	                    sortOrder: "asc",
	                    title: '发生时间',
	                  	//——修改——获取日期列的值进行转换  
	                    formatter: function (value, row, index) {  
	                        return changeDateFormat(value)  
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
	            $("#categoryName").empty().append('<option value="3">所有类别</option>'); 
	        }; 
	        
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
	        	refreshCategoryName();
	            initTable();
	            initEvent();
	        });
	        
	    })();
	</script>
</body>

</html>
