# 微信点餐数据库


```sql
-- 类目
create table `product_category` (
    `category_id` int not null auto_increment,
    `category_name` varchar(64) not null comment '类目名字',
    `category_type` int not null comment '类目编号',
    `create_time` timestamp not null default current_timestamp comment '创建时间',
    `update_time` timestamp not null default current_timestamp on update current_timestamp comment '修改时间',
    primary key (`category_id`)
);

-- 商品
create table `product_info` (
    `product_id` varchar(32) not null,
    `product_name` varchar(64) not null comment '商品名称',
    `product_price` decimal(8,2) not null comment '单价',
    `product_stock` int not null comment '库存',
    `product_description` varchar(64) comment '描述',
    `product_icon` varchar(512) comment '小图',
    `product_status` tinyint(3) DEFAULT '0' COMMENT '商品状态,0正常1下架',
    `category_type` int not null comment '类目编号',
    `create_time` timestamp not null default current_timestamp comment '创建时间',
    `update_time` timestamp not null default current_timestamp on update current_timestamp comment '修改时间',
    primary key (`product_id`)
);

-- 商品会员价
create table `product_membership` (
    `product_id` varchar(32) not null,
    `product_name` varchar(64) not null comment '商品名称',
    `score_class` int not null comment '会员等级',
    `product_price` decimal(8,2) not null comment '单价',
    `create_time` timestamp not null default current_timestamp comment '创建时间',
    `update_time` timestamp not null default current_timestamp on update current_timestamp comment '修改时间',
    primary key (`product_id`)
);
-- 订单
create table `order_master` (
    `order_id` varchar(32) not null,
    `buyer_name` varchar(32) not null comment '买家名字',
    `buyer_phone` varchar(32) not null comment '买家电话',
    `buyer_address` varchar(128) not null comment '买家地址',
    `buyer_openid` varchar(64) not null comment '买家微信openid',
    `order_amount` decimal(8,2) not null comment '订单总金额',
    `order_status` tinyint(3) not null default '0' comment '订单状态, 默认为新下单',
    `pay_status` tinyint(3) not null default '0' comment '支付状态, 默认未支付',
    `create_time` timestamp not null default current_timestamp comment '创建时间',
    `update_time` timestamp not null default current_timestamp on update current_timestamp comment '修改时间',
    primary key (`order_id`),
    key `idx_buyer_openid` (`buyer_openid`)
);

-- 订单商品
create table `order_detail` (
    `detail_id` varchar(32) not null,
    `order_id` varchar(32) not null,
    `product_id` varchar(32) not null,
    `product_name` varchar(64) not null comment '商品名称',
    `product_price` decimal(8,2) not null comment '当前价格,单位分',
    `product_quantity` int not null comment '数量',
    `product_icon` varchar(512) comment '小图',
    `create_time` timestamp not null default current_timestamp comment '创建时间',
    `update_time` timestamp not null default current_timestamp on update current_timestamp comment '修改时间',
    primary key (`detail_id`),
    key `idx_order_id` (`order_id`)
);

-- 卖家(登录后台使用, 卖家登录之后可能直接采用微信扫码登录，不使用账号密码)
create table `seller_info` (
    `sell_id` varchar(32) not null,
    `username` varchar(32) not null,
    `password` varchar(32) not null,
    `openid` varchar(64) not null comment '微信openid',
    `create_time` timestamp not null default current_timestamp comment '创建时间',
    `update_time` timestamp not null default current_timestamp on update current_timestamp comment '修改时间',
    primary key (`sell_id`)
) comment '卖家信息表';

--会员表
create table `membership_info` (
    `id` varchar(32) not null,
    `username` varchar(32) not null,
    `openid` varchar(64) not null comment '微信openid',
	`purchase_amount`decimal(10,2) not null comment '累计消费金额',
	`score` decimal(10,2) not null comment '当前积分',
    `score_grade` int not null comment '会员等级',
    `create_time` timestamp not null default current_timestamp comment '创建时间',
    `update_time` timestamp not null default current_timestamp on update current_timestamp comment '修改时间',
    primary key (`openid`),
     key `idx_openid` (`openid`)
) comment '会员表';

--会员分类表
create table `membership_class` (
	`sell_id` varchar(32) not null,
    `score_class` int not null comment '会员等级',
    `score_min` int not null comment '区间起始值',
    `score_max` int not null comment '区间最大值',
    `create_time` timestamp not null default current_timestamp comment '创建时间',
    `update_time` timestamp not null default current_timestamp on update current_timestamp comment '修改时间',
    primary key (`sell_id`,`score_class`)
) comment '会员分类表';

--系统功能参数表
create table `sysparameter`(
	`parametername` varchar(32) not null,
	`status` tinyint(1) not null comment '参数开关',
	`describe` varchar(64) comment '描述',
	primary key (`parametername`)
) comment '系统功能参数表';

--积分参数表
create table `score_function`(
	`seller_name` varchar(32) not null comment '商家',
	`score_ratio` decimal(8,4) not null comment '积分兑换比例',
	`threshold` int not null comment '积分起兑基数',
	primary key (`seller_name`)
) comment '积分参数表';

--积分静态表
create table `score_static`(
	`openid` varchar(64) not null comment '买家微信openid',
	`nickname` varchar(64) not null comment '买家微信昵称',
	`purchase_amount`int not null comment '累计消费金额',
	`score` int not null comment '当前积分',
	`create_time` timestamp not null default current_timestamp comment '创建时间',
    `update_time` timestamp not null default current_timestamp on update current_timestamp comment '修改时间',
    primary key (`openid`),
    key `idx_openid` (`openid`)
) comment '积分静态表';

--积分流水表
create table `score_detail`(
	`openid` varchar(64) not null comment '买家微信openid',
	`nickname` varchar(64) not null comment '买家微信昵称',
	`buz_type` int not null comment '业务代码',
	`purchase_amount`int not null comment '消费金额',
	`score` int not null comment '积分',
	`order_id` varchar(32) not null comment 'master订单号',
	`create_time` timestamp not null default current_timestamp comment '创建时间',
    `update_time` timestamp not null default current_timestamp on update current_timestamp comment '修改时间',
    primary key (`openid`),
    key `idx_openid` (`openid`)
) comment '份额流水表';
```

