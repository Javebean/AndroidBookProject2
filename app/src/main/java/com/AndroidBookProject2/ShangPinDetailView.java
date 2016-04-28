package com.AndroidBookProject2;

import java.net.URL;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextPaint;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ShangPinDetailView extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.shangpindetailview);//加载布局资源文件
		getGoodDetail();//获取商品详细信息
	}

	/**
	 * 获取商品详细信息
	 */
	public void getGoodDetail() {
		Bundle bundle = this.getIntent().getExtras();//获取Bundle
		final Goods theGood = (Goods) bundle.getSerializable("GoodObj");//获取Bundle中的商品对象
		// 显示详情信息		
		TextView goodsName = (TextView) this.findViewById(R.id.goodsName);//获取资源文件中的TextView
		goodsName.setText(theGood.getBrand());//将商品名称显示在TextView
		//商品图片
		ImageView goodsPic = (ImageView) this.findViewById(R.id.goodsPic);
		try {
			URL picUrl = new URL(theGood.getDir() + "/" + theGood.getPic());
			Bitmap pngBM = BitmapFactory.decodeStream(picUrl.openStream());
			goodsPic.setImageBitmap(pngBM);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 商品编号
		TextView goodsNum = (TextView) this.findViewById(R.id.goodsNum);
		goodsNum.setText("商品编号:" + theGood.getGid());
		// 价格
		TextView goodsPrice = (TextView) this.findViewById(R.id.goodsPrice);
		goodsPrice.setText("价       格:" + "￥" + theGood.getPrice().toString());
		// 人气
		TextView goodsDcount = (TextView) this.findViewById(R.id.goodsDcount);
		goodsDcount.setText("人       气:" + theGood.getBcount().toString());
		// 折扣
		TextView goodsDiscount = (TextView) this
				.findViewById(R.id.goodsDiscount);
		goodsDiscount.setText("折       扣:" + theGood.getDiscount().toString());
		// 详情
		TextView descTip = (TextView) this.findViewById(R.id.descTip);
		TextPaint textPaint = descTip.getPaint();
		textPaint.setFakeBoldText(true);
		TextView goodsDes = (TextView) this.findViewById(R.id.goodsDes);
		goodsDes.setText(theGood.getDes());
		// 放入购物车按钮
		Button addToCard = (Button) this.findViewById(R.id.addToCard);
		addToCard.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				Log.i("把商品加入到"+DataShare.user.getUid()+"的购物车：",theGood.toString());
				new ConnectWeb().addCart(theGood.getBrand(), DataShare.user.getUid(),
						theGood.getPic(), theGood.getPrice(), theGood.getGid());

				Log.i("加入购物车的图片路径" +theGood.getDir() + "  图片名称：", theGood.getPic());
				Toast.makeText(ShangPinDetailView.this, "放入购物车成功",
						Toast.LENGTH_LONG).show();
				// TODO Auto-generated method stub
				/*int index = DataShare.isExistGoods(theGood.getId());
				if (index != -1) {// 已添加过该商品
					Log.i("已添加过该商品",theGood.toString());
					DataShare.shopList.get(index).setBuyCount(
							DataShare.shopList.get(index).getBuyCount() + 1);
				} else {
					Log.i("增加商品到购物车",theGood.toString());
					theGood.setBuyCount(1);
					DataShare.shopList.add(theGood);
				}*/
			}
		});
	}

	// 菜单
	public boolean onCreateOptionsMenu(Menu menu) {

		MenuItem mnuxq = menu.add(0, 0, 0, "购物车");
		mnuxq.setIcon(R.drawable.cart);
		return super.onCreateOptionsMenu(menu);
	}

	// 菜单响应事件
	public boolean onOptionsItemSelected(MenuItem item) {

		super.onOptionsItemSelected(item);
		switch (item.getItemId()) {
			case 0:
				Intent it = new Intent();
				it.setClass(ShangPinDetailView.this, CartListView.class);
				startActivity(it);
				break;
		}
		return true;
	}
}
