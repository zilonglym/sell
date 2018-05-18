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
	                    <h4 class="modal-title" id="myModalLabel">修改</h4>
	                </div>
	                <div class="modal-body">
		                	<form class="form-inline" role="form" id="updateForm">
			                    <div class="form-group">
			                        <label for="productName">名称</label>
			                        <input type="text" name="productName" class="form-control" id="txt_productName" placeholder="名称">
			                    </div>
			                    <div class="form-group">
			                        <label for="productPrice">价格</label>
			                        <input type="text" name="productPrice" class="form-control" id="txt_productPrice" placeholder="价格">
			                    </div>
			                    <div class="form-group">
			                        <label for="productStock">库存</label>
			                        <input type="number" name="productStock" class="form-control" id="txt_productStock" placeholder="库存">
			                    </div>
			                    <div class="form-group">
			                        <label for="productDescription">描述</label>
			                        <input type="text" name="productDescription" class="form-control" id="txt_productDescription" placeholder="描述">
			                    </div>
			                    <div class="form-group">
			                        <label for="categoryType">类目</label>
			                        <input type="text" name="categoryType" class="form-control" id="txt_categoryType" placeholder="类目">
			                    </div>
			                    <input hidden type="text" name="productId" id="txt_productId" value="">	
				                <div class="form-group">
			                        <label >当前图片预览及图片地址</label>
			                        <img height="100" width="100" src="" alt="" id="showpic">
			                        <input style="width:460px" type="text" name="productIcon" class="form-control" id="txt_productIcon" placeholder="图片地址">
			                    </div>  
			                </form>
	                        <div style="float:right">
	                        	<span class="label label-warning">修改图片需要上传图片再保存</span>
	                        	<br>
	                        	<!-- <button type="submit" class="btn btn-default btn-primary">提交并保存</button> -->
	                        </div>
		                    <div id="uploadPicWindow" class="easyui-window" title="上传图片"  style="width:420px;height:220px;padding:20px;background:#fff;" data-options="iconCls:'icon-save',closable:true, collapsible:true,minimizable:true,maximizable:true">    
						        <form id="picForm" action="" method="post">    
						            <div id="preview"><label>上传图片预览：</label></div>  
						            <div style="margin-bottom:20px">    
						                  选择图片:    
						                <input type="file" id="file" data-options="prompt:'Choose a file...'" style="width:80%" onchange="preview(this);"/>    
						            </div>    
						            <div id="picTip"></div>    
						            <div id="formWindowfooterPic1" style="padding:5px;text-align:left;">     
						                <a href="#" onclick="submitPic();" class="easyui-linkbutton" data-options="iconCls:'icon-save'">上传图片</a>    
						            </div>    
						        </form>    
						    </div>
					    	<div class="modal-footer">
			                    <button type="button" class="btn btn-default" data-dismiss="modal"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span>关闭</button>
			                    <button type="button" id="btn_submit" class="btn btn-primary" data-dismiss="modal" ><span class="glyphicon glyphicon-floppy-disk" aria-hidden="true"></span>保存</button>
			                </div>
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
	        
	        update = function(){
	        	document.getElementById("updateForm").submit();
	        }
	      	//表单异步提交
            $("#btn_submit").on("click",function(){
                $.ajax({ 
                    type: 'post', 
                    data: $('#updateForm').serialize(), 
                    url: '${request.contextPath}/seller/product/save2',
                    cache:false,  
                    dataType:'text', 
                    success: function (data) {
                    	console.log(data);
                        if("success"==data){
                        	alert('保存成功,并将刷新当前分页');
                        	$("#table").bootstrapTable('refresh'); 
                        }else{
                        	alert('保存失败，请联系运维人员');
                        }
                    }   
                });
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
	            deleteProduct(ids);  
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
			                alert("批量上架成功，并将刷新当前分页");  
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
	        function deleteProduct(ids) {  
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
	                    title: '上架状态',
	                    formatter: function (value, row, index) {  
	                        if(row.productStatus==0){
	                        	return '在售';
	                        }else{
	                        	return '已下架';
	                        }
	                    }
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
	                	title: '修改',
	                	formatter:function(value,row,index){
		   					 var s;
		   					 s = '<button type="button" class="btn btn-info" data-toggle="modal" onclick="getEditInfo(\''+row.productId+'\',\''+row.productName+'\',\''+row.productPrice+'\',\''+row.productStock+'\',\''+row.productDescription+'\',\''+row.categoryType+'\',\''+row.productStatus+'\',\''+row.productIcon+'\');" data-target="#myModal">编辑</button>';
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
	      	
	      	//html页面调用js文件里的函数，写法必须为dosave = function (){}形式，其他方式写，html页面会搜索不到该函数
	      	getEditInfo = function(productId, productName, productPrice, productStock, productDescription, categoryType, productStatus, productIcon){
	      		$("#txt_productId").val(productId); 
	      		$("#txt_productName").val(productName);
	      		$("#txt_productPrice").val(productPrice);
	      		$("#txt_productStock").val(productStock);
	      		$("#txt_productDescription").val(productDescription);
	      		$("#txt_categoryType").val(categoryType);
	      		$("#txt_productStatus").val(productStatus);
	      		$("#txt_productIcon").val(productIcon);
	      		var obj=document.getElementById("showpic"); 
	      		obj.src = productIcon;
	      	};
	      	
	     	// 保存表单
	        check_form = function()
	        {
	            var user_id = $.trim($('#user_id').val());
	            var act     = $.trim($('#act').val());

	            if(!user_id)
	            {
	                alert('用户ID不能为空！');
	                return false;
	            }
	               var form_data = $('#form_data').serialize();

	            // 异步提交数据到action/add_action.php页面
	            $.ajax(
	                    {
	                        url: "action/user_action.php",
	                        data:{"form_data":form_data,"act":act},
	                        type: "post",
	                        beforeSend:function()
	                        {
	                            $("#tip").html("<span style='color:blue'>正在处理...</span>");
	                            return true;
	                        },
	                        success:function(data)
	                        {
	                            if(data > 0)
	                            {

	                                var msg = "添加";
	                                if(act == "edit") msg = "编辑";
	                                $("#tip").html("<span style='color:blueviolet'>恭喜，" +msg+ "成功！</span>");
	                                // document.location.href='system_notice.php'
	                                alert(msg + "OK！");
	                                location.reload();
	                            }
	                            else
	                            {
	                                $("#tip").html("<span style='color:red'>失败，请重试</span>");
	                                alert('操作失败');
	                            }
	                        },
	                        error:function()
	                        {
	                            alert('请求出错');
	                        },
	                        complete:function()
	                        {
	                            $('#acting_tips').hide();
	                        }
	                    });

	            return false;
	        }
	      	
	      	preview = function(file){    
			    var prevDiv = document.getElementById('preview');    
			    if (file.files && file.files[0]){    
			        var reader = new FileReader();    
			        reader.onload = function(evt){    
			            prevDiv.innerHTML = '<img height="100" width="100" src="' + evt.target.result + '" />';    
			        }      
			        reader.readAsDataURL(file.files[0]);    
			    }else{    
			        prevDiv.innerHTML = '<div class="img" style="filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale,src=\'' + file.value + '\'"></div>';    
			    }    
			}    
	      	
	      //新建或编辑 保存提交    
	        submitPic = function(){    
	            var type="1";//展示图片    
	            var f = $("#file").val();    
	            if(f==null||f==""){    
	                $("#picTip").html("<span style='color:Red'>错误提示:上传文件不能为空,请重新选择文件</span>");    
	                return false;    
	              }else{    
	               var extname = f.substring(f.lastIndexOf(".")+1,f.length);    
	               extname = extname.toLowerCase();//处理了大小写    
	               if(extname!= "jpeg"&&extname!= "jpg"&&extname!= "gif"&&extname!= "png"){    
	                 $("#picTip").html("<span style='color:Red'>错误提示:格式不正确,支持的图片格式为：JPEG、GIF、PNG！</span>");    
	                 return false;    
	                }    
	              }    
	             var file = document.getElementById("file").files;      
	             var size = file[0].size;    
	             if(size>2097152){    
	                  $("#picTip").html("<span style='color:Red'>错误提示:所选择的图片太大，图片大小最多支持2M!</span>");     
	                  return false;    
	              }    
	            ajaxFileUploadPic(name,type);    
	        }    
	    
	        function ajaxFileUploadPic() {    
	            $.ajaxFileUpload({    
	                url : 'imageupdate.action?type=1', //用于文件上传的服务器端请求地址    
	                secureuri : false, //一般设置为false    
	                fileElementId : 'file', //文件上传空间的id属性  <input type="file" id="file" name="file" />    
	                type : 'post',    
	                dataType : 'json', //返回值类型 一般设置为json    
	                success : function(data) //服务器成功响应处理函数    
	                {    
	                     var path = data.data.NetPath;  
	                     $("#picTip").html("<span style='color:Red'>图片上传成功!图片地址已更新</span>");
	                     $("#productIcon").val(String(path));
	                     
	                },    
	                error : function(data)//服务器响应失败处理函数    
	                {    
	                	 //alert(data.filePath);  
	                     $("#picTip").html("<span style='color:Red'>上传图片失败!</span>"); 
	                     
	                }    
	            });    
	            return false;    
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
