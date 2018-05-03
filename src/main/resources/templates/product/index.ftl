<html>
<#include "../common/header.ftl">

<body>
<div id="wrapper" class="toggled">

<#--边栏sidebar-->
<#include "../common/nav.ftl">

<#--主要内容content-->
    <div id="page-content-wrapper">
        <div class="container-fluid">
            <div class="row clearfix">
                <div class="col-md-12 column">
                    <form role="form" method="post" action="/sell/seller/product/save">
                        <div class="form-group">
                            <label>名称</label>
                            <input name="productName" type="text" class="form-control" value="${(productInfo.productName)!''}"/>
                        </div>
                        <div class="form-group">
                            <label>价格</label>
                            <input name="productPrice" type="text" class="form-control" value="${(productInfo.productPrice)!''}"/>
                        </div>
                        <div class="form-group">
                            <label>库存</label>
                            <input name="productStock" type="number" class="form-control" value="${(productInfo.productStock)!''}"/>
                        </div>
                        <div class="form-group">
                            <label>描述</label>
                            <input name="productDescription" type="text" class="form-control" value="${(productInfo.productDescription)!''}"/>
                        </div>

                        
                        <div class="form-group">
                            <label>类目</label>
                            <select name="categoryType" class="form-control">
                                <#list categoryList as category>
                                    <option value="${category.categoryId}"
                                            <#if (productInfo.categoryType)?? && productInfo.categoryType == category.categoryId>
                                                selected
                                            </#if>
                                        >${category.categoryName}-${category.categorySubName}
                                    </option>
                                </#list>
                            </select>
                        </div>
						<div class="form-group">
                            <label>当前图片预览及图片地址</label>
                            <img height="100" width="100" src="${(productInfo.productIcon)!''}" alt="">
                            <input id="productIcon" name="productIcon" type="text" class="form-control" value="${(productInfo.productIcon)!''}"/>
                        </div>
                        <input hidden type="text" name="productId" value="${(productInfo.productId)!''}">
                        <div style="float:right">
                        	<span class="label label-warning">提交前请核对图文信息</span>
                        	<br>
                        	<button type="submit" class="btn btn-default btn-primary">提交并保存</button>
                        </div>
                    </form>
                </div>
                <div id="uploadPicWindow" class="easyui-window" title="上传图片"  style="width:420px;height:220px;padding:20px;background:#fff;" data-options="iconCls:'icon-save',closable:true, collapsible:true,minimizable:true,maximizable:true">    
					        <form id="picForm" action="" method="post">    
					            <div id="preview"><label>上传图片预览：</label></div>  
					            <div style="margin-bottom:20px">    
					                  选择图片:    
					                <input type="file" name="file" id="file" data-options="prompt:'Choose a file...'" style="width:80%" onchange="preview(this);"/>    
					            </div>    
					            <div id="picTip"></div>    
					            <div id="formWindowfooterPic1" style="padding:5px;text-align:left;">     
					                <a href="#" onclick="submitPic();" class="easyui-linkbutton" data-options="iconCls:'icon-save'">上传图片</a>    
					            </div>    
					        </form>    
					    </div>
            </div>
        </div>
    </div>

</div>

	<script type="text/javascript">      
		function preview(file){    
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
	</script>   
    <script>  
        //新建或编辑 保存提交    
        function submitPic(){    
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
	</script>  

</body>
</html>