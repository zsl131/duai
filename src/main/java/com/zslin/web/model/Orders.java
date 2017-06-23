package com.zslin.web.model;

import com.zslin.web.vo.BaseEntity;

import javax.persistence.*;

/**
 * Created by 钟述林 393156105@qq.com on 2017/6/23 10:21.
 */
@Entity
@Table(name = "t_orders")
public class Orders extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    /** 订单编号 */
    private String no;

    /** 客户来源，1-网络；2-线下 */
    @Column(name = "customer_source")
    private String customerSource;

    /** 是否加急，1-是；0-否 */
    @Column(name = "is_urgent")
    private String isUrgent;

    /** 客户姓名 */
    @Column(name = "customer_name")
    private String customerName;

    /** 客户性别 */
    @Column(name = "customer_sex")
    private String customerSex;

    /** 客户手机号码 */
    @Column(name = "customer_phone")
    private String customerPhone;

    /** 收件人地址 */
    @Column(name = "customer_address")
    private String customerAddress;

    /** 快递公司 */
    @Column(name = "express_name")
    private String expressName;

    /** 快递单号 */
    @Column(name = "express_no")
    private String expressNo;

    /** 产品名称 */
    @Column(name = "product_name")
    private String productName;

    /** 产品编号 */
    @Column(name = "product_no")
    private String productNo;

    /** 商品单价 */
    private Float price;

    /** 商品数量 */
    private Integer amount;

    /** 内容类型，照片、文字、图标，以二进制形式表示，如：101表示：有照片和图标，无文字 */
    @Column(name = "content_type")
    private String contentType;

    /** 文字数量 */
    @Column(name = "word_amount")
    private Integer wordAmount;

    /** 图片数量 */
    @Column(name = "pic_amount")
    private Integer picAmount;

    /** 图标数量 */
    @Column(name = "icon_amount")
    private Integer iconAmount;

    /** 字体要求 */
    @Column(name = "font_demand")
    private String fontDemand;

    /** 销售员姓名 */
    @Column(name = "saler_name")
    private String salerName;

    @Column(name = "saler_openid")
    private String salerOpenid;

    @Column(name = "saler_phone")
    private String salerPhone;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getCustomerSource() {
        return customerSource;
    }

    public void setCustomerSource(String customerSource) {
        this.customerSource = customerSource;
    }

    public String getIsUrgent() {
        return isUrgent;
    }

    public void setIsUrgent(String isUrgent) {
        this.isUrgent = isUrgent;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerSex() {
        return customerSex;
    }

    public void setCustomerSex(String customerSex) {
        this.customerSex = customerSex;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getExpressName() {
        return expressName;
    }

    public void setExpressName(String expressName) {
        this.expressName = expressName;
    }

    public String getExpressNo() {
        return expressNo;
    }

    public void setExpressNo(String expressNo) {
        this.expressNo = expressNo;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Integer getWordAmount() {
        return wordAmount;
    }

    public void setWordAmount(Integer wordAmount) {
        this.wordAmount = wordAmount;
    }

    public Integer getPicAmount() {
        return picAmount;
    }

    public void setPicAmount(Integer picAmount) {
        this.picAmount = picAmount;
    }

    public Integer getIconAmount() {
        return iconAmount;
    }

    public void setIconAmount(Integer iconAmount) {
        this.iconAmount = iconAmount;
    }

    public String getFontDemand() {
        return fontDemand;
    }

    public void setFontDemand(String fontDemand) {
        this.fontDemand = fontDemand;
    }

    public String getSalerName() {
        return salerName;
    }

    public void setSalerName(String salerName) {
        this.salerName = salerName;
    }

    public String getSalerOpenid() {
        return salerOpenid;
    }

    public void setSalerOpenid(String salerOpenid) {
        this.salerOpenid = salerOpenid;
    }

    public String getSalerPhone() {
        return salerPhone;
    }

    public void setSalerPhone(String salerPhone) {
        this.salerPhone = salerPhone;
    }
}
