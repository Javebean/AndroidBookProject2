package com.AndroidBookProject2;

import java.io.Serializable;

public class Goods implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private Integer id;  //数据库编号
    private String brand;//名称
    private Float price;//价格
    private Float discount;//折扣
    private Integer bcount;//购买次数
    private String des;//描述
    private String pic;//图片名称
    private String dir;//图片路径
    private String gid;//商品编号
    private Integer type;//商品类型
    private Integer pop;//是否推荐
    private Integer buyCount;//当前用户对商品实际购买次数

    private boolean selected;

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Float getDiscount() {
        return discount;
    }

    public void setDiscount(Float discount) {
        this.discount = discount;
    }

    public Integer getBcount() {
        return bcount;
    }

    public void setBcount(Integer bcount) {
        this.bcount = bcount;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getPop() {
        return pop;
    }

    public void setPop(Integer pop) {
        this.pop = pop;
    }

    public Integer getBuyCount() {
        return buyCount;
    }

    public void setBuyCount(Integer buyCount) {
        this.buyCount = buyCount;
    }

    @Override
    public String toString() {
        return "Goods{" +
                "id=" + id +
                ", brand='" + brand + '\'' +
                ", price=" + price +
                ", discount=" + discount +
                ", bcount=" + bcount +
                ", des='" + des + '\'' +
                ", pic='" + pic + '\'' +
                ", dir='" + dir + '\'' +
                ", gid='" + gid + '\'' +
                ", type=" + type +
                ", pop=" + pop +
                ", buyCount=" + buyCount +
                '}';
    }
}
