<html>
    <#include "../common/header.ftl">
<style> 
.aaa1{ float:left} 
</style>
    <body>
        <div id="wrapper" class="toggled">
            <#--边栏sidebar-->
            <#include "../common/nav.ftl">

            <#--主要内容content-->
            <div id="page-content-wrapper2">
            <div  class="aaa1">
	            <p >&nbsp;&nbsp;&nbsp;姓名(模糊搜索):<br>
		        <input style="width:250px;" name="buyerName" id="buyerName" class="input-sm form-control"></p>
            </div >
            <div class="aaa1"> <p >&nbsp;&nbsp;&nbsp;</p></div>
            <div class="aaa1">
	            <p >&nbsp;&nbsp;&nbsp;电话(模糊搜索):<br>
		        <input style="width:250px;" name="buyerPhone" id="buyerPhone" class="input-sm form-control"></p>
            </div>
	        
			<div class="btn-group hidden-xs" id="toolbar" role="group">
				<div class="container">
					<div class="row clearfix">
						<div class="col-md-12 column">
							 <button id="query" type="button" class="btn btn-sm btn-primary">搜索</button>
							 <span width:60px;></span>
							 <button id="clear" type="button" class="btn btn-sm btn-primary">重置</button>
						</div>
					</div>
				</div>
		    </div>
		    <div style="height: 500px;overlow: auto;" class="table-responsive">
		    	<table class="table table-bordered table-condensed" id="table" data-mobile-responsive="true"></table>
		    	<thread></thread>
		    	<tbody></tbody>
		    </div>
		</div>
		
            </div>
            
            
        </div>

        <#-- 弹窗 -->
        <div class="modal fade" id="myModal" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                        <h4 class="modal-title" id="myModalLabel">
                            提醒
                        </h4>
                    </div>
                    <div class="modal-body">
                        你有新的订单
                    </div>
                    <div class="modal-footer">
                        <button onclick="javascript:document.getElementById('notice').pause()" type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                        <button onclick="location.reload()" type="button" class="btn btn-primary">查看新的订单</button>
                    </div>
                </div>
            </div>
        </div>

        <#-- 播放音乐 -->
        <audio id="notice" loop="loop">
            <source src="/sell/mp3/song.mp3" type="audio/mpeg" />
        </audio>

        <script src="${request.contextPath}/bootstrap/bootstrap.min.js"></script>
        <script>
            var websocket = null;
            if('WebSocket' in window) {
                websocket = new WebSocket('ws://111.230.47.102:8080/sell/webSocket');
            } else {
                alert('该浏览器不支持websocket!');
            }

            websocket.onopen = function (event) {
                console.log('建立连接');
            }

            websocket.onclose = function (event) {
                console.log('连接关闭');
            }

            websocket.onmessage = function (event) {
                console.log('收到消息:' + event.data)
                // 弹窗提醒, 播放音乐
                $('#myModal').modal('show');

                document.getElementById('notice').play();
            }

            websocket.onerror = function () {
                alert('websocket通信发生错误！');
            }

            window.onbeforeunload = function () {
                websocket.close();
            }
        </script>
        
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
	                document.getElementById("buyerName").value="";
	                document.getElementById("buyerPhone").value="";
	            });
	        }; 
	
	        //得到查询的参数
	        function queryParams(params) {
	            var temp = {
	                limit: params.limit,    //页面大小
	                offset: params.offset,   //页码
	                buyerName: $('#buyerName').val(),
	                buyerPhone: $('#buyerPhone').val(),
	            };
	            return temp;
	        };
	        
	        function initTable() {
	            $('#table').bootstrapTable({
	                url: '${request.contextPath}/seller/order/getlist',    //请求后台的URL（*）
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
	                    field: 'orderId',
	                    sortable: true,
	                    sortOrder: "asc",
	                    title: '订单id' 
	                },{
	                    field: 'buyerName',
	                    sortable: true,
	                    sortOrder: "asc",
	                    title: '姓名'
	                },  {
	                    field: 'buyerPhone',
	                    sortable: true,
	                    sortOrder: "asc",
	                    title: '手机号'
	                },{
	                    field: 'buyerAddress',
	                    sortable: true,
	                    sortOrder: "asc",
	                    title: '地址'
	                }, {
	                    field: 'orderAmount',
	                    sortable: true,
	                    sortOrder: "asc",
	                    title: '金额'
	                },{
	                    field: 'orderDeliveryAmount',
	                    sortable: true,
	                    sortOrder: "asc",
	                    title: '快递费'
	                },{
	                    field: 'buyerComment',
	                    sortable: true,
	                    sortOrder: "asc",
	                    title: '买家留言'
	                },{
	                    field: 'orderStatus',
	                    sortable: true,
	                    sortOrder: "asc",
	                    title: '订单状态',
	                    formatter: function (value, row, index) {  
	                        if(row.orderStatus==0){
	                        	return '未完结';
	                        }else{
	                        	return '已完结';
	                        }
	                    }
	                },{
	                    field: 'payStatus',
	                    sortable: true,
	                    sortOrder: "asc",
	                    title: '支付状态',
	                    formatter: function (value, row, index) {  
	                        if(row.payStatus==0){
	                        	return '未支付';
	                        }else{
	                        	return '已支付';
	                        }
	                    }
	                },{
	                    field: 'createTime',
	                    sortable: true,
	                    sortOrder: "asc",
	                    title: '创建时间',
	                  	//——修改——获取日期列的值进行转换  
	                    formatter: function (value, row, index) {  
	                        return changeDateFormat(value)  
	                    } 
	                },{
	                	title: '详情',
	                	formatter:function(value,row,index){
		   					 var s;
		   					 s = '<a href="/sell/seller/order/detail?orderId='+row.orderId+'">详情</a>';
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
	                var hour = date.getHours() < 10 ? "0" + date.getHours() : date.getHours();
	                var Minute = date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes();
	                var Second = date.getSeconds() < 10 ? "0" + date.getSeconds() : date.getSeconds();
	                return date.getFullYear() + "-" + month + "-" + currentDate +"   "+  hour + ":" + Minute + ":" + Second;
	            }  
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
