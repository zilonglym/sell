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
                <div class="col-md-9 column">
                    <table class="table table-bordered table-condensed">
                        <thead>
                        <tr>
                            <th style="display:none;">分级id</th>
                            <th>区间积分下限</th>
                            <th>区间积分上限</th>
                            <th>分级描述</th>
                            <th>修改</th>
                            <th>删除</th>
                            <th>增加</th>
                        </tr>
                        </thead>

                        <tbody>
                        <#list allClass as Class>
                        <tr>
                            <td style="display:none;">${Class.scoreClass}</td>
                            <td>${Class.scoreMin}</td>
                            <td>${Class.scoreMax}</td>
                            <td>${Class.scoreDesc}</td>
                            <td><a href="/sell/seller/membership/classindex?scoreClass=${Class.scoreClass}">修改</a></td>
                            <td><a href="/sell/seller/membership/classdelete?scoreClass=${Class.scoreClass}">删除</a></td>
                            <td><a href="/sell/seller/membership/classadd">增加</a></td>
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