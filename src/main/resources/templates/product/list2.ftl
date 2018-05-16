<!DOCTYPE html>
<html>
<#include "../common/header.ftl">


<body class="fixed-sidebar full-height-layout gray-bg" style="overflow:hidden">
	<div id="wrapper" class="toggled" >
	
		<#--边栏sidebar-->
		<#include "../common/nav.ftl">
		<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title" id="myModalLabel">新增</h4>
                </div>
                <div class="modal-body">

                    <div class="form-group">
                        <label for="productName">名称</label>
                        <input type="text" name="productName" class="form-control" id="txt_departmentname" placeholder="名称">
                    </div>
                    <div class="form-group">
                        <label for="productPrice">价格</label>
                        <input type="text" name="productPrice" class="form-control" id="txt_parentdepartment" placeholder="价格">
                    </div>
                    <div class="form-group">
                        <label for="productStock">库存</label>
                        <input type="number" name="productStock" class="form-control" id="txt_departmentlevel" placeholder="库存">
                    </div>
                    <div class="form-group">
                        <label for="txt_statu">描述</label>
                        <input type="text" name="txt_statu" class="form-control" id="txt_statu" placeholder="描述">
                    </div>
                    <div class="form-group">
                        <label for="categoryType">类目</label>
                        <input type="text" name="categoryType" class="form-control" id="txt_statu" placeholder="类目">
                    </div>
                    <div class="form-group">
                        <label for="productIcon">当前图片预览及图片地址</label>
                        <input type="text" name="productIcon" class="form-control" id="txt_statu" placeholder="图片地址">
                    </div>                    
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span>关闭</button>
                    <button type="button" id="btn_submit" class="btn btn-primary" data-dismiss="modal"><span class="glyphicon glyphicon-floppy-disk" aria-hidden="true"></span>保存</button>
                </div>
            </div>
        </div>
    </div>
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
							 <span width:60px;></span>
							 <button id="delete" type="button" class="btn btn-sm btn-primary">批量删除</button>
							 <span width:60px;></span>
							 <button id="modify" type="button" class="btn btn-sm btn-primary">修改</button>							 							 					 
						</div>
					</div>
				</div>
		    </div>
		    <div style="height: 400px;overlow: auto;" class="table-responsive">
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
	        
	        //修改窗口
	        $("#modify").click(function () {
	        	$("#myModalLabel").text("修改");
	        	$('#myModal').modal();
	        });
	        
	        $("#up").click(function () {
	            //获取所有被选中的记录  
	            var rows = $("#table").bootstrapTable('getSelections');  
	            if (rows.length== 0) {  
	                alert("请先选择要上架的商品!");  
	                return;  
	            }  
	            var ids = '';  
	            for (var i = 0; i < rows.length; i++) {  
	                ids += rows[i]['productId'] + ',';  
	            }  
	            ids = ids.substring(0, ids.length - 1);  
	            upProduct(ids);  
	        });
	        
	        $("#down").click(function () {
	            //获取所有被选中的记录  
	            var rows = $("#table").bootstrapTable('getSelections');  
	            if (rows.length== 0) {  
	                alert("请先选择要下架的商品!");  
	                return;  
	            }  
	            var ids = '';  
	            for (var i = 0; i < rows.length; i++) {  
	                ids += rows[i]['productId'] + ',';  
	            }  
	            ids = ids.substring(0, ids.length - 1);  
	            downProduct(ids);  
	        });
	        
	        $("#delete").click(function () {
	            //获取所有被选中的记录  
	            var rows = $("#table").bootstrapTable('getSelections');  
	            if (rows.length== 0) {  
	                alert("请先选择要删除的商品!");  
	                return;  
	            }  
	            var ids = '';  
	            for (var i = 0; i < rows.length; i++) {  
	                ids += rows[i]['productId'] + ',';  
	            }  
	            ids = ids.substring(0, ids.length - 1);  
	            downProduct(ids);  
	        });
	        
	      //上架
	        function upProduct(ids) {  
	            var msg = "确认批量上架？";  
	            if (confirm(msg) == true) {  
	                $.ajax({  
	                    url: "${request.contextPath}/seller/product/batch_on_sale",  
	                    type: "post",  
	                    data: {  
	                        ids: ids  
	                    },  
	                    success: function (data) {  
	                        alert(data.data);  
	                        //重新加载记录  
	                        //重新加载数据  
	                        $("#table").bootstrapTable('refresh');  
	                    }  
	                });  
	            }  
	        }  
	      //下架
	        function downProduct(ids) {  
	            var msg = "确认批量下架？";  
	            if (confirm(msg) == true) {  
	                $.ajax({  
	                    url: "${request.contextPath}/seller/product/batch_off_sale",  
	                    type: "post",  
	                    data: {  
	                        ids: ids  
	                    },  
	                    success: function (data) {  
	                        alert(data.data);  
	                        //重新加载记录  
	                        //重新加载数据  
	                        $("#table").bootstrapTable('refresh');  
	                    }  
	                });  
	            }  
	        }  
	      //删除
	        function downProduct(ids) {  
	            var msg = "请确认批量删除？删除后不可恢复";  
	            if (confirm(msg) == true) {  
	                $.ajax({  
	                    url: "${request.contextPath}/seller/product/batch_delete",  
	                    type: "post",  
	                    data: {  
	                        ids: ids  
	                    },  
	                    success: function (data) {  
	                        alert(data.data);  
	                        //重新加载记录  
	                        //重新加载数据  
	                        $("#table").bootstrapTable('refresh');  
	                    }  
	                });  
	            }  
	        }  
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
	                /* height: 400, */                        //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
	                uniqueId: "productName",                     //每一行的唯一标识，一般为主键列
	                cardView: false,                    //是否显示详细视图
	                detailView: false,                  //是否显示父子表
	                showRefresh: false,                   //刷新按钮
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
	                    title: '最后修改',
	                  	//——修改——获取日期列的值进行转换  
	                    formatter: function (value, row, index) {  
	                        return changeDateFormat(value)  
	                    } 
	                },{
	                	title: '详情',
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
