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
                    <table class="table table-bordered table-condensed">
                        <thead>
                        <tr>
                            <th>类目唯一识别号</th>
                            <th>大类目名称</th>
							<th>子类目名称</th>
                            <th>创建时间</th>
                            <th>修改时间</th>
                            <th>修改</th>
                            <th>删除</th>
                        </tr>
                        </thead>

                        <tbody>
                        <#list categoryList as category>
                        <tr>
                            <td>${category.categoryId}</td>
                            <td>${category.categoryName}</td>
                            <td>${category.categorySubName}</td>                        
                            <td>${category.createTime?string("yyyy-MM-dd HH:mm:ss")}</td>
                            <td>${category.updateTime?string("yyyy-MM-dd HH:mm:ss")}</td>
                            <td><a href="/sell/seller/category/index?categoryId=${category.categoryId}">修改</a></td>
                            <td><a href="/sell/seller/category/delete?categoryId=${category.categoryId}">删除</a></td>
                        </tr>
                        </#list>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>

</div>
</body>
</html>