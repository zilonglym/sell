<nav class="navbar navbar-inverse navbar-fixed-top" id="sidebar-wrapper" role="navigation">
    <ul class="nav sidebar-nav">
        <li class="sidebar-brand">
            <a href="/sell/seller/order/list2">
                	后台管理(主界面)
            </a>
        </li>
<!--         <li>
            <a href="/sell/seller/order/list2"><i class="fa fa-fw fa-list-alt"></i> 订单</a>
        </li> -->
        
        
        <li class="dropdown open">
            <a class="dropdown-toggle" data-toggle="dropdown" aria-expanded="true"><i class="fa fa-fw fa-plus"></i> 商品 <span class="caret"></span></a>
            <ul class="dropdown-menu" role="menu">
                <li class="dropdown-header">操作</li>
                <li><a href="/sell/seller/product/list2"> ->列表</a></li>
                <li><a href="/sell/seller/product/index"> ->新增</a></li>
            </ul>
        </li>
        <li class="dropdown open">
            <a class="dropdown-toggle" data-toggle="dropdown" aria-expanded="true"><i class="fa fa-fw fa-plus"></i> 类目 <span class="caret"></span></a>
            <ul class="dropdown-menu" role="menu">
                <li class="dropdown-header">操作</li>
                <li><a href="/sell/seller/category/list"> ->列表</a></li>
                <li><a href="/sell/seller/category/index"> ->新增</a></li>
            </ul>
        </li>
        
<!--         <li>
            <a href="/sell/seller/order/flow/index"><i class="fa fa-fw fa-list-alt"></i> 流水账单</a>
        </li> -->
        
		<li class="dropdown open">
            <a class="dropdown-toggle" data-toggle="dropdown" aria-expanded="true"><i class="fa fa-fw fa-plus"></i> 会员 <span class="caret"></span></a>
            <ul class="dropdown-menu" role="menu">
                <li class="dropdown-header">操作</li>
                <li><a href="/sell/seller/membership/scorefunctionlist"> ->积分功能开关</a></li>
                <li><a href="/sell/seller/membership/classlist"> ->会员设置</a></li>
                <li><a href="/sell/seller/membership/membershiperlist"> ->会员积分及消费额查询</a></li>
            </ul>
        </li>
        
        <li class="dropdown">
            <a class="dropdown-toggle" id="dropdownMenu1" data-toggle="dropdown"><i class="fa fa-fw fa-plus"></i> 订单及流水 <span class="caret"></span></a>
            <ul class="dropdown-menu">
                <li class="dropdown-header">操作</li>
                <li><a href="/sell/seller/order/list2"> ->订单</a></li>
                <li><a href="/sell/seller/order/flow/index"> ->流水账单</a></li>
            </ul>
        </li>
        
    </ul>
</nav>

<script>
$(function() {
    $(".dropdown-toggle").dropdown('toggle');
});
</script>