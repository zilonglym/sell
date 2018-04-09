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
                <div class="col-md-6 column">
                    <table class="table table-bordered table-condensed">
                        <thead>
                        <tr>
                            <th>描述</th>
                            <th>开关状态</th>
                            <th>阈值</th>
                            <th>积分兑换现金比例</th>
                            <th>操作</th>
                        </tr>
                        </thead>

                        <tbody>
                        <#list scoreFunctionList as scoreFunction>
                        <tr>
                            <td>${scoreFunction.describeInfo}</td>
                            <td>${scoreFunction.getScoreStatusEnum().message}</td>
                            <td>${scoreFunction.threshold}</td>
                            <td>${scoreFunction.scoreRatio}</td>
                            <td><a href="/sell/seller/membership/stausindex?score=zhihe">修改</a></td>
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