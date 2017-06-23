package com.zslin.web.model;

import javax.persistence.*;

/**
 * Created by 钟述林 393156105@qq.com on 2017/6/23 10:44.
 */
@Entity
@Table(name = "t_order_picture")
public class OrderPicture {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    /** 订单Id */
    @Column(name = "order_id")
    private Integer orderId;

    /** 订单编号 */
    @Column(name = "order_no")
    private String orderNo;

    /** 图片地址 */
    @Column(name = "pic_path")
    private String picPath;

    /** 级别，主要用于排序 */
    private Integer level;

    /** 名称，用于显示该图片属于哪个面 */
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
