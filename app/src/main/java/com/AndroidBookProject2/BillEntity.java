package com.AndroidBookProject2;

import java.util.ArrayList;
import java.util.List;

public class BillEntity {
	private Integer id=0;//数据库编号
	private String state="";//订单状态
	private String btime="";//送货时间
	private String btype="";//付款方式
	private String ctime="";//订单时间
	private List<GoodsListEntity> glist=new ArrayList<GoodsListEntity>();//商品列表
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getBtime() {
		return btime;
	}
	public void setBtime(String btime) {
		this.btime = btime;
	}
	public String getBtype() {
		return btype;
	}
	public void setBtype(String btype) {
		this.btype = btype;
	}
	public String getCtime() {
		return ctime;
	}
	public void setCtime(String ctime) {
		this.ctime = ctime;
	}
	public List<GoodsListEntity> getGlist() {
		return glist;
	}
	public void setGlist(List<GoodsListEntity> glist) {
		this.glist = glist;
	}
}
