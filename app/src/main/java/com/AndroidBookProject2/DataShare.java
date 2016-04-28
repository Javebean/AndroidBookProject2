package com.AndroidBookProject2;

import java.util.ArrayList;
import java.util.List;

public class DataShare {
	public static User user=new User();//保存当前登录用户的信息	
	public static List<Goods> shopList=new ArrayList<Goods>();//购物车列表
	/**
	 * 判断是否已经添加了一件该商品 返回值
	 * @param id：商品编号
	 * @return：-1：未添加过该商品，否则已添加过该商品
	 */
	public static int isExistGoods(int id){
		for(int i=0;i<shopList.size();i++){
			if(shopList.get(i).getId()==id){
				return i;
			}
		}
		return -1;
	}
	/**
	 * 获取购物车上商品的总价格
	 * @return
	 */
	public static float getCartListMoney(){
		float money=0.0f;
		for(int i=0;i<shopList.size();i++){
			money=money+shopList.get(i).getPrice()*shopList.get(i).getBuyCount();
		}
		return money;
	}
}
