package com.AndroidBookProject2;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.SimpleAdapter.ViewBinder;

public class CartListView extends Activity {
	private LinearLayout myListLayout;//声明LinearLayout类型变量
	private ListView tripListView;//声明ListView类型变量

	public static Map<String,String> selectGoods = null;
	public static float selectGoodsPrice = 0;
	public static List<String> selectGoodsId = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.viewcartlist);//加载布局资源文件
		myListLayout = (LinearLayout) this.findViewById(R.id.cartList);//获取资源文件中LinearLayout
		tripListView = new ListView(this);//创建ListView
		//创建布局参数
		LinearLayout.LayoutParams tripListViewParam = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		tripListView.setCacheColorHint(Color.parseColor("#FFFFF9EB"));
		myListLayout.addView(tripListView, tripListViewParam);//将ListView添加到LinearLayout上


		selectGoods = new HashMap<>();
		selectGoodsId = new ArrayList<>();
		showGoodsList();//获取商品列表
		//onLongClickItem(tripListView);//添加长按监听
		Button accountsBut = (Button) this.findViewById(R.id.accountsBut);//加载资源文件中的Button
		accountsBut.setOnClickListener(new OnClickListener() {//Button的单击事件

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// 判断用户是否登录				
				if (DataShare.user.getUid().equals("")) {
					Intent intent = new Intent();
					intent.setClass(CartListView.this, ViewLogin.class);
					startActivity(intent);
					Toast.makeText(CartListView.this, "请先登录", Toast.LENGTH_LONG).show();
				}else if(selectGoodsId.size()==0){
					Toast.makeText(CartListView.this, "您还没有选择商品", Toast.LENGTH_LONG).show();
				}else {
					Intent intent = new Intent();
					intent.setClass(CartListView.this, BillDetailView.class);
					startActivity(intent);
				}
			}

		});
	}



	// 填充商品列表适配器
	public void showGoodsList() {
		SimpleAdapter adapter = new SimpleAdapter(this, getTripList(),
				R.layout.cartlistrow, new String[] { "img", "num", "name",
				"price","selected" }, new int[] { R.id.cartImg, R.id.cartNum,
				R.id.cartName, R.id.cartPrice, R.id.checkBox1 }){

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				View view = super.getView(position, convertView, parent);

				//得到商品item
				final Map<String, Object> goods = (Map<String, Object>)this.getItem(position);
				final CheckBox cb = (CheckBox)view.findViewById(R.id.checkBox1);
				Log.i("checkbox添加监听", "位置为" + position + "的checkbox.");

				cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
						if (b) {
							selectGoods.put(goods.get("num2").toString(), goods.get("count").toString());
							selectGoodsPrice+=Float.parseFloat(goods.get("price2").toString());
							selectGoodsId.add(goods.get("db_id").toString());
						} else {
							selectGoods.remove(goods.get("num2").toString());
							selectGoodsPrice-=Float.parseFloat(goods.get("price2").toString());
							selectGoodsId.remove(goods.get("db_id").toString());
						}
					}
				});



				return view;
			}
		  };

		tripListView.setAdapter(adapter);
		adapter.setViewBinder(new ViewBinder() {
			public boolean setViewValue(View arg0, Object arg1,
										String textRepresentation) {
				if ((arg0 instanceof ImageView) & (arg1 instanceof Bitmap)) {
					ImageView imageView = (ImageView) arg0;
					Bitmap bitmap = (Bitmap) arg1;
					imageView.setImageBitmap(bitmap);
					return true;
				} else {
					return false;
				}

			}
		});
	}


	public List<Map<String, Object>> getTripList() {

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();


		List<Goods> goodsList = new ConnectWeb().getCartByUsername(DataShare.user.getUid());


		for (int i = 0; i < goodsList.size(); i += 1) {
			Map<String, Object> map = new HashMap<String, Object>();
			Goods goods = goodsList.get(i);
			try {
				URL picUrl = new URL(ConnectWeb.path+"img/"+goods.getPic());
				Bitmap pngBM = BitmapFactory.decodeStream(picUrl.openStream());
				map.put("img", pngBM);
			} catch (Exception e) {
				e.printStackTrace();
			}

			map.put("num", "商品编号：" + goods.getGid());
			map.put("name", "商品名称：" + goods.getBrand());
			map.put("price", "￥" + goods.getPrice()+"  数量："+goods.getBuyCount());
			map.put("count",goods.getBuyCount());
			map.put("num2", goods.getGid());
			map.put("price2",goods.getPrice());
			map.put("db_id",goods.getId());
			map.put("selected", goods.isSelected());

			list.add(map);
		}

		return list;
	}





}
