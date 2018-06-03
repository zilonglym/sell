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
                    <form role="form" method="post" action="/sell/seller/membership/classsave">
						<input type="hidden" name="scoreClass" type="number" class="form-control" value="${(membershipClass.scoreClass)!}"/>
                        <div class="form-group">
                            <label>区间积分下限</label>
                            <input name="scoreMin" type="number" class="form-control" value="${(membershipClass.scoreMin)!}" />
                        </div>
                        <div class="form-group">
                            <label>区间积分上限</label>
                            <input name="scoreMax" type="number" class="form-control" value="${(membershipClass.scoreMax)!}"/>
                        </div>
                        <div class="form-group">
                            <label>分级描述</label>
                            <input name="scoreDesc" type="text" class="form-control" value="${(membershipClass.scoreDesc)!''}"/>
                        </div>
                        <button type="submit" class="btn btn-default">提交</button>
                    </form>
                </div>
            </div>
        </div>	
    </div>

</div>
</body>
</html>