<!DOCTYPE html>
<html>
<#include "../common/header.ftl">


<body class="fixed-sidebar full-height-layout gray-bg" style="overflow:hidden">
<div id="wrapper" class="toggled">

	<#--边栏sidebar-->
	<#include "../common/nav.ftl">
	<div id="page-content-wrapper">
		<div class="btn-group hidden-xs" id="toolbar" role="group">
			<div><p>&nbsp;&nbsp;&nbsp;产品分类编号:<br>
	        <input style="width:250px;" name="categoryName" id="categoryName" placeholder="产品大类" class="input-sm form-control"></p>
	        </div>
	        <br><br>
			<div>
			<p>&nbsp;&nbsp;&nbsp;客户订单号:<br>
	        <input style="width:250px;" name="orderId" id="orderId" placeholder="订单id" class="input-sm form-control"></p>
			</div>
	        
	        <input style="width:250px;" name="startDate" id="startDate" placeholder="起始日期" class="input-sm form-control">
	        <input style="width:250px;" name="endDate" id="endDate" placeholder="结束日期" class="input-sm form-control">
	        <select style="width:143px;height: 30px" id="valid" name="valid" class="form-control" >
	            <option value="">全部</option>
	            <option value="1">启用</option>
	            <option value="0">禁用</option>
	        </select>
	        <button id="query" type="button" class="btn btn-sm btn-primary">搜索</button>
	    </div>
    	<table class="table table-bordered table-condensed" id="table" data-height="400" data-mobile-responsive="true"></table>
	</div>
</div>

<script type="text/javascript">
    (function () {

         //初始化事件
        function initEvent() {
            //2.查询按钮事件
            $('#query').click(function () {
                $('#table').bootstrapTable('refresh', {
                    pageNumber: 1
                });
            })

            //3.重置按钮事件
            $('#clear').click(function () {
                $('#queryForm')[0].reset();
                $("#selectOption").hide();

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
                pageSize: 15,                       //每页的记录行数（*）
                pageList: [15, 25, 50, 100],        //可供选择的每页的行数（*）
                strictSearch: true,
                clickToSelect: true,                //是否启用点击选中行
                height: 650,                        //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
                uniqueId: "detailId",                     //每一行的唯一标识，一般为主键列
                cardView: false,                    //是否显示详细视图
                detailView: false,                  //是否显示父子表
                showRefresh: true,                   //刷新按钮
                showExport: true,                     //是否显示导出
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
                    title: '订单Id'
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

        $(function () {
            initTable();
            initEvent();
        });
        
    })();
</script>
</body>

</html>
