package com.AndroidBookProject2;

import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SimpleAdapter.ViewBinder;

public class ViewJiaDian extends Activity {
	private LinearLayout myListLayout; //声明LinearLayout类型变量
	private ListView tripListView;//声明ListView类型变量
	private ProgressDialog myDialog;//声明ProgressDialog类型变量
	private List<Goods> goodsList;//声明List类型变量

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.viewjiadian);//加载viewtuijian.xml资源文件
		myListLayout = (LinearLayout) this.findViewById(R.id.tripList);// 获取资源文件中的LinearLayout

		tripListView = new ListView(this);//创建ListView对象
		//创建LinearLayout.LayoutParams类型对象
		LinearLayout.LayoutParams tripListViewParam = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.FILL_PARENT);
		tripListView.setCacheColorHint(Color.WHITE);
		myListLayout.addView(tripListView, tripListViewParam);//将tripListView添加到myListLayout布局上
		getGoodsList();//读取商品列表

		tripListView.setOnItemClickListener(new OnItemClickListener() {//tripListView列表项单击事件

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
				// TODO Auto-generated method stub
				Goods theGood=goodsList.get(position);// 获取当前列表项选中的商品
				Intent it = new Intent();//创建Intent对象
				Bundle bundle = new Bundle();//创建Bundle对象
				it.setClass(ViewJiaDian.this, ShangPinDetailView.class);
				bundle.putSerializable("GoodObj", (Serializable) theGood);
				it.putExtras(bundle);
				startActivity(it);
			}
		});
	}

	// 读取商品列表数据
	private void getGoodsList() {
		myDialog = ProgressDialog.show(ViewJiaDian.this, "请稍等...", "数据检索中...", true);
		new Thread() {
			public void run() {
				try {
					goodsList = new ConnectWeb().getTypeList(1);//获取家用电器类别商品列表，1表示家用电器
					Message m = new Message();
					listHandler.sendMessage(m);
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					myDialog.dismiss();
				}
			}
		}.start();
	}

	Handler listHandler = new Handler() {
		public void handleMessage(Message msg) {
			if (goodsList.size() == 0) {
				return;
			}
			showGoodsList();//填充路书列表适配器
		}
	};

	/**
	 * 填充路书列表适配器
	 */
	public void showGoodsList() {
		SimpleAdapter adapter = new SimpleAdapter(this, getTripList(),
				R.layout.tuijianrow, new String[] { "img", "name", "money",
				"zhe" }, new int[] { R.id.tripImg, R.id.tripTitle,
				R.id.tripSegName, R.id.tripProv });
		tripListView.setAdapter(adapter);//为tripListView添加适配器adapter
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
		for (int i = 0; i < goodsList.size(); i += 1) {
			Map<String, Object> map = new HashMap<String, Object>();
			Goods goods = goodsList.get(i);

			try {
				URL picUrl = new URL(goods.getDir() + "/" + goods.getPic());
				Bitmap pngBM = BitmapFactory.decodeStream(picUrl.openStream());
				map.put("img", pngBM);
			} catch (Exception e) {
				e.printStackTrace();
			}

			map.put("name", "商品名称："+goods.getBrand());
			map.put("money", "商品价格："+"￥" + goods.getPrice());
			map.put("zhe", "商品折扣："+goods.getDiscount());

			list.add(map);
		}

		return list;
	}
}

