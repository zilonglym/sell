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
                    <form role="form" method="post" action="/sell/seller/membership/save">
                    	<div class="form-group" contenteditable="false">
                            <label>店家简称</label>		
                            <input name="sellerName" type="text" class="form-control" value="${(scoreFunction.sellerName)!}'zhihe'" disabled="disabled"/>
                        </div>
                        <div class="form-group">
                            <label>描述</label>
                            <input name="describeInfo" type="text" class="form-control" value="${(scoreFunction.describeInfo)!}" />
                        </div>
                        <div class="form-group">
                            <label>开关状态(0-关，1-开)</label>
                            <input name="scoreStatus" type="number" class="form-control" value="${(scoreFunction.scoreStatus)!''}"/>
                        </div>
                        <div class="form-group">
                            <label>积分起兑的阈值</label>
                            <input name="threshold" type="number" class="form-control" value="${(scoreFunction.threshold)!''}"/>
                        </div>
                        <div class="form-group">
                            <label>积分兑换现金比例</label>
                            <input name="scoreRatio" type="text" class="form-control" value="${(scoreFunction.scoreRatio)!''}" onkeyup= "if( ! /^d*(?:.d{0,2})?$/.test(this.value)){alert('只能输入数字，小数点后只能保留两位');this.value='0.05';}" />
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