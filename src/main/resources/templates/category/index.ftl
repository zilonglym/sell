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
                    <form role="form" method="post" action="/sell/seller/category/save">
                        <div class="form-group">
                            <label>大类目名称</label>
                            <input name="categoryName" type="text" class="form-control" value="${(category.categoryName)!''}"/>
                        </div>
                        <div hidden class="form-group">
                            <label>大类目类型</label>
                            <input name="categoryType" type="number" class="form-control" value="0"/>
                        </div>
                        <div class="form-group">
                            <label>子类目名称</label>
                            <input name="categorySubName" type="text" class="form-control" value="${(category.categorySubName)!''}"/>
                        </div>
                        <div hidden class="form-group">
                            <label>子类目类型</label>
                            <input name="categorySubType" type="number" class="form-control" value="0"/>
                        </div>
                        <input hidden type="text" name="categoryId" value="${(category.categoryId)!''}">
                        <button type="submit" class="btn btn-default">提交</button>
                    </form>
                </div>
            </div>
        </div>
    </div>

</div>
</body>
</html>